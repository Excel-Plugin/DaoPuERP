package trapx00.lightx00.client.vo;

import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;

public interface Draftable extends KeyValueDisplayable {

    /**
     * Gets DeleteService corresponding to this type of draft.
     * @return DeleteService
     */
    DraftDeleteService deleteService();


    /**
     * Gets the DraftContinueWritableUiController service corresponding to this type of draft.
     * @return DraftContinueWritableUiController
     */
    DraftContinueWritableUiController continueWritableUi();

    /**
     * Gets the id of a draftable.
     * @return id
     */
    String getId();

}
