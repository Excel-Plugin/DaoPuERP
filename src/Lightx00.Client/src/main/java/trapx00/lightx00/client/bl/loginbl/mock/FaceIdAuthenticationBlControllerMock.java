package trapx00.lightx00.client.bl.loginbl.mock;

import trapx00.lightx00.client.bl.loginbl.FaceIdAuthenticationBlController;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.financestaff.FinanceStaffVo;
import trapx00.lightx00.shared.po.employee.EmployeeState;

import java.util.Date;
import java.util.Random;

public class FaceIdAuthenticationBlControllerMock extends FaceIdAuthenticationBlController {
    /**
     * Login with image.
     *
     * @param bytes image in bytes
     * @return EmployeeVo if authenticated. null otherwise.
     */
    @Override
    public EmployeeVo authenticate(byte[] bytes) {
        Random random = new Random();
        if (random.nextBoolean()) {
            return new FinanceStaffVo("10001","财务经理",new Date(),"123456", EmployeeState.Active,true);
        } else {
            return null;
        }

    }
}
