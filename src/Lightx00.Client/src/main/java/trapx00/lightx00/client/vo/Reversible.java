package trapx00.lightx00.client.vo;

import trapx00.lightx00.client.presentation.helpui.ReversibleUi;

public interface Reversible {

    /**
     * When it is called, it returns a ReversibleUi which can be used to acquire the ui component and controller.
     * @return reversible ui service.
     */
    ReversibleUi reversibleUi();
}
