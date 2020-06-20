package com.jukusoft.engine2d.core.permission;

import com.carrotsearch.hppc.ObjectByteHashMap;
import com.carrotsearch.hppc.ObjectByteMap;

public class PermissionManager {

    private static final PermissionManager instance = new PermissionManager();

    private ObjectByteMap<String> permissions = new ObjectByteHashMap<>(20);

    protected PermissionManager() {
        //
    }

    public boolean hasPermission(String token) {
        return this.permissions.containsKey(token);
    }

    public void addPermission(String permission) {
        this.permissions.addTo(permission, (byte) 0x01);
    }

    public void removePermission(String permission) {
        this.permissions.remove(permission);
    }

    public static PermissionManager getInstance() {
        return instance;
    }

}
