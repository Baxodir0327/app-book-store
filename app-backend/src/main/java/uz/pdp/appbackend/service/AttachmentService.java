package uz.pdp.appbackend.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.AttachmentDTO;

import java.util.UUID;

public interface AttachmentService {
    ApiResult<AttachmentDTO> uploadFile(MultipartHttpServletRequest request);

    ResponseEntity<?> downloadFile(UUID id, String view, HttpServletResponse response);
}
