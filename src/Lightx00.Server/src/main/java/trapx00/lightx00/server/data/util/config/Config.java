package trapx00.lightx00.server.data.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import trapx00.lightx00.server.data.util.IOUtil;
import trapx00.lightx00.server.exception.ConfigNotValidException;
import trapx00.lightx00.shared.exception.faceid.FileException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Config {

    @Expose
    @SerializedName("cognitiveService")
    private CognitiveServiceConfig cognitiveServiceConfig;

    @Expose
    @SerializedName("logBackup")
    private LogBackupConfig logBackupConfig;


    private static Config config;

    public boolean valid() {
        return cognitiveServiceConfig != null && logBackupConfig != null;
    }

    public static Config getConfig() {
        if (config != null) {
            return config;
        }

        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            File configFile = new File(IOUtil.getFilePathUnderRootDirOfJarFileOrClassDir("/config.json"));
            System.out.println(configFile.getAbsolutePath());

            if (!configFile.exists()) {
                throw new ConfigNotValidException(configFile);
            }
            config = gson.fromJson(new JsonReader(new FileReader(configFile)),Config.class);
            if (config.valid()) {
                return config;
            }
            throw new ConfigNotValidException(configFile);
        } catch (IOException e) {
            throw new FileException(e);
        }
    }

    public CognitiveServiceConfig getCognitiveServiceConfig() {
        return cognitiveServiceConfig;
    }

    public LogBackupConfig getLogBackupConfig() {
        return logBackupConfig;
    }
}
