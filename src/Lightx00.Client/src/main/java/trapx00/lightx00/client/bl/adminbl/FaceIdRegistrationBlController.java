package trapx00.lightx00.client.bl.adminbl;

import trapx00.lightx00.client.blservice.adminblservice.FaceIdRegistrationBlService;
import trapx00.lightx00.client.datafactory.admindataservicefactory.FaceIdRegistrationDataServiceFactory;
import trapx00.lightx00.shared.dataservice.admindataservice.FaceIdRegistrationDataService;
import trapx00.lightx00.shared.po.ResultMessage;

import java.rmi.RemoteException;

public class FaceIdRegistrationBlController implements FaceIdRegistrationBlService {

    private FaceIdRegistrationDataService dataService = FaceIdRegistrationDataServiceFactory.getDataService();

    /**
     * Registers a picture with a Employee id.
     *
     * @param employeeId employee id
     * @param image      image in binary
     * @return whether the registration is done successfully
     */
    @Override
    public ResultMessage register(String employeeId, byte[] image) {
        try {
            return dataService.register(employeeId, image);
        } catch (RemoteException e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }

    /**
     * Unregisters a employeeId.
     *
     * @param employeeId employee id
     * @return whether the unregistration is done successfully
     */
    @Override
    public ResultMessage unregister(String employeeId) {
        try {
            return dataService.unregister(employeeId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }
}
