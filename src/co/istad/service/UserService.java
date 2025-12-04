package co.istad.service;

import co.istad.dao.UserDao;
import co.istad.dao.impl.UserFileDao;
import co.istad.entity.User;

import java.util.List;

public class UserService {
    final UserDao userDao = new UserFileDao();
    public List<User> selectAllUser() {
        return userDao.selectAll();
    }
    public void insertUser(User user) {
        userDao.insert(user);
    }


}
