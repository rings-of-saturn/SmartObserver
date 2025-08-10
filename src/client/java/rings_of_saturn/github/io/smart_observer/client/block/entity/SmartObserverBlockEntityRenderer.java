package rings_of_saturn.github.io.smart_observer.client.block.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import rings_of_saturn.github.io.smart_observer.block.ModBlocks;
import rings_of_saturn.github.io.smart_observer.block.entity.SmartObserverBlockEntity;

public class SmartObserverBlockEntityRenderer implements BlockEntityRenderer<SmartObserverBlockEntity> {
    public SmartObserverBlockEntityRenderer(BlockEntityRendererFactory.Context ignoredCtx) {}

    @Override
    public void render(SmartObserverBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(blockEntity.getStack(0).getItem() != ModBlocks.EMPTY && blockEntity.getWorld().getBlockState(blockEntity.getPos()).isOf(ModBlocks.SMART_OBSERVER)) {
            matrices.push();

            switch (blockEntity.getWorld().getBlockState(blockEntity.getPos()).get(Properties.FACING)){
                case SOUTH,EAST,NORTH,WEST:
                    matrices.translate(0f, 0.5, 0.5f);
                case DOWN:
                    matrices.translate(0f, 0f, -1f);
                case UP:
                    matrices.translate(0.5f, 0.35f, 1f);
            }
            int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getStack(0), ModelTransformationMode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, blockEntity.getWorld(), 0);

            matrices.pop();
        }
    }
}
