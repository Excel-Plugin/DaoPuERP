package suwu.daopuerp.bl.account;

import suwu.daopuerp.blservice.account.AccountBlService;
import suwu.daopuerp.dto.account.UserDto;
import suwu.daopuerp.entity.account.User;
import suwu.daopuerp.exception.PasswordWrongException;
import suwu.daopuerp.exception.UserDoesNotExistException;

public class AccountBlServiceImpl implements AccountBlService {
    /**
     * Login with username and password provided.
     *
     * @param username username
     * @param password password
     * @return EmployeeVo is login is successful
     */
    @Override
    public UserDto login(String username, String password) throws PasswordWrongException, UserDoesNotExistException {
        try {
            User user = accountDataService.getUserByUsername(username);
            if (!user.getPassword().equals(password)) {
                throw new PasswordWrongException();
            } else {
                return new UserDto(user.getUsername());
            }
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
            throw new UserDoesNotExistException();
        }
    }
}
