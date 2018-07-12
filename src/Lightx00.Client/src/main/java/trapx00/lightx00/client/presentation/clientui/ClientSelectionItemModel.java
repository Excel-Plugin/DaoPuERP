package trapx00.lightx00.client.presentation.clientui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.client.vo.salestaff.ClientVo;

public class ClientSelectionItemModel extends RecursiveTreeObject<ClientSelectionItemModel> {
    private ObjectProperty<ClientVo> clientVoObjectProperty;

    public ClientSelectionItemModel(ClientVo clientVo) {
        this.clientVoObjectProperty = new SimpleObjectProperty<>(clientVo);
    }

    public ClientVo getClientVoObjectProperty() {
        return clientVoObjectProperty.get();
    }

    public void setClientVoObjectProperty(ClientVo clientVoObjectProperty) {
        this.clientVoObjectProperty.set(clientVoObjectProperty);
    }

    public ObjectProperty<ClientVo> clientVoObjectPropertyProperty() {
        return clientVoObjectProperty;
    }
}
