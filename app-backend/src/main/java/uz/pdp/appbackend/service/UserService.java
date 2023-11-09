package uz.pdp.appbackend.service;

import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.AdminAddDTO;
import uz.pdp.appbackend.payload.ChangePasswordDTO;
import uz.pdp.appbackend.payload.PaginationDTO;
import uz.pdp.appbackend.payload.UserDTO;

import java.util.UUID;

public interface UserService {
    ApiResult<UserDTO> createAdmin(AdminAddDTO adminAddDTO);

    ApiResult<String> deleteAdmin(UUID id);

    ApiResult<PaginationDTO<UserDTO>> listAdmin(int page, int size);

    ApiResult<PaginationDTO<UserDTO>> listUser(int page, int size);

    ApiResult<String> changePassword(ChangePasswordDTO changePasswordDTO);

}
