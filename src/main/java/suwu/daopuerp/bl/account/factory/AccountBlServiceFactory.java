package suwu.daopuerp.bl.account.factory;

import suwu.daopuerp.bl.account.AccountBlServiceImpl;
import suwu.daopuerp.blservice.account.AccountBlService;

public class AccountBlServiceFactory {
    private static AccountBlService accountBlService = new AccountBlServiceImpl();

    public static AccountBlService getAccountBlService() {
        return accountBlService;
    }
}
