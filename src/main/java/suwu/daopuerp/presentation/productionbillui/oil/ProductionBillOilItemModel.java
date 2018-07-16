package suwu.daopuerp.presentation.productionbillui.oil;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import suwu.daopuerp.dto.formula.FormulaItem;

public class ProductionBillOilItemModel extends RecursiveTreeObject<ProductionBillOilItemModel> {
    ObjectProperty<FormulaItem> formulaItemObjectProperty;

    public ProductionBillOilItemModel() {
    }

    public ProductionBillOilItemModel(FormulaItem formulaItem) {
        this.formulaItemObjectProperty = new SimpleObjectProperty<>(formulaItem);
    }

    public FormulaItem getFormulaItemObjectProperty() {
        return formulaItemObjectProperty.get();
    }

    public ObjectProperty<FormulaItem> formulaItemObjectPropertyProperty() {
        return formulaItemObjectProperty;
    }

    public void setFormulaItemObjectProperty(FormulaItem formulaItemObjectProperty) {
        this.formulaItemObjectProperty.set(formulaItemObjectProperty);
    }
}
