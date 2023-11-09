package uz.pdp.appbackend.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.*;

import java.util.UUID;

@RequestMapping(PaymentController.BASE_PATH)
public interface PaymentController {

    String BASE_PATH = "/api/payment";
    String ALL_PATH = "/all";
    String MY_PATH = "/my";


    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @PostMapping(ALL_PATH)
    HttpEntity<ApiResult<PaginationDTO<PaymentDTO>>> all(@RequestBody MainCriteriaDTO mainCriteriaDTO);


    @PostMapping(MY_PATH)
    HttpEntity<ApiResult<PaginationDTO<PaymentDTO>>> my(@RequestBody MainCriteriaDTO mainCriteriaDTO);
}
