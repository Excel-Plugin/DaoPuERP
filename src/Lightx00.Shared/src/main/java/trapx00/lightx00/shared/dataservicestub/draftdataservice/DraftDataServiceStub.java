package trapx00.lightx00.shared.dataservicestub.draftdataservice;

import trapx00.lightx00.shared.dataservice.draftdataservice.DraftDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.draft.DraftPo;
import trapx00.lightx00.shared.po.draft.DraftType;
import trapx00.lightx00.shared.queryvo.DraftQueryVo;

import java.rmi.RemoteException;
import java.util.Date;

public class DraftDataServiceStub implements DraftDataService {

    /**
     * Updates current user's drafts.
     *
     * @param query query
     * @return curent user's drafts
     */
    @Override
    public DraftPo[] query(DraftQueryVo query) throws RemoteException {
        return new DraftPo[] {
                new DraftPo(new Date(),"123","FXD-20171111-00001", DraftType.Bill)
        };
    }

    /**
     * Deletes a draft.
     *
     * @param draftId id for the draft to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(int draftId) {
        return ResultMessage.Success;
    }

    /**
     * Adds a draft.
     *
     * @param draft draft to be added
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(DraftPo draft) throws RemoteException {
        return ResultMessage.Success;
    }
}
