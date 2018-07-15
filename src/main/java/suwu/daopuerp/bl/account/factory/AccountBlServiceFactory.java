package suwu.daopuerp.bl.account.factory;

import suwu.daopuerp.bl.account.mock.AccountBlServiceMock;
import suwu.daopuerp.blservice.account.AccountBlService;

public class AccountBlServiceFactory {
    private static AccountBlService accountBlService = new AccountBlServiceMock();

    public static AccountBlService getAccountBlService() {
        return accountBlService;
    }
}
