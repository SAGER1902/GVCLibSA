package gvclib.event;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import gvclib.entity.living.EntityMobVehicle_Inv_Base;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class Render_HUDVehicleEvent {
	private final static int DAY_WIDTH = 34;
	private final static int DAY_HEIGHT = 34;

	private Minecraft mc;
	private int iii;

	public Render_HUDVehicleEvent(Minecraft mc) {
		this.mc = mc;
	}
	
	public void render_fuel(Minecraft minecraft, EntityMobVehicle_Inv_Base balaam, EntityPlayer entityplayer, int i, int j) {
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);
		
		GL11.glPushMatrix();//21
	//	GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		{
			if(balaam.can_fuel){
				GL11.glPushMatrix();//111
				int color = 0xFFFFFF;
				if(balaam.fuel > 0) {
					color = Color.GREEN.getRGB();
				}else {
					color = Color.RED.getRGB();
				}
				String fuel = String.format("%1$3d", balaam.fuel);
				ItemStack itemi = balaam.horseChest.getStackInSlot(63);
				String fuelmax = "0";
				if(!itemi.isEmpty()) {
					fuelmax = String.format("%1$3d", itemi.getCount());
				}
				fontrenderer.drawStringWithShadow("FUEL:" + fuel + "/" + fuelmax, i - 100, j - 90 + 0, color);
				GL11.glPopMatrix();//111
			}
		}
		{
			if(balaam.can_weapon1){
				if(balaam.weapon1_item != null)
				{
					RenderItem renderitem = mc.getRenderItem();
					renderitem.renderItemIntoGUI(new ItemStack(balaam.weapon1_item), i -120, j-60);
				}
				ItemStack itemi = balaam.horseChest.getStackInSlot(40);
				String shell_max = "0";
				int color = Color.RED.getRGB();
				if(!itemi.isEmpty()) {
					shell_max = String.format("%1$1d", itemi.getCount());
					if(itemi.getCount() > 0) {
						color = Color.GREEN.getRGB();
					}
				}
				fontrenderer.drawStringWithShadow(shell_max, i - 105, j - 60 + 0, color);
			}
			if(balaam.can_weapon2){
				if(balaam.weapon2_item != null)
				{
					RenderItem renderitem = mc.getRenderItem();
					renderitem.renderItemIntoGUI(new ItemStack(balaam.weapon2_item), i -120, j-40);
				}
				ItemStack itemi = balaam.horseChest.getStackInSlot(50);
				String shell_max = "0";
				int color = Color.RED.getRGB();
				if(!itemi.isEmpty()) {
					shell_max = String.format("%1$1d", itemi.getCount());
					if(itemi.getCount() > 0) {
						color = Color.GREEN.getRGB();
					}
				}
				fontrenderer.drawStringWithShadow(shell_max, i - 105, j - 40 + 0, color);
			}
		}
		
		
		GL11.glPopMatrix();
	//	GL11.glPopAttrib();//22
	}
	
	public void render_spg(Minecraft minecraft, EntityMobVehicle_Inv_Base balaam, EntityPlayer entityplayer, int i, int j) {
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);
		
		GL11.glPushMatrix();//21
	//	GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		{
			String x = String.format("%1$2f", balaam.spg_yaw);
			fontrenderer.drawStringWithShadow("X:" + x, scaledresolution.getScaledWidth()/2 + 50,  scaledresolution.getScaledHeight()/2 + 10, Color.GREEN.getRGB());
			String y = String.format("%1$2f", balaam.spg_y);
			fontrenderer.drawStringWithShadow("Y:" + y,  scaledresolution.getScaledWidth()/2 + 50,  scaledresolution.getScaledHeight()/2 + 00, Color.GREEN.getRGB());
			String z = String.format("%1$2f", balaam.spg_picth);
			fontrenderer.drawStringWithShadow("Z:" + z,  scaledresolution.getScaledWidth()/2 + 50,  scaledresolution.getScaledHeight()/2 - 10, Color.GREEN.getRGB());
		}
		
		
		GL11.glPopMatrix();
	//	GL11.glPopAttrib();//22
	}
}
