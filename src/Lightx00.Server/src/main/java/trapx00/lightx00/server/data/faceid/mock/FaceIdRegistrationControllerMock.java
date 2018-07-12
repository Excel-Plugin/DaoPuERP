package trapx00.lightx00.server.data.faceid.mock;

import trapx00.lightx00.server.data.faceid.FaceIdRegistrationController;
import trapx00.lightx00.shared.po.ResultMessage;

public class FaceIdRegistrationControllerMock extends FaceIdRegistrationController {
    /**
     * Unregisters a employeeId
     *
     * @param employeeId employee id
     * @return whether the unregistration is done successfully
     */
    @Override
    public ResultMessage unregister(String employeeId) {
        return ResultMessage.Success;
    }

    /**
     * Registers a picture with a Employee id
     *
     * @param employeeId employee id
     * @param image      image in binary
     * @return whether the registration is done successfully
     */
    @Override
    public ResultMessage register(String employeeId, byte[] image) {
        return ResultMessage.Success;
    }
}
