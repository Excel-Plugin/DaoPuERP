package suwu.daopuerp.daoimpl.user;

import org.junit.Test;
import suwu.daopuerp.dao.factory.UserDaoFactory;
import suwu.daopuerp.dao.user.UserDao;
import suwu.daopuerp.entity.account.User;

public class UserDaoImplTest {
    UserDao userDao = UserDaoFactory.getUserDao();

    @Test
    public void save() {
        userDao.save(new User("admin", "57578971"));
    }
}