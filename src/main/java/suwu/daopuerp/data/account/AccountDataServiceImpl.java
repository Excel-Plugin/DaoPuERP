package suwu.daopuerp.data.account;

import suwu.daopuerp.dao.factory.UserDaoFactory;
import suwu.daopuerp.dao.user.UserDao;
import suwu.daopuerp.dataservice.account.AccountDataService;
import suwu.daopuerp.entity.account.User;
import suwu.daopuerp.exception.UserDoesNotExistException;

public class AccountDataServiceImpl implements AccountDataService {
    private UserDao userDao = UserDaoFactory.getUserDao();

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
