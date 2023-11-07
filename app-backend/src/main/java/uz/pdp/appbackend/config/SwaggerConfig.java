package uz.pdp.appbackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Component;

@SecuritySchemes(value = {
        @SecurityScheme(
                name = "bearerAuth",
                description = "Tokenjon",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                scheme = "bearer",
                in = SecuritySchemeIn.HEADER
        ),
        @SecurityScheme(
                name = "basicAuth",
                description = "sadsa",
                type = SecuritySchemeType.HTTP,
                scheme = "basic",
                in = SecuritySchemeIn.HEADER
        )
})
@OpenAPIDefinition(
        info = @Info(title = "Test swagger",
                description = "B29 bilan",
                contact = @Contact(
                        url = "https://ketmon.uz",
                        email = "ketmon@ketmon.uz",
                        extensions = {@Extension(name = "extension test",
                                properties = {@ExtensionProperty(name = "FirstExtension",
                                        value = "Value extension")})}
                )),
        servers = {
                @Server(url = "http://localhost:8080", description = "Localhost"),
                @Server(url = "ketmon.uz", description = "Bla"),
                @Server(url = "bolta.uz", description = "Battar"),
        },
        security = {
                @SecurityRequirement(name = "bearerAuth"),
                @SecurityRequirement(name = "basicAuth"),
        }
)
@Component
public class SwaggerConfig {
}
