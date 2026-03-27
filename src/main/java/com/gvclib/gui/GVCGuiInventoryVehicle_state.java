package gvclib.gui;
 
import java.awt.Color;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import gvclib.entity.living.EntityVehicleBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
 
public class GVCGuiInventoryVehicle_state extends GuiContainer
{
    //private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");
    private static final ResourceLocation texture = new ResourceLocation("gvclib:textures/gui/vehicle_state.png");
    /** The mouse x-position recorded during the last rendered frame. */
    private float mousePosx;
    /** The mouse y-position recorded during the last renderered frame. */
    private float mousePosY;
    private static EntityPlayer player;
    private static EntityVehicleBase vehicle;
    
    private GuiButton home;
    private GuiButton weapon1;
    private GuiButton weapon2;
    private GuiButton weapon3;
    private GuiButton weapon4;
    
    
    public int page = 0;
 
    public GVCGuiInventoryVehicle_state(EntityPlayer p, EntityVehicleBase d)
    {
        super(new GVCContainerVehicle_state());
        this.xSize = 255;
        player = p;
        vehicle = d;
    }
    
    public void initGui()
    {
    	super.initGui();
    	int xpos = (this.width - this.xSize) / 2;
        int ypos = (this.height - this.ySize) / 2;
        this.home = this.addButton(new GuiButton(0, xpos + 255, ypos + 0, 30, 20, I18n.format("HOME")));
    	this.weapon1 = this.addButton(new GuiButton(1, xpos + 255, ypos + 20, 30, 20, I18n.format("WEAPON1")));
    	this.weapon2 = this.addButton(new GuiButton(2, xpos + 255, ypos + 40, 30, 20, I18n.format("WEAPON2")));
    	this.weapon3 = this.addButton(new GuiButton(3, xpos + 255, ypos + 60, 30, 20, I18n.format("WEAPON3")));
    	this.weapon4 = this.addButton(new GuiButton(4, xpos + 255, ypos + 80, 30, 20, I18n.format("WEAPON4")));
    }
    
