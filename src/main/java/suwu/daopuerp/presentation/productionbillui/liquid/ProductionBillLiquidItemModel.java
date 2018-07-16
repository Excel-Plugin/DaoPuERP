package suwu.daopuerp.presentation.productionbillui.liquid;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import suwu.daopuerp.dto.formula.FormulaItem;

public class ProductionBillLiquidItemModel extends RecursiveTreeObject<ProductionBillLiquidItemModel> {
    ObjectProperty<FormulaItem> formulaItemObjectProperty;

    public ProductionBillLiquidItemModel() {
    }

    public ProductionBillLiquidItemModel(FormulaItem formulaItem) {
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
