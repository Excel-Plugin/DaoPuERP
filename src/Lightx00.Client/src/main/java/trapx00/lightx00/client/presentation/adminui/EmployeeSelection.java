package trapx00.lightx00.client.presentation.adminui;

import trapx00.lightx00.client.vo.EmployeeVo;

import java.util.List;
import java.util.function.Consumer;

public interface EmployeeSelection {
    void showEmployeeSelectDialog(Consumer<List<EmployeeVo>> callback);

    EmployeeVo queryId(String id);
}
