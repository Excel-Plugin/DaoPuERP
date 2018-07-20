package suwu.daopuerp.presentation.stockui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import suwu.daopuerp.dto.stock.StockItem;

public class StockItemModel extends RecursiveTreeObject<StockItemModel> {
    ObjectProperty<StockItem> stockItemObjectProperty;

    public StockItemModel() {
    }

    public StockItemModel(StockItem stockItem) {
        this.stockItemObjectProperty = new SimpleObjectProperty<>(stockItem);
    }

    public StockItem getStockItemObjectProperty() {
        return stockItemObjectProperty.get();
    }

    public ObjectProperty<StockItem> stockItemObjectPropertyProperty() {
        return stockItemObjectProperty;
    }

    public void setStockItemObjectProperty(StockItem stockItemObjectProperty) {
        this.stockItemObjectProperty.set(stockItemObjectProperty);
    }
}
