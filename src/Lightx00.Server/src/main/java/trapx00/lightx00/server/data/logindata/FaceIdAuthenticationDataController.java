package trapx00.lightx00.server.data.logindata;

import trapx00.lightx00.server.data.faceid.FaceIdAuthenticationService;
import trapx00.lightx00.server.data.faceid.factory.FaceIdAuthenticationServiceFactory;
import trapx00.lightx00.shared.dataservice.logindataservice.FaceIdAuthenticationDataService;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class FaceIdAuthenticationDataController extends UnicastRemoteObject implements FaceIdAuthenticationDataService {
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
    public FaceIdAuthenticationDataController() throws RemoteException {
    }

    private FaceIdAuthenticationService service = FaceIdAuthenticationServiceFactory.getService();

    /**
     * Authenticate with image.
     *
     * @param image image in binary
     * @return registered employee id if login is successful. null otherwise
     */
    @Override
    public String authenticate(byte[] image) {
        return service.authenticate(image);
    }
}
