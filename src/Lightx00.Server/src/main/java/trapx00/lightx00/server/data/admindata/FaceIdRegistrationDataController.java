package trapx00.lightx00.server.data.admindata;

import trapx00.lightx00.server.data.faceid.FaceIdRegistrationService;
import trapx00.lightx00.server.data.faceid.factory.FaceIdRegistrationServiceFactory;
import trapx00.lightx00.shared.dataservice.admindataservice.FaceIdRegistrationDataService;
import trapx00.lightx00.shared.po.ResultMessage;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class FaceIdRegistrationDataController extends UnicastRemoteObject implements FaceIdRegistrationDataService {
    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     * <p>
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    public FaceIdRegistrationDataController() throws RemoteException {
    }

    private FaceIdRegistrationService registrationService = FaceIdRegistrationServiceFactory.getService();

    /**
     * Registers a picture with a Employee id.
     *
     * @param employeeId employee id
     * @param image      image in binary
     * @return whether the registration is done successfully
     */
    @Override
    public ResultMessage register(String employeeId, byte[] image) {
        return registrationService.register(employeeId, image);
    }

    /**
     * Unregisters a employeeId.
     *
     * @param employeeId employee id
     * @return whether the unregistration is done successfully
     */
    @Override
    public ResultMessage unregister(String employeeId) {
        return registrationService.unregister(employeeId);
    }
}
