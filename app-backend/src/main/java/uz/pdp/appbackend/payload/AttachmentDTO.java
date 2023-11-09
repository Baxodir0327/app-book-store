package uz.pdp.appbackend.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDTO {

    private UUID id;

    private String originalName;

    private String url;
}

