package uz.pdp.appbackend.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.AdminAddDTO;
import uz.pdp.appbackend.payload.ChangePasswordDTO;
import uz.pdp.appbackend.payload.PaginationDTO;
import uz.pdp.appbackend.payload.UserDTO;

import java.util.UUID;

@RequestMapping(UserController.BASE_PATH)
public interface UserController {

    String BASE_PATH = "/api/user";
    String ADD_ADMIN_PATH = "/add-admin";
    String REVOKE_ADMIN_PATH = "/remove-admin/{id}";
    String LIST_ADMINS_PATH = "/admin-list";
    String LIST_USERS_PATH = "/user-list";
    String CHANGE_PASSWORD_PATH = "/change-password";

    @PostMapping(ADD_ADMIN_PATH)
    HttpEntity<ApiResult<UserDTO>> addAdmin(@Valid @RequestBody AdminAddDTO adminAddDTO);

    @PatchMapping(REVOKE_ADMIN_PATH)
    HttpEntity<ApiResult<String>> removeAdmin(@PathVariable UUID id);

    @GetMapping(LIST_ADMINS_PATH)
    HttpEntity<ApiResult<PaginationDTO<UserDTO>>> listAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @GetMapping(LIST_USERS_PATH)
    HttpEntity<ApiResult<PaginationDTO<UserDTO>>> listUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortType,
            @RequestParam(defaultValue = "name") String sort
    );

    @PatchMapping(CHANGE_PASSWORD_PATH)
    HttpEntity<ApiResult<String>> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO);



}
