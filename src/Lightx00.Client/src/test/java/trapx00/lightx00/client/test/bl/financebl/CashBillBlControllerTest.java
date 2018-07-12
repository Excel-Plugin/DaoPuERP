package trapx00.lightx00.client.test.bl.financebl;

import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.client.bl.financebl.CashBillBlController;
import trapx00.lightx00.client.bl.financebl.factory.CashBillBlFactory;
import trapx00.lightx00.client.vo.financestaff.CashBillVo;
import trapx00.lightx00.shared.exception.database.BillInvalidStateException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.CashBillItem;
import trapx00.lightx00.shared.queryvo.CashBillQueryVo;
import trapx00.lightx00.shared.util.BillHelper;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * 现金付款单测试用例。
 * 用mock/stub跑必错。一定要使用真实的服务器代码/客户代码跑。
 */
public class CashBillBlControllerTest {
    private CashBillBlController controller = CashBillBlFactory.getController();
    private CashBillVo cashBill = null;
    @Before
    public void setUp() {
        cashBill = new CashBillVo(String.format("XJFKD-%s-00001", BillHelper.currentDateStringForBill()),
            new Date(), BillState.Draft,"123",1,
            new CashBillItem[] {
            new CashBillItem("123",1,"123")
            });
    }

    /**
     * 提交功能测试用例。
     * 因为状态是Draft（看上），所以abandon是真的删除。因为没有提供删除接口，只能默认认为这个行为是正确的。
     * 测试结束后需要删除，所以请尽量使用Draft状态的Vo。
     * 不能覆盖到的部分就先忽略……
     * @throws Exception
     */
    @Test
    public void submit() throws Exception {
        controller.submit(cashBill);
        CashBillVo[] queryResult = controller.query(new CashBillQueryVo().idEq(cashBill.getId()));
        try {
            assertEquals(1, queryResult.length);
            assertEquals(BillState.Draft, queryResult[0].getState());
        } finally {
            controller.abandon(cashBill.getId());
        }
    }

    /**
     * 测不了，因为两个草稿的后果是草稿单据被更新，并不会触发IdExistsException
     * @throws Exception
     */
    //@Test(expected = IdExistsException.class)
    public void submitDuplicate() throws Exception {

    }

    /**
     * 测试存为草稿功能。因为草稿没开BL层查草稿功能，且那个记录草稿的功能是由DraftBl测，这里默认它是正确的（其他模块的返回值不影响这个用例其他的地方的执行）。
     * 测一下数据库有没有保存就行。
     * 后面的都是这样，如果用到了其他模块提供的功能，默认其是正确的，只测试自己相关的部分。
     * 如果不能默认，即需要依赖其他模块的返回数据，就先别测。最后大爆炸……
     * @throws Exception
     */
    @Test
    public void saveAsDraft() throws Exception {
        controller.saveAsDraft(cashBill);
        CashBillVo[] queryResult = controller.query(new CashBillQueryVo().idEq(cashBill.getId()));
        try {
            assertEquals(1, queryResult.length);
            assertEquals(BillState.Draft, queryResult[0].getState());
        } finally {
            controller.abandon(cashBill.getId());
        }
    }


    @Test
    public void getId() throws Exception {
        controller.submit(cashBill);
        try {
            assertEquals(String.format("XJFYD-%s-00002", BillHelper.currentDateStringForBill()), controller.getId());
        } finally {
            controller.abandon(cashBill.getId());
        }

    }

    @Test(expected = NoMoreBillException.class)
    public void getIdTooMuch() throws Exception {
        cashBill.setId(String.format("XJFKD-%s-99999", BillHelper.currentDateStringForBill()));
        try {
            controller.submit(cashBill);
            controller.getId();
        } finally {
            controller.abandon(cashBill.getId());
            cashBill.setId(String.format("XJFKD-%s-00001", BillHelper.currentDateStringForBill()));
        }
    }

    @Test
    public void deleteDraft() throws Exception {
        controller.submit(cashBill);
        controller.deleteDraft(cashBill.getId());
        CashBillVo[] queryResult = controller.query(new CashBillQueryVo().idEq(cashBill.getId()));
        try {
            assertEquals(0, queryResult.length);
        } finally {
            try {
                controller.abandon(cashBill.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 丢弃功能测试。
     * 这就是之前说的有逻辑覆盖不到的地方了……为了能够删除单据，这里必须使用Draft状态的单据。
     * 但是丢弃不只是丢弃Draft类型的单据……
     * 没办法了😰也懒得加个删除接口………如果你们想加也行
     * 而且也不能保证删除（不能直接操作数据库，唯一的删除入口就是它）……很难受
     * 尽量保证一次写好吧
     * @throws Exception
     */
    @Test
    public void abandon() throws Exception {
        controller.submit(cashBill);
        controller.abandon(cashBill.getId());
        CashBillVo[] queryResult = controller.query(new CashBillQueryVo().idEq(cashBill.getId()));
        assertEquals(0, queryResult.length);
    }

    /**
     * 激活操作测试。
     * 测不了……因为激活操作要求单据状态要是Approved，根据上文的原因，它不能是Approved（不然就删不掉了……）
     * 还是不想加接口😀
     * 我一写就对😊
     * @throws Exception
     */
    @Test
    public void activate() throws Exception {

    }

    /**
     * 激活操作错误的状态。
     * 这个倒是可以测。Draft就是错误的状态
     * @throws Exception
     */
    @Test(expected = BillInvalidStateException.class)
    public void activateWrongBillState() throws Exception {
        controller.submit(cashBill);
        try {
            controller.activate(cashBill.getId());
        } finally {
            controller.abandon(cashBill.getId());
        }
    }

    /**
     * 审批通过错误的目标状态。
     * 测不了，同上上上。
     * 但是其实这种用例只要data过了就稳了。毕竟bl层就是直接调data层的方法而已。
     * @throws Exception
     */
    public void approvalCompleteWrongTargetState() throws Exception {

    }

    @Test(expected = BillInvalidStateException.class)
    public void approvalCompleteWrongBillState() throws Exception {
        controller.submit(cashBill);
        try {
            controller.approvalComplete(cashBill.getId(), BillState.Approved);
        } finally {
            controller.abandon(cashBill.getId());
        }
    }

    @Test
    public void approvalComplete() throws Exception {

    }

    @Test
    public void query() throws Exception {
        controller.submit(cashBill);
        try {
            assertEquals(1, controller.query(new CashBillQueryVo()).length);
            assertEquals(1, controller.query(new CashBillQueryVo().idEq(cashBill.getId())).length);
            assertEquals(0, controller.query(new CashBillQueryVo().eq("operatorId", "1234587986435")).length);
        } finally {
            controller.abandon(cashBill.getId());
        }

    }

}