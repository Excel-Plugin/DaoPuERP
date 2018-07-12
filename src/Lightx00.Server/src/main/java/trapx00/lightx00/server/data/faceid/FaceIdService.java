package trapx00.lightx00.server.data.faceid;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import trapx00.lightx00.server.data.faceid.factory.FaceIdDaoFactory;
import trapx00.lightx00.server.data.util.IOUtil;
import trapx00.lightx00.server.data.util.config.CognitiveServiceConfig;
import trapx00.lightx00.server.data.util.config.Config;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.faceid.FileException;
import trapx00.lightx00.shared.exception.faceid.MultipleFacesException;
import trapx00.lightx00.shared.exception.faceid.NetworkException;
import trapx00.lightx00.shared.exception.faceid.NoFaceDetectedException;
import trapx00.lightx00.shared.po.employee.EmployeeFaceIdInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class FaceIdService {
    private Dao<EmployeeFaceIdInfo, String> dao = FaceIdDaoFactory.getDao();
    private static String api1;
    private static String endpoint;

    static {
        CognitiveServiceConfig cognitiveServiceConfig = Config.getConfig().getCognitiveServiceConfig();
        api1 = cognitiveServiceConfig.getApi1();
        endpoint = cognitiveServiceConfig.getEndpoint();
    }

    public byte[] readAsByteArray(String resourceUri) {
        try {
            return com.amazonaws.util.IOUtils.toByteArray(
                new FileInputStream(new File(IOUtil.getFilePathUnderRootDirOfJarFileOrClassDir(resourceUri))));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException(e);
        }
    }

    public void updateEmployeeFaceInfo(EmployeeFaceIdInfo info) {
        if (info.needUpdate()) {
            byte[] imgContent = readAsByteArray(info.getImgUri());
            String newFaceId = fetchFaceId(imgContent);
            info.setFaceId(newFaceId);
            try {
                dao.update(info);
            } catch (SQLException e) {
                throw new DbSqlException(e);
            }
        }
    }

    public String fetchFaceId(byte[] img) {
        try {
            URIBuilder builder = new URIBuilder(endpoint + "/detect");
            builder.setParameter("returnFaceId", "true");
            builder.setParameter("returnFaceLandmarks", "false");

            HttpResponse response = post(builder.build(), new ByteArrayEntity(img), "application/octet-stream");

            HttpEntity resEntity = response.getEntity();

            if (resEntity == null || response.getStatusLine().getStatusCode() >= 500) {
                throw new NetworkException(response.getStatusLine().getStatusCode());
            }

            if (response.getStatusLine().getStatusCode() == 200) {
                Gson gson = new Gson();
                String result = EntityUtils.toString(resEntity);
                Face[] faces = gson.fromJson(result, Face[].class);
                if (faces.length == 0) {
                    throw new NoFaceDetectedException();
                } else if (faces.length > 1) {
                    throw new MultipleFacesException(faces.length);
                }
                return faces[0].getFaceId();
            }
            throw new NetworkException(response.getStatusLine().getStatusCode());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            throw new FileException(e);
        }
    }

    public HttpResponse post(URI uri, HttpEntity entity, String contentType) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-Type", contentType);
        request.setHeader("Ocp-Apim-Subscription-Key", api1);
        request.setEntity(entity);
        return httpclient.execute(request);
    }
}
