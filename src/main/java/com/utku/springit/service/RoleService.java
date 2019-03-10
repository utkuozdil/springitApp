package com.utku.springit.service;

import com.utku.springit.domain.Role;
import com.utku.springit.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private final RoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
