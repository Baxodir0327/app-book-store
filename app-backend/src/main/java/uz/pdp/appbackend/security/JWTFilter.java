package uz.pdp.appbackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.appbackend.entity.User;
import uz.pdp.appbackend.service.AuthServiceImpl;
import uz.pdp.appbackend.utils.AppConstants;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;
    private final AuthServiceImpl authServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            if (authorization.startsWith(AppConstants.BEARER_TYPE))
                setAuthenticationBearer(request, authorization);
            else if (authorization.startsWith(AppConstants.BASIC_TYPE))
                setAuthenticationBasic(request, authorization);

        }
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationBasic(HttpServletRequest request, String authorization) {
        authorization = authorization.substring(AppConstants.BASIC_TYPE.length()).trim();
        String[] split = new String(Base64.getDecoder().decode(authorization)).split(":", 2);
        UserPrincipal principal = authServiceImpl.checkCredential(split[0], split[1]);
        setAuthentication(request, principal);
    }

    private void setAuthenticationBearer(HttpServletRequest request, String authorization) {
        String userId = jwtProvider.extractUserId(authorization.substring(AppConstants.BEARER_TYPE.length()).trim());

        Optional<User> optionalUser = authServiceImpl.findUserById(userId);
        optionalUser.ifPresent(user -> {
            UserPrincipal principal = new UserPrincipal(user);
            if (principal.allOk())
                setAuthentication(request, principal);
        });
    }

    private void setAuthentication(HttpServletRequest request, UserPrincipal principal) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        principal.getAuthorities()
                );

        //TODO sesion based + token based auth qilsak kerak bo'ladi
        WebAuthenticationDetails details = new WebAuthenticationDetails(request);
        authentication.setDetails(details);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
