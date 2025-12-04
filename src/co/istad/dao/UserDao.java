package co.istad.dao;

import co.istad.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> selectByUsername(String username);
    User searchById(Integer id);
    List<User> selectAll();
    void insert(User user);
}
