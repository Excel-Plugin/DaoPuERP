package suwu.daopuerp.dataservice.account;

import suwu.daopuerp.entity.account.User;
import suwu.daopuerp.exception.UserDoesNotExistException;

public interface AccountDataService {
    /**
     * get user by username
     *
     * @param username
     * @return
     */
    User getUserByUsername(String username) throws UserDoesNotExistException;
}
