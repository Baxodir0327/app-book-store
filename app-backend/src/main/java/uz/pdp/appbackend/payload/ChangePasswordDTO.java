package uz.pdp.appbackend.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import uz.pdp.appbackend.utils.AppConstants;

public record ChangePasswordDTO(
        @NotBlank @Pattern(regexp = AppConstants.PASSWORD_REGEXP) String oldPassword,
        @NotBlank @Pattern(regexp = AppConstants.PASSWORD_REGEXP) String password,
        @NotBlank @Pattern(regexp = AppConstants.PASSWORD_REGEXP) String prePassword) {
}
