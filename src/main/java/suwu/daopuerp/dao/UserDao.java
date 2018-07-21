package suwu.daopuerp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import suwu.daopuerp.entity.account.User;

public interface UserDao extends JpaRepository<User, String> {
    User findUserByUsername(String username);
}

