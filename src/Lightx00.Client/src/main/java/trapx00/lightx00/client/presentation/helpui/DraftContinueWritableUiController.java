package trapx00.lightx00.client.presentation.helpui;

import trapx00.lightx00.client.vo.Draftable;

import java.io.IOException;

/**
 * 可用于继续填写草稿的UI控制器的接口。
 * @param <T> 草稿具体类型
 */
public interface DraftContinueWritableUiController<T extends Draftable> {
    /**
     * Start continuing write a draft. Returns a External loaded ui package.
     * Overrides to return a specific ui controller.
     * @param draft draft
     * @return External loaded ui package including a ExternalLoadableUiController and the component.
     */
    ExternalLoadedUiPackage continueWriting(T draft) throws IOException;

}
