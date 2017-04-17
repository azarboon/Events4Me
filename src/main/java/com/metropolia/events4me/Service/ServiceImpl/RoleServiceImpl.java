package com.metropolia.events4me.Service.ServiceImpl;

import com.metropolia.events4me.DAO.RoleDAO;
import com.metropolia.events4me.Model.security.Role;
import com.metropolia.events4me.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dmitry on 17.04.2017.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private RoleDAO roleDAO;

    @Autowired
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public Role saveOrUpdateRole(Role role) {
        return roleDAO.save(role);
    }

    @Override
    public List<Role> listRoles() {
        return roleDAO.findAll();
    }
}
