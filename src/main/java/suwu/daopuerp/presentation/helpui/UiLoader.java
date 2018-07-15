package suwu.daopuerp.presentation.helpui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import suwu.daopuerp.MainApplication;

import java.io.IOException;

@SuppressWarnings("unchecked")
public class UiLoader {
    private FXMLLoader loader = new FXMLLoader();

    public UiLoader() {

    }

    public UiLoader(String location) {
        setLocation(location);
    }

    public void setLocation(String location) {
        loader.setLocation(MainApplication.class.getResource(location));
    }

    public <T extends Parent> T load() throws IOException {
        return loader.load();
    }

    public <T extends Parent> T loadWithoutException() {
        try {
            return load();
        } catch (IOException e) {
            handleIOException(e);
            return null;
        }
    }

    public <T> T getController() {
        return loader.getController();
    }

    public <T extends Parent> T load(String location) throws IOException {
        setLocation(location);
        return load();
    }

    public <T extends Parent> T loadWithoutException(String location) {
        setLocation(location);
        return loadWithoutException();
    }

    private void handleIOException(IOException e) {
        e.printStackTrace();
    }

    public ExternalLoadedUiPackage loadAndGetPackageWithoutException() {
        return new ExternalLoadedUiPackage(loadWithoutException(), getController());
    }

    public ExternalLoadedUiPackage loadAndGetPackage() throws IOException {
        return new ExternalLoadedUiPackage(load(), getController());
    }
}
