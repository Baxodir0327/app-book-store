package uz.pdp.appbackend.compenents;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.appbackend.config.PropertiesToCustomClassConfig;
import uz.pdp.appbackend.entity.User;
import uz.pdp.appbackend.enums.RoleEnum;
import uz.pdp.appbackend.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PropertiesToCustomClassConfig properties;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlMode;

    @Override
    public void run(String... args) throws Exception {
        if (ddlMode.equals("create")) {
            User user = properties.getUser();
            user.setRole(RoleEnum.ROLE_SUPER_ADMIN);
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }
}
