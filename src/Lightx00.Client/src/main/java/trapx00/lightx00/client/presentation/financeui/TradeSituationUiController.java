package trapx00.lightx00.client.presentation.financeui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import trapx00.lightx00.client.bl.financebl.factory.TradeSituationBlFactory;
import trapx00.lightx00.client.bl.util.ExcelOutput;
import trapx00.lightx00.client.blservice.financeblservice.TradeSituationBlService;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadableUiController;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.PromptDialogHelper;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.financestaff.TradeSituationVo;
import trapx00.lightx00.shared.util.BillHelper;
import trapx00.lightx00.shared.util.DateHelper;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class TradeSituationUiController implements ExternalLoadableUiController {
    public JFXDatePicker dpStart;
    public JFXDatePicker dpEnd;
    public JFXTreeTableView<TradeSituationItem> tbSituation;
    public JFXTreeTableColumn<TradeSituationItem, String> tcName;
    public JFXTreeTableColumn<TradeSituationItem, String> tcNumber;

    private TradeSituationBlService blService = TradeSituationBlFactory.getController();
    private ObjectProperty<TradeSituationVo> tradeSituation = new SimpleObjectProperty<>();

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/financeui/TradeSituationUi.fxml").loadAndGetPackageWithoutException();
    }


    public void initialize() {
        initTable();
    }

    public void initTable() {
        tcName.setCellValueFactory(x -> x.getValue().getValue().nameProperty());
        tcNumber.setCellValueFactory(x -> new SimpleStringProperty(BillHelper.toFixed(x.getValue().getValue().getNumber())));

        tradeSituation.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showItem(newValue);
            } else {
                showItem(new TradeSituationVo());
            }
        });
    }

    public void showItem(TradeSituationVo situation) {
        TreeItem<TradeSituationItem> income = new TreeItem<>(new TradeSituationItem("折让后总收入", situation.getSaleIncome() + situation.getCommodityIncome()));
        TreeItem<TradeSituationItem> saleIncome = new TreeItem<>(new TradeSituationItem("销售收入", situation.getSaleIncome()));
        TreeItem<TradeSituationItem> commodityIncome = new TreeItem<>(new TradeSituationItem("商品类收入", situation.getCommodityIncome()));
        commodityIncome.getChildren().add(new TreeItem<>(new TradeSituationItem("报溢收入", situation.getOverflowIncome())));
        commodityIncome.getChildren().add(new TreeItem<>(new TradeSituationItem("成本调价", situation.getIncomeAdjustIncome())));
        commodityIncome.getChildren().add(new TreeItem<>(new TradeSituationItem("进货退货差价", situation.getDifferenceOfSaleAndRefundIncome())));
        commodityIncome.getChildren().add(new TreeItem<>(new TradeSituationItem("未使用的代金券", situation.getUnusedCouponValue())));
        TreeItem<TradeSituationItem> promotion = new TreeItem<>(new TradeSituationItem("折让", situation.getPromotion()));
        income.getChildren().addAll(saleIncome, commodityIncome, promotion);

        TreeItem<TradeSituationItem> cost = new TreeItem<>(new TradeSituationItem("支出", situation.getSaleCost() + situation.getCommodityCost()));
        TreeItem<TradeSituationItem> saleCost = new TreeItem<>(new TradeSituationItem("销售支出", situation.getSaleCost()));
        TreeItem<TradeSituationItem> commodityCost = new TreeItem<>(new TradeSituationItem("商品类支出", situation.getCommodityCost()));
        commodityCost.getChildren().add(new TreeItem<>(new TradeSituationItem("商品报损", situation.getLossCost())));
        commodityCost.getChildren().add(new TreeItem<>(new TradeSituationItem("商品赠出", situation.getGiveawayCost())));
        cost.getChildren().addAll(saleCost, commodityCost);

        TreeItem<TradeSituationItem> profit = new TreeItem<>(new TradeSituationItem("利润", situation.getProfit()));

        TreeItem<TradeSituationItem> root = new TreeItem<>();
        root.getChildren().addAll(income, cost ,profit);

        tbSituation.setRoot(root);
        tbSituation.setShowRoot(false);

    }

    public void onRefreshButtonClicked(ActionEvent actionEvent) {
        LocalDate start = dpStart.getValue();
        LocalDate end = dpEnd.getValue();

        if (start == null || end == null) {
            PromptDialogHelper.start("请选择日期","请选择日期！")
                .addCloseButton("好","CHECK", null)
                .createAndShow();
            return;
        }

        TradeSituationVo query = blService.query(DateHelper.fromLocalDate(start), DateHelper.fromLocalDate(end));
        tradeSituation.set(query);
    }

    private String[] toExcel(TradeSituationVo situation) {
        List<String> content = new ArrayList<>();
        content.add("项-值");
        content.add(String.format("销售收入-%.2f", situation.getSaleIncome()));
        content.add(String.format("商品类收入-%.2f", situation.getCommodityIncome()));
        content.add(String.format("报溢收入-%.2f", situation.getOverflowIncome()));
        content.add(String.format("成本调价-%.2f", situation.getIncomeAdjustIncome()));
        content.add(String.format("进货退货差价-%.2f", situation.getDifferenceOfSaleAndRefundIncome()));
        content.add(String.format("未使用的代金券-%.2f", situation.getUnusedCouponValue()));
        content.add(String.format("折让后总收入-%.2f", situation.getSaleIncome() + situation.getCommodityIncome()));
        content.add(String.format("销售支出-%.2f", situation.getSaleCost()));
        content.add(String.format("商品类支出-%.2f", situation.getCommodityCost()));
        content.add(String.format("商品报损-%.2f", situation.getLossCost()));
        content.add(String.format("商品赠出-%.2f", situation.getGiveawayCost()));
        content.add(String.format("支出-%.2f", situation.getSaleCost() + situation.getCommodityCost()));
        content.add(String.format("利润部分-%.2f", situation.getProfit()));
        return content.toArray(new String[content.size()]);
    }

    public void onExportClicked(ActionEvent actionEvent) {
        if (tradeSituation.get() != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择路径");
            fileChooser.setInitialFileName(String.format("经营情况表-%s.xls", DateHelper.currentDateString("yyyy_MM_dd-HH_mm_ss")));
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                ExcelOutput.createExcel(file.getParent(), toExcel(tradeSituation.get()), file.getName());

                blService.export(tradeSituation.get());

                PromptDialogHelper.start("导出成功！",String.format("经营情况表已经导出到%s。", file.getAbsolutePath()))
                    .addCloseButton("好","CHECK",null)
                    .createAndShow();
            }

        } else {
            PromptDialogHelper.start("导出失败！","请先查询报表！")
                .addCloseButton("好","CHECK",null)
                .createAndShow();
        }
    }
}
