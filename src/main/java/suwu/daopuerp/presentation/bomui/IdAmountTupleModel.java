package suwu.daopuerp.presentation.bomui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import suwu.daopuerp.dto.bom.IdAmountTuple;

public class IdAmountTupleModel extends RecursiveTreeObject<IdAmountTupleModel> {
    ObjectProperty<IdAmountTuple> idAmountTupleObjectProperty;

    public IdAmountTupleModel(IdAmountTuple idAmountTuple) {
        this.idAmountTupleObjectProperty = new SimpleObjectProperty<>(idAmountTuple);
    }

    public IdAmountTuple getIdAmountTupleObjectProperty() {
        return idAmountTupleObjectProperty.get();
    }

    public ObjectProperty<IdAmountTuple> idAmountTupleObjectPropertyProperty() {
        return idAmountTupleObjectProperty;
    }
}
