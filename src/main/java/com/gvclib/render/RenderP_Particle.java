package gvclib.render;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.mod_GVCLib;
import gvclib.entity.EntityP_Particle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
import net.minecraft.client.renderer.OpenGlHelper;
import gvclib.render.SARenderHelper;
import gvclib.render.SARenderHelper.RenderType;
import java.net.URL;

@SideOnly(Side.CLIENT)
public class RenderP_Particle<T extends EntityP_Particle> extends Render<T>
{
    private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/entity/null.mqo"));
    private static final ResourceLocation boatTextures0 = new ResourceLocation("gvclib:textures/entity/null.png");

    public RenderP_Particle(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	Minecraft minecraft = Minecraft.getMinecraft();
        
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        /*GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);*/
		
        if(entity.model == null && entity.getModel() != null){
        	boolean mo = false;
        	for(int ii = 0; ii < 1024; ++ii) {
        		if(mod_GVCLib.modelt[ii] != null && mod_GVCLib.modelt[ii].equals(entity.getModel())) {
        			entity.model  = mod_GVCLib.model[ii];
        			mo = true;
        			break;
        		}
        	}
        	if(!mo) {
        		ResourceLocation resource = new ResourceLocation(entity.getModel());
        		try {
					IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resource);
					if(res != null) {
	        			entity.model = AdvancedModelLoader.loadModel(resource);
	            		++mod_GVCLib.entityid;
	            		mod_GVCLib.model[mod_GVCLib.entityid] = entity.model;
	            		mod_GVCLib.modelt[mod_GVCLib.entityid] = entity.getModel();
	            		System.out.println(String.format("HMGGVC-" + mod_GVCLib.entityid));
	        		}
				} catch (IOException e) {
					//System.out.println(String.format("warning! not exist model!::::" + entity.getModel()));
				}
        	}
		}
        
        String tu1 = String.valueOf(entity.time);
        if(entity.model != null){
			//GL11.glDisable(GL11.GL_CULL_FACE);//一面刷双面
			GL11.glDepthMask(false);
			GL11.glColor4f(1F, 1F, 1F, 0.6F);
//不旋转
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GlStateManager.disableLighting();
			//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glEnable(GL11.GL_BLEND);
            minecraft.getTextureManager().bindTexture(new ResourceLocation(entity.getTex()));
            entity.model.renderPart("exp_" + tu1);
            entity.model.renderPart("mat1");
            entity.model.renderPart("mat1_light");
            entity.model.renderPart("mat2");
			entity.model.renderPart("mat2_light");
            entity.model.renderPart("mat4");
			entity.model.renderPart("mat4_light");
            entity.model.renderPart("mat5");
            entity.model.renderPart("mat5_1");
			entity.model.renderPart("mat5_light");
            entity.model.renderPart("mat6_1");
			entity.model.renderPart("mat6_light");
			GL11.glDisable(GL11.GL_BLEND);
//随着玩家水平视角旋转
        	GL11.glRotatef(180.0F - minecraft.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
			this.enableBlendMode();
			entity.model.renderPart("light_" + tu1);
			this.disableBlendMode();
//随着玩家垂直视角旋转
            GL11.glRotatef(-minecraft.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
			GL11.glEnable(GL11.GL_LIGHT1);
			GL11.glEnable(GL11.GL_LIGHTING);
			GlStateManager.pushMatrix();
			SARenderHelper.enableBlendMode(RenderType.ALPHA);
			entity.model.renderPart("bullet_hit_" + tu1);
			entity.model.renderPart("stand");
			SARenderHelper.disableBlendMode(RenderType.ALPHA);
			GlStateManager.popMatrix();

			
			GlStateManager.pushMatrix();
			SARenderHelper.enableBlendMode(RenderType.ADDITIVE);
			entity.model.renderPart("flash_" + tu1);
			SARenderHelper.disableBlendMode(RenderType.ADDITIVE);
			GlStateManager.popMatrix();
			
			//OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
            GlStateManager.enableLighting();
            GL11.glDepthMask(true);
            //GL11.glEnable(GL11.GL_CULL_FACE);
			{
			GlStateManager.pushMatrix();
        	GL11.glRotatef(180.0F - minecraft.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-minecraft.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
			GL11.glEnable(GL11.GL_LIGHT1);
			GL11.glEnable(GL11.GL_LIGHTING);
			if(entity.time>5&&entity.time<(entity.timemax-10)){
				minecraft.getTextureManager().bindTexture(new ResourceLocation(entity.getTex2()+tu1+".png"));//动态切换贴图
			}
			entity.model.renderPart("mat_change");
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_LIGHT1);
			GlStateManager.popMatrix();
			this.bindEntityTexture(entity);
			}
        }
        GlStateManager.translate((float)-x, (float)-y, (float)-z);
        GlStateManager.popMatrix();
        
        
        
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	protected static int lastBlendFuncSrc=0;
	protected static int lastBlendFuncDest=0;
	
	protected static float lastBrightnessX=0;
	protected static float lastBrightnessY=0;
	
    public static void enableBlendMode() {
		//RenderType.ADDITIVE
		lastBlendFuncSrc = GlStateManager.glGetInteger(GL11.GL_BLEND_SRC);
		lastBlendFuncDest = GlStateManager.glGetInteger(GL11.GL_BLEND_DST);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		//光照START
		lastBrightnessX= OpenGlHelper.lastBrightnessX;
		lastBrightnessY= OpenGlHelper.lastBrightnessY;
		GlStateManager.disableLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
	}
	public static void disableBlendMode() {
		//光照END
    	GlStateManager.enableLighting();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
		//RenderType.ADDITIVE
		GlStateManager.blendFunc(lastBlendFuncSrc, lastBlendFuncDest);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return boatTextures0;
	}
	
}