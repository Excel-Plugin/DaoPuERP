package trapx00.lightx00.client.bl.loginbl;

import trapx00.lightx00.client.vo.EmployeeVo;

public interface    CurrentUserService {
    /**
     * Gets current logged-in user.
     * @return current user
     */
    EmployeeVo getCurrentUser();
}
