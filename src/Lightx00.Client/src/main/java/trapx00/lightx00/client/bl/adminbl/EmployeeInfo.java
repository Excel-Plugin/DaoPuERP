package trapx00.lightx00.client.bl.adminbl;

import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

public interface EmployeeInfo {
    /**
     * Queries EmployeeVo with queryvo.
     *
     * @param queryVo query vo
     * @return all employeevos that match condition. if 0 return new EmployeeVo[]
     */
    EmployeeVo[] queryEmployee(UserAccountQueryVo queryVo);

    /**
     * Queries EmployeeVo by id.
     *
     * @param id id
     * @return EmployeeVo if found. Null otherwise.
     */
    EmployeeVo queryById(String id);
}
