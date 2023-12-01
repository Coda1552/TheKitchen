package codyhuh.thekitchen;

import codyhuh.thekitchen.registry.ModEntities;
import codyhuh.thekitchen.registry.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TheKitchen.MOD_ID)
public class TheKitchen {
    public static final String MOD_ID = "thekitchen";

    public TheKitchen() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntities.ENTITIES.register(bus);
        ModItems.ITEMS.register(bus);

        bus.addListener(this::populateTabs);
    }

    private void populateTabs(BuildCreativeModeTabContentsEvent e) {
        if (e.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            e.accept(ModItems.MOUSE_SPAWN_EGG.get());
        }
    }
}
