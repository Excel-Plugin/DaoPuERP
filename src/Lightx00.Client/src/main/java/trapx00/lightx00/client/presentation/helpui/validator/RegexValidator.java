package trapx00.lightx00.client.presentation.helpui.validator;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class RegexValidator extends ValidatorBase {
    private String regex;
    public RegexValidator(String regex) {
        this.regex = regex;
    }
    /**
     * will evaluate the validation condition once calling validate method
     */
    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            TextInputControl control = (TextInputControl) srcControl.get();
            String text = control.getText();
            hasErrors.set(!text.matches(regex));
        }
    }
}
