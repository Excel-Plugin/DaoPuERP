package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.vo.financestaff.SystemSnapshotVo;
import trapx00.lightx00.shared.po.ResultMessage;

public interface InitialEstablishmentBlService {

    /**
     * Auto fills some contents with current system state.
     * @return SystemSnapshot with current system state
     */
    SystemSnapshotVo autofill();

    /**
     * Submits the system snapshot.
     * @param snapshot SystemSnapVo to be submitted
     * @return whether the operation is done successfully
     */

    ResultMessage submit(SystemSnapshotVo snapshot);

    /**
     * Saves a half-completed system snapshot as a draft.
     * @param snapshot SystemSnapshotVo to be saved as draft
     * @return whether the operation is done successfully
     */
    ResultMessage saveAsDraft(SystemSnapshotVo snapshot);
}
