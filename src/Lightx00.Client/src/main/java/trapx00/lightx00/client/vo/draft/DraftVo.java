package trapx00.lightx00.client.vo.draft;

import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.po.draft.DraftPo;
import trapx00.lightx00.shared.po.draft.DraftType;

import java.util.Date;

public class DraftVo {
    private Date saveTime;
    private int id;
    private EmployeeVo saver;
    private Draftable draft;
    private DraftType draftType;

    public DraftVo(int id, Date saveTime, EmployeeVo saver, Draftable draft, DraftType draftType) {
        this.id = id;
        this.saveTime = saveTime;
        this.saver = saver;
        this.draft = draft;
        this.draftType = draftType;
    }

    public DraftVo(DraftPo draftPo) {
        this.saveTime = draftPo.getSaveTime();
        this.id = draftPo.getId();

        this.draftType = draftPo.getDraftType();

        this.draft = DraftableQueryServiceRegistry.queryDraftable(draftType, draftPo.getDraftableId());
        //this.saver = query client
    }


    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmployeeVo getSaver() {
        return saver;
    }

    public void setSaver(EmployeeVo saver) {
        this.saver = saver;
    }

    public Draftable getDraft() {
        return draft;
    }

    public void setDraft(Draftable draft) {
        this.draft = draft;
    }

    public DraftType getDraftType() {
        return draftType;
    }

    void setDraftType(DraftType draftType) {
        this.draftType = draftType;
    }

}
