package suwu.daopuerp.presentation.productionbillui.liquid;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import suwu.daopuerp.dto.stock.ProductionBillStockItem;

public class ProductionBillStockItemModel extends RecursiveTreeObject<ProductionBillStockItemModel> {
    ObjectProperty<ProductionBillStockItem> productionBillStockItemObjectProperty;

    public ProductionBillStockItemModel(ProductionBillStockItem productionBillStockItem) {
        this.productionBillStockItemObjectProperty = new SimpleObjectProperty<>(productionBillStockItem);
    }

    public ProductionBillStockItem getProductionBillStockItemObjectProperty() {
        return productionBillStockItemObjectProperty.get();
    }

    public ObjectProperty<ProductionBillStockItem> productionBillStockItemObjectPropertyProperty() {
        return productionBillStockItemObjectProperty;
    }

    public void setProductionBillStockItemObjectProperty(ProductionBillStockItem productionBillStockItemObjectProperty) {
        this.productionBillStockItemObjectProperty.set(productionBillStockItemObjectProperty);
    }
}
