package trapx00.lightx00.client.bl.salebl.mock;

import trapx00.lightx00.client.bl.salebl.SaleBillBlController;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.SaleBillQueryVo;

public class SaleBillBlControllerMock extends SaleBillBlController {

    /**
     * Deletes a draft.
     *
     * @param id id of the draft to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage deleteDraft(String id) {
        return ResultMessage.Success;
    }

    /**
     * Abandons a bill.
     *
     * @param id id for the bill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(String id) {
        return ResultMessage.Success;
    }

    /**
     * Activates a bill that has been approved of.
     *
     * @param id id for the bill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage activate(String id) {
        return ResultMessage.Success;
    }

    /**
     * submit a saleBill
     *
     * @param saleBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(SaleBillVo saleBill) {
        return ResultMessage.Success;
    }

    /**
     * saves a half-completed saleBill as draft.
     *
     * @param saleBill SaleBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(SaleBillVo saleBill) {
        return ResultMessage.Success;
    }

    /**
     * Gets the id for the next saleBill
     *
     * @return id for the next saleBill
     */
    @Override
    public String getId() {
        return "0";
    }

    /**
     * query for SaleBills
     *
     * @param query function
     * @return array of SaleBillVo
     */
    @Override
    public SaleBillVo[] querySaleBill(SaleBillQueryVo query) {
        SaleBillVo[] saleBillVos=new SaleBillVo[1];
        saleBillVos[0]=new SaleBillVo("XSD-171203-00000",null,null,null,null,null,1,null,100,100,100,100,null,1,"1",null,0);
        return saleBillVos;
    }
}
