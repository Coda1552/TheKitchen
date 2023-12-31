package codyhuh.thekitchen.client;

import codyhuh.thekitchen.TheKitchen;
import codyhuh.thekitchen.client.models.MouseModel;
import codyhuh.thekitchen.client.models.PinkyModel;
import codyhuh.thekitchen.client.renders.MouseRenderer;
import codyhuh.thekitchen.client.renders.PinkyRenderer;
import codyhuh.thekitchen.registry.ModEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheKitchen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenders(EntityRenderersEvent.RegisterRenderers e) {
        e.registerEntityRenderer(ModEntities.MOUSE.get(), MouseRenderer::new);
        e.registerEntityRenderer(ModEntities.PINKY.get(), PinkyRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(MouseModel.LAYER_LOCATION, MouseModel::createBodyLayer);
        e.registerLayerDefinition(PinkyModel.LAYER_LOCATION, PinkyModel::createBodyLayer);
    }
}
