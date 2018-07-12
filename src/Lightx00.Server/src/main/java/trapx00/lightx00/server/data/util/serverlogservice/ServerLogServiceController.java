package trapx00.lightx00.server.data.util.serverlogservice;

import trapx00.lightx00.shared.util.DateHelper;

public class ServerLogServiceController implements ServerLogService {
    /**
     * Prints log into server log system.
     *
     * @param sender  sender of the operation
     * @param content content
     */
    @Override
    public void printLog(Object sender, String content) {
        StringBuilder sb = new StringBuilder();
        sb.append(DateHelper.currentDateStringForLog())
                .append(" [")
                .append(sender)
                .append("] ")
                .append(content);
        System.out.println(sb);
    }
}
