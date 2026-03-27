package gvclib.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import gvclib.entity.living.EntityMobVehicle_Inv_Base;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GVCGuiInventoryItem_vehicle extends GuiContainer
{
    //private static final ResourceLocation HORSE_GUI_TEXTURES = new ResourceLocation("pixelcaravan:textures/gui/guitest.png");
	private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("gvclib:textures/gui/gui_tank.png");
    /** The player inventory bound to this GUI. */
    private final IInventory playerInventory;
    /** The horse inventory bound to this GUI. */
    private final IInventory horseInventory;
    /** The EntityHorse whose inventory is currently being accessed. */
    private final EntityMobVehicle_Inv_Base trader;
    /** The mouse x-position recorded during the last rendered frame. */
    private float mousePosx;
    /** The mouse y-position recorded during the last renderered frame. */
    private float mousePosY;

    private final int inventoryRows;
    
    public GVCGuiInventoryItem_vehicle(IInventory playerInv, IInventory horseInv, EntityMobVehicle_Inv_Base horse)
    {
        super(new GVCContainerInventory_vehicle(playerInv, horseInv, horse, Minecraft.getMinecraft().player));
        this.playerInventory = playerInv;
        this.horseInventory = horseInv;
        this.trader = horse;
        this.allowUserInput = false;
        //this.ySize = 222;
        this.inventoryRows = horse.slot_max / 9;
       // this.ySize = 114 + this.inventoryRows * 18;
       // this.xSize = 237;
    }

    public void initGui()
    {
    	super.initGui();
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        //this.fontRenderer.drawString(trader.getCustomNameTag(), 8, 6, 4210752);
    	this.fontRenderer.drawString(I18n.format("container.crafting"), 8, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
    	 
    }
    
    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        	this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
            int i = (this.width - this.xSize) / 2;
            int j = (this.height - this.ySize) / 2;
            this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
            //this.drawTexturedModalRect(i, j, 0, 0, 220, 212);
           // this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
            //this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
            /*if(trader != null){
            	GL11.glPushMatrix();
            	GL11.glScalef(1F, 1F, 1F);
            	GuiInventory.drawEntityOnScreen((i+ 100), (j + 20), 17, 300, 0, trader);
            	GL11.glPopMatrix();
            }*/
        {
        	FontRenderer fontReader = mc.fontRenderer;
    		mc.fontRenderer.setUnicodeFlag(mc.isUnicode());
            GL11.glPushMatrix();
    		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
    				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
    				GlStateManager.DestFactor.ZERO);
    		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    		{
				String d1 = String.format("%1$3d", (int)trader.getHealth());
				String d2 = String.format("%1$3d", (int)trader.getMaxHealth());
				fontReader.drawString("HP : "  + d1 + " / " + d2,  i + 10, j + 22, Color.BLACK.getRGB());
			}
    		{
				String d1 = String.format("%1$.2f", trader.sp * 100);
				fontReader.drawString("SPEED: "  + d1,  i + 10, j + 32, Color.BLACK.getRGB());
			}
    		{
				String d1 = String.format("%1$.2f", trader.turnspeed);
				fontReader.drawString("TURN-SPD: "  + d1,  i + 10, j + 42, Color.BLACK.getRGB());
			}
    		{
				String d1 = String.format("%1$.1f", -trader.rotationp_max);
				String d2 = String.format("%1$.1f", -trader.rotationp_min);
				fontReader.drawString("E/D ANGLE: "  + d1 + " / " + d2,  i + 10, j + 52, Color.BLACK.getRGB());
			}
    		
    		RenderItem renderitem = mc.getRenderItem();
    		{
    			ItemStack item =  new ItemStack(trader.fuel_item);
				if (!item.isEmpty()) {
					renderitem.renderItemIntoGUI(item, i + 80,j + 18);
				}
    		}
    		{
    			ItemStack item =  new ItemStack(trader.weapon1_item);
				if (!item.isEmpty()) {
					renderitem.renderItemIntoGUI(item, i + 80,j + 36);
				}
    		}
    		{
    			ItemStack item =  new ItemStack(trader.weapon2_item);
				if (!item.isEmpty()) {
					renderitem.renderItemIntoGUI(item, i + 80,j + 54);
				}
    		}
    		GL11.glPopMatrix();
        }
        
        
        
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