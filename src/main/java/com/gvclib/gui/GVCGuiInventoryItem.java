package gvclib.gui;
 
import org.lwjgl.opengl.GL11;

import gvclib.event.gun.Layermat;
import gvclib.item.ItemGunBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import gvclib.item.ItemAttachment;

public class GVCGuiInventoryItem extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation("gvclib:textures/gui/gun_gui.png");
    private static EntityPlayer player;
    /** The mouse x-position recorded during the last rendered frame. */
    private float mousePosx;
    /** The mouse y-position recorded during the last renderered frame. */
    private float mousePosY;
 
    public GVCGuiInventoryItem(InventoryPlayer inventoryPlayer, ItemStack itemstack)
    {
        super(new GVCContainerInventoryItem(inventoryPlayer, itemstack));
        this.ySize = 222;
        player = inventoryPlayer.player;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int p_146979_2_)
    {
    	
        //描画する文字, X, Y, 色
        this.fontRenderer.drawString("Attachment", 8, 6, 4210752);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
    }
 
    /*
        背景の描画
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int x = 20;
        GL11.glPushMatrix();
		//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(this.player != null){
			ItemStack itemstack = player.getHeldItemMainhand();
			if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase) {//item
				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
				if(!gun.render_sight) {
					if(gun.canuse_sight && gun.designated_kazu > 0) {
						this.drawTexturedModalRect(k + 11+4*x, l + 19, 192, 0, 16, 16);
					}else if(gun.canuse_sight) {
						this.drawTexturedModalRect(k + 11+4*x, l + 19, 208, 0, 16, 16);
					}else {
						this.drawTexturedModalRect(k + 11+4*x, l + 19, 176, 0, 16, 16);
					}
				}
				if(!gun.render_light) {
					if(gun.canuse_light && gun.designated_kazu > 0) {
						this.drawTexturedModalRect(k + 11+x, l + 19, 192, 0, 16, 16);// x y
					}else if(gun.canuse_light) {
						this.drawTexturedModalRect(k + 11+x, l + 19, 208, 0, 16, 16);
					}else {
						this.drawTexturedModalRect(k + 11+x, l + 19, 176, 0, 16, 16);
					}
				}
				if(!gun.render_muss) {
					if(gun.canuse_muss && gun.designated_kazu > 0) {
						this.drawTexturedModalRect(k + 11, l + 19, 192, 0, 16, 16);
					}else if(gun.canuse_muss) {
						this.drawTexturedModalRect(k + 11, l + 19, 208, 0, 16, 16);
					}else {
						this.drawTexturedModalRect(k + 11, l + 19, 176, 0, 16, 16);
					}
				}
				if(!gun.render_grip) {
					if(gun.canuse_grip && gun.designated_kazu > 0) {
						this.drawTexturedModalRect(k + 11+2*x, l + 19, 192, 0, 16, 16);
					}else if(gun.canuse_grip) {
						this.drawTexturedModalRect(k + 11+2*x, l + 19, 208, 0, 16, 16);
					}else {
						this.drawTexturedModalRect(k + 11+2*x, l + 19, 176, 0, 16, 16);
					}
				}
				//if(!gun.rightmode)this.drawTexturedModalRect(k + 98, l + 36, 176, 0, 15, 15);
			}
		}
		GL11.glPopMatrix();
        
        GL11.glPushMatrix();
		//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(this.player != null){
			ItemStack itemstack = player.getHeldItemMainhand();
			if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase) {//item
				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
				ScaledResolution scaledresolution = new ScaledResolution(mc);
				//GL11.glScalef(-1F, 1F, 1F);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GlStateManager.translate(scaledresolution.getScaledWidth()/2 + 20, scaledresolution.getScaledHeight()/2 - 20, 5);
				GL11.glScalef(15F, 15F, 15F);
				//GlStateManager.translate(15, 7, 5);
				GlStateManager.rotate(-180, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
				gun.obj_model.renderPart("mat1");
				gun.obj_model.renderPart("mat100");
				gun.obj_model.renderPart("mat2");
				gun.obj_model.renderPart("mat3");
				Layermat.rendersight( player,  itemstack,  gun);
				//this.rendersight(player, itemstack, gun);
				Layermat.renderattachment( player,  itemstack,  gun);
				gun.obj_model.renderPart("mat25");
				gun.obj_model.renderPart("mat31");
				gun.obj_model.renderPart("mat32");/**/
			}
		}
		GL11.glPopMatrix();
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.mousePosx = (float)mouseX;
        this.mousePosY = (float)mouseY;
        super.drawScreen(mouseX, mouseY, partialTicks);
        //this.renderHoveredToolTip(mouseX, mouseY);
        this.renderHoveredToolTip(mouseX, mouseY);
        //player.inventoryContainer.renderTooltip(this.guiLeft, this.guiTop, mouseX, mouseY);
    }
}