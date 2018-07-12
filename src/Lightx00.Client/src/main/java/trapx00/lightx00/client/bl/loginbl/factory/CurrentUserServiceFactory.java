package trapx00.lightx00.client.bl.loginbl.factory;

import trapx00.lightx00.client.bl.loginbl.CurrentUserService;

public class CurrentUserServiceFactory {
    private static CurrentUserService currentUserService = LoginBlFactory.getController();

    /**
     * Gets a CurrentUserService instance.
     * @return CurrentUserService instance
     */
    public static CurrentUserService getCurrentUserService() {
        return currentUserService;
    }
}
