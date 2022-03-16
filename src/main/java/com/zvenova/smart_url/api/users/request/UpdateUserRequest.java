package com.zvenova.smart_url.api.users.request;

import com.zvenova.smart_url.domain.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private Long id;

    private String username;

    private String password;
    
    private Boolean isActive;
    
    private Set<Role> role;
}
