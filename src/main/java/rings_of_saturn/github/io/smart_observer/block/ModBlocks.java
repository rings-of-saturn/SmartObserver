package rings_of_saturn.github.io.smart_observer.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import rings_of_saturn.github.io.smart_observer.block.custom.SmartObserverBlock;

import static rings_of_saturn.github.io.smart_observer.SmartObserver.MOD_ID;


public class ModBlocks {
    public static Item createBlockItem(Block block, String name, String MOD_ID){
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static RegistryKey<Block> getBlockKey(String name, String MOD_ID){
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, name));
    }

    public static Block createBlock(Block block, String name, String MOD_ID){
        createBlockItem(block, name, MOD_ID);
        return Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
    }

    public static final Block SMART_OBSERVER = createBlock(new SmartObserverBlock(
                    AbstractBlock.Settings.copy(Blocks.OBSERVER).mapColor(MapColor.DEEPSLATE_GRAY).pistonBehavior(PistonBehavior.BLOCK).registryKey(getBlockKey("smart_observer", MOD_ID))
            ), "smart_observer", MOD_ID
    );

    public static final Item EMPTY = createBlockItem(null, "empty", MOD_ID);



    public static void registerBlocks(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(itemGroup -> itemGroup.add(ModBlocks.SMART_OBSERVER));
    }
}
