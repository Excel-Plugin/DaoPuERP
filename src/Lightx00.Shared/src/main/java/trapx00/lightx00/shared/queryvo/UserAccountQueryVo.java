package trapx00.lightx00.shared.queryvo;

import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class UserAccountQueryVo extends BaseQueryVo<EmployeePo,String, UserAccountQueryVo> implements Serializable {

    private HashMap<EmployeePosition, SpecificUserAccountQueryVo> specificUserAccountQueryVoHashMap = new HashMap<>();

    public UserAccountQueryVo addQueryVoForOneEmployeePosition(EmployeePosition employeePosition, SpecificUserAccountQueryVo specificUserAccountQueryVo) {
        specificUserAccountQueryVoHashMap.put(employeePosition, specificUserAccountQueryVo);
        return this;
    }

    public SpecificUserAccountQueryVo getQueryVoForPosition(EmployeePosition employeePosition) {
        return specificUserAccountQueryVoHashMap.get(employeePosition);
    }

    public UserAccountQueryVo addQueryVoForAllEmployeePosition(SpecificUserAccountQueryVo queryVo) {
        for (EmployeePosition position : EmployeePosition.values()) {
            specificUserAccountQueryVoHashMap.put(position, queryVo);
        }
        return this;
    }

    @Override
    public UserAccountQueryVo idEq(String id) {
        return addQueryVoForAllEmployeePosition((SpecificUserAccountQueryVo) new SpecificUserAccountQueryVo().idEq(id));
    }
}
