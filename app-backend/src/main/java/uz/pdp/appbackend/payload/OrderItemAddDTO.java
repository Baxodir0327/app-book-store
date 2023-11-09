package uz.pdp.appbackend.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record OrderItemAddDTO(@NotNull UUID bookId,
                              @Positive int count) {
}
