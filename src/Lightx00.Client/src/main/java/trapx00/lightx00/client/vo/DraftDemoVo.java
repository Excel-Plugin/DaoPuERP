package trapx00.lightx00.client.vo;

import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.presentation.helpui.ContentDisplayUi;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableDemoUiController;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;

public class DraftDemoVo implements Draftable {
    private String draftContent = "123";

    /**
     * Gets DeleteService corresponding to this type of draft.
     *
     * @return DeleteService
     */
    @Override
    public DraftDeleteService deleteService() {
        return null;
    }

    /**
     * Gets the DraftContinueWritableUiController service corresponding to this type of draft.
     *
     * @return DraftContinueWritableUiController
     */
    @Override
    public DraftContinueWritableUiController continueWritableUi() {
        return new DraftContinueWritableDemoUiController();
    }

    /**
     * Gets the id of a draftable.
     *
     * @return id
     */
    @Override
    public String getId() {
        return "1";
    }


    public String getDraftContent() {
        return draftContent;
    }

    /**
     * 显示详细信息UI
     *
     * @return 显示详细信息UI
     */
    @Override
    public ContentDisplayUi contentDisplayUi() {
        return null;
    }
}
