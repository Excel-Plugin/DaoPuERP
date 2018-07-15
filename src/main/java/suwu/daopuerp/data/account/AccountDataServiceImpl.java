package suwu.daopuerp.data.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suwu.daopuerp.dao.UserDao;
import suwu.daopuerp.dataservice.account.AccountDataService;
import suwu.daopuerp.entity.User;
import suwu.daopuerp.exception.UserDoesNotExistException;

@Service
public class AccountDataServiceImpl implements AccountDataService {
    private final UserDao userDao;

    @Autowired
    public AccountDataServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * get user by username
     *
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) throws UserDoesNotExistException {
        User user = userDao.findUserByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UserDoesNotExistException();
        }
    }
}
