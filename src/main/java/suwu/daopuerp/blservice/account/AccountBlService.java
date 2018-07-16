package suwu.daopuerp.blservice.account;

import suwu.daopuerp.dto.account.UserDto;
import suwu.daopuerp.exception.PasswordWrongException;
import suwu.daopuerp.exception.UserDoesNotExistException;

public interface AccountBlService {
    /**
     * Login with username and password provided.
     * @param username username
     * @param password password
     */
    UserDto login(String username, String password) throws PasswordWrongException, UserDoesNotExistException;
}
