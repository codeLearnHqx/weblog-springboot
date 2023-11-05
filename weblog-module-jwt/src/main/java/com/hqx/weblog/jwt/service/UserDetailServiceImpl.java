package com.hqx.weblog.jwt.service;

import com.hqx.weblog.common.domain.dos.UserDO;
import com.hqx.weblog.common.domain.dos.UserRoleDO;
import com.hqx.weblog.common.domain.mapper.UserMapper;
import com.hqx.weblog.common.domain.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中查询
        UserDO userDO = userMapper.findByUsername(username);
        // 判断用户是否存在
        if (Objects.isNull(userDO)) {
            throw new UsernameNotFoundException("该用户不存在");
        }

        // 用户角色
        List<UserRoleDO> roleDOS = userRoleMapper.selectByUsername(username);

        String[] roleArr = null;
        // 转数组
        if (!CollectionUtils.isEmpty(roleDOS)) {
            roleArr = roleDOS.stream().map(UserRoleDO::getRole).toArray(String[]::new);
        }

        // authorities 用于指定角色
        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities(roleArr)
                .build();
    }
}