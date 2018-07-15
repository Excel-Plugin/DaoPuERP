package suwu.daopuerp.presentation.helpui;

/**
 * External loadable ui. If a ui can be externally loaded, implement this interface.
 */
public interface ExternalLoadableUiController {
    /**
     * Loads the controller.
     * @return external loaded ui controller and component
     */
    ExternalLoadedUiPackage load();

    default void onClose() {

    }
}
