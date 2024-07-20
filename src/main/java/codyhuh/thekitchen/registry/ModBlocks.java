package codyhuh.thekitchen.registry;

import codyhuh.thekitchen.TheKitchen;
import codyhuh.thekitchen.common.blocks.BreadcrumbTrailBlock;
import codyhuh.thekitchen.common.blocks.LittlePlatterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TheKitchen.MOD_ID);

    public static final RegistryObject<Block> BREADCRUMBS = BLOCKS.register("breadcrumbs", () -> new BreadcrumbTrailBlock(BlockBehaviour.Properties.of().noCollission().instabreak().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> LITTLE_PLATTER = BLOCKS.register("little_platter", () -> new LittlePlatterBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHER_BRICKS).instabreak().pushReaction(PushReaction.DESTROY)));
}
