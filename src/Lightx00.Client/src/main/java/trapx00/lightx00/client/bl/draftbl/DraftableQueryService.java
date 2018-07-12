package trapx00.lightx00.client.bl.draftbl;

import trapx00.lightx00.client.vo.Draftable;

public interface DraftableQueryService {
    /**
     * Queries draft with id.
     * @param id id
     * @return draft
     */
    Draftable queryDraft(String id);
}
