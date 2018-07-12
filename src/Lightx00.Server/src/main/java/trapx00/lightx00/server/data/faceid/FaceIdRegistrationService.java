package trapx00.lightx00.server.data.faceid;

import trapx00.lightx00.shared.po.ResultMessage;

public interface FaceIdRegistrationService {

    /**
     * Unregisters a employeeId
     * @param employeeId employee id
     * @return whether the unregistration is done successfully
     */
    ResultMessage unregister(String employeeId);

    /**
     * Registers a picture with a Employee id
     * @param employeeId employee id
     * @param image image in binary
     * @return whether the registration is done successfully
     */
    ResultMessage register(String employeeId, byte[] image);
}
