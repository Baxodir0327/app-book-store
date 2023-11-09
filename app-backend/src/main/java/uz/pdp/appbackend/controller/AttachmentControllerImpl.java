package uz.pdp.appbackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.AttachmentDTO;
import uz.pdp.appbackend.service.AttachmentService;

import java.util.UUID;

@RestController
public record AttachmentControllerImpl(AttachmentService attachmentService) implements AttachmentController {


    @Override
    public ApiResult<AttachmentDTO> uploadFile(MultipartHttpServletRequest request) {
        return attachmentService.uploadFile(request);
    }

    @Override
    public ResponseEntity<?> downloadFile(UUID id, String view, HttpServletResponse response) {
        return attachmentService.downloadFile(id, view, response);
    }
}
