package trapx00.lightx00.shared.dataservice.draftdataservice;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.draft.DraftPo;
import trapx00.lightx00.shared.queryvo.DraftQueryVo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DraftDataService extends Remote {
    /**
     * Updates current user's drafts.
     * @return curent user's drafts
     */
    DraftPo[] query(DraftQueryVo query) throws RemoteException;

    /**
     * Deletes a draft.
     * @param draftId id for the draft to be deleted
     * @return whether the operation is done successfully
     */

    ResultMessage delete(int draftId) throws RemoteException;

    /**
     * Adds a draft.
     * @param draft draft to be added
     * @return whether the operation is done successfully
     */
    ResultMessage add(DraftPo draft) throws RemoteException;
}
