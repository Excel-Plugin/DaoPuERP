package trapx00.lightx00.client.bl.promotionbl;

import trapx00.lightx00.client.bl.util.PoVoConverter;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.shared.po.manager.promotion.PromotionPoBase;

public interface PromotionPoVoConverter<PromotionPo extends PromotionPoBase, PromotionVo extends PromotionVoBase> extends PoVoConverter<PromotionPo,PromotionVo> {
}
