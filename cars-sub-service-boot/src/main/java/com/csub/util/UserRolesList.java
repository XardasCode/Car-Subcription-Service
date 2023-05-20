package com.csub.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRolesList {
    MANAGER(1),
    USER(2);

    private final int roleId;


    public static UserRolesList getRoleById(int id) {
        for (UserRolesList role : UserRolesList.values()) {
            if (role.getRoleId() == id) {
                return role;
            }
        }
        return null;
    }

    public static UserRolesList getRoleByName(String name) {
        for (UserRolesList role : UserRolesList.values()) {
            if (role.name().equals(name)) {
                return role;
            }
        }
        return null;
    }
}
