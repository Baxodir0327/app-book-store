package uz.pdp.appbackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.ConfirmAdminDTO;
import uz.pdp.appbackend.payload.LoginDTO;
import uz.pdp.appbackend.payload.TokenDTO;
import uz.pdp.appbackend.service.AuthService;

@RestController
public record AuthControllerImpl(AuthService authService) implements AuthController {

    @Override
    public HttpEntity<ApiResult<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        ApiResult<TokenDTO> result = authService.login(loginDTO);
        return ResponseEntity.ok(result);
    }

    @Override
    public HttpEntity<ApiResult<TokenDTO>> refreshToken(String accessToken, String refreshToken) {
        ApiResult<TokenDTO> result = authService.refreshToken(accessToken, refreshToken);
        return ResponseEntity.ok(result);
    }

    @Override
    public HttpEntity<ApiResult<TokenDTO>> confirmForAdmin(ConfirmAdminDTO confirmAdminDTO) {
        ApiResult<TokenDTO> result = authService.confirmAdmin(confirmAdminDTO);
        return ResponseEntity.ok(result);
    }
}
