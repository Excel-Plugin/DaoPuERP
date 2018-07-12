package trapx00.lightx00.client.blservice.draftblservice;

import trapx00.lightx00.client.vo.draft.DraftVo;
import trapx00.lightx00.shared.po.ResultMessage;

public interface DraftBlService {
    /**
     * Updates current user's draft.
     * @return Current User's draft
     */
    DraftVo[] update();

    /**
     * Deletes a draft.
     * @param draft draft to be deleted
     * @return whether the operation is done successfully
     */

    ResultMessage delete(DraftVo draft);
}
