package codyhuh.thekitchen.client.renders;

import codyhuh.thekitchen.TheKitchen;
import codyhuh.thekitchen.client.models.MouseModel;
import codyhuh.thekitchen.common.entities.Mouse;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MouseRenderer extends MobRenderer<Mouse, MouseModel<Mouse>> {
    private static final ResourceLocation LOC = new ResourceLocation(TheKitchen.MOD_ID, "textures/entity/mouse.png");

    public MouseRenderer(EntityRendererProvider.Context cntxt) {
        super(cntxt, new MouseModel<>(cntxt.bakeLayer(MouseModel.LAYER_LOCATION)), 0.2F);
    }

    public ResourceLocation getTextureLocation(Mouse entity) {
        return LOC;
    }
}