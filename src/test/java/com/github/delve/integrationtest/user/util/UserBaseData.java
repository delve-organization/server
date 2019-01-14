package com.github.delve.integrationtest.user.util;

import com.github.delve.dev.UserTestData;
import com.github.delve.integrationtest.util.basedata.BaseData;
import com.github.delve.security.repository.UserRepository;
import com.github.delve.security.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserBaseData implements BaseData {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void load() {
        UserTestData.createTestData(userService);
    }

    @Override
    public void unload() {
        userRepository.deleteAll();
    }

    @Override
    public Class<? extends BaseData> dependsOn() {
        return RoleBaseData.class;
    }
}
