package suwu.daopuerp.bl.account;

import org.springframework.beans.factory.annotation.Autowired;
import suwu.daopuerp.blservice.account.AccountBlService;
import suwu.daopuerp.dataservice.account.AccountDataService;
import suwu.daopuerp.dto.UserDto;
import suwu.daopuerp.entity.User;
import suwu.daopuerp.exception.PasswordWrongException;
import suwu.daopuerp.exception.UserDoesNotExistException;

public class AccountBlServiceImpl implements AccountBlService {
    private final AccountDataService accountDataService;

    @Autowired
    public AccountBlServiceImpl(AccountDataService accountDataService) {
        this.accountDataService = accountDataService;
    }

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
