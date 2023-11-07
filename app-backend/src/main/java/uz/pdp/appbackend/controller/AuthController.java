package uz.pdp.appbackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.LoginDTO;
import uz.pdp.appbackend.payload.TokenDTO;
import uz.pdp.appbackend.utils.AppConstants;

@RequestMapping(AuthController.BASE_PATH)
public interface AuthController {

    String BASE_PATH = "/api/auth";
    String LOGIN_PATH = "/login";

    String REFRESH_TOKEN_PATH = "/refresh-token";

    @PostMapping(LOGIN_PATH)
    HttpEntity<ApiResult<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO);

    @PatchMapping(REFRESH_TOKEN_PATH)
    HttpEntity<ApiResult<TokenDTO>> refreshToken(
            @RequestHeader(AppConstants.AUTH_HEADER) String accessToken,
            @RequestHeader(AppConstants.REFRESH_AUTH_HEADER) String refreshToken
    );


}
