package uz.pdp.appbackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.LoginDTO;
import uz.pdp.appbackend.payload.TokenDTO;
import uz.pdp.appbackend.service.AuthService;
import uz.pdp.appbackend.service.AuthServiceImpl;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

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
}
