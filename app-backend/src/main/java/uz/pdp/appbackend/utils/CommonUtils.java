package uz.pdp.appbackend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.pdp.appbackend.exceptions.RestException;
import uz.pdp.appbackend.payload.MainCriteriaDTO;
import uz.pdp.appbackend.security.UserPrincipal;

import java.util.Optional;

public class CommonUtils {
    public static Optional<UserPrincipal> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser"))
            return Optional.empty();
        return Optional.of((UserPrincipal) authentication.getPrincipal());
    }

    public static UserPrincipal getCurrentUserIfNullThrow() {
        return getCurrentUser().orElseThrow(() -> RestException.restThrow(
                "User not authenticated",
                HttpStatus.UNAUTHORIZED));
    }

}
