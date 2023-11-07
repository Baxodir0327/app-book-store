package uz.pdp.appbackend.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static uz.pdp.appbackend.enums.PageEnum.*;

@AllArgsConstructor
public enum RoleEnum {
    ROLE_USER(new LinkedHashSet<>(Arrays.asList(BOOKS, PAYMENTS, ORDERS, CHANGE_PASSWORD))),
    ROLE_ADMIN(new LinkedHashSet<>(Arrays.asList(BOOKS, PAYMENTS, ORDERS, ALL_BOOKS, ALL_PAYMENTS, ALL_ORDERS, USERS, CHANGE_PASSWORD))),
    ROLE_SUPER_ADMIN(new LinkedHashSet<>(Arrays.asList(BOOKS, PAYMENTS, ORDERS, USERS, ADMINS, CHANGE_PASSWORD)));

    private Set<PageEnum> pages;
}
