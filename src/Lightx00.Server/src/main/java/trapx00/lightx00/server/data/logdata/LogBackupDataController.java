package trapx00.lightx00.server.data.logdata;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.logdata.factory.LogDataDaoFactory;
import trapx00.lightx00.server.data.util.IOUtil;
import trapx00.lightx00.server.data.util.config.Config;
import trapx00.lightx00.server.data.util.config.LogBackupConfig;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.shared.dataservice.logdataservice.LogBackupDataService;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.oos.CannotOpenTempFileException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.log.LogPo;
import trapx00.lightx00.shared.queryvo.LogBackupVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogBackupDataController extends UnicastRemoteObject implements LogBackupDataService {
    private static String END_POINT;
    private static String BUCKET_NAME;
    private static String ACCESS_KEY;
    private static String SECRET_KEY;
    private static final String FILE_PATH = IOUtil.getFilePathUnderRootDirOfJarFileOrClassDir("/temp.txt");
    private static final String SEPATAROR = " | ";
    private static final long EXPIRATION =
            new Date().getTime() * 1000 * 60 * 60 * 24;

    private Dao<LogPo, Integer> logDao = LogDataDaoFactory.getLogDao();
    private Object delegate = this;
    private ServerLogService logService = ServerLogServiceFactory.getService();

    static {
        LogBackupConfig config = Config.getConfig().getLogBackupConfig();
        ACCESS_KEY = config.getAccessKey();
        SECRET_KEY = config.getSecretKey();
        BUCKET_NAME = config.getBucketName();
        END_POINT = config.getEndpoint();
    }

    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     * The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    public LogBackupDataController() throws RemoteException {
    }

    private void handleSQLException(SQLException e) {
        logService.printLog(delegate, "failed at a database operation. Error message: " + e.getMessage());
        throw new DbSqlException(e);
    }

    private void handleIOException(IOException e) {
        logService.printLog(delegate, "failed at a io operation. Error message: " + e.getMessage());
        throw new CannotOpenTempFileException();
    }

    /**
     * back up the log information on the server to oos and clear the log
     *
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage backupLog() {
        try {
            List<LogPo> logPos = logDao.queryBuilder().query();
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            for (LogPo logPo : logPos) {
                fileWriter.write(logPo.getId() + SEPATAROR);
                fileWriter.write(logPo.getSeverity().toString() + SEPATAROR);
                fileWriter.write(DateHelper.fromDate(logPo.getDate()) + SEPATAROR);
                fileWriter.write(logPo.getContent() + System.lineSeparator());
            }
            fileWriter.flush();
            fileWriter.close();
            logDao.delete(logPos);

            AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
            AmazonS3 oos = new AmazonS3Client(credentials);
            oos.setEndpoint(END_POINT);
            oos.putObject(BUCKET_NAME, DateHelper.currentDateString("yyyy_MM_dd_HH:mm:ss"), file);
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            handleSQLException(e);
            return ResultMessage.Failure;
        } catch (IOException e) {
            e.printStackTrace();
            handleIOException(e);
            return ResultMessage.Failure;
        }
    }

    /**
     * get the log backed up on the cloud
     *
     * @return the temp uri of the log resources
     */
    @Override
    public LogBackupVo[] fetchCloudLog() {
        ArrayList<LogBackupVo> result = new ArrayList<>();
        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonS3 oos = new AmazonS3Client(credentials);

        oos.setEndpoint(END_POINT);
        List<S3ObjectSummary> list = oos.listObjects(BUCKET_NAME).getObjectSummaries();
        for (S3ObjectSummary object : list) {
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(BUCKET_NAME, object.getKey());
            generatePresignedUrlRequest.setExpiration(new Date(EXPIRATION));
            URL url = oos.generatePresignedUrl(generatePresignedUrlRequest);
            LogBackupVo logBackupVo = new LogBackupVo(object.getKey(), url.toString());
            logService.printLog(delegate, "fetch cloud log:" + logBackupVo.getDate() + "|" + logBackupVo.getUrl());
            result.add(logBackupVo);
        }
        return result.toArray(new LogBackupVo[result.size()]);
    }
}
