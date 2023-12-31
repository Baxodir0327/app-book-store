package uz.pdp.appbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.appbackend.entity.template.AbsUUIDEntity;
import uz.pdp.appbackend.enums.RoleEnum;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbsUUIDEntity {

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    private String name;

    @Builder.Default
    private boolean enabled = false;

    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;
}
