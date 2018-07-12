package trapx00.lightx00.shared.queryvo;

import trapx00.lightx00.shared.po.employee.EmployeePo;

public class SpecificUserAccountQueryVo<T extends EmployeePo> extends BaseQueryVo<T, String, SpecificUserAccountQueryVo<T>> {
    public SpecificUserAccountQueryVo() {
    }

    public SpecificUserAccountQueryVo(BaseQueryVo another) {
        super(another);
    }
}
