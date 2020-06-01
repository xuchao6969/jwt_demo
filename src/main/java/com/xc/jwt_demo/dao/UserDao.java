package com.xc.jwt_demo.dao;

import org.apache.ibatis.annotations.Mapper;
import com.xc.jwt_demo.entity.User;
import java.util.Map;

@Mapper
public interface UserDao {
        User getUserByUserName(String username);

        void addToken2User(Map<String, Object> map);
}
