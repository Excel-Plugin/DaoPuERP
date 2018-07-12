package trapx00.lightx00.server.data.util;

import trapx00.lightx00.server.Server;

public class IOUtil {
    public static String getFilePathUnderRootDirOfJarFileOrClassDir(String relativePath) {
        return Server.class.getProtectionDomain().getCodeSource().getLocation().getPath() +"/.." + relativePath;
    }
}
