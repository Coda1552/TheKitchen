package codyhuh.thekitchen.registry;

import codyhuh.thekitchen.TheKitchen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheKitchen.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_TAB = CREATIVE_TABS.register("the_kitchen_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + TheKitchen.MOD_ID))
                    .icon(ModItems.CHEESE.get()::getDefaultInstance)
                    .displayItems((displayParams, output) -> {
                        for (var item : ModItems.ITEMS.getEntries()) {
                            output.accept(item.get());
                        }
                    })
                    .build()
    );
}
