package uz.pdp.appbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.AdminAddDTO;
import uz.pdp.appbackend.payload.ChangePasswordDTO;
import uz.pdp.appbackend.payload.PaginationDTO;
import uz.pdp.appbackend.payload.UserDTO;
import uz.pdp.appbackend.service.UserService;

import java.util.UUID;

@RestController
@Slf4j
public record UserControllerImpl(UserService userService) implements UserController {

    @Override
    public HttpEntity<ApiResult<UserDTO>> addAdmin(AdminAddDTO adminAddDTO) {
        log.info("Add admin method entered: {}", adminAddDTO);
        ApiResult<UserDTO> result = userService.createAdmin(adminAddDTO);
        log.info("Add admin method exited: {}", result);
        return ResponseEntity.status(201).body(result);
    }

    @Override
    public HttpEntity<ApiResult<String>> removeAdmin(UUID id) {
        log.info("Remove admin method entered: {}", id);
        ApiResult<String> result = userService.deleteAdmin(id);
        log.info("Remove admin method exited: {}", result);
        return ResponseEntity.status(202).body(result);
    }

    @Override
    public HttpEntity<ApiResult<PaginationDTO<UserDTO>>> listAdmin(int page, int size) {
        log.info("List admin method entered: page: {}, size: {}", page, size);
        ApiResult<PaginationDTO<UserDTO>> result = userService.listAdmin(page, size);
        return ResponseEntity.ok(result);
    }

    @Override
    public HttpEntity<ApiResult<PaginationDTO<UserDTO>>> listUser(int page, int size, String search, Sort.Direction sortType, String sort) {
        log.info("List user method entered: page: {}, size: {}", page, size);
        ApiResult<PaginationDTO<UserDTO>> result = userService.listUser(page, size);
        return ResponseEntity.ok(result);
    }

    @Override
    public HttpEntity<ApiResult<String>> changePassword(ChangePasswordDTO changePasswordDTO) {
        log.info("Change password method entered: {}", changePasswordDTO);
        ApiResult<String> result = userService.changePassword(changePasswordDTO);
        return ResponseEntity.ok(result);
    }
}
