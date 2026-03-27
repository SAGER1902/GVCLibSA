package gvclib.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.OpenGlHelper;
import gvclib.entity.EntityB_Bullet;
public class RenderLaserBullet extends Render<EntityB_Bullet> {
		
	public RenderLaserBullet(RenderManager renderManager) {
		super(renderManager);
		this.texture = new ResourceLocation("techguns:textures/fx/laser3.png");
		this.textureStart = new ResourceLocation("techguns:textures/fx/laser3_start.png");
		this.laserWidth = 30.0d;
	}

	private ResourceLocation texture;
	private ResourceLocation textureStart;
	private double laserWidth;	

	@Override
	public void doRender(EntityB_Bullet laser, double x, double y, double z, float entityYaw, float partialTicks) {
		 double distance = laser.distance+20;//source.distanceTo(target);
		 int maxTicks = laser.maxTicks;
		 
		 float prog = ((float)laser.ticksExisted)/((float)maxTicks);
		 double width = laserWidth * (Math.sin(Math.sqrt(prog)*Math.PI))*2;
		 double distance_start = Math.min(1.0d, distance);
		 double u = (distance / laserWidth) * 2.0D;
    	 this.bindEntityTexture(laser);        
         GlStateManager.pushMatrix();
		this.enableBlendMode();
    	  GlStateManager.enableRescaleNormal();
    	  GlStateManager.disableCull();
         GlStateManager.depthMask(false);
    	 
         GlStateManager.translate(x, y, z);
         /*GlStateManager.rotate(laser.laserYaw-90F, 0.0F, 1.0F, 0.0F);              
         GlStateManager.rotate(laser.laserPitch, 0.0F, 0.0F, 1.0F);*/
		 
        GlStateManager.rotate(laser.prevRotationYaw + (laser.rotationYaw - laser.prevRotationYaw) * partialTicks - 90F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-laser.rotationPitch, 1.0F, 0.0F, 0.0F);
		
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         float f10 = 0.0125F;
         
         
         distance*=80.0D;
         distance_start*=80.0D;
         
         float rot_x = 45f+(prog*180f);
         
         GlStateManager.rotate(rot_x+90f , 1.0F, 0.0F, 0.0F);
         GlStateManager.scale(f10, f10, f10);

         float brightness = (float) Math.sin(Math.sqrt(prog)*Math.PI);
        		
         if (distance > distance_start) { //Beam Segment
	         GlStateManager.pushMatrix();
	         for (int i = 0; i < 2; ++i)
	         {
	             GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
	             GlStateManager.glNormal3f(0.0F, 0.0F, f10); //????
	  
	             bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
	             
	             bufferbuilder.pos(distance, -width, 0.0D).tex(u+prog, 0).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
	             bufferbuilder.pos(distance_start, -width, 0.0D).tex(prog, 0).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
	             bufferbuilder.pos(distance_start, width, 0.0D).tex( prog, 1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
	             bufferbuilder.pos(distance, width, 0.0D).tex(u+prog, 1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
	             tessellator.draw();
	         }
	         GlStateManager.popMatrix();
         }
         GlStateManager.pushMatrix();
         renderManager.renderEngine.bindTexture(textureStart);
         for (int i = 0; i < 2; ++i) //Beam start segment
         {
             GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
             GlStateManager.glNormal3f(0.0F, 0.0F, f10); //????
  
             bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
             
             bufferbuilder.pos(distance_start, -width, 0.0D).tex(1, 0).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             bufferbuilder.pos(0, -width, 0.0D).tex(0, 0).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             bufferbuilder.pos(0, width, 0.0D).tex(0, 1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             bufferbuilder.pos(distance_start, width, 0.0D).tex( 1, 1).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
             tessellator.draw();
         }
         GlStateManager.popMatrix();
         GlStateManager.depthMask(true);
         GlStateManager.enableCull();
         GlStateManager.disableRescaleNormal();	 
		 this.disableBlendMode();
         GlStateManager.popMatrix();
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
	
    /**
     * This includes FXLighting!
     */
	public static void disableBlendMode() {
		//光照END
    	GlStateManager.enableLighting();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
		//RenderType.ADDITIVE
		GlStateManager.blendFunc(lastBlendFuncSrc, lastBlendFuncDest);
	}
	
	
	@Override
	protected ResourceLocation getEntityTexture(EntityB_Bullet entity) {
		return texture;
	}
}
