package trapx00.lightx00.server.data.faceid;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.j256.ormlite.dao.Dao;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import trapx00.lightx00.server.Server;
import trapx00.lightx00.server.data.faceid.factory.FaceIdDaoFactory;
import trapx00.lightx00.server.data.util.config.Config;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.faceid.FileException;
import trapx00.lightx00.shared.exception.faceid.NotRegisteredException;
import trapx00.lightx00.shared.po.employee.EmployeeFaceIdInfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

public class FaceIdAuthenticationController implements FaceIdAuthenticationService {
    private static final String endpoint = Config.getConfig().getCognitiveServiceConfig().getEndpoint();
    private FaceIdService faceIdService = new FaceIdService();
    private Dao<EmployeeFaceIdInfo, String> dao = FaceIdDaoFactory.getDao();
    private static final double ConfidenceThreshold = 0.5;

    /**
     * Authenticate with image.
     *
     * @param image image in binary
     * @return registered employee id if login is successful. null otherwise
     */
    @Override
    public String authenticate(byte[] image) {
        try {
            List<EmployeeFaceIdInfo> employeeFaceIdInfos = dao.queryForAll();
            String inputFaceId = faceIdService.fetchFaceId(image);
            for (EmployeeFaceIdInfo employeeFaceIdInfo : employeeFaceIdInfos) {
                faceIdService.updateEmployeeFaceInfo(employeeFaceIdInfo);
                if (match(inputFaceId, employeeFaceIdInfo.getFaceId())) {
                    return employeeFaceIdInfo.getEmployeeId();
                }
            }
        } catch (SQLException e) {
            throw new DbSqlException(e);
        }
        throw new NotRegisteredException();
    }

    private byte[] readAsByteArray(String uri) {
        try {
            return com.amazonaws.util.IOUtils.toByteArray(Server.class.getResourceAsStream(uri));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException(e);
        }
    }

    private boolean match(String faceId1, String faceId2) {
        Gson gson = new Gson();
        JsonObject jo = new JsonObject();
        jo.addProperty("faceId1",faceId1);
        jo.addProperty("faceId2", faceId2);
        try {
            StringEntity entity = new StringEntity(gson.toJson(jo));
            URIBuilder builder = new URIBuilder(endpoint + "/verify");
            HttpResponse response = faceIdService.post(builder.build(), entity, "application/json");

            HttpEntity httpEntity = response.getEntity();

            if (httpEntity != null) {
                JsonParser parser = new JsonParser();
                String httpResult = EntityUtils.toString(httpEntity);
                double confidence = parser.parse(httpResult).getAsJsonObject().get("confidence").getAsDouble();
                return confidence >= ConfidenceThreshold;
            }
        } catch (UnsupportedEncodingException | URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new FileException(e);
        }
        return false;
    }
}
