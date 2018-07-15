package suwu.daopuerp.presentation.helpui.validator;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

public class ValidatorHelper {
    private static final String defaultRequiredPrompt = "请输入信息";
    private static final String defaultNumberPrompt = "请输入整数";
    private static final String defaultDoublePrompt = "请输入整数";
    public static void addValidator(Class<? extends ValidatorBase> validateClass, JFXTextField tf, String message) {
        try {
            ValidatorBase base = validateClass.newInstance();
            if (message != null) {
                base.setMessage(message);
            }
            tf.getValidators().add(base);
            tf.focusedProperty().addListener(((observable, oldValue, newValue) -> {
                if (!newValue) {
                    tf.validate();
                }
            }));
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void addDefaultRequiredValidator(JFXTextField textField) {
        addValidator(RequiredFieldValidator.class, textField, defaultRequiredPrompt);
    }

    public static void addDefaultNumberValidator(JFXTextField textField) {
        addValidator(NumberValidator.class, textField, defaultNumberPrompt);
    }

    public static void addDefaultDoubleValidator(JFXTextField textField) {
        addValidator(DoubleValidator.class, textField, defaultDoublePrompt);
    }
}
