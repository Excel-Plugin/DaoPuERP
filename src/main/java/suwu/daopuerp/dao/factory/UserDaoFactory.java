package suwu.daopuerp.dao.factory;

import suwu.daopuerp.dao.user.UserDao;
import suwu.daopuerp.daoimpl.user.UserDaoImpl;

public class UserDaoFactory {
    private static UserDao userDao = new UserDaoImpl();

    public static UserDao getUserDao() {
        return userDao;
    }
}
