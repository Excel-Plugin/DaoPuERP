package trapx00.lightx00.client.blservicestub.loginblservice;

import trapx00.lightx00.shared.dataservice.logindataservice.FaceIdAuthenticationDataService;

import java.rmi.RemoteException;

public class FaceIdAuthenticationBlServiceStub implements FaceIdAuthenticationDataService {
    /**
     * Login with face image.
     *
     * @param faceImage face image in binary
     * @return EmployeePo if login is successful. null otherwise
     */
    @Override
    public String authenticate(byte[] faceImage) throws RemoteException {
        return "1";
    }
}
