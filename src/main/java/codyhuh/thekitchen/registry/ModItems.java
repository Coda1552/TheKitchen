package codyhuh.thekitchen.registry;

import codyhuh.thekitchen.TheKitchen;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TheKitchen.MOD_ID);

    public static final RegistryObject<Item> MOUSE_SPAWN_EGG = ITEMS.register("mouse_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.MOUSE, 0x6a5d56, 0xed848e, new Item.Properties()));
}
