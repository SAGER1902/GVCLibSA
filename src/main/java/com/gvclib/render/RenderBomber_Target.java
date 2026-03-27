package gvclib.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.FMLClientHandler;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

public class RenderBomber_Target {

	private static final ResourceLocation t1 = new ResourceLocation("gvclib:textures/marker/bomberpoint.png");
    private static final IModelCustom marker = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/marker/bomberpoint.mqo"));
    
	public static void can(EntityGVCLivingBase entity, float offset_speed) {
		
		BlockPos basep = FMLClientHandler.instance().getClient().world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
		float bsp = (float)Math.tan((entity.motionX * entity.motionX) + (entity.motionZ * entity.motionZ)) * offset_speed;
		//double ve = Math.sqrt((bsp * Math.cos(0)) * (bsp * Math.cos(0)) + 2 * basep.getY() * 0.03F);
		float xspeed = (float) (bsp * Math.sqrt((2  * (entity.posY - basep.getY()) * 0.65F  ) / 0.03F));
		
		//float oo = (float) ((float) ((entity.posY - basep.getY()) * 0.65F  ) +(((bsp * Math.sin(-entity.rotationPitch) * (bsp * Math.sin(-entity.rotationPitch))) / (2 *0.03F))));
		//float xspeed = (float) (bsp * Math.sqrt((2  * oo) / 0.03F));
		
		float xx = xspeed;
		float zz = 0;
		float xxx = 0;
		float yyy = 0;
		float zzz = 0;
		float yaw = entity.rotationYawHead * (2 * (float) Math.PI / 360);
		xxx -= MathHelper.sin(yaw) * xx;
		zzz += MathHelper.cos(yaw) * xx;
		xxx -= MathHelper.sin(yaw - 1.57F) * zz;
		zzz += MathHelper.cos(yaw - 1.57F) * zz;
		BlockPos bp = FMLClientHandler.instance().getClient().world.getHeight(new BlockPos(xxx + entity.posX, entity.posY, zzz + entity.posZ));
		yyy = bp.getY();
		
		float x = xxx;
		float y = (float)(yyy - entity.posY);
		float z = zzz;
		
		
		GL11.glPushMatrix();//glstart
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		{
			float value = (float)(entity.posY - yyy) * 0.2F;
			
			GL11.glTranslatef(x, y, z);
			
			GL11.glScalef(1F + value, 1F, 1F + value);
			
			//GL11.glTranslatef(0.0F, value, 0.0F);
			GlStateManager.disableLighting();
			Minecraft.getMinecraft().renderEngine.bindTexture(t1);
	        marker.renderPart("mat1");
	        GlStateManager.enableLighting();
		}
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();//glend
	}
	
}
