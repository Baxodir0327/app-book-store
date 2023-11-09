package uz.pdp.appbackend.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.UUID;

public record BookDTO(UUID id,
                      String title,
                      String author,
                      boolean status,
                      int pageCount,
                      double price,
                      String readSampleUrl,
                      List<String> photosUrl) {
}
