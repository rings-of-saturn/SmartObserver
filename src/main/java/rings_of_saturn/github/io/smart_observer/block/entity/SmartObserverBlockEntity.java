package rings_of_saturn.github.io.smart_observer.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import rings_of_saturn.github.io.smart_observer.block.ModBlocks;
import rings_of_saturn.github.io.smart_observer.block.entity.inventory.ImplementedInventory;

public class SmartObserverBlockEntity extends BlockEntity implements ImplementedInventory {
    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ModBlocks.EMPTY.getDefaultStack());
    public SmartObserverBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SMART_OBSERVER, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void readData(ReadView view) {
        Inventories.readData(view, inventory);
        super.readData(view);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    protected void writeData(WriteView view) {
        Inventories.writeData(view, inventory);
        super.writeData(view);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}




