package uz.pdp.appbackend.payload;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.*;
import uz.pdp.appbackend.entity.Attachment;

import java.util.List;
import java.util.UUID;

public record BookAddDTO(@NotBlank String title,
                         @NotBlank String author,
                         boolean status,
                         @Positive int pageCount,
                         @PositiveOrZero double price,
                         @NotNull UUID readSampleId,
                         List<UUID> photoIds) {
}
