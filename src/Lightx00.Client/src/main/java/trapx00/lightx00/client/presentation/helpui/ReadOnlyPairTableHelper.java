package trapx00.lightx00.client.presentation.helpui;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.util.Map;

@SuppressWarnings("unchecked")
public class ReadOnlyPairTableHelper {
    private JFXTreeTableView<Pair> table = new JFXTreeTableView<>();
    private ObservableList<Pair> contentList = FXCollections.observableArrayList();
    private JFXTreeTableColumn<Pair, String> keyColumn = new JFXTreeTableColumn<>();
    private JFXTreeTableColumn<Pair, String> valueColumn = new JFXTreeTableColumn<>();

    public static ReadOnlyPairTableHelper start(Pair... contentList) {
        return new ReadOnlyPairTableHelper(contentList);
    }

    public static ReadOnlyPairTableHelper start() {
        return new ReadOnlyPairTableHelper();
    }


    public ReadOnlyPairTableHelper addPair(String key, String value) {
        contentList.add(new Pair(key, value));
        return this;
    }

    public ReadOnlyPairTableHelper addMap(Map<String, String> dict) {
        for (Map.Entry<String, String> s : dict.entrySet()) {
            contentList.add(new Pair(s.getKey(), s.getValue()));
        }
        return this;
    }

    public ReadOnlyPairTableHelper(Pair... contentList) {
        this();
        this.contentList.addAll(contentList);
    }

    public ReadOnlyPairTableHelper() {
        keyColumn.setText("键");
        valueColumn.setText("值");
        keyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().key));
        valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().value));
    }


    public JFXTreeTableView create() {
        TreeItem<Pair> root = new RecursiveTreeItem<>(contentList, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.getColumns().setAll(keyColumn, valueColumn);
        table.setEditable(false);
        table.setShowRoot(false);
        return table;
    }


}

class Pair extends RecursiveTreeObject<Pair> {
    public String key;

    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String value;
}
