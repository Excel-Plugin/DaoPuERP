package trapx00.lightx00.client.vo.financestaff;

import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.salestaff.ClientVo;

import java.util.Date;


public class SaleDetailQueryVo { //销售明细表查询条件
    private Date start;
    private Date end;
    private String commodityName;
    private ClientVo[] clients;
    private EmployeeVo[] operators;
    private int repository;

    public SaleDetailQueryVo(Date start, Date end, String commodityName, ClientVo[] clients,
                             EmployeeVo[] operators, int repository) {
        this.start = start;
        this.end = end;
        this.commodityName = commodityName;
        this.clients = clients;
        this.operators = operators;
        this.repository = repository;
    }

    public SaleDetailQueryVo() {
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public ClientVo[] getClients() {
        return clients;
    }

    public void setClient(ClientVo[] clients) {
        this.clients = clients;
    }

    public EmployeeVo[] getOperators() {
        return operators;
    }

    public void setOperator(EmployeeVo[] operator) {
        this.operators = operator;
    }

    public int getRepository() {
        return repository;
    }

    public void setRepository(int repository) {
        this.repository = repository;
    }
}
