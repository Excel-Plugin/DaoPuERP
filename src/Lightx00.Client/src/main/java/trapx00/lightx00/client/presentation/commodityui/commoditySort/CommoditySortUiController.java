package trapx00.lightx00.client.presentation.commodityui.commoditySort;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlService;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlServiceFactory;
import trapx00.lightx00.client.blservice.commodityblservice.CommoditySortBlService;
import trapx00.lightx00.client.blservice.commodityblservice.CommoditySortBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.inventorystaff.CommoditySortVo;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;
import trapx00.lightx00.shared.queryvo.CommoditySortQueryVo;

public class CommoditySortUiController  implements ExternalLoadableUiController {
    public JFXButton deleteButton;
    public TreeView<String> tv;
    public JFXButton addCommodityButton;
    public JFXTextField tfSearch;
    public JFXTextField tfOperator;
    public JFXTextField tfDate;
    public JFXTextField tfId;
    public JFXTextField tfName;
    public JFXListView<Label> list;

    TreeItem<String> root;
    String currentId;
    TreeItem<String> currentItem;
    private CommoditySortBlService blService= CommoditySortBlServiceFactory.getInstance();
    private CommodityBlService blService1=CommodityBlServiceFactory.getInstance();
    private final Node rootIcon = new ImageView(
            new Image("/img/com.png")
    );
    @FXML
    private void initialize() {
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
        for(int i = 0 ; i < 4 ; i++) list.getItems().add(new Label("Item " + i));
        list.getStyleClass().add("mylistview");

        tv.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                if(tv.getSelectionModel().getSelectedItem().getValue().equals("CommoditySort"))
                {
                    currentId=null;
                }
                else {
                    currentId = blService.query(new CommoditySortQueryVo().eq("name",
                            tv.getSelectionModel().getSelectedItem().getValue()))[0].getId();
                    currentItem = tv.getSelectionModel().getSelectedItem();
                    update(new CommoditySortQueryVo().eq("id", currentId));
                }
            }
        });
    }
    public void showGoodsTree(){
        try{
            CommoditySortVo[] commoditySortVos = blService.query(new CommoditySortQueryVo());
            CommoditySortVo t;
            for (CommoditySortVo goodskindsVO : commoditySortVos) {
                t = goodskindsVO;
                if (t.getPreId() == null) {
                    TreeItem<String> treeItem = new TreeItem<>(t.getName());
                    treeItem.setGraphic(rootIcon);
                    root.getChildren().add(treeItem);
                    treeItem.setExpanded(false);
                    showkinds(treeItem);
                }
            }
        }catch (UncheckedRemoteException e){
        }

    }

    private void showkinds(TreeItem<String> treeItem)  {
        try{
            CommoditySortVo commoditySortVo =blService.query(new CommoditySortQueryVo().eq("name",treeItem.getValue()))[0];
            CommoditySortVo[] commoditySortVos=blService.query(new CommoditySortQueryVo().eq("preId",commoditySortVo.getId()));
            if(commoditySortVos != null) {
                for (CommoditySortVo commoditySortVo1:commoditySortVos) {
                    TreeItem<String> treeItem1 = new TreeItem<>(commoditySortVo1.getName());
                    treeItem1.setExpanded(false);
                    treeItem.getChildren().add(treeItem1);
                    showkinds(treeItem1);
                }
            }
        }catch (UncheckedRemoteException e){

        }


    }

    private void initSearch() {
        tfSearch.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                if (tfSearch.getText().length() > 0) {
                    update(new CommoditySortQueryVo().eq("id", tfSearch.getText()));
                } else {
                    update();
                }

            }
        });
    }
    private void update() {
        update(new CommoditySortQueryVo());
    }

    private void update(CommoditySortQueryVo queryVo) {
        CommoditySortVo queryResult = blService.query(queryVo)[0];
        tfId.setText(queryResult.getId());
        tfName.setText(queryResult.getName());
        if(queryResult.getPreId()==null)
            tfOperator.setText("");
        else
            tfOperator.setText(blService.query(new CommoditySortQueryVo().eq("id",queryResult.getPreId()))[0].getName());
        tfDate.setText((queryResult.getLeaf()==1 ? "是":"不是"));
    }

    public  boolean delete() {
        boolean isdelete;
        CommodityVo[] temp = blService1.query(new CommodityQueryVo().eq("type", currentId));
        CommoditySortVo[] temptemp=blService.query(new CommoditySortQueryVo().eq("preId",currentId));
        if (temp.length==0&&temptemp.length==0)
            isdelete = true;
        else
            isdelete = false;
        return isdelete;
    }



    public void onDeleteButtonClicked(ActionEvent actionEvent) {
        CommoditySortVo selected=blService.query(new CommoditySortQueryVo().eq("id", currentId))[0];
        if (selected != null) {
             PromptDialogHelper.start("确定删除",
                    String.format("您确定要删除商品分类(id: %s, 名称: %s)吗？", selected.getId(), selected.getName()))
                    .addCloseButton("取消","CLOSE",null)
                    .addButton("确定","CHECK",e -> {
                        if(!delete())
                             PromptDialogHelper.start("删除失败",String.format("分类（id:%s）含有分类或商品",selected.getId()))
                                    .addCloseButton("返回","check",e2->{
                                        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                        }).createAndShow();
                        else{
                                blService.delete(selected);
                            PromptDialogHelper.start("删除成功",String.format("分类(id: %s)已经删除！", selected.getId()))
                                        .addCloseButton("好","CHECK", e3 -> {
                                            FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                                            currentItem.getParent().getChildren().remove(currentItem);
                                        })
                                        .createAndShow();
                            }

                    }).createAndShow();
        }

    }
    public void onAddButtonClicked(ActionEvent actionEvent){
        if(currentId==null){
            new AddCommoditySortDialog().show(currentId,this::changeroot);
        }else{
            CommoditySortVo selected=blService.query(new CommoditySortQueryVo().eq("id", currentId))[0];
            if(selected.getLeaf()==1){
                PromptDialogHelper.start("","当前选中分类为叶节点，无法添加子分类").addCloseButton("取消","CLOSE",null)
                        .createAndShow();
            }else{
                new AddCommoditySortDialog().show(currentId,this::change);
            }
        }

    }
    public void change(){
        CommoditySortVo[] commoditySortVo=blService.query(new CommoditySortQueryVo().eq("preId",currentId));
            currentItem.getChildren().remove(0,currentItem.getChildren().size());
        showkinds(currentItem);
    }
    public void changeroot(){
        root.getChildren().remove(0,root.getChildren().size());
        showGoodsTree();
    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/commoditysort/CommoditySortUi.fxml").loadAndGetPackageWithoutException();
    }

    public void onBtnModifyClicked(ActionEvent actionEvent) {
       ;
    }
}
