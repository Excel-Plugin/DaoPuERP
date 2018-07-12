package trapx00.lightx00.client.presentation.commodityui.commodity;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlService;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlServiceFactory;
import trapx00.lightx00.client.blservice.commodityblservice.CommoditySortBlService;
import trapx00.lightx00.client.blservice.commodityblservice.CommoditySortBlServiceFactory;
import trapx00.lightx00.client.presentation.commodityui.commoditySort.AddCommoditySortDialog;
import trapx00.lightx00.client.presentation.commodityui.commoditySort.CommoditySortModificationUi;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.presentation.mainui.InventoryStaffUiController;
import trapx00.lightx00.client.vo.inventorystaff.CommoditySortVo;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;
import trapx00.lightx00.shared.queryvo.CommoditySortQueryVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommodityUiController implements ExternalLoadableUiController {


    public JFXButton deleteButton;
    public TreeView<String>tv;
    public JFXButton btnModify;
    public JFXTreeTableView<CommoditySelectionItemModel> commodityTable;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableNameColumn;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableSortColumn;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableAmountColumn;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableIdColumn;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableDateColumn;
    public JFXButton addCommodityButton;

    public JFXTextField tfOperator;
    public JFXTextField tfDate;
    public JFXTextField tfId;
    public JFXTextField tfName;
    public MenuItem addMenuItem;
    public MenuItem modifyMenuItem;
    public MenuItem deleteMenuItem;

    @FXML
    private JFXTextField tfSearch;
    TreeItem<String> root;
    private InventoryStaffUiController inventoryStaffUiController;
    private  String currentSortId;
    TreeItem<String> currentItem;
    private final Node rootIcon = new ImageView(
            new Image("/img/com.png")
    );


    public ObservableList<CommoditySelectionItemModel> commodityModels = FXCollections.observableArrayList();
    private CommodityBlService blService= CommodityBlServiceFactory.getInstance();
    private CommoditySortBlService blService1= CommoditySortBlServiceFactory.getInstance();



    @FXML
    private void initialize() {
        initTable();
        initView();
        initSearch();
    }

    private void initView(){
        root = new TreeItem<>("CommoditySort");
        root.setExpanded(false);
        root.setGraphic(rootIcon);
        tv.setRoot(root);
        tv.setShowRoot(true);
        showGoodsTree();
        tv.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 1) {
                        if(tv.getSelectionModel().getSelectedItem().getValue()==null){

                        }
                        if (tv.getSelectionModel().getSelectedItem().getValue().equals("CommoditySort")) {
                            currentSortId = null;
                            update(new CommodityQueryVo());
                        } else {
                                currentSortId = blService1.query(new CommoditySortQueryVo().eq("name",
                                        tv.getSelectionModel().getSelectedItem().getValue()))[0].getId();
                                update(new CommodityQueryVo().eq("type", currentSortId));
                                currentItem = tv.getSelectionModel().getSelectedItem();
                                update(new CommoditySortQueryVo().eq("id", currentSortId));


                        }
                    }
                }
        );
        addMenuItem.setOnAction((ActionEvent t) -> {
            if(currentSortId==null){
                new AddCommoditySortDialog().show(currentSortId,this::changeroot);
            }else{
                CommoditySortVo selected=blService1.query(new CommoditySortQueryVo().eq("id", currentSortId))[0];
                if(selected.getLeaf()==1){
                    PromptDialogHelper.start("","当前选中分类为叶节点，无法添加子分类").addCloseButton("取消","CLOSE",null)
                            .createAndShow();
                }else{
                    new AddCommoditySortDialog().show(currentSortId,this::change);
                }
            }
        });
        modifyMenuItem.setOnAction((ActionEvent t)->{
            if(currentSortId==null){
                PromptDialogHelper.start("修改失败","该节点不能修改哦~").createAndShow();
            }else{
                CommoditySortVo selected=blService1.query(new CommoditySortQueryVo().eq("id",currentSortId))[0];
                new CommoditySortModificationUi().show(selected,this::modifyItem);
            }
        });
        deleteMenuItem.setOnAction((ActionEvent t) -> {
            if (currentSortId == null) {
                PromptDialogHelper.start("删除失败", "根根根节点不能删除哦~").createAndShow();
            } else {
                CommoditySortVo selected = blService1.query(new CommoditySortQueryVo().eq("id", currentSortId))[0];
                if (selected != null) {
                    PromptDialogHelper.start("确定删除",
                            String.format("您确定要删除商品分类(id: %s, 名称: %s)吗？", selected.getId(), selected.getName()))
                            .addCloseButton("取消", "CLOSE", null)
                            .addButton("确定", "CHECK", e -> {
                                if (!delete())
                                    PromptDialogHelper.start("删除失败", String.format("分类（id:%s）含有分类或商品", selected.getId()))
                                            .addCloseButton("返回", "check", e2 -> {
                                                FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                                            }).createAndShow();
                                else {
                                    blService1.delete(selected);
                                    PromptDialogHelper.start("删除成功", String.format("分类(id: %s)已经删除！", selected.getId()))
                                            .addCloseButton("好", "CHECK", e3 -> {
                                                FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                                                currentItem.getParent().getChildren().remove(currentItem);
                                            })
                                            .createAndShow();
                                }

                            }).createAndShow();
                }
            }
        });



    }
    public  boolean delete() {
        boolean isdelete;
        CommodityVo[] temp = blService.query(new CommodityQueryVo().eq("type", currentSortId));
        CommoditySortVo[] temptemp=blService1.query(new CommoditySortQueryVo().eq("preId",currentSortId));
        if (temp.length==0&&temptemp.length==0)
            isdelete = true;
        else
            isdelete = false;
        return isdelete;
    }

    public void showGoodsTree(){
        try{
            CommoditySortVo[] commoditySortVos = blService1.query(new CommoditySortQueryVo());
            CommoditySortVo t;
            for (CommoditySortVo goodskindsVO : commoditySortVos) {
                t = goodskindsVO;
                if (t.getPreId() == null) {
                    TreeItem<String> treeItem = new TreeItem<>(t.getName());
                    root.getChildren().add(treeItem);
                    treeItem.setExpanded(true);
                    showkinds(treeItem);
                }
            }
        }catch (UncheckedRemoteException e){

        }

    }
    private void showkinds(TreeItem<String> treeItem)  {
        try{
            CommoditySortVo commoditySortVo =blService1.query(new CommoditySortQueryVo().eq("name",treeItem.getValue()))[0];
            CommoditySortVo[] commoditySortVos=blService1.query(new CommoditySortQueryVo().eq("preId",commoditySortVo.getId()));
            if(commoditySortVos != null) {
                for (CommoditySortVo commoditySortVo1:commoditySortVos) {
                    TreeItem<String> treeItem1 = new TreeItem<>(commoditySortVo1.getName());
                    treeItem1.setExpanded(true);
                    treeItem.getChildren().add(treeItem1);
                    showkinds(treeItem1);
                }
            }
        }catch (UncheckedRemoteException e){

        }


    }

    private void initSearch() {
        tfSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                CommodityVo[] clientVos = blService.queryNormally(tfSearch.getText());
                commodityModels.clear();
                commodityModels.addAll(Arrays.stream(clientVos).map(CommoditySelectionItemModel::new).collect(Collectors.toList()));
            }
        });
    }

    private void update() {
        update(new CommodityQueryVo().eq("type",currentSortId));
    }

    private void update(CommodityQueryVo queryVo) {
        CommodityVo[] queryResult = blService.query(queryVo);
        commodityModels.clear();
        commodityModels.addAll(Arrays.stream(queryResult).map(CommoditySelectionItemModel::new).collect(Collectors.toList()));
    }

    private void update(CommoditySortQueryVo queryVo) {
        CommoditySortVo queryResult = blService1.query(queryVo)[0];
        tfId.setText(queryResult.getId());
        tfName.setText(queryResult.getName());
        if(queryResult.getPreId()==null)
            tfOperator.setText("");
        else
            tfOperator.setText(blService1.query(new CommoditySortQueryVo().eq("id",queryResult.getPreId()))[0].getName());
        tfDate.setText((queryResult.getLeaf()==1 ? "是":"不是"));
    }

    public void change(){
        CommoditySortVo[] commoditySortVo=blService1.query(new CommoditySortQueryVo().eq("preId",currentSortId));
        currentItem.getChildren().remove(0,currentItem.getChildren().size());
        showkinds(currentItem);
    }
    public void changeroot(){
        root.getChildren().remove(0,root.getChildren().size());
        showGoodsTree();
    }
    public void modifyItem(){
        TreeItem treeItem=new TreeItem<String>(blService1.query(new CommoditySortQueryVo().eq("id",currentSortId
        ))[0].getName());
        currentItem.getParent().getChildren().add(treeItem);
        currentItem.getParent().getChildren().remove(currentItem);
        showkinds(treeItem);
    }

    @FXML
    private void initTable() {
        tableDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DateHelper.fromDate(cellData.getValue().getValue().getCommodityVoObjectProperty().getProductionDate())));
        tableNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityVoObjectProperty().getName()));
        tableIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCommodityVoObjectProperty().getId())));
        tableAmountColumn.setCellValueFactory(cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCommodityVoObjectProperty().getAmount())));
        tableSortColumn.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getValue().getCommodityVoObjectProperty().getType()));
        commodityTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
               new CommodityDetailUi().show(getSelected(),this::update);
            }
        });

        TreeItem<CommoditySelectionItemModel> root = new RecursiveTreeItem<>(commodityModels, RecursiveTreeObject::getChildren);
        commodityTable.setRoot(root);
        commodityTable.setShowRoot(false);
        commodityTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //支持多选，没有这句话就是不支持

    }




    public void onDeleteButtonClicked(ActionEvent actionEvent) {
        CommodityVo selected=getSelected();
        if (selected != null) {
            new PromptDialogHelper("确定删除",
                    String.format("您确定要删除商品(id: %s, 名称: %s)吗？", selected.getId(), selected.getName()))
                    .addCloseButton("取消","CLOSE",null)
                    .addButton("确定","CHECK",e -> {
                        if(selected.getAmount()==0){
                            blService.delete(selected);
                            new PromptDialogHelper("删除成功",String.format("商品(id: %s)已经删除！", selected.getId()))
                                    .addCloseButton("好","CHECK", e2 -> {
                                        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                                        update();
                                    })
                                    .createAndShow();
                        }else{
                            new PromptDialogHelper("删除失败",String.format("商品(id: %s)在库存中仍有存货", selected.getId()))
                                    .addCloseButton("好","CHECK", e2 -> {
                                        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                                        update();
                                    })
                                    .createAndShow();
                        }

                    })
                    .createAndShow();
        }
    }

    public void onAddButtonClicked(ActionEvent actionEvent){
        if(currentSortId==null){
            PromptDialogHelper.start("添加失败","当前分类不是叶节点分类无法添加商品").addCloseButton("确定","CHECK",null)
                    .createAndShow();
        }
        else if(blService1.query(new CommoditySortQueryVo().idEq(currentSortId))[0].getLeaf()==1){
            new AddCommodityDialog().show(currentSortId,this::update);
        }
        else{
            PromptDialogHelper.start("添加失败","当前分类不是叶节点分类无法添加商品").addCloseButton("确定","CHECK",null)
                    .createAndShow();
        }

    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/commodity/CommodityUi.fxml").loadAndGetPackageWithoutException();
    }

    public CommodityVo getSelected() {
        try {
            return commodityTable.getSelectionModel().getSelectedItem().getValue().getCommodityVoObjectProperty();
        } catch (Exception e) {
            new PromptDialogHelper("未选择！","请先选择一个商品！")
                    .addCloseButton("好","CHECK", null)
                    .createAndShow();
            return null;
        }

    }

    public void onBtnModifyClicked(ActionEvent actionEvent) {
        CommodityVo selected = getSelected();
        if (selected != null) {
            new CommodityModificationUi().show(selected, this::update);
        }
    }


}
