package suwu.daopuerp.presentation.productionbillui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import suwu.daopuerp.dto.productionbill.ProductionBillItem;

public class ProductionBillItemModel extends RecursiveTreeObject<ProductionBillItemModel> {
    ObjectProperty<ProductionBillItem> productionBillItemObjectProperty;

    public ProductionBillItemModel(ProductionBillItem productionBillItem) {
        this.productionBillItemObjectProperty = new SimpleObjectProperty<>(productionBillItem);
    }

    public ProductionBillItem getProductionBillItemObjectProperty() {
        return productionBillItemObjectProperty.get();
    }

    public ObjectProperty<ProductionBillItem> productionBillItemObjectPropertyProperty() {
        return productionBillItemObjectProperty;
    }

    public void setProductionBillItemObjectProperty(ProductionBillItem productionBillItemObjectProperty) {
        this.productionBillItemObjectProperty.set(productionBillItemObjectProperty);
    }
}
