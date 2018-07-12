package trapx00.lightx00.shared.dataservicestub.logindataservice;

import trapx00.lightx00.shared.dataservice.logindataservice.FaceIdAuthenticationDataService;

public class FaceIdAuthenticationDataServiceStub implements FaceIdAuthenticationDataService {
    /**
     * Login with face image.
     *
     * @param faceImage face image in binary
     * @return EmployeePo if login is successful. null otherwise
     */
    @Override
    public String authenticate(byte[] faceImage) {
        return "123";
    }
}
