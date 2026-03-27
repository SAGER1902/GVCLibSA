package gvclib.event;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



/**主に乗り物関係*/
public class RenderHUDEvent extends Gui {

	private final static int DAY_WIDTH = 34;
	private final static int DAY_HEIGHT = 34;

	private Minecraft mc;
	private int iii;

	public String remainmax = "∞";
	
	public RenderHUDEvent(Minecraft mc) {
		this.mc = mc;
	}
	
	public void renderBenri(Minecraft minecraft, EntityPlayer entityplayer, int i, int j) {
		FontRenderer fontrenderer = this.mc.fontRenderer;
		this.mc.entityRenderer.setupOverlayRendering();
		ScaledResolution scaledresolution = new ScaledResolution(this.mc);

		GL11.glPushMatrix();//21
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		
		String d4 = String.format("%1$3d", (int)this.mc.getDebugFPS());
		fontrenderer.drawStringWithShadow(d4, i - 40,  5, 0xFFFFFF);
		
		//String d5 = String.format("%1$3d", (int)this.mc.getDebugFPS());
		String d5 = entityplayer.world.getBiomeForCoordsBody(entityplayer.getPosition()).getBiomeName();
		fontrenderer.drawStringWithShadow(d5, i - 40,  20, 0xFFFFFF);
		
		fontrenderer.drawStringWithShadow("X:" + String.format("%1$3d", (int)entityplayer.posX), i - 40,  35, 0xFFFFFF);
		fontrenderer.drawStringWithShadow("Y:" + String.format("%1$3d", (int)entityplayer.posY), i - 40,  50, 0xFFFFFF);
		fontrenderer.drawStringWithShadow("Z:" + String.format("%1$3d", (int)entityplayer.posZ), i - 40,  65, 0xFFFFFF);
		
		GL11.glPopMatrix();
	}
	
	public void renderHP(Minecraft minecraft, EntityGVCLivingBase balaam, EntityPlayer entityplayer, int i, int j) {
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);
		
		int max = (int) balaam.getMaxHealth();
		int ima = (int) balaam.getHealth();
		GL11.glPushMatrix();//21
	//	GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
	//	mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		String d = String.format("%1$3d", max);//String.valueOf(max);
		String d2 = String.format("%1$3d", ima);//String.valueOf(ima);
		fontrenderer.drawStringWithShadow("HP  "+ d2 + "/" + d, i - 100, j - 90 + 0, 0xFFFFFF);
		{
			GL11.glPushMatrix();//111
			int color = 0xFFFFFF;
			String name1 = balaam.render_hud_information_1;
			String name2 = balaam.render_hud_information_2;
			String name3 = balaam.render_hud_information_3;
			String name4 = balaam.render_hud_information_4;
			String name5 = balaam.render_hud_information_5;
			String name6 = balaam.render_hud_information_6;
			fontrenderer.drawStringWithShadow(name1, i - 100, j - 150 + 0, color);
			fontrenderer.drawStringWithShadow(name2, i - 100, j - 140 + 0, color);
			fontrenderer.drawStringWithShadow(name3, i - 100, j - 130 + 0, color);
			fontrenderer.drawStringWithShadow(name4, i - 100, j - 120 + 0, color);
			fontrenderer.drawStringWithShadow(name5, i - 100, j - 110 + 0, color);
			fontrenderer.drawStringWithShadow(name6, i - 100, j - 100 + 0, color);
			GL11.glPopMatrix();//111
		}
		{
			if(!balaam.w1namebase.equals("")){
				GL11.glPushMatrix();//111
				int color = 0xFFFFFF;
				if(balaam.render_hud_scaleup_text1)color = Color.GREEN.getRGB();
				String name = balaam.w1namebase;
				fontrenderer.drawStringWithShadow(name, i - 100, j - 80 + 0, color);
				if(balaam instanceof EntityVehicleBase) {
					EntityVehicleBase vehicle = (EntityVehicleBase) balaam;
					if(vehicle.w1can_cooldown) {
						/*GL11.glPushMatrix();
						{
							GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
									GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
									GlStateManager.SourceFactor.ONE,
									GlStateManager.DestFactor.ZERO);
							GuiIngame g = mc.ingameGUI;
							mc.renderEngine
									.bindTexture(
											new ResourceLocation("gvclib:textures/misc/gun_limit.png"));
							GL11.glScalef(0.125f, 0.125f, 1);
							g.drawTexturedModalRect((i - 90) * 8, ( j - 80) * 8, 0, 0, 256, 256);
						}
						GL11.glPopMatrix();
						GL11.glPushMatrix();
						{
							GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
									GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
									GlStateManager.SourceFactor.ONE,
									GlStateManager.DestFactor.ZERO);
							GuiIngame g = mc.ingameGUI;
							mc.renderEngine
									.bindTexture(new ResourceLocation(
											"gvclib:textures/misc/gun_limit_empty.png"));
							GL11.glScalef(0.125f, 0.125f, 1);
							double max_bar = balaam.magazine;
							double range = 256 / max_bar;
							double heat_bar = balaam.getRemain_L() * range;
							double overheat_bar = balaam.reload1 * (256 / balaam.reload_time1);
							if(balaam.getRemain_L() > 0){
								g.drawTexturedModalRect((i - 90) * 8, ( j - 80) * 8, 0, 0, 256 - (int)heat_bar,
										256);
							}else {
								g.drawTexturedModalRect((i - 90) * 8, ( j - 80) * 8, 0, 0, 256 - (int)overheat_bar,
										256);
							}
						}
						GL11.glPopMatrix();*/
						render_overheat(minecraft, i, j, 100, 80, balaam.reload1, balaam.reload_time1, balaam.magazine, balaam.getRemain_L());
					}else {
						String remain = String.format("%1$3d", balaam.getRemain_L());
						//String remainmax = String.format("%1$3d", balaam.magazine);
						fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 70 + 0, color);
						render_heat(minecraft, i, j, 100, 80, balaam.reload1, balaam.reload_time1, balaam.getRemain_L());
					}
				}else {
					String remain = String.format("%1$3d", balaam.getRemain_L());
					//String remainmax = String.format("%1$3d", balaam.magazine);
					fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 70 + 0, color);
				}
				
