package suwu.daopuerp.presentation.helpui.validator;

public class EmailValidator extends RegexValidator {
    public EmailValidator() {
        super("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
    }
}
