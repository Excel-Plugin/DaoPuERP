package trapx00.lightx00.client.presentation.notificationui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.FrameworkUiManager;
import trapx00.lightx00.client.presentation.helpui.PromptDialogHelper;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.notification.billapproval.BillApprovalNotificationVo;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.util.BillHelper;
import trapx00.lightx00.shared.util.DateHelper;

import java.io.IOException;

@SuppressWarnings("unchecked")
public class BillApprovalNotificationDetailDisplayUiController extends NotificationDetailUi<BillApprovalNotificationVo> {
    public JFXButton btnAcknowledge;
    public JFXButton btnModify;
    public JFXButton btnBack;
    public JFXTextField tfDate;
    public JFXTextField tfBillId;
    public JFXTextField tfSender;
    public JFXTextField tfId;
    public JFXButton btnAbandon;
    public JFXTextField tfResult;

    private BillVo billVo;
    private BillApprovalNotificationVo notificationVo;

    @Override
    public ExternalLoadedUiPackage showContent(BillApprovalNotificationVo arg) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        BillApprovalNotificationDetailDisplayUiController controller = externalLoadedUiPackage.getController();
        controller.tfId.setText(String.valueOf(arg.getId()));
        controller.tfDate.setText(DateHelper.fromDate(arg.getDate()));
        controller.tfSender.setText(arg.getSender().getName());
        controller.tfBillId.setText(arg.getBill().getId());
        controller.billVo = arg.getBill();
        controller.tfResult.setText(arg.getBill().getState().toString());
        if (arg.getBill().getState().equals(BillState.Rejected)) {
            controller.btnAcknowledge.setDisable(true);
        }
        controller.notificationVo = arg;
        return externalLoadedUiPackage;
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load(){
        return new UiLoader("/fxml/notificationui/BillApprovalNotificationDetailDisplayUi.fxml").loadAndGetPackageWithoutException();
    }

    public void onBtnAcknowledgeClicked(ActionEvent actionEvent) {
        if (onAcknowledgeClicked != null) {
            onAcknowledgeClicked.accept(notificationVo);
            close();
        }
        PromptDialogHelper.start("入账成功！",String.format("单据%s已经被入账！", notificationVo.getBill().getId()))
            .addCloseButton("好","CHECK",null)
            .createAndShow();
        FrameworkUiManager.getFrameworkUiController().refreshNotificationStatus();

    }

    public void onBtnBackClicked(ActionEvent actionEvent) {
        if (onIgnoreClicked != null) {
            onIgnoreClicked.accept(notificationVo);
        }
    }

    private void abandon() {
        notificationVo.operationService().abandon(notificationVo);

    }

    public void onBtnModifyClicked(ActionEvent actionEvent) {
        try {
            String originalId = billVo.getId();
            billVo.setId(BillHelper.refreshIdRequest);
            ExternalLoadedUiPackage uiPackage = billVo.continueWritableUi().continueWriting(billVo);
            billVo.setId(originalId);
            close();
            abandon();
            FrameworkUiManager.switchFunction(uiPackage, "修改单据",true);
            FrameworkUiManager.getFrameworkUiController().refreshNotificationStatus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBtnAbandonClicked(ActionEvent actionEvent) {
        abandon();
        close();
        PromptDialogHelper.start("丢弃成功！",String.format("单据%s已经被丢弃！", notificationVo.getBill().getId()))
            .addCloseButton("好","CHECK",null)
            .createAndShow();
        FrameworkUiManager.getFrameworkUiController().refreshNotificationStatus();
    }

    public void onTfBillIdClicked(MouseEvent mouseEvent) {
        PromptDialogHelper.start("单据详情","")
            .setContent(billVo.billDetailUi().showContent(billVo).getComponent())
            .createAndShow();
    }
}
