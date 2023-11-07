package uz.pdp.appbackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import uz.pdp.appbackend.entity.User;

@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class PropertiesToCustomClassConfig {

    private User user;


}
