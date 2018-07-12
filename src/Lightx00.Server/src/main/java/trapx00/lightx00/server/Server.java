package trapx00.lightx00.server;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import trapx00.lightx00.server.data.util.config.Config;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.server.exception.ConfigNotValidException;
import trapx00.lightx00.shared.exception.faceid.FileException;
import trapx00.lightx00.shared.util.RmiHelper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLException;


public class Server {

    public static final String caller = "Main function";
    public static ServerLogService logService = ServerLogServiceFactory.getService();

    /**
     * Server runner
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        initRmi();
    }

    public static void initRmi() {
        try {
            testConfig();
            BaseDatabaseFactory.init();
            LocateRegistry.createRegistry(Integer.parseInt(RmiHelper.getPort()));
            exportAll();
            logService.printLog(caller, "Initialization done.");

        } catch (RemoteException | SQLException e) {
            logService.printLog(caller, String.format("%s occurred. Message: %s", e.getClass().toString(), e.getMessage()));
            e.printStackTrace();
        }
    }

    public static void testConfig() {
        try {
            Config.getConfig();
        } catch (ConfigNotValidException e) {
            logService.printLog(caller, "Config is not valid. Extracting default config files from resources.");
            try {
                Files.copy(Server.class.getResourceAsStream("/config.json"), e.getConfigFile().toPath());
            } catch (IOException e1) {
                throw new FileException(e1);
            }
        }

    }

    public static void exportAll() {
        FastClasspathScanner scanner = new FastClasspathScanner();
        scanner.matchClassesWithAnnotation(Export.class, classWithAnnotation -> {
            try {
                export((Remote) classWithAnnotation.getMethod("getService").invoke(null));
            } catch (RemoteException | MalformedURLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }).scan();

    }


    public static void export(Remote remoteObj) throws RemoteException, MalformedURLException {
        for (Class clazz : remoteObj.getClass().getInterfaces()) {
            if (Remote.class.isAssignableFrom(clazz)) {
                String url = RmiHelper.generateRmiUrl(clazz);
                logService.printLog(caller, String.format("registered %s to %s", url, remoteObj.toString()));
                Naming.rebind(url, remoteObj);
                return;
            }
        }
    }

}

