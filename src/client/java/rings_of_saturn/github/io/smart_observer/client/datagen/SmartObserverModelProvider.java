package rings_of_saturn.github.io.smart_observer.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import rings_of_saturn.github.io.smart_observer.block.ModBlocks;

import static net.minecraft.data.client.BlockStateModelGenerator.createBooleanModelMap;
import static net.minecraft.data.client.BlockStateModelGenerator.createNorthDefaultRotationStates;

public class SmartObserverModelProvider extends FabricModelProvider {
    public SmartObserverModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        Identifier identifier = ModelIds.getBlockModelId(ModBlocks.SMART_OBSERVER);
        Identifier identifier2 = ModelIds.getBlockSubModelId(ModBlocks.SMART_OBSERVER, "_on");
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.SMART_OBSERVER).coordinate(createBooleanModelMap(Properties.POWERED, identifier2, identifier)).coordinate(createNorthDefaultRotationStates()));

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
