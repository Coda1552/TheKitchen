package codyhuh.thekitchen.common;

import codyhuh.thekitchen.TheKitchen;
import codyhuh.thekitchen.common.entities.Mouse;
import codyhuh.thekitchen.common.entities.Pinkie;
import codyhuh.thekitchen.registry.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheKitchen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void createAttributes(EntityAttributeCreationEvent e) {
        e.put(ModEntities.MOUSE.get(), Mouse.createMouseAttributes().build());
        e.put(ModEntities.PINKIE.get(), Pinkie.createPinkieAttributes().build());
    }
}
