package trapx00.lightx00.server.data.promotiondata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.shared.exception.database.*;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionPoBase;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.queryvo.BaseQueryVo;
import trapx00.lightx00.shared.util.PromotionHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class PromotionDataController<Po extends PromotionPoBase> {
    private static final int MAX_PROMOTION_NUM_FOR_A_DAY = 99999;
    private Dao<Po,String> dao;
    private Object delegate = this;
    private ServerLogService logService = ServerLogServiceFactory.getService();

    public PromotionDataController(Dao<Po, String> dao) {
        this.dao = dao;
    }

    /**
     * Constructor with delegate. Pass in (this) directly to log with actual caller.
     * @param dao dao
     * @param delegate delegate object. Pass in (this) directly to log with actual caller.
     */
    public PromotionDataController(Dao<Po, String> dao, Object delegate) {
        this.dao = dao;
        this.delegate = delegate;
    }

    private void handleSqlException(SQLException e) {
        logService.printLog(delegate, "数据库操作失败，错误原因是: " + e.getMessage());
        throw new DbSqlException(e);
    }

    /**
     * This function is a util to assert whether the id is used.
     * If assertExists is true but the id is not used, a IdNotExistsException is thrown.
     * If assertExists is false but the id is used, a IdExistsException is thrown.
     * If assertExists is true and the id is used, the bill for the id is returned and no exception is thrown.
     * If assertExists is false and the id is not used, null will be returned and no exception is thrown.
     * @param id id
     * @param assertExists assert exists flag
     * @return PromotionPo if assertExists is true and the id is used.
     */
    public Po assertIdExistence(String id, boolean assertExists) {
        try {
            Po promotion = dao.queryForId(id);
            boolean actualExistence = promotion != null;
            if (actualExistence && !assertExists) {
                throw new IdExistsException(id);
            }
            if (!actualExistence && assertExists) {
                throw new IdNotExistsException(id);
            }
            return promotion;
        } catch (SQLException e) {
            handleSqlException(e);
            return null;
        }
    }

    /**
     * Submits a promotion or save it as a draft.
     * If there is a promotion with the same id as passed-in parameter do,
     *    if the existing promotion is in PromotionPoBase.Draft state, it will be updated/replaced by parameter.
     *    otherwise a IdExistsException would be thrown.
     *
     * @param promotion promotion
     * @return whether the operation is done successfully
     */
    public ResultMessage submit(Po promotion) {
        try {
            Po po = dao.queryForId(promotion.getId());
            //promotion是草稿状态（数据库已经有了id）
            if (po != null && po.getState().equals(PromotionState.Draft)) {
                dao.update(promotion);
                logService.printLog(delegate,String.format("更新促销策略草稿(id: %s)，新内容是: %s", promotion.getId(), promotion.toString()));
                return ResultMessage.Success;
            }
            //id被占用
            if (po != null) {
                throw new IdExistsException(promotion.getId());
            }
            //id没有占用
            dao.create(promotion);
            promotion.setId(dao.extractId(promotion));
            logService.printLog(delegate, String.format("创建促销策略草稿(id: %s)，内容是: %s", promotion.getId(), promotion.toString()));
            return ResultMessage.Success;
        } catch (SQLException e) {
            handleSqlException(e);
            return ResultMessage.Failure;
        }
    }

    /**
     * Abandons a promotion.
     * If a promotion is in PromotionState.Draft, it will be deleted.
     * If a promotion is in PromotionState.Waiting/Overdue, it will be changed as Abandoned.
     * If a promotion is in other state, a PromotionInvalidStateException will be thrown.
     * @param id id for the promotion to be abandoned
     * @return whether the operation is done successfully
     */
    public ResultMessage delete(String id) {
        try {
            Po po = assertIdExistence(id,true);
            PromotionState previousState = po.getState();
            switch (previousState) {
                case Draft:
                    dao.deleteById(id);
                    logService.printLog(delegate, String.format("删除促销策略草稿(id: %s)", id));
                    return ResultMessage.Success;
                case Overdue:
                    po.setState(PromotionState.Abandoned);
                    dao.update(po);
                    logService.printLog(delegate, String.format("作废过期促销策略(id: %s)", id));
                    return ResultMessage.Success;
                case Active:
                    throw new PromotionInvalidStateException(previousState, PromotionState.Abandoned,PromotionState.Overdue,PromotionState.Waiting);
                case Waiting:
                    po.setState(PromotionState.Abandoned);
                    dao.update(po);
                    logService.printLog(delegate, String.format("作废待生效促销策略(id: %s)",id));
                    return ResultMessage.Success;
                case Abandoned:
                    dao.deleteById(id);
                    logService.printLog(delegate, String.format("删除已作废促销策略(id: %s)", id));
                    return ResultMessage.Success;
                default:
                    return ResultMessage.Failure;
            }
        } catch (SQLException e) {
            handleSqlException(e);
            return ResultMessage.Failure;
        }
    }


    /**
     * Queries promotions.
     *
     * @param query query condition
     * @return promotions that match the query condition
     */
    @SuppressWarnings("unchecked")
    public <Q extends BaseQueryVo> List<Po> query(Q query) {
        try {
            List<Po> results = dao.query(query.prepareQuery(dao));
            logService.printLog(delegate, String.format("查询促销策略，得到%d条结果.", results.size()));
            return results;
        } catch (SQLException e) {
            handleSqlException(e);
            return new ArrayList<>();
        }
    }

    /**
     * Gets the id for the next bill.
     * If there are already 99999 bills for this day, a NoMoreBillException will be thrown.
     *
     * @param leadingText leading text for the bill. For instance 现金费用单's leading text is XJFYD. It can be found in the doc
     * @return id for the next bill
     */
    public String getId(String leadingText) {
        try {
            OptionalInt maxId = dao.queryBuilder().selectColumns("id").query().stream()
                    .map(PromotionPoBase::getId)
                    .filter(x -> x.split("-")[1].equals(PromotionHelper.currentDateStringForPromotion()))
                    .map(x -> x.split("-")[2])
                    .mapToInt(Integer::parseInt)
                    .max();
            if (maxId.orElse(-1) == MAX_PROMOTION_NUM_FOR_A_DAY) {
                logService.printLog(delegate, "当日促销策略数量已满");
                throw new NoMoreBillException();
            }
            String newId = leadingText + "-" + PromotionHelper.currentDateStringForPromotion() + "-" + PromotionHelper.formatId(maxId.orElse(0) + 1);
            logService.printLog(delegate, "得到编号" + newId);
            return newId;
        } catch (SQLException e) {
            handleSqlException(e);
            return "";
        }
    }

}
