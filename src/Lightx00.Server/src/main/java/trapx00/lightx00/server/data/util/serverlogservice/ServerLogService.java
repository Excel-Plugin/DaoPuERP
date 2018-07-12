package trapx00.lightx00.server.data.util.serverlogservice;

public interface ServerLogService {
    /**
     * Prints log into server log system.
     * @param sender sender of the operation
     * @param content content
     */
    void printLog(Object sender, String content);
}
