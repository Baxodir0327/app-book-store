package uz.pdp.appbackend.payload;

import uz.pdp.appbackend.enums.RoleEnum;

import java.util.UUID;

public record UserDTO(UUID id, String name, String email, RoleEnum role) {
}
