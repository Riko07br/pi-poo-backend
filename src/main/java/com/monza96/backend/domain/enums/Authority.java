package com.monza96.backend.domain.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum Authority {
    SUPER_ADMIN(1),
    PROJECT_CREATOR(2),
    PROJECT_ADMIN(3),
    PROJECT_ORG(4),
    PROJECT_USER(5),
    PROJECT_VIEWER(6);

    final private static Map<Integer, Authority> map = new HashMap<>();
    @Getter
    final private int value;

    static {
        for (Authority val : Authority.values()) {
            map.put(val.getValue(), val);
        }
    }

    Authority(int value) {
        this.value = value;
    }

    public static Authority valueOf(int value) {
        if (map.containsKey(value))
            return map.get(value);
        throw new IllegalArgumentException("Invalid value for Authority");
    }
}
