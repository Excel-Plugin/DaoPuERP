package trapx00.lightx00.server.exception;

import java.io.File;

public class ConfigNotValidException extends RuntimeException {
    private File configFile;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ConfigNotValidException(File configFile) {
        this.configFile = configFile;
    }

    public File getConfigFile() {
        return configFile;
    }
}
