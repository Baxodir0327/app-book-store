package uz.pdp.appbackend.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import uz.pdp.appbackend.utils.AppConstants;

import java.util.UUID;

public record ConfirmAdminDTO(@NotNull UUID id,
                              @NotBlank @Pattern(regexp = AppConstants.PASSWORD_REGEXP) String password,
                              @NotBlank @Pattern(regexp = AppConstants.PASSWORD_REGEXP) String prePassword,
                              @NotBlank String name) {
}
