package gvclib.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import gvclib.mod_GVCLib;
import gvclib.entity.trader.EntityNPCBase;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
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
public class TraderGuiInventoryItem extends GuiContainer
{
    private static final ResourceLocation HORSE_GUI_TEXTURES = new ResourceLocation("gvclib:textures/gui/trader.png");
    /** The player inventory bound to this GUI. */
    private final IInventory playerInventory;
    /** The horse inventory bound to this GUI. */
    private final IInventory horseInventory;
    /** The EntityHorse whose inventory is currently being accessed. */
    private final EntityNPCBase trader;
    /** The mouse x-position recorded during the last rendered frame. */
    private float mousePosx;
    /** The mouse y-position recorded during the last renderered frame. */
    private float mousePosY;

    private GuiButton next;
    private GuiButton back;
    
    private GuiButton next2;
    private GuiButton back2;
    
    public TraderGuiInventoryItem(IInventory playerInv, IInventory horseInv, EntityNPCBase horse)
    {
        super(new TraderContainerInventoryItem(playerInv, horseInv, horse, Minecraft.getMinecraft().player));
        this.playerInventory = playerInv;
        this.horseInventory = horseInv;
        this.trader = horse;
        this.allowUserInput = false;
    }

    public void initGui()
    {
    	super.initGui();
    	this.next = this.addButton(new GuiButton(0, this.width / 2 + 30, this.height / 2 - 10, 20, 10, I18n.format("next")));
    	this.back = this.addButton(new GuiButton(1, this.width / 2 - 30, this.height / 2 - 10, 20, 10, I18n.format("back")));
    	this.next2 = this.addButton(new GuiButton(2, this.width / 2 + 90, this.height / 2 - 80, 20, 10, I18n.format("next")));
    	this.back2 = this.addButton(new GuiButton(3, this.width / 2 + 90, this.height / 2 - 70, 20, 10, I18n.format("back")));
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(trader.getCustomNameTag(), 8, 6, 4210752);
        this.fontRenderer.drawString("page:"+String.format("%1$3d", trader.sell_now_id), 85, 75, 4210752);
        //this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			//GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(1500, trader.getEntityId()));
			if (trader.sell_now_id < trader.sell_page - 1) {
				++trader.sell_now_id;
			}
			int page = 10 * trader.sell_now_id;
			for (int x = 0; x < 9; ++x) {
				trader.hand[0 + x + page] = false;
			}
			GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(1500, trader.getEntityId()));
		}
		if (button.id == 1) {
			//GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(1501, trader.getEntityId()));
			if (trader.sell_now_id > 0) {
				--trader.sell_now_id;
			}
			int page = 10 * trader.sell_now_id;
			for (int x = 0; x < 9; ++x) {
				trader.hand[0 + x + page] = false;
			}
			GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(1501, trader.getEntityId()));
		}
		if (button.id == 2) {
			//GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(1502, trader.getEntityId()));
			if (trader.buy_now_id < trader.buy_page - 1) {
				++trader.buy_now_id;
			}
			GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(1502, trader.getEntityId()));
		}
		if (button.id == 3) {
			//GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(1503, trader.getEntityId()));
			if (trader.buy_now_id > 0) {
				--trader.buy_now_id;
			}
			GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(1503, trader.getEntityId()));
		}
	}
    
    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(HORSE_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        //this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(i, j, 0, 0, 220, 212);

        FontRenderer fontReader = mc.fontRenderer;
		mc.fontRenderer.setUnicodeFlag(mc.isUnicode());
        GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		{
			RenderItem renderitem = mc.getRenderItem();
			ItemStack currency = new ItemStack(trader.currency);
			if (!currency.isEmpty()) {
				renderitem.renderItemIntoGUI(currency, i + 35, j + 30);//62/18
			}
			
			int page = 10 * trader.sell_now_id;
			for(int ii = 0; ii < 9; ++ii) {
				ItemStack item = new ItemStack(trader.sell[ii + page], trader.sell_size[ii + page], trader.sell_id[ii + page]);
				int x = 0;
				int y = 0;
				if(ii < 3) {
					x = 0;
					y = ii;
				}
				else if(ii >= 3 && ii < 6) {
					x = 1;
					y = ii - 3;
				}else {
					x = 2;
					y = ii - 6;
				}
				if (!item.isEmpty()) {
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
					renderitem.renderItemIntoGUI(item, i + 80 + (x * 36), j + 17 + (y * 18));//62/18
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				}
				{
					int s = trader.sell_size[ii + page];
					String d2 = String.format("%1$3d", s);
					if(s > 0)
					fontReader.drawString(d2,  i + 74 + (x * 36), j + 30 + (y * 18), 0xFFFFFF);
				}
				{
					int s = trader.sellm[ii + page];
					String d2 = String.format("%1$3d", s);
					if(s > 0)
					fontReader.drawString(d2,  i + 82 + (x * 36), j + 30 + (y * 18), 0X00FF00);
				}
				
			}
			/*for (int x = 0; x < 3; ++x) {
				for (int y = 0; y < 3; ++y) {
					ItemStack item = new ItemStack(trader.sell[x + y]);
					if (!item.isEmpty()) {
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
						renderitem.renderItemIntoGUI(item, i + 62 + (x * 36), j + 18 + (y * 18));
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					}
				}
			}
			for (int x = 0; x < 3; ++x) {
				for (int y = 0; y < 3; ++y) {
					int s = trader.sellm[x + y];
					String d2 = String.format("%1$3d", s);
					fontReader.drawString(d2,  i + 80 + (x * 36), j + 26 + (y * 18), 0xFFFFFF);
				}
			}*/
			
		}
		{
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			RenderItem renderitem = mc.getRenderItem();
			int x = (this.width - this.xSize) / 2;
			int y = (this.height - this.ySize) / 2;
			int page = 10 * trader.buy_now_id;
			for(int ite = 0; ite < 9; ++ite) {
				ItemStack items = new ItemStack(trader.buy[0 + ite + page]);
				if(!items.isEmpty()) {
					renderitem.renderItemIntoGUI(items, x + 180, y + 25 + (20 * ite));
					fontReader.drawString(String.format("%1$3d", trader.buy_size[0 + ite + page]), x + 195, y + 35  + (20 * ite), 0X00FF00);
					fontReader.drawString(String.format("%1$3d", trader.buym[0 + ite + page]), x + 187, y + 35  + (20 * ite), 0xFFFFFF);
				}
			}
			GL11.glPopMatrix();
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