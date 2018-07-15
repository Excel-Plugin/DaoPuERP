package suwu.daopuerp.presentation.helpui;

import com.jfoenix.controls.JFXDialog;

import java.util.Stack;

public class DialogStack {
    private Stack<JFXDialog> dialogStack = new Stack<>();

    public DialogStack push(JFXDialog dialog) {
        dialogStack.push(dialog);
        return this;
    }

    public DialogStack pushAndShow(JFXDialog dialog) {
//        if (!dialogStack.empty()) {
//            dialogStack.peek().close();
//        }
        dialogStack.push(dialog);
        dialog.show();
        return this;
    }

    public DialogStack closeCurrentAndPopAndShowNext() {
        if (!dialogStack.empty()) {
            dialogStack.pop().close();
        }
//        if (!dialogStack.empty()) {
//            dialogStack.peek().show();
//        }
        return this;
    }

    public DialogStack closeTop() {
        if (!dialogStack.empty()) {
            dialogStack.peek().close();
        }
        return this;
    }

    public DialogStack pop() {
        if (!dialogStack.empty()) {
            dialogStack.pop();
        }
        return this;
    }

    public JFXDialog getTop() {
        return dialogStack.empty() ? null : dialogStack.peek();
    }

}
