package net.bloof.mushexpanded.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import net.bloof.mushexpanded.block.entity.IncubatorBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;

import java.util.List;

import static net.bloof.mushexpanded.block.custom.IncubatorBlock.FACING;

public class IncubatorBlockEntityRenderer implements BlockEntityRenderer<IncubatorBlockEntity> {
    float[][] position = {
            {0.35f, 0.25f, 0.5f},
            {0.65f, 0.25f, 0.5f},
            {0.35f, 0.5f, 0.5f},
            {0.65f, 0.5f, 0.5f}
    };

    public IncubatorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(IncubatorBlockEntity incubatorBlockEntity, float pPartialTick, PoseStack pPostStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        for(int i = 0; i < incubatorBlockEntity.inventory.getSlots(); ++i) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            ItemStack stack = incubatorBlockEntity.inventory.getStackInSlot(i);
            Direction direction = incubatorBlockEntity.getBlockState().getValue(FACING);



            pPostStack.pushPose();
            pPostStack.translate(0.5, 0, 0.5);
            pPostStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
            pPostStack.translate(-0.5, 0, -0.5);
            pPostStack.translate(position[i][0], position[i][1], position[i][2]);
            pPostStack.mulPose(Axis.XP.rotationDegrees(90f));
            pPostStack.scale(0.25f, 0.25f, 0.25f);



            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(incubatorBlockEntity.getLevel(),
                            incubatorBlockEntity.getBlockPos()),
                            OverlayTexture.NO_OVERLAY, pPostStack, pBufferSource, incubatorBlockEntity.getLevel(), 1);
            pPostStack.popPose();

        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
