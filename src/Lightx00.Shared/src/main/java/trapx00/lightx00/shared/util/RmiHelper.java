package trapx00.lightx00.shared.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RmiHelper {
    private static final String url = "localhost";
    private static final String port = "8888";

    public static String generateRmiUrl(Class... interfaces) {
        return "rmi://" + url + ":" + port + "/" + Arrays.stream(interfaces).map(Class::getName).collect(Collectors.joining("/"));

    }

    public static String getUrl() {
        return url;
    }

    public static String getPort() {
        return port;
    }
}
