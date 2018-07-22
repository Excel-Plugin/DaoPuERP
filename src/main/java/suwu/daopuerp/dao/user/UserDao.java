package suwu.daopuerp.dao.user;

import suwu.daopuerp.entity.account.User;

public interface UserDao {
    User save(User user);

    User findUserByUsername(String username);
}

