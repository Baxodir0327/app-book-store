package uz.pdp.appbackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.*;

import java.util.UUID;

@RequestMapping(BookController.BASE_PATH)
public interface BookController {

    String BASE_PATH = "/api/book";
    String CHANGE_STATUS_PATH = "/change-status/{id}";
    String LIST_FOR_ADMIN_PATH = "/for-admin";
    String LIST_FOR_USER_PATH = "/for-user";

    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @PostMapping
    HttpEntity<ApiResult<BookDTO>> add(@Valid @RequestBody BookAddDTO bookAddDTO);

    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    HttpEntity<ApiResult<BookDTO>> edit(@PathVariable UUID id, @Valid @RequestBody BookAddDTO bookAddDTO);

    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @PatchMapping(CHANGE_STATUS_PATH)
    HttpEntity<ApiResult<BookDTO>> changeStatus(@PathVariable UUID id);

    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    HttpEntity<ApiResult<String>> remove(@PathVariable UUID id);

    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @PostMapping(LIST_FOR_ADMIN_PATH)
    HttpEntity<ApiResult<PaginationDTO<BookDTO>>> forAdmin(@RequestBody MainCriteriaDTO mainCriteriaDTO);

    @PostMapping(LIST_FOR_USER_PATH)
    HttpEntity<ApiResult<PaginationDTO<BookDTO>>> forUser(@RequestBody MainCriteriaDTO mainCriteriaDTO);
}
