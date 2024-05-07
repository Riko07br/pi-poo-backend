package com.monza96.backend.domain.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum ProjectAuthority {
    CREATOR(1),
    ADMIN(2),
    ORGANIZER(3),
    USER(4),
    VIEWER(5);

    final private static Map<Integer, ProjectAuthority> map = new HashMap<>();
    @Getter
    final private int value;

    static {
        for (ProjectAuthority val : ProjectAuthority.values()) {
            map.put(val.getValue(), val);
        }
    }

    ProjectAuthority(int value) {
        this.value = value;
    }

    public static ProjectAuthority valueOf(int value) {
        if (map.containsKey(value))
            return map.get(value);
        throw new IllegalArgumentException("Invalid value for Authority");
    }
}
