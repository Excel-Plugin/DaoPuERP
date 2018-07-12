package trapx00.lightx00.shared.dataservice.inventorydataservice;

import trapx00.lightx00.shared.dataservice.CommonBillDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.inventorystaff.InventoryGiftPo;
import trapx00.lightx00.shared.queryvo.InventoryGiftQueryVo;

import java.rmi.RemoteException;

public interface InventoryGiftDataService  extends CommonBillDataService<InventoryGiftPo, InventoryGiftQueryVo> {

    /**
     * Submits a BillPo or save it as a draft.
     * If there is a bill with the same id as passed-in parameter do,
     *    if the existing bill is in BillState.Draft state, it will be updated/replaced by parameter.
     *    otherwise a IdExistsException would be thrown.
     *
     * @param bill bill
     * @return whether the operation is done successfully
     */
    ResultMessage submit(InventoryGiftPo bill) throws RemoteException;


    /**
     * Activates a CashBill.
     * The bill must be in BillState.WaitingForApproval state.
     * OtherwiseEx a BillInvalidStateception will be thrown.
     *
     * @param id id for the bill that have been approved of
     * @return whether the operation is done successfully
     */
    ResultMessage activate(String id) throws RemoteException;

    /**
     * Abandons a Bill.
     * If a Bill is in BillState.Draft, it will be deleted.
     * If a Bill is in BillState.Rejected/Approved/WaitingForApproval, it will be changed as Abandoned.
     * If a bill is in other state, a BillInvalidStateException will be thrown.
     * @param id id for the CashBill to be abandoned
     * @return whether the operation is done successfully
     */
    ResultMessage abandon(String id) throws RemoteException;

    /**
     * Changes the state of a bill if approval is completed.
     * @param billId the id of the bill.
     * @param billState new bill state. Only Approved and Rejected is allowed.
     * @return whether the operation is done successfully.
     */
    ResultMessage approvalComplete(String billId, BillState billState) throws RemoteException;

    /**
     * Gets the id for the next bill.
     * If there are already 99999 bills for this day, a NoMoreBillException will be thrown.
     *
     * @return id for the next bill
     */
    String getId() throws RemoteException;

    /**
     * Queries Bill.
     * @param query query condition
     * @return BillVos that match the query condition
     */

    InventoryGiftPo[] query(InventoryGiftQueryVo query) throws RemoteException;
}