	/*
        ChestとかInventoryとか文字を描画する
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int p_146979_2_)
    {
    	
        //描画する文字, X, Y, 色
        //this.fontRenderer.drawString("AR Attachment", 8, 6, 4210752);
        //this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
        if(vehicle != null) {
        	int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;
        	
        	//this.fontRenderer.drawString(String.format("%1$3d", doll.getRemain_A()), 50, this.ySize - 100 + 2, 4210752);
        }
    }
 
    protected void actionPerformed(GuiButton button) throws IOException
    {
    	if (button.id == 0)
        {
    		page = 0;
        }
    	if (button.id == 1)
        {
    		page = 1;
        }
    	if (button.id == 2)
        {
    		page = 2;
        }
    	if (button.id == 3)
        {
    		page = 3;
        }
    	if (button.id == 4)
        {
    		page = 4;
        }
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
        if(vehicle != null){
        	GL11.glPushMatrix();
        	GlStateManager.disableLighting();
        	GL11.glScalef(1F, 1F, 1F);
        	GuiInventory.drawEntityOnScreen((k+ 51)/1,( l + 140)/1, 17, (float)(k + 51) - this.mousePosx, (float)(l + 75 - 50) - this.mousePosY, vehicle);
        	GlStateManager.enableLighting();
        	GL11.glPopMatrix();
        }
        
        FontRenderer fontReader = mc.fontRenderer;
		mc.fontRenderer.setUnicodeFlag(mc.isUnicode());
        GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		if(vehicle != null){
			
			if(page ==0) {
				RenderItem renderitem = mc.getRenderItem();
				{
					GL11.glPushMatrix();
					GL11.glScalef(2F, 2F, 2F);
					//fontReader.drawString(vehicle.getCustomNameTag(), (k + 115)/2, (l + 18)/2, 0xFFFFFF);
					GL11.glPopMatrix();
					{
						if(vehicle.vehicletype == 1) {
							fontReader.drawString("AIRCRAFT",  k + 98, l + 10, 0xFFFFFF);
						}else if(vehicle.vehicletype == 2) {
							fontReader.drawString("HILICOPTER",  k + 98, l + 10, 0xFFFFFF);
						}else if(vehicle.vehicletype == 3) {
							fontReader.drawString("SPG",  k + 98, l + 10, 0xFFFFFF);
						}else if(vehicle.vehicletype == 5) {
							fontReader.drawString("BOAT",  k + 98, l + 10, 0xFFFFFF);
						}else {
							fontReader.drawString("VEHICLE",  k + 98, l + 10, 0xFFFFFF);
						}
					}
					if(vehicle.amphibious){
						fontReader.drawString("AMPHIBIOUS",  k + 98, l + 20, 0xFFFFFF);
					}
					{
						String d1 = String.format("%1$3d", (int)vehicle.getHealth());
						String d2 = String.format("%1$3d", (int)vehicle.getMaxHealth());
						fontReader.drawString("HP : "  + d1 + " / " + d2,  k + 98, l + 30, 0xFFFFFF);
					}
					{
						String d1 = String.format("%1$.2f", vehicle.sp);
						fontReader.drawString("SPEED : "  + d1,  k + 98, l + 40, 0xFFFFFF);
					}
					{
						String d1 = String.format("%1$.2f", vehicle.turnspeed);
						fontReader.drawString("TURNSPEED : "  + d1,  k + 98, l + 50, 0xFFFFFF);
					}
					{
						String d1 = String.format("%1$.2f", -vehicle.rotationp_max);
						String d2 = String.format("%1$.2f", -vehicle.rotationp_min);
						fontReader.drawString("+/-ANGLE : "  + d1 + " / " + d2 ,  k + 98, l + 60, 0xFFFFFF);
					}
					if(vehicle.ridding_rotation_lock){
						String d1 = String.format("%1$.2f", vehicle.rotation_max);
						fontReader.drawString("ANGLE_LIMIT : "  + d1,  k + 98, l + 70, 0xFFFFFF);
					}else {
						fontReader.drawString("ANGLE_FREE",  k + 98, l + 70, 0xFFFFFF);
					}
					{
						fontReader.drawString("ARMOR",  k + 98, l + 80, 0xFFFFFF);
					}
					{
						String d1 = String.format("%1$.2f", vehicle.damage_front);
						String d2 = String.format("%1$.2f", vehicle.damage_side);
						String d3 = String.format("%1$.2f", vehicle.damage_rear);
						fontReader.drawString("::FRONT/SIDE/REAR : "  + d1 + " / " + d2 + " / " + d3,  k + 98, l + 90, 0xFFFFFF);
					}
					{
						String d1 = String.format("%1$.2f", vehicle.damage_top);
						String d2 = String.format("%1$.2f", vehicle.damage_bottom);
						fontReader.drawString("::TOP/BOTTOM : "  + d1 + " / " + d2,  k + 98, l + 100, 0xFFFFFF);
					}
					if(vehicle.can_turret){
						fontReader.drawString("TURRET_ARMOR",  k + 98, l + 110, 0xFFFFFF);
						
						String d1 = String.format("%1$.2f", vehicle.damage_turret_front);
						String d2 = String.format("%1$.2f", vehicle.damage_turret_side);
						String d3 = String.format("%1$.2f", vehicle.damage_turret_rear);
						fontReader.drawString("::FRONT/SIDE/REAR : "  + d1 + " / " + d2 + " / " + d3,  k + 98, l + 120, 0xFFFFFF);
					}
					{
						fontReader.drawString("ANTI_BULLET",  k + 98, l + 130, 0xFFFFFF);
						
						String d1 = String.format("%1$.2f", vehicle.antibullet_0);
						String d2 = String.format("%1$.2f", vehicle.antibullet_1);
						String d3 = String.format("%1$.2f", vehicle.antibullet_2);
						String d4 = String.format("%1$.2f", vehicle.antibullet_3);
						fontReader.drawString("::"  + d1 + " / " + d2 + " / " + d3 + " / " + d3,  k + 98, l + 140, 0xFFFFFF);
					}
				}
			}
			else if(page ==1 && vehicle.weapon1true){
				{
					fontReader.drawString("WEAPON1",  k + 98, l + 10, 0xFFFFFF);
					fontReader.drawString(vehicle.w1name,  k + 98, l + 20, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$3d", (int)vehicle.getRemain_L());
					String d2 = String.format("%1$3d", (int)vehicle.magazine);
					fontReader.drawString("MAGAZINE : "  + d1 + " / " + d2,  k + 98, l + 30, 0xFFFFFF);
				}
				{
					int cycle = vehicle.ammo1;
					if(cycle <= 0)cycle = 1;
					String d1 = String.format("%1$3d", (int)(1200 / cycle));
					fontReader.drawString("CYCLE : "  + d1 + "rpm",  k + 98, l + 40, 0xFFFFFF);
				}
				{
					int i = vehicle.bullet_type1[0];
					String bu = "Bullet";
					if(i == 0) {
						bu = "Bullet";
					}
					if(i == 1) {
						bu = "HugeBullet";
					}
					if(i == 2) {
						bu = "HE";
					}
					if(i == 3) {
						bu = "AP";
					}
					if(i == 4) {
						bu = "MIssile";
					}
					if(i == 5) {
						bu = "Bullet";
					}
					if(i == 6) {
						bu = "Fire";
					}
					if(i == 7) {
						bu = "VT_fuze";
					}
					if(i == 8) {
						bu = "Remote_Missile";
					}
					if(i == 9) {
						bu = "Grenade";
					}
					if(i == 10) {
						bu = "Bullet";
					}
					if(i == 11) {
						bu = "Mortar_HE";
					}
					if(i == 12) {
						bu = "Mortar_AP";
					}
					if(i == 13) {
						bu = "Mortar_HE";
					}
					if(i == 14) {
						bu = "Mortar_AP";
					}
					if(i == 21) {
						bu = "Flare";
					}
					fontReader.drawString("BULLE_TYPE : " + bu,  k + 98, l + 50, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$3d", (int)vehicle.bullet_damage1[0]);
					fontReader.drawString("DAMAGE : "  + d1,  k + 98, l + 60, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", vehicle.bullet_speed1[0]);
					fontReader.drawString("SPEED : "  + d1,  k + 98, l + 70, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", vehicle.bullet_bure1[0]);
					fontReader.drawString("GROUPING : "  + d1,  k + 98, l + 80, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", vehicle.bullet_expower1[0]);
					fontReader.drawString("EX_POWER : "  + d1,  k + 98, l + 90, 0xFFFFFF);
				}
				{
					String tr = "true";
					if(vehicle.bullet_ex1[0]) {
						 tr = "true";
					}else {
						 tr = "false";
					}
					fontReader.drawString("BLOCK_DESTROY : "  + tr,  k + 98, l + 100, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$3d", (int)vehicle.bullet_kazu1[0]);
					fontReader.drawString("KAZU : "  + d1,  k + 98, l + 110, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", (float)vehicle.bullet_gravity1[0]);
					fontReader.drawString("GRAVITY : "  + d1,  k + 98, l + 120, 0xFFFFFF);
				}
			}
			else if(page ==2 && vehicle.weapon2true){
				{
					fontReader.drawString("WEAPON2",  k + 98, l + 10, 0xFFFFFF);
					fontReader.drawString(vehicle.w2name,  k + 98, l + 20, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$3d", (int)vehicle.getRemain_R());
					String d2 = String.format("%1$3d", (int)vehicle.magazine2);
					fontReader.drawString("MAGAZINE : "  + d1 + " / " + d2,  k + 98, l + 30, 0xFFFFFF);
				}
				{
					int cycle = vehicle.ammo2;
					if(cycle <= 0)cycle = 1;
					String d1 = String.format("%1$3d", (int)(1200 / cycle));
					fontReader.drawString("CYCLE : "  + d1 + "rpm",  k + 98, l + 40, 0xFFFFFF);
				}
				{
					int i = vehicle.bullet_type2[0];
					String bu = "Bullet";
					if(i == 0) {
						bu = "Bullet";
					}
					if(i == 1) {
						bu = "HugeBullet";
					}
					if(i == 2) {
						bu = "HE";
					}
					if(i == 3) {
						bu = "AP";
					}
					if(i == 4) {
						bu = "MIssile";
					}
					if(i == 5) {
						bu = "Bullet";
					}
					if(i == 6) {
						bu = "Fire";
					}
					if(i == 7) {
						bu = "VT_fuze";
					}
					if(i == 8) {
						bu = "Remote_Missile";
					}
					if(i == 9) {
						bu = "Grenade";
					}
					if(i == 10) {
						bu = "Bullet";
					}
					if(i == 11) {
						bu = "Mortar_HE";
					}
					if(i == 12) {
						bu = "Mortar_AP";
					}
					if(i == 13) {
						bu = "Mortar_HE";
					}
					if(i == 14) {
						bu = "Mortar_AP";
					}
					if(i == 21) {
						bu = "Flare";
					}
					fontReader.drawString("BULLE_TYPE : " + bu,  k + 98, l + 50, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$3d", (int)vehicle.bullet_damage2[0]);
					fontReader.drawString("DAMAGE : "  + d1,  k + 98, l + 60, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", vehicle.bullet_speed2[0]);
					fontReader.drawString("SPEED : "  + d1,  k + 98, l + 70, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", vehicle.bullet_bure2[0]);
					fontReader.drawString("GROUPING : "  + d1,  k + 98, l + 80, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", vehicle.bullet_expower2[0]);
					fontReader.drawString("EX_POWER : "  + d1,  k + 98, l + 90, 0xFFFFFF);
				}
				{
					String tr = "true";
					if(vehicle.bullet_ex2[0]) {
						 tr = "true";
					}else {
						 tr = "false";
					}
					fontReader.drawString("BLOCK_DESTROY : "  + tr,  k + 98, l + 100, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$3d", (int)vehicle.bullet_kazu2[0]);
					fontReader.drawString("KAZU : "  + d1,  k + 98, l + 110, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", (float)vehicle.bullet_gravity2[0]);
					fontReader.drawString("GRAVITY : "  + d1,  k + 98, l + 120, 0xFFFFFF);
				}
			}
			else if(page ==3 && vehicle.weapon3true){
				{
					fontReader.drawString("WEAPON3",  k + 98, l + 10, 0xFFFFFF);
					fontReader.drawString(vehicle.w3name,  k + 98, l + 20, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$3d", (int)vehicle.getRemain_A());
					String d2 = String.format("%1$3d", (int)vehicle.magazine3);
					fontReader.drawString("MAGAZINE : "  + d1 + " / " + d2,  k + 98, l + 30, 0xFFFFFF);
				}
				{
					int cycle = vehicle.ammo3;
					if(cycle <= 0)cycle = 1;
					String d1 = String.format("%1$3d", (int)(1200 / cycle));
					fontReader.drawString("CYCLE : "  + d1 + "rpm",  k + 98, l + 40, 0xFFFFFF);
				}
				{
					int i = vehicle.bullet_type3[0];
					String bu = "Bullet";
					if(i == 0) {
						bu = "Bullet";
					}
					if(i == 1) {
						bu = "HugeBullet";
					}
					if(i == 2) {
						bu = "HE";
					}
					if(i == 3) {
						bu = "AP";
					}
					if(i == 4) {
						bu = "MIssile";
					}
					if(i == 5) {
						bu = "Bullet";
					}
					if(i == 6) {
						bu = "Fire";
					}
					if(i == 7) {
						bu = "VT_fuze";
					}
					if(i == 8) {
						bu = "Remote_Missile";
					}
					if(i == 9) {
						bu = "Grenade";
					}
					if(i == 10) {
						bu = "Bullet";
					}
					if(i == 11) {
						bu = "Mortar_HE";
					}
					if(i == 12) {
						bu = "Mortar_AP";
					}
					if(i == 13) {
						bu = "Mortar_HE";
					}
					if(i == 14) {
						bu = "Mortar_AP";
					}
					if(i == 21) {
						bu = "Flare";
					}
					fontReader.drawString("BULLE_TYPE : " + bu,  k + 98, l + 50, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$3d", (int)vehicle.bullet_damage3[0]);
					fontReader.drawString("DAMAGE : "  + d1,  k + 98, l + 60, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", vehicle.bullet_speed3[0]);
					fontReader.drawString("SPEED : "  + d1,  k + 98, l + 70, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", vehicle.bullet_bure3[0]);
					fontReader.drawString("GROUPING : "  + d1,  k + 98, l + 80, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", vehicle.bullet_expower3[0]);
					fontReader.drawString("EX_POWER : "  + d1,  k + 98, l + 90, 0xFFFFFF);
				}
				{
					String tr = "true";
					if(vehicle.bullet_ex3[0]) {
						 tr = "true";
					}else {
						 tr = "false";
					}
					fontReader.drawString("BLOCK_DESTROY : "  + tr,  k + 98, l + 100, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$3d", (int)vehicle.bullet_kazu3[0]);
					fontReader.drawString("KAZU : "  + d1,  k + 98, l + 110, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", (float)vehicle.bullet_gravity3[0]);
					fontReader.drawString("GRAVITY : "  + d1,  k + 98, l + 120, 0xFFFFFF);
				}
			}
			else if(page ==4 && vehicle.weapon4true){
				{
					fontReader.drawString("WEAPON4",  k + 98, l + 10, 0xFFFFFF);
					fontReader.drawString(vehicle.w4name,  k + 98, l + 20, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$3d", (int)vehicle.getRemain_S());
					String d2 = String.format("%1$3d", (int)vehicle.magazine4);
					fontReader.drawString("MAGAZINE : "  + d1 + " / " + d2,  k + 98, l + 30, 0xFFFFFF);
				}
				{
					int cycle = vehicle.ammo4;
					if(cycle <= 0)cycle = 1;
					String d1 = String.format("%1$3d", (int)(1200 / cycle));
					fontReader.drawString("CYCLE : "  + d1 + "rpm",  k + 98, l + 40, 0xFFFFFF);
				}
				{
					int i = vehicle.bullet_type3[0];
					String bu = "Bullet";
					if(i == 0) {
						bu = "Bullet";
					}
					if(i == 1) {
						bu = "HugeBullet";
					}
					if(i == 2) {
						bu = "HE";
					}
					if(i == 3) {
						bu = "AP";
					}
					if(i == 4) {
						bu = "MIssile";
					}
					if(i == 5) {
						bu = "Bullet";
					}
					if(i == 6) {
						bu = "Fire";
					}
					if(i == 7) {
						bu = "VT_fuze";
					}
					if(i == 8) {
						bu = "Remote_Missile";
					}
					if(i == 9) {
						bu = "Grenade";
					}
					if(i == 10) {
						bu = "Bullet";
					}
					if(i == 11) {
						bu = "Mortar_HE";
					}
					if(i == 12) {
						bu = "Mortar_AP";
					}
					if(i == 13) {
						bu = "Mortar_HE";
					}
					if(i == 14) {
						bu = "Mortar_AP";
					}
					if(i == 21) {
						bu = "Flare";
					}
					fontReader.drawString("BULLE_TYPE : " + bu,  k + 98, l + 50, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$3d", (int)vehicle.bullet_damage4[0]);
					fontReader.drawString("DAMAGE : "  + d1,  k + 98, l + 60, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", vehicle.bullet_speed4[0]);
					fontReader.drawString("SPEED : "  + d1,  k + 98, l + 70, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", vehicle.bullet_bure4[0]);
					fontReader.drawString("GROUPING : "  + d1,  k + 98, l + 80, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", vehicle.bullet_expower4[0]);
					fontReader.drawString("EX_POWER : "  + d1,  k + 98, l + 90, 0xFFFFFF);
				}
				{
					String tr = "true";
					if(vehicle.bullet_ex4[0]) {
						 tr = "true";
					}else {
						 tr = "false";
					}
					fontReader.drawString("BLOCK_DESTROY : "  + tr,  k + 98, l + 100, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$3d", (int)vehicle.bullet_kazu4[0]);
					fontReader.drawString("KAZU : "  + d1,  k + 98, l + 110, 0xFFFFFF);
				}
				{
					String d1 = String.format("%1$.2f", (float)vehicle.bullet_gravity4[0]);
					fontReader.drawString("GRAVITY : "  + d1,  k + 98, l + 120, 0xFFFFFF);
				}
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
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}