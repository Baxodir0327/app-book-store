package uz.pdp.appbackend.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.pdp.appbackend.controller.AttachmentController;
import uz.pdp.appbackend.entity.Attachment;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.exceptions.RestException;
import uz.pdp.appbackend.payload.AttachmentDTO;
import uz.pdp.appbackend.repository.AttachmentRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.UUID;

@Service
public record AttachmentServiceImpl(AttachmentRepository attachmentRepository,
                                    Environment environment) implements AttachmentService {

    @Override
    public ApiResult<AttachmentDTO> uploadFile(MultipartHttpServletRequest request) {
        MultipartFile file = getMultipartFileFromRequest(request);

        Attachment attachment = mapAttachment(file);

        Path path = Paths.get(attachment.getPath());

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw RestException.restThrow(e.getMessage());
        }

        attachmentRepository.save(attachment);
        return ApiResult.successResponse(mapAttachmentDTO(attachment));
    }

    @Override
    public ResponseEntity<?> downloadFile(UUID id, String view, HttpServletResponse response) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> RestException.restThrow(""));
        try {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(attachment.getContentType()))
                    .headers(httpHeaders -> httpHeaders.set("Content-Disposition", view + "; filename=" + attachment.getOriginalName()))
                    .body(Files.readAllBytes(Path.of(attachment.getPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MultipartFile getMultipartFileFromRequest(MultipartHttpServletRequest request) {

        Iterator<String> fileNames = request.getFileNames();

        if (!fileNames.hasNext())
            throw RestException.restThrow("FILE_NAME_REQUIRED", HttpStatus.BAD_REQUEST);

        MultipartFile file = request.getFile(fileNames.next());

        if (file == null)
            throw RestException.restThrow("FILE NOT FOUND");

        return file;
    }

    private Attachment mapAttachment(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();

        //BU METHOD SYSTEMAGA(PAPKA ICHIGA) FILE UN UNIQUE NAME YASAB BERADI
        assert originalFilename != null;
        String contentType = file.getContentType();
        long size = file.getSize();
        String path = collectFolder(createFileName(originalFilename));

        Attachment attachment = new Attachment();
        attachment.setContentType(contentType);
        attachment.setOriginalName(originalFilename);
        attachment.setSize(size);
        attachment.setPath(path);

        return attachment;
    }

    private String createFileName(String originalFilename) {
        String name = UUID.randomUUID().toString();
        String[] split = originalFilename.split("\\.");
        String contentType = split[split.length - 1];
        name = name + "." + contentType;
        return name;
    }

    private String collectFolder(String uuidName) {
        LocalDate localDate = LocalDate.now();
        String folderPath =
                environment.getProperty("app.upload.folder")
                        + "/" + localDate.getYear() + "/" + localDate.getMonthValue() + "/" + localDate.getDayOfMonth();
        new File(folderPath).mkdirs();
        return folderPath + "/" + uuidName;
    }

    private AttachmentDTO mapAttachmentDTO(Attachment attachment) {
        return AttachmentDTO.builder()
                .id(attachment.getId())
                .originalName(attachment.getOriginalName())
                .url(ServletUriComponentsBuilder
                        .fromCurrentContextPath().path(AttachmentController.BASE_PATH)
                        .path(attachment.getId().toString()).toUriString())
                .build();
    }

}
