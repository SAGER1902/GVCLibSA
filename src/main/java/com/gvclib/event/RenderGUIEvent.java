package gvclib.event;

import org.lwjgl.opengl.GL11;

import gvclib.entity.living.EntityGVCLivingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderGUIEvent extends Gui {

	private final static ResourceLocation overlayTimerSun = new ResourceLocation(
			"hmggvc:textures/misc/ironsight_ak74.png");

	private final static int DAY_WIDTH = 34;
	private final static int DAY_HEIGHT = 34;

	private Minecraft mc;
	private int iii;

	public RenderGUIEvent(Minecraft mc) {
		this.mc = mc;
	}
	
	
	public void render(Minecraft minecraft, EntityGVCLivingBase balaam, EntityPlayer entityplayer, int i, int j, String tex) {
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);

		int max = (int) balaam.getMaxHealth();
		int ima = (int) balaam.getHealth();
		GL11.glPushMatrix();//21
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		mc.renderEngine.bindTexture(new ResourceLocation(tex));
		GL11.glTranslatef(0.5F,0.5F, 0F);
		float scale = 8;
		GL11.glScalef(1/scale, 1/scale, 1);//0.0625
		drawTexturedModalRect((scaledresolution.getScaledWidth()/2-16)*scale,
				(scaledresolution.getScaledHeight()/2-16)*scale, 0,0, 256, 256);
		GL11.glPopMatrix();//22
	}
	
	public void renderHori(Minecraft minecraft, EntityGVCLivingBase balaam, EntityPlayer entityplayer, int i, int j, String tex) {
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);

		int max = (int) balaam.getMaxHealth();
		int ima = (int) balaam.getHealth();
		{
			GL11.glPushMatrix();//21
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			mc.renderEngine.bindTexture(new ResourceLocation(tex));
			GL11.glTranslatef(0.5F,0.5F, 0F);
			//GL11.glTranslatef(0F,balaam.prevRotationPitch, 0F);
			float scale = 8;
			GL11.glScalef(1/scale, 1/scale, 1);//0.0625
			drawTexturedModalRect((scaledresolution.getScaledWidth()/2-16)*scale,
					(scaledresolution.getScaledHeight()/2-16 + balaam.rotationPitch)*scale, 0,0, 256, 256);
			GL11.glPopMatrix();//22
		}
	}
	
	public void render_bomber(Minecraft minecraft, EntityGVCLivingBase balaam, EntityPlayer entityplayer, int i, int j, String tex) {
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);

		int max = (int) balaam.getMaxHealth();
		int ima = (int) balaam.getHealth();
		{
			GL11.glPushMatrix();//21
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			mc.renderEngine.bindTexture(new ResourceLocation(tex));
			GL11.glTranslatef(0.5F,0.5F, 0F);
			//GL11.glTranslatef(0F,balaam.prevRotationPitch, 0F);
			float scale = 8;
			GL11.glScalef(1/scale, 1/scale, 1);//0.0625
			drawTexturedModalRect((scaledresolution.getScaledWidth()/2-16)*scale,
					(scaledresolution.getScaledHeight()/2-16 + 90 - balaam.rotationPitch)*scale, 0,0, 256, 256);
			GL11.glPopMatrix();//22
		}
	}
	
	public void render_Cruising_MODE(Minecraft minecraft, EntityGVCLivingBase balaam, EntityPlayer entityplayer, int i, int j) {
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);

		int max = (int) balaam.getMaxHealth();
		int ima = (int) balaam.getHealth();
		{
			GL11.glPushMatrix();//21
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			fontrenderer.drawString("CRUISE_MODE", i -30, j + 30 + 0, 0xFFFFFF);
			GL11.glPopMatrix();//22
		}
	}
	public void render_Hovering_MODE(Minecraft minecraft, EntityGVCLivingBase balaam, EntityPlayer entityplayer, int i, int j) {
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);

		int max = (int) balaam.getMaxHealth();
		int ima = (int) balaam.getHealth();
		{
			GL11.glPushMatrix();//21
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			fontrenderer.drawString("HOVERING_MODE", i -30, j + 30 + 0, 0xFFFFFF);
			GL11.glPopMatrix();//22
		}
	}
	
	public void render_SPG_MODE(Minecraft minecraft, EntityGVCLivingBase balaam, EntityPlayer entityplayer, int i, int j) {
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);

		int max = (int) balaam.getMaxHealth();
		int ima = (int) balaam.getHealth();
		{
			GL11.glPushMatrix();//21
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			fontrenderer.drawString("SPG_MODE", i -30, j + 30 + 0, 0xFFFFFF);
			GL11.glPopMatrix();//22
		}
	}
}