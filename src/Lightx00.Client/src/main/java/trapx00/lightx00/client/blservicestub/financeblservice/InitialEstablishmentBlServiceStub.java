package trapx00.lightx00.client.blservicestub.financeblservice;

import trapx00.lightx00.client.blservice.financeblservice.InitialEstablishmentBlService;
import trapx00.lightx00.client.vo.financestaff.SystemSnapshotVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;

import java.util.Date;

public class InitialEstablishmentBlServiceStub implements InitialEstablishmentBlService {
    @Override
    public SystemSnapshotVo autofill() {
        SystemSnapshotVo vo = new SystemSnapshotVo("123",new Date(),
                BillState.Draft, null, null, null, "123");
        return vo;
    }

    @Override
    public ResultMessage submit(SystemSnapshotVo snapshot) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage saveAsDraft(SystemSnapshotVo snapshot) {
        return ResultMessage.Success;
    }

}
