package codyhuh.thekitchen.registry;

import codyhuh.thekitchen.TheKitchen;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TheKitchen.MOD_ID);

    //Food
    public static final RegistryObject<Item> LITTLE_PLATTER = ITEMS.register("little_platter", () -> new BlockItem(ModBlocks.LITTLE_PLATTER.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
    public static final RegistryObject<Item> BREADCRUMBS = ITEMS.register("breadcrumbs", () -> new BlockItem(ModBlocks.BREADCRUMBS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CITRUS = ITEMS.register("citrus", () -> new Item(new Item.Properties()));

    // Misc.
    public static final RegistryObject<Item> MOUSE_SPAWN_EGG = ITEMS.register("mouse_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.MOUSE, 0x6a5d56, 0xed848e, new Item.Properties()));
}
