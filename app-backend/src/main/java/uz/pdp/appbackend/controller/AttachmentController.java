package uz.pdp.appbackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.AttachmentDTO;

import java.util.UUID;

@RequestMapping(value = AttachmentController.BASE_PATH)
public interface AttachmentController {

    String BASE_PATH = "/api/attachment/";
    String UPLOAD_PATH = "upload";

    @PostMapping(UPLOAD_PATH)
    ApiResult<AttachmentDTO> uploadFile(MultipartHttpServletRequest request);


    @GetMapping("{id}")
    ResponseEntity<?> downloadFile(@PathVariable UUID id,
                                   @RequestParam(defaultValue = "inline") String view,
                                   HttpServletResponse response);
}
