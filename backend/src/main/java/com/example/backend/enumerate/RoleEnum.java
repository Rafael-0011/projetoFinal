package com.example.backend.enumerate;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.example.backend.enumerate.PermissionEnum.ADMIN_CREATE;
import static com.example.backend.enumerate.PermissionEnum.ADMIN_DELETE;
import static com.example.backend.enumerate.PermissionEnum.ADMIN_READ;
import static com.example.backend.enumerate.PermissionEnum.ADMIN_UPDATE;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {

        USER(Collections.emptySet()),
        ADMIN(
                Set.of(
                        ADMIN_READ,
                        ADMIN_UPDATE,
                        ADMIN_DELETE,
                        ADMIN_CREATE
                )
  )              ;


        private final Set<PermissionEnum> permissions;

        public List<SimpleGrantedAuthority> getAuthorities() {
                var authorities = getPermissions()
                        .stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getPermissionEnum()))
                        .collect(Collectors.toList());
                authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
                return authorities;
        }
}