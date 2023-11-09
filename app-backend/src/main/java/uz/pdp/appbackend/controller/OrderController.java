package uz.pdp.appbackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.appbackend.exceptions.ApiResult;
import uz.pdp.appbackend.payload.*;

import java.util.List;

@RequestMapping(OrderController.BASE_PATH)
public interface OrderController {
    String BASE_PATH = "/api/order";
    String ADD_ORDER_PATH = "/add";
    String LIST_FOR_ADMIN_PATH = "/for-admin";
    String LIST_FOR_USER_PATH = "/for-user";

    @PostMapping(ADD_ORDER_PATH)
    HttpEntity<OrderDTO> createOrder(@Valid @RequestBody List<OrderItemAddDTO> orderItems);

    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @PostMapping(LIST_FOR_ADMIN_PATH)
    HttpEntity<ApiResult<PaginationDTO<OrderDTO>>> forAdmin(@RequestBody MainCriteriaDTO mainCriteriaDTO);

    @PostMapping(LIST_FOR_USER_PATH)
    HttpEntity<ApiResult<PaginationDTO<OrderDTO>>> forUser(@RequestBody MainCriteriaDTO mainCriteriaDTO);


}