				GL11.glPopMatrix();//111
			}
			if(!balaam.w2name.equals("")){
				GL11.glPushMatrix();//111
				int color = 0xFFFFFF;
				if(balaam.render_hud_scaleup_text2)color = Color.GREEN.getRGB();
				String name = balaam.w2name;
				fontrenderer.drawStringWithShadow(name, i - 100, j - 60 + 0, color);
				if(balaam instanceof EntityVehicleBase) {
					EntityVehicleBase vehicle = (EntityVehicleBase) balaam;
					if(vehicle.w2can_cooldown) {
						render_overheat(minecraft, i, j, 100, 60, balaam.reload2, balaam.reload_time2, balaam.magazine2, balaam.getRemain_R());
					}else {
						String remain = String.format("%1$3d", balaam.getRemain_R());
						//String remainmax = String.format("%1$3d", balaam.magazine2);
						fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 50 + 0, color);
						render_heat(minecraft, i, j, 100, 60, balaam.reload2, balaam.reload_time2, balaam.getRemain_R());
					}
				}else {
					String remain = String.format("%1$3d", balaam.getRemain_R());
					//String remainmax = String.format("%1$3d", balaam.magazine2);
					fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 50 + 0, color);
				}
				GL11.glPopMatrix();//111
			}
			if(!balaam.w3name.equals("")){
				/*GL11.glPushMatrix();//111
				int color = 0xFFFFFF;
				if(balaam.render_hud_scaleup_text3)color = Color.GREEN.getRGB();
				String name = balaam.w3name;
				String remain = String.format("%1$3d", balaam.getRemain_A());
				//String remainmax = String.format("%1$3d", balaam.magazine3);
				fontrenderer.drawStringWithShadow(name, i - 100, j - 40 + 0, color);
				fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 30 + 0, color);
				GL11.glPopMatrix();//111*/
				GL11.glPushMatrix();//111
				int color = 0xFFFFFF;
				if(balaam.render_hud_scaleup_text3)color = Color.GREEN.getRGB();
				String name = balaam.w3name;
				fontrenderer.drawStringWithShadow(name, i - 100, j - 40 + 0, color);
				if(balaam instanceof EntityVehicleBase) {
					EntityVehicleBase vehicle = (EntityVehicleBase) balaam;
					if(vehicle.w3can_cooldown) {
						render_overheat(minecraft, i, j, 100, 40, balaam.reload3, balaam.reload_time3, balaam.magazine3, balaam.getRemain_A());
					}else {
						String remain = String.format("%1$3d", balaam.getRemain_A());
						//String remainmax = String.format("%1$3d", balaam.magazine3);
						fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 30 + 0, color);
						render_heat(minecraft, i, j, 100, 40, balaam.reload3, balaam.reload_time3, balaam.getRemain_A());
					}
				}else {
					String remain = String.format("%1$3d", balaam.getRemain_A());
					//String remainmax = String.format("%1$3d", balaam.magazine3);
					fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 30 + 0, color);
				}
				GL11.glPopMatrix();//111
			}
			if(!balaam.w4name.equals("")){
				/*GL11.glPushMatrix();//111
				int color = 0xFFFFFF;
				if(balaam.render_hud_scaleup_text4)color = Color.GREEN.getRGB();
				String name = balaam.w4name;
				String remain = String.format("%1$3d", balaam.getRemain_S());
				//String remainmax = String.format("%1$3d", balaam.magazine4);
				fontrenderer.drawStringWithShadow(name, i - 100, j - 20 + 0, color);
				fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 10 + 0, color);
				GL11.glPopMatrix();//111*/
				GL11.glPushMatrix();//111
				int color = 0xFFFFFF;
				if(balaam.render_hud_scaleup_text4)color = Color.GREEN.getRGB();
				String name = balaam.w4name;
				fontrenderer.drawStringWithShadow(name, i - 100, j - 20 + 0, color);
				if(balaam instanceof EntityVehicleBase) {
					EntityVehicleBase vehicle = (EntityVehicleBase) balaam;
					if(vehicle.w4can_cooldown) {
						render_overheat(minecraft, i, j, 100, 20, balaam.reload4, balaam.reload_time4, balaam.magazine4, balaam.getRemain_S());
					}else {
						String remain = String.format("%1$3d", balaam.getRemain_S());
						//String remainmax = String.format("%1$3d", balaam.magazine4);
						fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 10 + 0, color);
						render_heat(minecraft, i, j, 100, 20, balaam.reload4, balaam.reload_time4, balaam.getRemain_S());
					}
				}else {
					String remain = String.format("%1$3d", balaam.getRemain_S());
					//String remainmax = String.format("%1$3d", balaam.magazine4);
					fontrenderer.drawStringWithShadow(remain + " /" + remainmax, i - 100, j - 10 + 0, color);
				}
				GL11.glPopMatrix();//111
			}
			
			
			
			if(balaam.debag_test_client){
				{
					GL11.glPushMatrix();//111
					int color = Color.GREEN.getRGB();
					String xxx = String.format("%1$.2f", balaam.ridding_view1_x);
					fontrenderer.drawStringWithShadow("X : " + xxx, 100, j - 10 + 0, color);
					GL11.glPopMatrix();//111
				}
				{
					GL11.glPushMatrix();//111
					int color = Color.GREEN.getRGB();
					String xxx = String.format("%1$.2f", balaam.ridding_view1_y);
					fontrenderer.drawStringWithShadow("Y : " + xxx, 100, j - 20 + 0, color);
					GL11.glPopMatrix();//111
				}
				{
					GL11.glPushMatrix();//111
					int color = Color.GREEN.getRGB();
					String xxx = String.format("%1$.2f", balaam.ridding_view1_z);
					fontrenderer.drawStringWithShadow("Z : " + xxx, 100, j - 30 + 0, color);
					GL11.glPopMatrix();//111
				}
				if(balaam instanceof EntityVehicleBase){
					EntityVehicleBase vehicle = (EntityVehicleBase) balaam;
					{
						GL11.glPushMatrix();//111
						int color = Color.GREEN.getRGB();
						String xxx = String.format("%1$.2f", vehicle.riddingx[0]);
						fontrenderer.drawStringWithShadow("RX : " + xxx, 100, j - 40 + 0, color);
						GL11.glPopMatrix();//111
					}
					{
						GL11.glPushMatrix();//111
						int color = Color.GREEN.getRGB();
						String xxx = String.format("%1$.2f", vehicle.riddingy[0]);
						fontrenderer.drawStringWithShadow("RY : " + xxx, 100, j - 50 + 0, color);
						GL11.glPopMatrix();//111
					}
					{
						GL11.glPushMatrix();//111
						int color = Color.GREEN.getRGB();
						String xxx = String.format("%1$.2f", vehicle.riddingz[0]);
						fontrenderer.drawStringWithShadow("RZ : " + xxx, 100, j - 60 + 0, color);
						GL11.glPopMatrix();//111
					}
				}
				
			}
		}
		
		GL11.glPopMatrix();
	//	GL11.glPopAttrib();//22
	}
	
	private static void render_heat(Minecraft mc, int i, int j, int x, int y, int reload, int reload_time, int getmagazine) {
		GL11.glPushMatrix();
		{
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
					GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			GuiIngame g = mc.ingameGUI;
			mc.renderEngine
					.bindTexture(
							new ResourceLocation("gvclib:textures/misc/gun_limit.png"));
			GL11.glScalef(0.125f, 0.125f, 1);
			g.drawTexturedModalRect((i - x) * 8, ( j - y) * 8, 0, 0, 256, 256);
		}
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		{
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
					GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			GuiIngame g = mc.ingameGUI;
			mc.renderEngine
					.bindTexture(new ResourceLocation(
							"gvclib:textures/misc/gun_limit_empty.png"));
			GL11.glScalef(0.125f, 0.125f, 1);
			if(reload_time == 0)reload_time = 1;
			double overheat_bar = reload * (256 / reload_time);
			if(getmagazine <= 0){
				g.drawTexturedModalRect((i - x) * 8, ( j - y) * 8, 0, 0, 256 - (int)overheat_bar,
						256);
			}
		}
		GL11.glPopMatrix();
	}
	
	private static void render_overheat(Minecraft mc, int i, int j, int x, int y, int reload, int reload_time, int magazine, int getmagazine) {
		GL11.glPushMatrix();
		{
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
					GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			GuiIngame g = mc.ingameGUI;
			mc.renderEngine
					.bindTexture(
							new ResourceLocation("gvclib:textures/misc/gun_limit.png"));
			GL11.glScalef(0.125f, 0.125f, 1);
			g.drawTexturedModalRect((i - x) * 8, ( j - y) * 8, 0, 0, 256, 256);
		}
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		{
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
					GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			GuiIngame g = mc.ingameGUI;
			mc.renderEngine
					.bindTexture(new ResourceLocation(
							"gvclib:textures/misc/gun_limit_empty.png"));
			GL11.glScalef(0.125f, 0.125f, 1);
			double max_bar = magazine;
			double range = 256 / max_bar;
			double heat_bar = getmagazine * range;
			if(reload_time == 0)reload_time = 1;
			double overheat_bar = reload * (256 / reload_time);
			if(getmagazine > 0){
				g.drawTexturedModalRect((i - x) * 8, ( j - y) * 8, 0, 0, 256 - (int)heat_bar,
						256);
			}else {
				g.drawTexturedModalRect((i - x) * 8, ( j - y) * 8, 0, 0, 256 - (int)overheat_bar,
						256);
			}
		}
		GL11.glPopMatrix();
	}
	
	
	
	
	
	public void rendertank(Minecraft minecraft, EntityGVCLivingBase balaam, EntityPlayer entityplayer, int i, int j) {
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);
		
		GL11.glPushMatrix();//21
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
	//	this.renderView(minecraft, scaledresolution, "hmggvc:textures/misc/tank.png");
		GL11.glPopMatrix();//22

		GL11.glPushMatrix();//21
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/hou1.png"));
		GL11.glTranslatef(32, scaledresolution.getScaledHeight()-32, 0F);
		GL11.glRotatef(balaam.prevRotationYawHead, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-32, -(scaledresolution.getScaledHeight()-32), 0F);
		//drawTexturedModalRect(scaledresolution.getScaledWidth()/2 -0,  scaledresolution.getScaledHeight()/2 +24, 0,0, 256, 256);
		GL11.glScalef(0.25f, 0.25f, 1);
		drawTexturedModalRect(0,(scaledresolution.getScaledHeight()-64)*4, 0,0, 256, 256);
		GL11.glPopMatrix();//22
		
		GL11.glPushMatrix();//21
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/hou2.png"));
		GL11.glTranslatef(32, scaledresolution.getScaledHeight()-32, 0F);
		GL11.glRotatef(entityplayer.prevRotationYawHead, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-32, -(scaledresolution.getScaledHeight()-32), 0F);
		GL11.glScalef(0.25f, 0.25f, 1);
		drawTexturedModalRect(0,(scaledresolution.getScaledHeight()-64)*4, 0,0, 256, 256);
		GL11.glPopMatrix();//22
		
		/*
		GL11.glPushMatrix();//21
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/cross.png"));
		GL11.glTranslatef(0.5F,0F, 0F);
		GL11.glScalef(0.0625f, 0.0625f, 1);
		drawTexturedModalRect((scaledresolution.getScaledWidth()/2-8)*16,
				(scaledresolution.getScaledHeight()/2-8)*16, 0,0, 256, 256);
		GL11.glPopMatrix();//22
		*/
	}
	
	public void renderhud(Minecraft minecraft, EntityGVCLivingBase balaam, EntityPlayer entityplayer, int i, int j) {
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);
		
		GL11.glPushMatrix();//21
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
		String d51 = String.format("%1$3d", (int)balaam.throttle);
		fontrenderer.drawStringWithShadow(d51, scaledresolution.getScaledWidth()/2 - 30,  scaledresolution.getScaledHeight()/2, 0xFFFFFF);
		double dx = Math.abs(balaam.posX - balaam.prevPosX);
		double dz = Math.abs(balaam.posZ - balaam.prevPosZ);
		float dd = (float)Math.sqrt((dx * dx) + (dz * dz)) * 20;
		String d5 = String.format("%1$.2f", dd);
		fontrenderer.drawStringWithShadow(d5, scaledresolution.getScaledWidth()/2 - 30,  scaledresolution.getScaledHeight()/2 + 10, 0xFFFFFF);
		String d6 = String.format("%1$3d", (int) Math.abs(Math.atan2(balaam.motionX,balaam.motionZ)* 180));
		String d7 = String.format("%1$.2f", balaam.posY);
		fontrenderer.drawStringWithShadow(d7, scaledresolution.getScaledWidth()/2 + 30,  scaledresolution.getScaledHeight()/2, 0xFFFFFF);
		{
			BlockPos bp = balaam.world.getHeight(new BlockPos(balaam.posX, balaam.posY, balaam.posZ));
			int genY = bp.getY();
			String d71 = String.format("%1$.2f", balaam.posY - genY);
			fontrenderer.drawStringWithShadow(d71, scaledresolution.getScaledWidth()/2 + 30,  scaledresolution.getScaledHeight()/2+10, 0xFFFFFF);
		}
		GL11.glPopMatrix();//22
		
		{
			GL11.glPushMatrix();//21
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
			int mobkazu = balaam.getPassengers().size();
			for(int x = 0; x < mobkazu; ++x) {
				if(balaam.getPassengers().get(x) != null){
					Entity ent = balaam.getPassengers().get(x);
					String name = ent.getCustomNameTag();
					if(ent instanceof EntityPlayer)name = ent.getName();
					String seat = String.format("%1$3d", x);
					fontrenderer.drawStringWithShadow("SEAT" + x + ":" + name, 0,  scaledresolution.getScaledHeight()/2 + (10 * x), 0xFFFFFF);
				}
			}
			
			GL11.glPopMatrix();//22
		}
	}
	
	@SideOnly(Side.CLIENT)
	protected void renderView(Minecraft minecraft, ScaledResolution scaledRes, String adss) {
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableAlpha();
		minecraft.getTextureManager().bindTexture(new ResourceLocation(adss));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0D, (double)scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos((double)scaledRes.getScaledWidth(), (double)scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos((double)scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
}