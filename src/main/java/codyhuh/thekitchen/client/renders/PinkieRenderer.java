package codyhuh.thekitchen.client.renders;

import codyhuh.thekitchen.TheKitchen;
import codyhuh.thekitchen.client.models.PinkieModel;
import codyhuh.thekitchen.common.entities.Pinkie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PinkieRenderer extends MobRenderer<Pinkie, PinkieModel<Pinkie>> {
    private static final ResourceLocation LOC = new ResourceLocation(TheKitchen.MOD_ID, "textures/entity/pinkie.png");

    public PinkieRenderer(EntityRendererProvider.Context cntxt) {
        super(cntxt, new PinkieModel<>(cntxt.bakeLayer(PinkieModel.LAYER_LOCATION)), 0.1F);
    }

    public ResourceLocation getTextureLocation(Pinkie entity) {
        return LOC;
    }
}