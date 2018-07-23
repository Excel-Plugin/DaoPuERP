package suwu.daopuerp.presentation.mainui;

import suwu.daopuerp.presentation.bomui.BomUiController;
import suwu.daopuerp.presentation.formulaui.FormulaUiController;
import suwu.daopuerp.presentation.productionbillui.ProductionBillUiController;

public class EmployeeUiController extends FrameworkUiController {
    public void initialize() {
        // 必须有。调用基类初始化来初始化基类的元素。
        super.initialize();
        // 如果还需要初始化自己的元素请接着写。
    }

    public void onManageFormulaTemplate() {
        switchFunction(FormulaUiController.class, "管理配方模板", true);
    }

    public void onManageProductionBill() {
        switchFunction(ProductionBillUiController.class, "管理生产原始单", true);
    }

    public void onGenerateTotalBill() {
        switchFunction(BomUiController.class, "生成BOM表", true);
    }

    /**
     * 增加一个HomeUiController后，重写这个方法做到退回主界面。
     */
    @Override
    public void switchBackToHome() {
        switchFunction(EmployeeHomeUiController.class, "主界面", true);
    }
}
