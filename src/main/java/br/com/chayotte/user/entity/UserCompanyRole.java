package br.com.chayotte.user.entity;

import lombok.Getter;

@Getter
public enum UserCompanyRole {
    OWNER("owner"),
    OPERATOR("operator");

    private final String roleName;

    UserCompanyRole(String roleName) {
        this.roleName = roleName;
    }
}
