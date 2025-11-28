package co.istad.dao;

import co.istad.entity.User;

import java.util.Optional;

public interface UserDao {
        Optional<User> selectByUsername(String username);
    }
