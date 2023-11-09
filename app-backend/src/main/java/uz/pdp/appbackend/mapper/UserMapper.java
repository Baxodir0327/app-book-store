package uz.pdp.appbackend.mapper;

import org.mapstruct.Mapper;
import uz.pdp.appbackend.entity.User;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);
}
