package trapx00.lightx00.client.presentation.clientui;

import trapx00.lightx00.client.vo.salestaff.ClientVo;

import java.util.function.Consumer;

public interface ClientInfoUi {
    /**
     * show the select client dialog
     * @param callback call back function
     */
    void showClientSelectDialog(Consumer<ClientVo> callback);

    /**
     * query the whole clientvo by id
     * @param id
     * @return the whole clientvo
     */
    ClientVo queryById(String id);
}
