package gvclib.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

public class RenderSPG_Target {

	private static final ResourceLocation t0 = new ResourceLocation("gvclib:textures/marker/marker0.png");
	private static final ResourceLocation t1 = new ResourceLocation("gvclib:textures/marker/marker1.png");
    private static final IModelCustom marker = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/marker/firepoint.mqo"));
    
	public static void can(EntityGVCLivingBase entity, float x, float y, float z) {
		GL11.glPushMatrix();//glstart
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		{
			float value = (float)Math.sin(entity.ontick100sec * 0.1) * 0.1F;
			GL11.glTranslatef(x, y, z);
			GL11.glScalef(1F + value, 1F + value, 1F + value);
			//GL11.glTranslatef(0.0F, value, 0.0F);
			GlStateManager.disableLighting();
			boolean can = true;
			if(entity instanceof EntityVehicleBase) {
				EntityVehicleBase vehicle = (EntityVehicleBase) entity;
				float spg_target_x = (float) (entity.posX + vehicle.spg_yaw);
				float spg_target_z = (float) (entity.posZ + vehicle.spg_picth);
				{
					double d5 = spg_target_x - entity.posX;
					double d7 = spg_target_z - entity.posZ;
			        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
			        if(d3 >= vehicle.spg_min_range) {
			        	can = true;
			        }else {
			        	can = false;
			        }
				}
			}
			if(can) {
				Minecraft.getMinecraft().renderEngine.bindTexture(t0);
			}else {
				Minecraft.getMinecraft().renderEngine.bindTexture(t1);
			}
	        marker.renderPart("mat1");
	        GlStateManager.enableLighting();
		}
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();//glend
	}
	
}
