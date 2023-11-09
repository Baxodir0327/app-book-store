package uz.pdp.appbackend.service;

import org.springframework.stereotype.Service;
import uz.pdp.appbackend.entity.User;
import uz.pdp.appbackend.enums.RoleEnum;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.mapper.UserMapper;
import uz.pdp.appbackend.payload.AdminAddDTO;
import uz.pdp.appbackend.payload.ChangePasswordDTO;
import uz.pdp.appbackend.payload.PaginationDTO;
import uz.pdp.appbackend.payload.UserDTO;
import uz.pdp.appbackend.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public record UserServiceImpl(UserRepository userRepository,
                              UserMapper userMapper) implements UserService {

    @Override
    public ApiResult<UserDTO> createAdmin(AdminAddDTO adminAddDTO) {
        //todo emailga sendgrid orqali yuboringlar

        Optional<User> optionalUser = userRepository.findByEmail(adminAddDTO.email());

        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            user.setRole(RoleEnum.ROLE_ADMIN);
            //todo email info uchun
        } else {
            user = User.builder()
                    .email(adminAddDTO.email())
                    .name(adminAddDTO.email())
                    .role(RoleEnum.ROLE_ADMIN)
                    .build();
            //todo email link(id, 2 ta parol, ism)
        }

        userRepository.save(user);

        return ApiResult.successResponse(userMapper.toUserDTO(user));
    }

    @Override
    public ApiResult<String> deleteAdmin(UUID id) {
        //todo set user role
        return null;
    }

    @Override
    public ApiResult<PaginationDTO<UserDTO>> listAdmin(int page, int size) {
        //todo findAllByRole(ADMIN,pageable);
        return null;
    }

    @Override
    public ApiResult<PaginationDTO<UserDTO>> listUser(int page, int size) {
        //todo findAllByRole(USER,pageable);
        return null;
    }

    @Override
    public ApiResult<String> changePassword(ChangePasswordDTO changePasswordDTO) {
        //todo check old password match in db password
//        UserPrincipal userPrincipal = CommonUtils.getCurrentUserIfNullThrow();

        return null;
    }
}
