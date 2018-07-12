package trapx00.lightx00.shared.dataservicestub.financedataservice;

import trapx00.lightx00.shared.dataservice.financedataservice.InitialEstablishmentDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.client.ClientPo;
import trapx00.lightx00.shared.po.client.ClientState;
import trapx00.lightx00.shared.po.client.ClientType;
import trapx00.lightx00.shared.po.financestaff.BankAccountPo;
import trapx00.lightx00.shared.po.financestaff.SystemSnapshotPo;
import trapx00.lightx00.shared.po.inventorystaff.CommodityPo;
import trapx00.lightx00.shared.queryvo.SystemSnapshotQueryVo;

import java.rmi.RemoteException;
import java.util.Date;

public class InitialEstablishmentDataServiceStub implements InitialEstablishmentDataService {
    /**
     * Submits a snapshot.
     *
     * @param snapshot snapshot to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(SystemSnapshotPo snapshot) {
        return ResultMessage.Success;
    }

    /**
     * Activates a SystemSnapshot.
     *
     * @param id id for the SystemSnapshot
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage activate(String id) {
        return ResultMessage.Success;
    }

    /**
     * Deletes a draft.
     *
     * @param id id for a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(String id) {
        return ResultMessage.Success;
    }

    /**
     * Gets the id for the next snapshot.
     *
     * @return id for the next snapshot
     */
    @Override
    public String getId() {
        return "QCJZ-20171111-00020";
    }

    /**
     * Changes the state of a bill if approval is completed.
     *
     * @param billId    the id of the bill.
     * @param billState new bill state. Only Approved and Rejected is allowed.
     * @return whether the operation is done successfully.
     */
    @Override
    public ResultMessage approvalComplete(String billId, BillState billState) throws RemoteException {
        return ResultMessage.Success;
    }

    /**
     * Queries SystemSnapshot.
     *
     * @param query query
     * @return SystemSnapshotVos that match the condition
     */
    @Override
    public SystemSnapshotPo[] query(SystemSnapshotQueryVo query) {
        return  new SystemSnapshotPo[]{
                new SystemSnapshotPo("QCJZ-20171111-00001", new Date(), BillState.Approved,
                        new CommodityPo[]{new CommodityPo("123", "123", "123", 10.0, new Date(), "123", "123", 10.0, 10.0, 10.0, 10.0, 10.0,0)},
                        new ClientPo[]{new ClientPo("123", ClientType.Retailer, 5, "123", "123", "123", "123", "123@gmail.com", 10.0, 10.0, null, ClientState.Real)},
                        new BankAccountPo[]{new BankAccountPo("123", 10.0, new Date())},
                        "123"
                )
        };
    }


}
