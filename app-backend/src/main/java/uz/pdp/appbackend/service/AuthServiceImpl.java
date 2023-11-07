package uz.pdp.appbackend.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appbackend.entity.User;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.exceptions.RestException;
import uz.pdp.appbackend.payload.LoginDTO;
import uz.pdp.appbackend.payload.TokenDTO;
import uz.pdp.appbackend.repository.UserRepository;
import uz.pdp.appbackend.security.JWTProvider;
import uz.pdp.appbackend.security.UserPrincipal;
import uz.pdp.appbackend.utils.AppConstants;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepository,
                           @Lazy AuthenticationManager authenticationManager,
                           JWTProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipal(userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
    }

    @Override
    public Optional<User> findUserById(String userId) {
        if (userId == null)
            return Optional.empty();
        return userRepository.findById(UUID.fromString(userId));
    }

    @Override
    public ApiResult<TokenDTO> login(LoginDTO loginDTO) {


        UserPrincipal principal = checkCredential(loginDTO.username(), loginDTO.password());

        TokenDTO tokenDTO = generateTokenDTO(principal);

        return ApiResult.successResponse(tokenDTO);
    }


    @Override
    public ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        if (!accessToken.startsWith(AppConstants.BEARER_TYPE))
            throw RestException.restThrow("Bearer emas");

        accessToken = accessToken.substring(AppConstants.BEARER_TYPE.length()).trim();
        if (!jwtProvider.isExpired(accessToken))
            throw RestException.restThrow("Token muddati tugamagan");

        if (!jwtProvider.validRefreshToken(refreshToken))
            throw RestException.restThrow("Refresh token valid emas");

        String userId = jwtProvider.extractUserIdFromRefreshToken(refreshToken);
        User user = findUserById(userId).orElseThrow(() -> RestException.restThrow("User not found: " + userId, HttpStatus.NOT_FOUND));

        TokenDTO tokenDTO = generateTokenDTO(new UserPrincipal(user));

        return ApiResult.successResponse(tokenDTO);
    }

    private TokenDTO generateTokenDTO(UserPrincipal principal) {
        String id = principal.user().getId().toString();
        String accessToken = jwtProvider.createAccessToken(id);
        String refreshToken = jwtProvider.createRefreshAccessToken(id);
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public UserPrincipal checkCredential(String username, String password) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                password
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return (UserPrincipal) authentication.getPrincipal();
    }
}
