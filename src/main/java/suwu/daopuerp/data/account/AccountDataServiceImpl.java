package suwu.daopuerp.data.account;

import org.springframework.beans.factory.annotation.Autowired;
import suwu.daopuerp.dao.user.UserDao;
import suwu.daopuerp.dataservice.account.AccountDataService;
import suwu.daopuerp.entity.account.User;
import suwu.daopuerp.exception.UserDoesNotExistException;

public class AccountDataServiceImpl implements AccountDataService {
    private final UserDao userDao;

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
