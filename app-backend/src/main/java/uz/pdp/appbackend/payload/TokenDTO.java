package uz.pdp.appbackend.payload;

import lombok.Builder;
import lombok.Data;
import uz.pdp.appbackend.utils.AppConstants;

@Data
@Builder
public class TokenDTO {

    private String accessToken;

    private String refreshToken;

    @Builder.Default
    private final String tokenType = AppConstants.BEARER_TYPE;
}
