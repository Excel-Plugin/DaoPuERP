package trapx00.lightx00.client.presentation.clientui;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.salestaff.ClientVo;

public class ClientDetailUiController extends ClientDetailUi implements ExternalLoadableUiController {
    @FXML
    private JFXTextField clientId;
    @FXML
    private JFXTextField clientName;
    @FXML
    private JFXTextField clientType;
    @FXML
    private JFXTextField clientLevel;
    @FXML
    private JFXTextField clientPhone;
    @FXML
    private JFXTextField clientAddress;
    @FXML
    private JFXTextField clientZipCode;
    @FXML
    private JFXTextField clientEmail;
    @FXML
    private JFXTextField clientReceivableQuota;
    @FXML
    private JFXTextField clientReceivable;
    @FXML
    private JFXTextField clientPayable;
    @FXML
    private JFXTextField clientDefaultOperator;

    @Override
    public ExternalLoadedUiPackage showContent(ClientVo arg) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        ClientVo clientVo = arg;
        ClientDetailUiController clientDetailUiController = externalLoadedUiPackage.getController();
        clientDetailUiController.clientId.setText(clientVo.getId());
        clientDetailUiController.clientName.setText(clientVo.getName());
        clientDetailUiController.clientType.setText(clientVo.getClientType().toString());
        clientDetailUiController.clientLevel.setText(clientVo.getClientLevel() + "");
        clientDetailUiController.clientPhone.setText(clientVo.getPhone());
        clientDetailUiController.clientAddress.setText(clientVo.getAddress());
        clientDetailUiController.clientZipCode.setText(clientVo.getZipCode());
        clientDetailUiController.clientEmail.setText(clientVo.getEmail());
        clientDetailUiController.clientReceivableQuota.setText(clientVo.getReceivableQuota() + "");
        clientDetailUiController.clientReceivable.setText(clientVo.getReceivable() + "");
        clientDetailUiController.clientPayable.setText(clientVo.getPayable() + "");
        clientDetailUiController.clientDefaultOperator.setText(clientVo.getDefaultOperatorId());
        return externalLoadedUiPackage;
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/clientui/ClientDetailUi.fxml").loadAndGetPackageWithoutException();
    }

    @FXML
    private void onBtnCancelClicked() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}
