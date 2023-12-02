package codyhuh.thekitchen.client.renders;

import codyhuh.thekitchen.TheKitchen;
import codyhuh.thekitchen.client.models.PinkyModel;
import codyhuh.thekitchen.common.entities.Pinky;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PinkyRenderer extends MobRenderer<Pinky, PinkyModel<Pinky>> {
    private static final ResourceLocation LOC = new ResourceLocation(TheKitchen.MOD_ID, "textures/entity/pinky.png");

    public PinkyRenderer(EntityRendererProvider.Context cntxt) {
        super(cntxt, new PinkyModel<>(cntxt.bakeLayer(PinkyModel.LAYER_LOCATION)), 0.1F);
    }

    public ResourceLocation getTextureLocation(Pinky entity) {
        return LOC;
    }
}