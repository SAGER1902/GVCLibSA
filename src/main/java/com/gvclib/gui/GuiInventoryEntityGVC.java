package gvclib.gui;

import org.lwjgl.opengl.GL11;

import gvclib.block.tile.TileEntityInvasion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiInventoryEntityGVC extends GuiContainer
{
	//private static final ResourceLocation field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
	private static final ResourceLocation texture = new ResourceLocation("gvclib:textures/gui/inv.png");
	//private static final ResourceLocation texturepara = new ResourceLocation("gvcrbattlemachine:textures/gui/robogui_para.png");
	 private final InventoryPlayer playerInventory;
	 private final IInventory tileFurnace;
    private TileEntityInvasion tile;
    private int inventoryRows;
    private static final String __OBFID = "CL_00000760";
    private boolean tex = false;
    
    /** The old x position of the mouse pointer */
    private float oldMouseX;
    /** The old y position of the mouse pointer */
    private float oldMouseY;

    public GuiInventoryEntityGVC(InventoryPlayer player, IInventory furnaceInv, TileEntityInvasion t)
    {
        super(new ContainerInventoryEntityGVC(player, t));
        this.playerInventory = player;
        this.tileFurnace = furnaceInv;
        this.tile = t;
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.tileFurnace.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int i = this.guiLeft;
        int j = this.guiTop;
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        int k = x + (this.xSize / 2);
        int l = y + (this.ySize / 2);
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        {
        	//String c = String.valueOf(TileEntityInvasion.getClientRange(tile));
        	String c = String.valueOf(TileEntityInvasion.getClientRange(tile));
        	this.fontRenderer.drawString("SpwanRange : " + c, x + 80, y + 20, 4210752);
        	String d = String.valueOf(TileEntityInvasion.getClientLevel(tile));
        	this.fontRenderer.drawString("Wave : " + d, x + 80, y + 30, 4210752);
        	String hp = String.valueOf(100 - TileEntityInvasion.getClientHP(tile));
        	this.fontRenderer.drawString("HP : " + hp, x + 80, y + 40, 4210752);
        	
        	String time = String.valueOf((this.tile.spawntime - TileEntityInvasion.getClientTick(tile)) / 20);
        	this.fontRenderer.drawString("Time : " + time, x + 80, y + 50, 4210752);
        }
        
        
        GL11.glPushMatrix();//glstart
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.scale(0.75F, 0.75F, 0.75F);
        //drawEntityOnScreen(i + 110, j + 170, 30, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 50) - this.oldMouseY, field_147034_x);
        GL11.glPopMatrix();//glend
    }
    
    
    @Override
    protected void actionPerformed(GuiButton button) {
    	
    }
    
    /**
     * Draws an entity on the screen looking toward the cursor.
     */
    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}