package gvclib.render;

import java.io.IOException;
import org.lwjgl.opengl.GL11;
import gvclib.mod_GVCLib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
import gvclib.render.SARenderHelper;
import gvclib.render.SARenderHelper.RenderType;
import net.minecraft.entity.Entity;

import net.minecraft.client.renderer.OpenGlHelper;

import jp.mc.ancientred.jointblock.entity.EntityBullet;

@SideOnly(Side.CLIENT)
public class RenderJB_Base<T extends EntityBullet> extends Render<T>
{
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/entity/bullet.mqo"));
    private static final ResourceLocation boatTextures0 = new ResourceLocation("gvclib:textures/entity/bullet.png");
    public String bulletmodel = "gvclib:textures/entity/bullet.mqo";
    public String bullettex = "gvclib:textures/entity/bullet.png";
    public RenderJB_Base(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    //@Override
    public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ)
    {
    	return true;
    }
	
    public IModelCustom model = null;
	public int time = 0;
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	
    	Minecraft minecraft = Minecraft.getMinecraft();

		++this.time;
		
        if(entity.lazerWidth==151){
			this.bulletmodel = "gvclib:textures/entity/bulletmissile.mqo";
			this.bullettex = "gvclib:textures/entity/bulletmissile.png";
		}else if(entity.lazerLength==152){
			this.bulletmodel = "gvclib:textures/entity/bomb.mqo";
			this.bullettex = "gvclib:textures/entity/bomb.png";
		}else if(entity.lazerLength==153){
			this.bulletmodel = "gvclib:textures/entity/bulletaaa.mqo";
			this.bullettex = "gvclib:textures/entity/bulletaaa.png";
		}else if(entity.lazerLength==154){
			this.bulletmodel = "gvclib:textures/entity/bulletrocket.mqo";
			this.bullettex = "gvclib:textures/entity/bulletrocket.png";
		}else if(entity.lazerLength==155){
			this.bulletmodel = "gvclib:textures/entity/bulletmissile.mqo";
			this.bullettex = "gvclib:textures/entity/bulletmissile.png";
		}
		
        if(this.model == null && this.bulletmodel != null){
        	boolean mo = false;
        	for(int ii = 0; ii < 1024; ++ii) {
        		if(mod_GVCLib.modelt[ii] != null && mod_GVCLib.modelt[ii].equals(this.bulletmodel)) {
        			this.model  = mod_GVCLib.model[ii];
        			mo = true;
        			break;
        		}
        	}
        	if(!mo) {
        		ResourceLocation resource = new ResourceLocation(this.bulletmodel);
        		try {
					IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resource);
					if(res != null) {
	        			this.model = AdvancedModelLoader.loadModel(resource);
	            		++mod_GVCLib.entityid;
	            		mod_GVCLib.model[mod_GVCLib.entityid] = this.model;
	            		mod_GVCLib.modelt[mod_GVCLib.entityid] = this.bulletmodel;
	            		System.out.println(String.format("HMGGVC-" + mod_GVCLib.entityid));
	        		}
				} catch (IOException e) {
					System.out.println(String.format("warning! not exist model!::::" + this.bulletmodel));
				}
        	}
		}
		
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);
		
        if(this.model != null && this.time > 20){
            minecraft.getTextureManager().bindTexture(new ResourceLocation(this.bullettex));
        	//minecraft.getTextureManager().bindTexture(entity.entity_tex);
			this.enableBlendMode();
            {
            	 GlStateManager.pushMatrix();
                 this.model.renderPart("bullet_no_rote");
            	 GlStateManager.popMatrix();
            }
            {
            	 GlStateManager.pushMatrix();
            	 /*if(!entity.onGround) */{
            		//GlStateManager.rotate(entity.time * 10, 0.0F, 0.0F, 1.0F);
					/*if(!entity.inGround)*/this.model.renderPart("bullet");
            	 }
				this.model.renderPart("bullet_stick");
            	 GlStateManager.popMatrix();
            }
            {
           	 GlStateManager.pushMatrix();
           	 if(!entity.onGround) {
           		 //GlStateManager.rotate(entity.time * 10, 1.0F, 0.0F, 0.0F);
           	 }
                this.model.renderPart("bullet_x");
           	 GlStateManager.popMatrix();
           }
            {
              	 GlStateManager.pushMatrix();
              	 if(!entity.onGround) {
              		 //GlStateManager.rotate(entity.time * 10, 0.0F, 1.0F, 0.0F);
              	 }
                   this.model.renderPart("bullet_y");
              	 GlStateManager.popMatrix();
              }
            this.disableBlendMode();
			 
            {
            	GlStateManager.disableLighting();
            	float size = partialTicks * 0.4F + 1;
            	 if(!entity.onGround) {
            		//GlStateManager.rotate(entity.time * 10, 0.0F, 0.0F, 1.0F);
					GlStateManager.scale(size, size, size);
					SARenderHelper.enableBlendMode(RenderType.ADDITIVE);
					/*if(!entity.inGround)*/this.model.renderPart("bullet_e_1");
					SARenderHelper.disableBlendMode(RenderType.ADDITIVE);
            	 }
            	 GlStateManager.enableLighting();
            }
			
			{
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDepthMask(false);
            	GlStateManager.disableLighting();
            	float size1 = partialTicks * 0.2F + 1;
            	GlStateManager.scale(size1, size1, size1);
				SARenderHelper.enableBlendMode(RenderType.ADDITIVE);
				this.model.renderPart("bullet_blade");
				SARenderHelper.disableBlendMode(RenderType.ADDITIVE);
				GL11.glColor4f(1F, 1F, 1F, 0.6F);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_LIGHT1);
            	GlStateManager.enableLighting();
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
			}
			
        }
        GlStateManager.translate((float)-x, (float)-y, (float)-z);
        GlStateManager.popMatrix();
        
        {
        	this.bindTexture(boatTextures0);
        }
        
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
		return boatTextures0;
	}
}