package uz.pdp.appbackend.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import uz.pdp.appbackend.entity.User;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.LoginDTO;
import uz.pdp.appbackend.payload.TokenDTO;

import java.util.Optional;

public interface AuthService extends UserDetailsService {

    Optional<User> findUserById(String userId);

    ApiResult<TokenDTO> login(LoginDTO loginDTO);

    ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken);
}
