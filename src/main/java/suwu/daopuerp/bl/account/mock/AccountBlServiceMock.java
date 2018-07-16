package suwu.daopuerp.bl.account.mock;

import org.springframework.stereotype.Service;
import suwu.daopuerp.blservice.account.AccountBlService;
import suwu.daopuerp.dto.account.UserDto;
import suwu.daopuerp.exception.PasswordWrongException;
import suwu.daopuerp.exception.UserDoesNotExistException;

@Service
public class AccountBlServiceMock implements AccountBlService {
    /**
     * Login with username and password provided.
     *
     * @param username username
     * @param password password
     */
    @Override
    public UserDto login(String username, String password) throws PasswordWrongException, UserDoesNotExistException {
        return new UserDto(username);
    }
}
