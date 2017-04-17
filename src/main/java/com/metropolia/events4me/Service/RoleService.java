package com.metropolia.events4me.Service;


import com.metropolia.events4me.Model.security.Role;

import java.util.List;

/**
 * Created by Dmitry on 09.01.2017.
 */

public interface RoleService {

    Role saveOrUpdateRole(Role role);

    List<Role> listRoles();
}
