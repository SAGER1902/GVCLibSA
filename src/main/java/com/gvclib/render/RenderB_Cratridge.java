package gvclib.render;

import java.io.IOException;

import gvclib.entity.EntityB_Cratridge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

@SideOnly(Side.CLIENT)
public class RenderB_Cratridge<T extends EntityB_Cratridge> extends Render<T> {
	private static final IModelCustom tankk = AdvancedModelLoader
			.loadModel(new ResourceLocation("gvclib:textures/model/smoke.obj"));
	private static final ResourceLocation boatTextures0 = new ResourceLocation("gvclib:textures/model/smoke0.png");

	public RenderB_Cratridge(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		// this.bindEntityTexture(new ResourceLocation(entity.getModel()));
		GlStateManager.pushMatrix();
		// GlStateManager.disableLighting();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks,
				0.0F, 1.0F, 0.0F);
		// GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch -
		// entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);

		if (entity.model == null && entity.getModel() != null) {
			ResourceLocation resource = new ResourceLocation(entity.getModel());
			try {
				IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resource);
				if (res != null) {
					entity.model = AdvancedModelLoader.loadModel(resource);
				}
			} catch (IOException e) {
				// e.printStackTrace();
				System.out.println(String.format("warning! not exist model!::::" + entity.getModel()));
			}
		}
		Minecraft minecraft = Minecraft.getMinecraft();
		minecraft.getTextureManager().bindTexture(new ResourceLocation(entity.getTex()));
		if(entity.model != null)entity.model.renderPart("mat1");
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return boatTextures0;
	}
}