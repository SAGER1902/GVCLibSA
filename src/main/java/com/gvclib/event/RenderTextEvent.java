package gvclib.event;

import org.lwjgl.opengl.GL11;

import gvclib.item.ItemGunBase;
import gvclib.item.ItemMagazine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import gvclib.RenderParameters;
import static gvclib.RenderParameters.*;

public class RenderTextEvent extends Gui {

	private final static ResourceLocation overlayTimerSun = new ResourceLocation(
			"hmggvc:textures/misc/ironsight_ak74.png");

	private final static int DAY_WIDTH = 34;
	private final static int DAY_HEIGHT = 34;

	private Minecraft mc;
	private int iii;

	public RenderTextEvent(Minecraft mc) {
		this.mc = mc;
	}
	
	public void rendergun_cross(ItemStack itemstack, ItemGunBase gun, int i, int j, float sneak) {
		ScaledResolution scaledresolution = new ScaledResolution(mc);
		float b = gun.bure * 4 * sneak;
		NBTTagCompound nbt = itemstack.getTagCompound();
		/*if(nbt != null) {
			boolean recoiled = nbt.getBoolean("Recoiled");
			if(!recoiled) {
				b = b *1.6F;
			}
		}*/
		float recoil = 0;
		recoil -= playerRecoilPitch;
		recoil += antiRecoilPitch * 0.25F;
		
		b = b*(1-recoil*0.1F);
		
		GuiIngame g  = mc.ingameGUI;
		{
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
				mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/cross_h.png"));
				GL11.glTranslatef(0.5F,0F, 0F);
				GL11.glScalef(0.0625f, 0.0625f, 1);
				g.drawTexturedModalRect((scaledresolution.getScaledWidth()/2 - 8 - b)*16,
						(scaledresolution.getScaledHeight()/2 - 8)*16, 0,0, 256, 256);
				GL11.glEnable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
		{
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
				mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/cross_h.png"));
				GL11.glTranslatef(0.5F,0F, 0F);
				GL11.glScalef(0.0625f, 0.0625f, 1);
				g.drawTexturedModalRect((scaledresolution.getScaledWidth()/2 - 8 + b)*16,
						(scaledresolution.getScaledHeight()/2 - 8)*16, 0,0, 256, 256);
				GL11.glEnable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
		{
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
				mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/cross_v.png"));
				GL11.glTranslatef(0.5F,0F, 0F);
				GL11.glScalef(0.0625f, 0.0625f, 1);
				g.drawTexturedModalRect((scaledresolution.getScaledWidth()/2 - 8)*16,
						(scaledresolution.getScaledHeight()/2 - 8 - b)*16, 0,0, 256, 256);
				GL11.glEnable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
		{
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
				mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/cross_v.png"));
				GL11.glTranslatef(0.5F,0F, 0F);
				GL11.glScalef(0.0625f, 0.0625f, 1);
				g.drawTexturedModalRect((scaledresolution.getScaledWidth()/2 - 8)*16,
						(scaledresolution.getScaledHeight()/2 - 8 + b)*16, 0,0, 256, 256);
				GL11.glEnable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}
	
	public void rendergun(ItemStack itemstack, ItemGunBase gun, int i, int j) {
		EntityPlayer player = mc.player;
		InventoryPlayer playerInv = player.inventory;
		FontRenderer fontReader = mc.fontRenderer;
		mc.fontRenderer.setUnicodeFlag(mc.isUnicode());
		
		GL11.glPushMatrix();//21
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/gun_hud_back.png"));
		GL11.glScalef(0.25f, 0.125f, 1);
		drawTexturedModalRect((i -77)*4,(j-50)*8, 0,0, 256, 256);
		GL11.glPopMatrix();//22
		GL11.glPushMatrix();//21
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/gun_hud.png"));
		GL11.glScalef(0.25f, 0.125f, 1);
		drawTexturedModalRect((i -77)*4,(j-50)*8, 0,0, 256, 256);
		GL11.glPopMatrix();//22
		
		
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		String d = String.format("%1$3d", itemstack.getMaxDamage() - itemstack.getItemDamage());
		String d1 = String.format("%1$3d", itemstack.getMaxDamage());
		for (int is = 0; is < 36; ++is) {
			ItemStack itemi = playerInv.getStackInSlot(is);
			if (!itemi.isEmpty()) {
				if(gun.magazine != null && gun.magazine instanceof ItemMagazine) {
		    		ItemMagazine maga = (ItemMagazine) gun.magazine;
		    		if(itemi.getItem() instanceof ItemMagazine) {
		    			ItemMagazine maga_item = (ItemMagazine)itemi.getItem();
		    			if(maga_item.magazine_tab.equals(maga.magazine_tab)) {
		    				iii = iii + itemi.getCount();
		    			}
		    		}
				}else if(itemi.getItem() == gun.magazine){
					iii = iii + itemi.getCount();
				}
			}

		}
		String d2 = String.format("%1$3d", iii);
		fontReader.drawString(d + " /" + d1, i -70, j - 30 + 0, 0xFFFFFF);
		fontReader.drawString( "x " + d2, i -50, j - 45 + 0, 0xFFFFFF);
		iii = 0;
		if(gun.magazine != null)
		{
			RenderItem renderitem = mc.getRenderItem();
			renderitem.renderItemIntoGUI(new ItemStack(gun.magazine), i -68, j-49);
		}
		GL11.glPopMatrix();
		
		if(!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand() == itemstack 
				&& !gun.underbarrel.isEmpty() && gun.underbarrel.getItem() instanceof ItemGunBase)
			//if(!gun.underbarrel.isEmpty() && gun.underbarrel.getItem() instanceof ItemGunBase)
			{
			ItemGunBase under = (ItemGunBase) gun.underbarrel.getItem();
			GL11.glPushMatrix();//21
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/gun_hud_back.png"));
			GL11.glScalef(0.25f, 0.125f, 1);
			drawTexturedModalRect((i -77)*4,(j-80)*8, 0,0, 256, 256);
			GL11.glPopMatrix();//22
			GL11.glPushMatrix();//21
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/gun_hud.png"));
			GL11.glScalef(0.25f, 0.125f, 1);
			drawTexturedModalRect((i -77)*4,(j-80)*8, 0,0, 256, 256);
			GL11.glPopMatrix();//22
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			String dx = String.format("%1$3d", gun.underbarrel.getMaxDamage() - gun.underbarrel.getItemDamage());
			String d1x = String.format("%1$3d", gun.underbarrel.getMaxDamage());
			for (int is = 0; is < 36; ++is) {
				ItemStack itemi = playerInv.getStackInSlot(is);
				if (itemi != null && itemi.getItem() == under.magazine) {
					iii = iii + itemi.getCount();
				}
			}
			String d2x = String.format("%1$3d", iii);
			fontReader.drawString(dx + " /" + d1x, i -70, j - 60 + 0, 0xFFFFFF);
			fontReader.drawString( "x " + d2x, i -50, j - 75 + 0, 0xFFFFFF);
			iii = 0;
			if(under.magazine != null)
			{
				RenderItem renderitem = mc.getRenderItem();
				renderitem.renderItemIntoGUI(new ItemStack(under.magazine), i -68, j-79);
			}
			GL11.glPopMatrix();
		}

	}
	
	public void rendergunl(ItemStack itemstack, ItemGunBase gun, int i, int j) {
		EntityPlayer player = mc.player;
		InventoryPlayer playerInv = player.inventory;
		FontRenderer fontReader = mc.fontRenderer;
		mc.fontRenderer.setUnicodeFlag(mc.isUnicode());
		
		GL11.glPushMatrix();//21
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/gun_hud_back.png"));
		GL11.glScalef(0.25f, 0.125f, 1);
		drawTexturedModalRect((10)*4,(j-50)*8, 0,0, 256, 256);
		GL11.glPopMatrix();//22
		GL11.glPushMatrix();//21
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		mc.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/gun_hud.png"));
		GL11.glScalef(0.25f, 0.125f, 1);
		drawTexturedModalRect((10)*4,(j-50)*8, 0,0, 256, 256);
		GL11.glPopMatrix();//22
		
		
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		String d = String.format("%1$3d", itemstack.getMaxDamage() - itemstack.getItemDamage());
		String d1 = String.format("%1$3d", itemstack.getMaxDamage());
		for (int is = 0; is < 36; ++is) {
			ItemStack itemi = playerInv.getStackInSlot(is);
			if (itemi != null && itemi.getItem() == gun.magazine) {
				iii = iii + itemi.getCount();

			}

		}
		String d2 = String.format("%1$3d", iii);
		fontReader.drawString(d + " /" + d1, 20, j - 30 + 0, 0xFFFFFF);
		fontReader.drawString( "x " + d2, 40, j - 45 + 0, 0xFFFFFF);
		iii = 0;
		if(gun.magazine != null)
		{
			RenderItem renderitem = mc.getRenderItem();
			renderitem.renderItemIntoGUI(new ItemStack(gun.magazine), 23, j-49);
		}
		GL11.glPopMatrix();

	}
}