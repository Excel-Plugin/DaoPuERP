package trapx00.lightx00.client.presentation.financeui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TradeSituationItem extends RecursiveTreeObject<TradeSituationItem> {
    private StringProperty name;
    private DoubleProperty number;

    public TradeSituationItem(String name, Double number) {
        this.name = new SimpleStringProperty(name);
        this.number = new SimpleDoubleProperty(number);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getNumber() {
        return number.get();
    }

    public DoubleProperty numberProperty() {
        return number;
    }

    public void setNumber(double number) {
        this.number.set(number);
    }
}
