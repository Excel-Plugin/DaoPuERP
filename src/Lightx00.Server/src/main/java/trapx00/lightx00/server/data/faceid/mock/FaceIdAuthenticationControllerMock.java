package trapx00.lightx00.server.data.faceid.mock;

import trapx00.lightx00.server.data.faceid.FaceIdAuthenticationController;

public class FaceIdAuthenticationControllerMock extends FaceIdAuthenticationController {
    /**
     * Authenticate with image.
     *
     * @param image image in binary
     * @return registered employee id if login is successful. null otherwise
     */
    @Override
    public String authenticate(byte[] image) {
        return "1";
    }
}
