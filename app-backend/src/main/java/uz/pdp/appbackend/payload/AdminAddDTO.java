package uz.pdp.appbackend.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AdminAddDTO(@NotNull @Email String email) {
}
