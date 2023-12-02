package codyhuh.thekitchen.registry;

import codyhuh.thekitchen.TheKitchen;
import codyhuh.thekitchen.common.blocks.BreadcrumbTrailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TheKitchen.MOD_ID);

    public static final RegistryObject<Block> BREADCRUMBS = BLOCKS.register("breadcrumbs", () -> new BreadcrumbTrailBlock(BlockBehaviour.Properties.of().noCollission().instabreak().pushReaction(PushReaction.DESTROY)));
}
