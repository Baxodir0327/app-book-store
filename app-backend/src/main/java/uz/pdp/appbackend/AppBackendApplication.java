package uz.pdp.appbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uz.pdp.appbackend.config.PropertiesToCustomClassConfig;

@SpringBootApplication
@EnableConfigurationProperties(value = {PropertiesToCustomClassConfig.class})
public class AppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppBackendApplication.class, args);
    }

}
