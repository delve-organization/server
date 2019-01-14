package com.github.delve.integrationtest.user.util;

import com.github.delve.dev.RoleTestData;
import com.github.delve.integrationtest.util.basedata.BaseData;
import com.github.delve.security.repository.RoleRepository;
import com.github.delve.security.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleBaseData implements BaseData {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void load() {
        RoleTestData.createTestData(roleService);
    }

    @Override
    public void unload() {
        roleRepository.deleteAll();
    }
}
