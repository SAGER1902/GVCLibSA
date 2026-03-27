package gvclib.gui;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import gvclib.mod_GVCLib;
import gvclib.item.ItemGunBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
 
public class GVCGunReloadtestGuiInventory extends GuiContainer
{
    //private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");
    private static final ResourceLocation texture = new ResourceLocation("gvclib:textures/gui/AR.png");
    private static EntityPlayer player;
    /** The mouse x-position recorded during the last rendered frame. */
    private float mousePosx;
    /** The mouse y-position recorded during the last renderered frame. */
    private float mousePosY;
    
    private GuiButton set;
    private GuiButton next;
    private GuiButton back;
    
    private GuiButton next10;
    private GuiButton back10;
    
    /**matの種類*/
    private int mat = 0;
    /**part*/
    private int matid = 0;
    
    private GuiButton matselect_n;
    private GuiButton matselect_b;
    
    private GuiButton matkazu_n;
    private GuiButton matkazu_b;
    
    private GuiButton matset_n;
    private GuiButton matset_b;
    private GuiButton mattime_n;
    private GuiButton mattime_b;
    private GuiButton mattime10_n;
    private GuiButton mattime10_b;
    
    private GuiButton matxpoint_n;
    private GuiButton matxpoint_b;
    private GuiButton matypoint_n;
    private GuiButton matypoint_b;
    private GuiButton matzpoint_n;
    private GuiButton matzpoint_b;
    
    private GuiButton matxpoint10_n;
    private GuiButton matxpoint10_b;
    private GuiButton matypoint10_n;
    private GuiButton matypoint10_b;
    private GuiButton matzpoint10_n;
    private GuiButton matzpoint10_b;
    
    private GuiButton matxrote_n;
    private GuiButton matxrote_b;
    private GuiButton matyrote_n;
    private GuiButton matyrote_b;
    private GuiButton matzrote_n;
    private GuiButton matzrote_b;
    
    private GuiButton matxrote10_n;
    private GuiButton matxrote10_b;
    private GuiButton matyrote10_n;
    private GuiButton matyrote10_b;
    private GuiButton matzrote10_n;
    private GuiButton matzrote10_b;
    
    private GuiButton matxmove_n;
    private GuiButton matxmove_b;
    private GuiButton matymove_n;
    private GuiButton matymove_b;
    private GuiButton matzmove_n;
    private GuiButton matzmove_b;
    
    private GuiButton matxmove10_n;
    private GuiButton matxmove10_b;
    private GuiButton matymove10_n;
    private GuiButton matymove10_b;
    private GuiButton matzmove10_n;
    private GuiButton matzmove10_b;
    
    private GuiButton mat_copy_n;
    private GuiButton mat_copy_b;
    
    private GuiButton mat_copy_hand;
    
    private GuiButton fileoutput;
    
    private ItemGunBase gun;
 
    public GVCGunReloadtestGuiInventory(InventoryPlayer inventoryPlayer, ItemStack itemstack, ItemGunBase g)
    {
        super(new GVCGunReloadtestContainer());
        this.ySize = 222;
        player = inventoryPlayer.player;
        this.gun = g;
    }

    int x = 140;
	int xmin = 120;
	int y = 20;
    public void initGui()
    {
    	super.initGui();
    	/*this.set = this.addButton(new GuiButton(0, this.width / 2 + 100, this.height / 2 - 70, 20, 20, I18n.format("set")));
    	this.next = this.addButton(new GuiButton(1, this.width / 2 + 40, this.height / 2 - 70, 20, 20, I18n.format("next")));
    	this.back = this.addButton(new GuiButton(2, this.width / 2 + 20, this.height / 2 - 70, 20, 20, I18n.format("back")));
    	this.next10 = this.addButton(new GuiButton(42, this.width / 2 + 40, this.height / 2 - 50, 20, 20, I18n.format("next10")));
    	this.back10 = this.addButton(new GuiButton(43, this.width / 2 + 20, this.height / 2 - 50, 20, 20, I18n.format("back10")));
    	
    	this.fileoutput = this.addButton(new GuiButton(29, this.width / 2 + 30, this.height / 2 - 90, 60, 20, I18n.format("FileOutPut")));
    	*/
    	this.set = this.addButton(new GuiButton(0, 260, y + 0, 20, 20, I18n.format("set")));
    	this.next = this.addButton(new GuiButton(1, 220, y + 20, 20, 20, I18n.format("next")));
    	this.back = this.addButton(new GuiButton(2, 200, y + 20, 20, 20, I18n.format("back")));
    	this.next10 = this.addButton(new GuiButton(42, 220, y + 40, 20, 20, I18n.format("next10")));
    	this.back10 = this.addButton(new GuiButton(43, 200, y + 40, 20, 20, I18n.format("back10")));
    	
    	this.fileoutput = this.addButton(new GuiButton(29, 200, y + 0, 60, 20, I18n.format("FileOutPut")));
    	
    	
    	this.matselect_n = this.addButton(new GuiButton(3, x, y + 0, 20, 11, I18n.format("next")));
    	this.matselect_b = this.addButton(new GuiButton(4, xmin, y + 0, 20, 11, I18n.format("back")));
    	
    	this.matkazu_n = this.addButton(new GuiButton(5, x, y + 20, 20, 11, I18n.format("next")));
    	this.matkazu_b = this.addButton(new GuiButton(6, xmin, y + 20, 20, 11, I18n.format("back")));
    	
    	
    	this.mat_copy_n = this.addButton(new GuiButton(46, x + 60, y + 90, 40, 20, I18n.format("CopyNext")));
    	this.mat_copy_b = this.addButton(new GuiButton(47, x + 60, y + 110, 40, 20, I18n.format("CopyBack")));
    	
    	this.mat_copy_hand = this.addButton(new GuiButton(48, x + 60, y + 140, 40, 20, I18n.format("HandSET")));
    	
    	
    	this.matxpoint_n = this.addButton(new GuiButton(7, x, y + 80, 20, 11, I18n.format("+0.1")));
    	this.matxpoint_b = this.addButton(new GuiButton(8, xmin, y + 80, 20, 11, I18n.format("-0.1")));
    	this.matypoint_n = this.addButton(new GuiButton(9, x, y + 95, 20, 11, I18n.format("+0.1")));
    	this.matypoint_b = this.addButton(new GuiButton(10, xmin, y + 95, 20, 11, I18n.format("-0.1")));
    	this.matzpoint_n = this.addButton(new GuiButton(11, x, y + 110, 20, 11, I18n.format("+0.1")));
    	this.matzpoint_b = this.addButton(new GuiButton(12, xmin, y + 110, 20, 11, I18n.format("-0.1")));
    	
    	this.matxpoint10_n = this.addButton(new GuiButton(57, x + 20, y + 80, 20, 11, I18n.format("+1.0")));
    	this.matxpoint10_b = this.addButton(new GuiButton(58, xmin - 20, y + 80, 20, 11, I18n.format("-1.0")));
    	this.matypoint10_n = this.addButton(new GuiButton(59, x + 20, y + 95, 20, 11, I18n.format("+1.0")));
    	this.matypoint10_b = this.addButton(new GuiButton(60, xmin - 20, y + 95, 20, 11, I18n.format("-1.0")));
    	this.matzpoint10_n = this.addButton(new GuiButton(61, x + 20, y + 110, 20, 11, I18n.format("+1.0")));
    	this.matzpoint10_b = this.addButton(new GuiButton(62, xmin - 20, y + 110, 20, 11, I18n.format("-1.0")));
    	
    	this.matxrote_n = this.addButton(new GuiButton(13, x, y + 130, 20, 11, I18n.format("+1")));
    	this.matxrote_b = this.addButton(new GuiButton(14, xmin, y + 130, 20, 11, I18n.format("-1")));
    	this.matyrote_n = this.addButton(new GuiButton(15, x, y + 145, 20, 11, I18n.format("+1")));
    	this.matyrote_b = this.addButton(new GuiButton(16, xmin, y + 145, 20, 11, I18n.format("-1")));
    	this.matzrote_n = this.addButton(new GuiButton(17, x, y + 160, 20, 11, I18n.format("+1")));
    	this.matzrote_b = this.addButton(new GuiButton(18, xmin, y + 160, 20, 11, I18n.format("-1")));
    	
    	this.matxrote10_n = this.addButton(new GuiButton(30, x + 20, y + 130, 20, 11, I18n.format("+10")));
    	this.matxrote10_b = this.addButton(new GuiButton(31, xmin - 20, y + 130, 20, 11, I18n.format("-10")));
    	this.matyrote10_n = this.addButton(new GuiButton(32, x + 20, y + 145, 20, 11, I18n.format("+10")));
    	this.matyrote10_b = this.addButton(new GuiButton(33, xmin - 20, y + 145, 20, 11, I18n.format("-10")));
    	this.matzrote10_n = this.addButton(new GuiButton(34, x + 20, y + 160, 20, 11, I18n.format("+10")));
    	this.matzrote10_b = this.addButton(new GuiButton(35, xmin - 20, y + 160, 20, 11, I18n.format("-10")));
    	
    	this.matxmove_n = this.addButton(new GuiButton(19, x, y + 180, 20, 11, I18n.format("+0.1")));
    	this.matxmove_b = this.addButton(new GuiButton(20, xmin, y + 180, 20, 11, I18n.format("-0.1")));
    	this.matymove_n = this.addButton(new GuiButton(21, x, y + 195, 20, 11, I18n.format("+0.1")));
    	this.matymove_b = this.addButton(new GuiButton(22, xmin, y + 195, 20, 11, I18n.format("-0.1")));
    	this.matzmove_n = this.addButton(new GuiButton(23, x, y + 210, 20, 11, I18n.format("+0.1")));
    	this.matzmove_b = this.addButton(new GuiButton(24, xmin, y + 210, 20, 10, I18n.format("-0.1")));
    	
    	this.matxmove10_n = this.addButton(new GuiButton(36, x + 20, y + 180, 20, 11, I18n.format("+1")));
    	this.matxmove10_b = this.addButton(new GuiButton(37, xmin - 20, y + 180, 20, 11, I18n.format("-1")));
    	this.matymove10_n = this.addButton(new GuiButton(38, x + 20, y + 195, 20, 11, I18n.format("+1")));
    	this.matymove10_b = this.addButton(new GuiButton(39, xmin - 20, y + 195, 20, 11, I18n.format("-1")));
    	this.matzmove10_n = this.addButton(new GuiButton(40, x + 20, y + 210, 20, 11, I18n.format("+1")));
    	this.matzmove10_b = this.addButton(new GuiButton(41, xmin - 20, y + 210, 20, 10, I18n.format("-1")));
    	
    	this.matset_n = this.addButton(new GuiButton(25, x, y + 40, 20, 11, I18n.format("+1")));
    	this.matset_b = this.addButton(new GuiButton(26, xmin, y + 40, 20, 11, I18n.format("-1")));
    	
    	this.mattime_n = this.addButton(new GuiButton(27, x, y + 60, 20, 11, I18n.format("+1")));
    	this.mattime_b = this.addButton(new GuiButton(28, xmin, y + 60, 20, 11, I18n.format("-1")));
    	this.mattime10_n = this.addButton(new GuiButton(44, x + 20, y + 60, 20, 11, I18n.format("+10")));
    	this.mattime10_b = this.addButton(new GuiButton(45, xmin - 20, y + 60, 20, 11, I18n.format("-10")));
    }
    
    protected void actionPerformed(GuiButton button) throws IOException
    {
    	 if (button.id == 0)
         {
    		 if(!gun.reloadmotion_test) {
    			 gun.reloadmotion_test = true;
    		 }else {
    			 gun.reloadmotion_test = false;
    		 }
         }
    	 if (button.id == 1 && gun.retime < gun.reloadtime)
         {
    		 ++gun.retime;
         }
    	 if (button.id == 2 && gun.retime > 0)
         {
    		 --gun.retime;
         }
    	 if (button.id == 42 && gun.retime < gun.reloadtime-10)
         {
    		 gun.retime = gun.retime + 10;
         }
    	 if (button.id == 43 && gun.retime > 9)
         {
    		 gun.retime = gun.retime - 10;
         }
    	 
    	 if (button.id == 3 && mat < 10)
         {
    		 ++mat;
         }
    	 if (button.id == 4 && mat > 0)
         {
    		 --mat;
         }
    	 
    	 if (button.id == 25)
         {
    		 ++matid;
         }
    	 if (button.id == 26 && matid > 0)
         {
    		 --matid;
         }
    	 
    	 /*if (button.id == 5 && getmatid() < 199)
         {
    		 switch (mat) {
    	        case 2:
    	            ++gun.remat2;
    	            break;
    	        case 3:
    	        	++gun.remat3;
    	            break;
    	        case 4:
    	        	++gun.remat22;
    	            break;
    	        case 5:
    	        	++gun.remat24;
    	            break;
    	        case 6:
    	        	++gun.remat25;
    	            break;
    	        case 7:
    	        	++gun.remat311;
    	            break;
    	        case 8:
    	        	++gun.remat32;
    	            break;
    	        case 9:
    	        	++gun.rematlefthand;
    	            break;
    	        case 10:
    	        	++gun.rematrighthand;
    	            break;
    	        default:
    	        	++gun.remat0;
    	        }
         }
    	 if (button.id == 6)
         {
    		 switch (mat) {
    	        case 2:
    	            if(gun.remat2 > 0)--gun.remat2;
    	            break;
    	        case 3:
    	        	 if(gun.remat3 > 0)--gun.remat3;
    	            break;
    	        case 4:
    	        	 if(gun.remat22 > 0)--gun.remat22;
    	            break;
    	        case 5:
    	        	 if(gun.remat24 > 0)--gun.remat24;
    	            break;
    	        case 6:
    	        	 if(gun.remat25 > 0)--gun.remat25;
    	            break;
    	        case 7:
    	        	 if(gun.remat311 > 0)--gun.remat311;
    	            break;
    	        case 8:
    	        	 if(gun.remat32 > 0)--gun.remat32;
    	            break;
    	        case 9:
    	        	 if(gun.rematlefthand > 0)--gun.rematlefthand;
    	            break;
    	        case 10:
    	        	 if(gun.rematrighthand > 0)--gun.rematrighthand;
    	            break;
    	        default:
    	        	 if(gun.remat0 > 0)--gun.remat0;
    	        }
         }*/
    	 if (button.id == 27)
         {
    		 switch (mat) {
    	        case 1:
    	            ++gun.remat1_time[matid];
    	            break;
    	        case 2:
    	        	++gun.remat2_time[matid];
    	            break;
    	        case 3:
    	        	++gun.remat3_time[matid];
    	            break;
    	        case 4:
    	        	++gun.remat22_time[matid];
    	            break;
    	        case 5:
    	        	++gun.remat24_time[matid];
    	            break;
    	        case 6:
    	        	++gun.remat25_time[matid];
    	            break;
    	        case 7:
    	        	++gun.remat31_time[matid];
    	            break;
    	        case 8:
    	        	++gun.remat32_time[matid];
    	            break;
    	        case 9:
    	        	++gun.rematlefthand_time[matid];
    	            break;
    	        case 10:
    	        	++gun.rematrighthand_time[matid];
    	            break;
    	        default:
    	        	++gun.remat0_time[matid];
    	        }
         }
    	 if (button.id == 28)
         {
    		 switch (mat) {
    		 case 1:
 	            if(gun.remat1_time[matid] > 0)--gun.remat1_time[matid];
 	            break;  
    		 case 2:
    	            if(gun.remat2_time[matid] > 0)--gun.remat2_time[matid];
    	            break;
    	        case 3:
    	        	if(gun.remat3_time[matid] > 0)--gun.remat3_time[matid];
    	            break;
    	        case 4:
    	        	if(gun.remat22_time[matid] > 0)--gun.remat22_time[matid];
    	            break;
    	        case 5:
    	        	if(gun.remat24_time[matid] > 0)--gun.remat24_time[matid];
    	            break;
    	        case 6:
    	        	if(gun.remat25_time[matid] > 0)--gun.remat25_time[matid];
    	            break;
    	        case 7:
    	        	if(gun.remat31_time[matid] > 0)--gun.remat31_time[matid];
    	            break;
    	        case 8:
    	        	if(gun.remat32_time[matid] > 0)--gun.remat32_time[matid];
    	            break;
    	        case 9:
    	        	if(gun.rematlefthand_time[matid] > 0)--gun.rematlefthand_time[matid];
    	            break;
    	        case 10:
    	        	if(gun.rematrighthand_time[matid] > 0)--gun.rematrighthand_time[matid];
    	            break;
    	        default:
    	        	if(gun.remat0_time[matid] > 0)--gun.remat0_time[matid];
    	        }
         }
    	 
    	 if (button.id == 44)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_time[matid] = gun.remat1_time[matid] + 10;
 	            break;
    	        case 2:
    	            gun.remat2_time[matid] = gun.remat2_time[matid] + 10;
    	            break;
    	        case 3:
    	        	gun.remat3_time[matid] = gun.remat3_time[matid] + 10;
    	            break;
    	        case 4:
    	        	gun.remat22_time[matid] = gun.remat22_time[matid] + 10;
    	            break;
    	        case 5:
    	        	gun.remat24_time[matid] = gun.remat24_time[matid] + 10;
    	            break;
    	        case 6:
    	        	gun.remat25_time[matid] = gun.remat25_time[matid] + 10;
    	            break;
    	        case 7:
    	        	gun.remat31_time[matid] = gun.remat31_time[matid] + 10;
    	            break;
    	        case 8:
    	        	gun.remat32_time[matid] = gun.remat32_time[matid] + 10;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_time[matid] = gun.rematlefthand_time[matid] + 10;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_time[matid] = gun.rematrighthand_time[matid] + 10;
    	            break;
    	        default:
    	        	gun.remat0_time[matid] = gun.remat0_time[matid] + 10;
    	        }
         }
    	 if (button.id == 45)
         {
    		 switch (mat) {
    		 case 1:
 	            if(gun.remat1_time[matid] > 9) gun.remat1_time[matid] = gun.remat1_time[matid] - 10;
 	            break;  
    		 case 2:
    	            if(gun.remat2_time[matid] > 9) gun.remat2_time[matid] = gun.remat2_time[matid] - 10;
    	            break;
    	        case 3:
    	        	if(gun.remat3_time[matid] > 9)gun.remat3_time[matid] = gun.remat3_time[matid] - 10;
    	            break;
    	        case 4:
    	        	if(gun.remat22_time[matid] > 9)gun.remat22_time[matid] = gun.remat22_time[matid] - 10;
    	            break;
    	        case 5:
    	        	if(gun.remat24_time[matid] > 9)gun.remat24_time[matid] = gun.remat24_time[matid] - 10;
    	            break;
    	        case 6:
    	        	if(gun.remat25_time[matid] > 9)gun.remat25_time[matid] = gun.remat25_time[matid] - 10;
    	            break;
    	        case 7:
    	        	if(gun.remat31_time[matid] > 9)gun.remat31_time[matid] = gun.remat31_time[matid] - 10;
    	            break;
    	        case 8:
    	        	if(gun.remat32_time[matid] > 9)gun.remat32_time[matid] = gun.remat32_time[matid] - 10;
    	            break;
    	        case 9:
    	        	if(gun.rematlefthand_time[matid] > 9)gun.rematlefthand_time[matid] = gun.rematlefthand_time[matid] - 10;
    	            break;
    	        case 10:
    	        	if(gun.rematrighthand_time[matid] > 9)gun.rematrighthand_time[matid] = gun.rematrighthand_time[matid] - 10;
    	            break;
    	        default:
    	        	if(gun.remat0_time[matid] > 9)gun.remat0_time[matid] = gun.remat0_time[matid] - 10;
    	        }
         }
    	 
    	 
    	 if (button.id == 7)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_xpoint[matid] = gun.remat1_xpoint[matid] + 0.1F;
 	            break;
    	        case 2:
    	            gun.remat2_xpoint[matid] = gun.remat2_xpoint[matid] + 0.1F;
    	            break;
    	        case 3:
    	        	gun.remat3_xpoint[matid] = gun.remat3_xpoint[matid] + 0.1F;
    	            break;
    	        case 4:
    	        	gun.remat22_xpoint[matid] = gun.remat22_xpoint[matid] + 0.1F;
    	            break;
    	        case 5:
    	        	gun.remat24_xpoint[matid] = gun.remat24_xpoint[matid] + 0.1F;
    	            break;
    	        case 6:
    	        	gun.remat25_xpoint[matid] = gun.remat25_xpoint[matid] + 0.1F;
    	            break;
    	        case 7:
    	        	gun.remat31_xpoint[matid] = gun.remat31_xpoint[matid] + 0.1F;
    	            break;
    	        case 8:
    	        	gun.remat32_xpoint[matid] = gun.remat32_xpoint[matid] + 0.1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_xpoint[matid] = gun.rematlefthand_xpoint[matid] + 0.1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xpoint[matid] = gun.rematrighthand_xpoint[matid] + 0.1F;
    	            break;
    	        default:
    	        	gun.remat0_xpoint[matid] = gun.remat0_xpoint[matid] + 0.1F;
    	        }
         }
    	 if (button.id == 8)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_xpoint[matid] = gun.remat1_xpoint[matid] - 0.1F;
 	            break;
    	        case 2:
    	            gun.remat2_xpoint[matid] = gun.remat2_xpoint[matid] - 0.1F;
    	            break;
    	        case 3:
    	        	gun.remat3_xpoint[matid] = gun.remat3_xpoint[matid] - 0.1F;
    	            break;
    	        case 4:
    	        	gun.remat22_xpoint[matid] = gun.remat22_xpoint[matid] - 0.1F;
    	            break;
    	        case 5:
    	        	gun.remat24_xpoint[matid] = gun.remat24_xpoint[matid] - 0.1F;
    	            break;
    	        case 6:
    	        	gun.remat25_xpoint[matid] = gun.remat25_xpoint[matid] - 0.1F;
    	            break;
    	        case 7:
    	        	gun.remat31_xpoint[matid] = gun.remat31_xpoint[matid] - 0.1F;
    	            break;
    	        case 8:
    	        	gun.remat32_xpoint[matid] = gun.remat32_xpoint[matid] - 0.1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_xpoint[matid] = gun.rematlefthand_xpoint[matid] - 0.1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xpoint[matid] = gun.rematrighthand_xpoint[matid] - 0.1F;
    	            break;
    	        default:
    	        	gun.remat0_xpoint[matid] = gun.remat0_xpoint[matid] - 0.1F;
    	        }
         }
    	 
    	 if (button.id == 9)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_ypoint[matid] = gun.remat1_ypoint[matid] + 0.1F;
 	            break;
    	        case 2:
    	            gun.remat2_ypoint[matid] = gun.remat2_ypoint[matid] + 0.1F;
    	            break;
    	        case 3:
    	        	gun.remat3_ypoint[matid] = gun.remat3_ypoint[matid] + 0.1F;
    	            break;
    	        case 4:
    	        	gun.remat22_ypoint[matid] = gun.remat22_ypoint[matid] + 0.1F;
    	            break;
    	        case 5:
    	        	gun.remat24_ypoint[matid] = gun.remat24_ypoint[matid] + 0.1F;
    	            break;
    	        case 6:
    	        	gun.remat25_ypoint[matid] = gun.remat25_ypoint[matid] + 0.1F;
    	            break;
    	        case 7:
    	        	gun.remat31_ypoint[matid] = gun.remat31_ypoint[matid] + 0.1F;
    	            break;
    	        case 8:
    	        	gun.remat32_ypoint[matid] = gun.remat32_ypoint[matid] + 0.1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_ypoint[matid] = gun.rematlefthand_ypoint[matid] + 0.1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_ypoint[matid] = gun.rematrighthand_ypoint[matid] + 0.1F;
    	            break;
    	        default:
    	        	gun.remat0_ypoint[matid] = gun.remat0_ypoint[matid] + 0.1F;
    	        }
         }
    	 if (button.id == 10)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_ypoint[matid] = gun.remat1_ypoint[matid] - 0.1F;
 	            break;
    	        case 2:
    	            gun.remat2_ypoint[matid] = gun.remat2_ypoint[matid] - 0.1F;
    	            break;
    	        case 3:
    	        	gun.remat3_ypoint[matid] = gun.remat3_ypoint[matid] - 0.1F;
    	            break;
    	        case 4:
    	        	gun.remat22_ypoint[matid] = gun.remat22_ypoint[matid] - 0.1F;
    	            break;
    	        case 5:
    	        	gun.remat24_ypoint[matid] = gun.remat24_ypoint[matid] - 0.1F;
    	            break;
    	        case 6:
    	        	gun.remat25_ypoint[matid] = gun.remat25_ypoint[matid] - 0.1F;
    	            break;
    	        case 7:
    	        	gun.remat31_ypoint[matid] = gun.remat31_ypoint[matid] - 0.1F;
    	            break;
    	        case 8:
    	        	gun.remat32_ypoint[matid] = gun.remat32_ypoint[matid] - 0.1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_ypoint[matid] = gun.rematlefthand_ypoint[matid] - 0.1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_ypoint[matid] = gun.rematrighthand_ypoint[matid] - 0.1F;
    	            break;
    	        default:
    	        	gun.remat0_ypoint[matid] = gun.remat0_ypoint[matid] - 0.1F;
    	        }
         }
    	 
    	 if (button.id == 11)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_zpoint[matid] = gun.remat1_zpoint[matid] + 0.1F;
 	            break;
    	        case 2:
    	            gun.remat2_zpoint[matid] = gun.remat2_zpoint[matid] + 0.1F;
    	            break;
    	        case 3:
    	        	gun.remat3_zpoint[matid] = gun.remat3_zpoint[matid] + 0.1F;
    	            break;
    	        case 4:
    	        	gun.remat22_zpoint[matid] = gun.remat22_zpoint[matid] + 0.1F;
    	            break;
    	        case 5:
    	        	gun.remat24_zpoint[matid] = gun.remat24_zpoint[matid] + 0.1F;
    	            break;
    	        case 6:
    	        	gun.remat25_zpoint[matid] = gun.remat25_zpoint[matid] + 0.1F;
    	            break;
    	        case 7:
    	        	gun.remat31_zpoint[matid] = gun.remat31_zpoint[matid] + 0.1F;
    	            break;
    	        case 8:
    	        	gun.remat32_zpoint[matid] = gun.remat32_zpoint[matid] + 0.1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_zpoint[matid] = gun.rematlefthand_zpoint[matid] + 0.1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_zpoint[matid] = gun.rematrighthand_zpoint[matid] + 0.1F;
    	            break;
    	        default:
    	        	gun.remat0_zpoint[matid] = gun.remat0_zpoint[matid] + 0.1F;
    	        }
         }
    	 if (button.id == 12)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_zpoint[matid] = gun.remat1_zpoint[matid] - 0.1F;
 	            break;
    	        case 2:
    	            gun.remat2_zpoint[matid] = gun.remat2_zpoint[matid] - 0.1F;
    	            break;
    	        case 3:
    	        	gun.remat3_zpoint[matid] = gun.remat3_zpoint[matid] - 0.1F;
    	            break;
    	        case 4:
    	        	gun.remat22_zpoint[matid] = gun.remat22_zpoint[matid] - 0.1F;
    	            break;
    	        case 5:
    	        	gun.remat24_zpoint[matid] = gun.remat24_zpoint[matid] - 0.1F;
    	            break;
    	        case 6:
    	        	gun.remat25_zpoint[matid] = gun.remat25_zpoint[matid] - 0.1F;
    	            break;
    	        case 7:
    	        	gun.remat31_zpoint[matid] = gun.remat31_zpoint[matid] - 0.1F;
    	            break;
    	        case 8:
    	        	gun.remat32_zpoint[matid] = gun.remat32_zpoint[matid] - 0.1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_zpoint[matid] = gun.rematlefthand_zpoint[matid] - 0.1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_zpoint[matid] = gun.rematrighthand_zpoint[matid] - 0.1F;
    	            break;
    	        default:
    	        	gun.remat0_zpoint[matid] = gun.remat0_zpoint[matid] - 0.1F;
    	        }
         }
    	 
    	 
    	 if (button.id == 57)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_xpoint[matid] = gun.remat1_xpoint[matid] + 1.0F;
 	            break;
    	        case 2:
    	            gun.remat2_xpoint[matid] = gun.remat2_xpoint[matid] + 1.0F;
    	            break;
    	        case 3:
    	        	gun.remat3_xpoint[matid] = gun.remat3_xpoint[matid] + 1.0F;
    	            break;
    	        case 4:
    	        	gun.remat22_xpoint[matid] = gun.remat22_xpoint[matid] + 1.0F;
    	            break;
    	        case 5:
    	        	gun.remat24_xpoint[matid] = gun.remat24_xpoint[matid] + 1.0F;
    	            break;
    	        case 6:
    	        	gun.remat25_xpoint[matid] = gun.remat25_xpoint[matid] + 1.0F;
    	            break;
    	        case 7:
    	        	gun.remat31_xpoint[matid] = gun.remat31_xpoint[matid] + 1.0F;
    	            break;
    	        case 8:
    	        	gun.remat32_xpoint[matid] = gun.remat32_xpoint[matid] + 1.0F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_xpoint[matid] = gun.rematlefthand_xpoint[matid] + 1.0F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xpoint[matid] = gun.rematrighthand_xpoint[matid] + 1.0F;
    	            break;
    	        default:
    	        	gun.remat0_xpoint[matid] = gun.remat0_xpoint[matid] + 1.0F;
    	        }
         }
    	 if (button.id == 58)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_xpoint[matid] = gun.remat1_xpoint[matid] - 1.0F;
 	            break;
    	        case 2:
    	            gun.remat2_xpoint[matid] = gun.remat2_xpoint[matid] - 1.0F;
    	            break;
    	        case 3:
    	        	gun.remat3_xpoint[matid] = gun.remat3_xpoint[matid] - 1.0F;
    	            break;
    	        case 4:
    	        	gun.remat22_xpoint[matid] = gun.remat22_xpoint[matid] - 1.0F;
    	            break;
    	        case 5:
    	        	gun.remat24_xpoint[matid] = gun.remat24_xpoint[matid] - 1.0F;
    	            break;
    	        case 6:
    	        	gun.remat25_xpoint[matid] = gun.remat25_xpoint[matid] - 1.0F;
    	            break;
    	        case 7:
    	        	gun.remat31_xpoint[matid] = gun.remat31_xpoint[matid] - 1.0F;
    	            break;
    	        case 8:
    	        	gun.remat32_xpoint[matid] = gun.remat32_xpoint[matid] - 1.0F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_xpoint[matid] = gun.rematlefthand_xpoint[matid] - 1.0F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xpoint[matid] = gun.rematrighthand_xpoint[matid] - 1.0F;
    	            break;
    	        default:
    	        	gun.remat0_xpoint[matid] = gun.remat0_xpoint[matid] - 1.0F;
    	        }
         }
    	 
    	 if (button.id == 59)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_ypoint[matid] = gun.remat1_ypoint[matid] + 1.0F;
 	            break;
    	        case 2:
    	            gun.remat2_ypoint[matid] = gun.remat2_ypoint[matid] + 1.0F;
    	            break;
    	        case 3:
    	        	gun.remat3_ypoint[matid] = gun.remat3_ypoint[matid] + 1.0F;
    	            break;
    	        case 4:
    	        	gun.remat22_ypoint[matid] = gun.remat22_ypoint[matid] + 1.0F;
    	            break;
    	        case 5:
    	        	gun.remat24_ypoint[matid] = gun.remat24_ypoint[matid] + 1.0F;
    	            break;
    	        case 6:
    	        	gun.remat25_ypoint[matid] = gun.remat25_ypoint[matid] + 1.0F;
    	            break;
    	        case 7:
    	        	gun.remat31_ypoint[matid] = gun.remat31_ypoint[matid] + 1.0F;
    	            break;
    	        case 8:
    	        	gun.remat32_ypoint[matid] = gun.remat32_ypoint[matid] + 1.0F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_ypoint[matid] = gun.rematlefthand_ypoint[matid] + 1.0F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_ypoint[matid] = gun.rematrighthand_ypoint[matid] + 1.0F;
    	            break;
    	        default:
    	        	gun.remat0_ypoint[matid] = gun.remat0_ypoint[matid] + 1.0F;
    	        }
         }
    	 if (button.id == 60)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_ypoint[matid] = gun.remat1_ypoint[matid] - 1.0F;
 	            break;
    	        case 2:
    	            gun.remat2_ypoint[matid] = gun.remat2_ypoint[matid] - 1.0F;
    	            break;
    	        case 3:
    	        	gun.remat3_ypoint[matid] = gun.remat3_ypoint[matid] - 1.0F;
    	            break;
    	        case 4:
    	        	gun.remat22_ypoint[matid] = gun.remat22_ypoint[matid] - 1.0F;
    	            break;
    	        case 5:
    	        	gun.remat24_ypoint[matid] = gun.remat24_ypoint[matid] - 1.0F;
    	            break;
    	        case 6:
    	        	gun.remat25_ypoint[matid] = gun.remat25_ypoint[matid] - 1.0F;
    	            break;
    	        case 7:
    	        	gun.remat31_ypoint[matid] = gun.remat31_ypoint[matid] - 1.0F;
    	            break;
    	        case 8:
    	        	gun.remat32_ypoint[matid] = gun.remat32_ypoint[matid] - 1.0F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_ypoint[matid] = gun.rematlefthand_ypoint[matid] - 1.0F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_ypoint[matid] = gun.rematrighthand_ypoint[matid] - 1.0F;
    	            break;
    	        default:
    	        	gun.remat0_ypoint[matid] = gun.remat0_ypoint[matid] - 1.0F;
    	        }
         }
    	 
    	 if (button.id == 61)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_zpoint[matid] = gun.remat1_zpoint[matid] + 1.0F;
 	            break;
    	        case 2:
    	            gun.remat2_zpoint[matid] = gun.remat2_zpoint[matid] + 1.0F;
    	            break;
    	        case 3:
    	        	gun.remat3_zpoint[matid] = gun.remat3_zpoint[matid] + 1.0F;
    	            break;
    	        case 4:
    	        	gun.remat22_zpoint[matid] = gun.remat22_zpoint[matid] + 1.0F;
    	            break;
    	        case 5:
    	        	gun.remat24_zpoint[matid] = gun.remat24_zpoint[matid] + 1.0F;
    	            break;
    	        case 6:
    	        	gun.remat25_zpoint[matid] = gun.remat25_zpoint[matid] + 1.0F;
    	            break;
    	        case 7:
    	        	gun.remat31_zpoint[matid] = gun.remat31_zpoint[matid] + 1.0F;
    	            break;
    	        case 8:
    	        	gun.remat32_zpoint[matid] = gun.remat32_zpoint[matid] + 1.0F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_zpoint[matid] = gun.rematlefthand_zpoint[matid] + 1.0F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_zpoint[matid] = gun.rematrighthand_zpoint[matid] + 1.0F;
    	            break;
    	        default:
    	        	gun.remat0_zpoint[matid] = gun.remat0_zpoint[matid] + 1.0F;
    	        }
         }
    	 if (button.id == 62)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_zpoint[matid] = gun.remat1_zpoint[matid] - 1.0F;
 	            break;
    	        case 2:
    	            gun.remat2_zpoint[matid] = gun.remat2_zpoint[matid] - 1.0F;
    	            break;
    	        case 3:
    	        	gun.remat3_zpoint[matid] = gun.remat3_zpoint[matid] - 1.0F;
    	            break;
    	        case 4:
    	        	gun.remat22_zpoint[matid] = gun.remat22_zpoint[matid] - 1.0F;
    	            break;
    	        case 5:
    	        	gun.remat24_zpoint[matid] = gun.remat24_zpoint[matid] - 1.0F;
    	            break;
    	        case 6:
    	        	gun.remat25_zpoint[matid] = gun.remat25_zpoint[matid] - 1.0F;
    	            break;
    	        case 7:
    	        	gun.remat31_zpoint[matid] = gun.remat31_zpoint[matid] - 1.0F;
    	            break;
    	        case 8:
    	        	gun.remat32_zpoint[matid] = gun.remat32_zpoint[matid] - 1.0F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_zpoint[matid] = gun.rematlefthand_zpoint[matid] - 1.0F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_zpoint[matid] = gun.rematrighthand_zpoint[matid] - 1.0F;
    	            break;
    	        default:
    	        	gun.remat0_zpoint[matid] = gun.remat0_zpoint[matid] - 1.0F;
    	        }
         }
    	 
    	 if (button.id == 13)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_xrote[matid] = gun.remat1_xrote[matid] + 1F;
 	            break;
    	        case 2:
    	            gun.remat2_xrote[matid] = gun.remat2_xrote[matid] + 1F;
    	            break;
    	        case 3:
    	        	gun.remat3_xrote[matid] = gun.remat3_xrote[matid] + 1F;
    	            break;
    	        case 4:
    	        	gun.remat22_xrote[matid] = gun.remat22_xrote[matid] + 1F;
    	            break;
    	        case 5:
    	        	gun.remat24_xrote[matid] = gun.remat24_xrote[matid] + 1F;
    	            break;
    	        case 6:
    	        	gun.remat25_xrote[matid] = gun.remat25_xrote[matid] + 1F;
    	            break;
    	        case 7:
    	        	gun.remat31_xrote[matid] = gun.remat31_xrote[matid] + 1F;
    	            break;
    	        case 8:
    	        	gun.remat32_xrote[matid] = gun.remat32_xrote[matid] + 1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_xrote[matid] = gun.rematlefthand_xrote[matid] + 1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xrote[matid] = gun.rematrighthand_xrote[matid] + 1F;
    	            break;
    	        default:
    	        	gun.remat0_xrote[matid] = gun.remat0_xrote[matid] + 1F;
    	        }
         }
    	 if (button.id == 14)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_xrote[matid] = gun.remat1_xrote[matid] - 1F;
 	            break;
    	        case 2:
    	            gun.remat2_xrote[matid] = gun.remat2_xrote[matid] - 1F;
    	            break;
    	        case 3:
    	        	gun.remat3_xrote[matid] = gun.remat3_xrote[matid] - 1F;
    	            break;
    	        case 4:
    	        	gun.remat22_xrote[matid] = gun.remat22_xrote[matid] - 1F;
    	            break;
    	        case 5:
    	        	gun.remat24_xrote[matid] = gun.remat24_xrote[matid] - 1F;
    	            break;
    	        case 6:
    	        	gun.remat25_xrote[matid] = gun.remat25_xrote[matid] - 1F;
    	            break;
    	        case 7:
    	        	gun.remat31_xrote[matid] = gun.remat31_xrote[matid] - 1F;
    	            break;
    	        case 8:
    	        	gun.remat32_xrote[matid] = gun.remat32_xrote[matid] - 1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_xrote[matid] = gun.rematlefthand_xrote[matid] - 1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xrote[matid] = gun.rematrighthand_xrote[matid] - 1F;
    	            break;
    	        default:
    	        	gun.remat0_xrote[matid] = gun.remat0_xrote[matid] - 1F;
    	        }
         }
    	 
    	 if (button.id == 15)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_yrote[matid] = gun.remat1_yrote[matid] + 1F;
 	            break;
    	        case 2:
    	            gun.remat2_yrote[matid] = gun.remat2_yrote[matid] + 1F;
    	            break;
    	        case 3:
    	        	gun.remat3_yrote[matid] = gun.remat3_yrote[matid] + 1F;
    	            break;
    	        case 4:
    	        	gun.remat22_yrote[matid] = gun.remat22_yrote[matid] + 1F;
    	            break;
    	        case 5:
    	        	gun.remat24_yrote[matid] = gun.remat24_yrote[matid] + 1F;
    	            break;
    	        case 6:
    	        	gun.remat25_yrote[matid] = gun.remat25_yrote[matid] + 1F;
    	            break;
    	        case 7:
    	        	gun.remat31_yrote[matid] = gun.remat31_yrote[matid] + 1F;
    	            break;
    	        case 8:
    	        	gun.remat32_yrote[matid] = gun.remat32_yrote[matid] + 1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_yrote[matid] = gun.rematlefthand_yrote[matid] + 1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_yrote[matid] = gun.rematrighthand_yrote[matid] + 1F;
    	            break;
    	        default:
    	        	gun.remat0_yrote[matid] = gun.remat0_yrote[matid] + 1F;
    	        }
         }
    	 if (button.id == 16)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_yrote[matid] = gun.remat1_yrote[matid] - 1F;
 	            break;
    	        case 2:
    	            gun.remat2_yrote[matid] = gun.remat2_yrote[matid] - 1F;
    	            break;
    	        case 3:
    	        	gun.remat3_yrote[matid] = gun.remat3_yrote[matid] - 1F;
    	            break;
    	        case 4:
    	        	gun.remat22_yrote[matid] = gun.remat22_yrote[matid] - 1F;
    	            break;
    	        case 5:
    	        	gun.remat24_yrote[matid] = gun.remat24_yrote[matid] - 1F;
    	            break;
    	        case 6:
    	        	gun.remat25_yrote[matid] = gun.remat25_yrote[matid] - 1F;
    	            break;
    	        case 7:
    	        	gun.remat31_yrote[matid] = gun.remat31_yrote[matid] - 1F;
    	            break;
    	        case 8:
    	        	gun.remat32_yrote[matid] = gun.remat32_yrote[matid] - 1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_yrote[matid] = gun.rematlefthand_yrote[matid] - 1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_yrote[matid] = gun.rematrighthand_yrote[matid] - 1F;
    	            break;
    	        default:
    	        	gun.remat0_yrote[matid] = gun.remat0_yrote[matid] - 1F;
    	        }
         }
    	 
    	 if (button.id == 17)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_zrote[matid] = gun.remat1_zrote[matid] + 1F;
 	            break;
    	        case 2:
    	            gun.remat2_zrote[matid] = gun.remat2_zrote[matid] + 1F;
    	            break;
    	        case 3:
    	        	gun.remat3_zrote[matid] = gun.remat3_zrote[matid] + 1F;
    	            break;
    	        case 4:
    	        	gun.remat22_zrote[matid] = gun.remat22_zrote[matid] + 1F;
    	            break;
    	        case 5:
    	        	gun.remat24_zrote[matid] = gun.remat24_zrote[matid] + 1F;
    	            break;
    	        case 6:
    	        	gun.remat25_zrote[matid] = gun.remat25_zrote[matid] + 1F;
    	            break;
    	        case 7:
    	        	gun.remat31_zrote[matid] = gun.remat31_zrote[matid] + 1F;
    	            break;
    	        case 8:
    	        	gun.remat32_zrote[matid] = gun.remat32_zrote[matid] + 1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_zrote[matid] = gun.rematlefthand_zrote[matid] + 1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_zrote[matid] = gun.rematrighthand_zrote[matid] + 1F;
    	            break;
    	        default:
    	        	gun.remat0_zrote[matid] = gun.remat0_zrote[matid] + 1F;
    	        }
         }
    	 if (button.id == 18)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_zrote[matid] = gun.remat1_zrote[matid] - 1F;
 	            break;
    	        case 2:
    	            gun.remat2_zrote[matid] = gun.remat2_zrote[matid] - 1F;
    	            break;
    	        case 3:
    	        	gun.remat3_zrote[matid] = gun.remat3_zrote[matid] - 1F;
    	            break;
    	        case 4:
    	        	gun.remat22_zrote[matid] = gun.remat22_zrote[matid] - 1F;
    	            break;
    	        case 5:
    	        	gun.remat24_zrote[matid] = gun.remat24_zrote[matid] - 1F;
    	            break;
    	        case 6:
    	        	gun.remat25_zrote[matid] = gun.remat25_zrote[matid] - 1F;
    	            break;
    	        case 7:
    	        	gun.remat31_zrote[matid] = gun.remat31_zrote[matid] - 1F;
    	            break;
    	        case 8:
    	        	gun.remat32_zrote[matid] = gun.remat32_zrote[matid] - 1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_zrote[matid] = gun.rematlefthand_zrote[matid] - 1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_zrote[matid] = gun.rematrighthand_zrote[matid] - 1F;
    	            break;
    	        default:
    	        	gun.remat0_zrote[matid] = gun.remat0_zrote[matid] - 1F;
    	        }
         }
    	 
    	 
    	 
    	 if (button.id == 30)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_xrote[matid] = gun.remat1_xrote[matid] + 10F;
 	            break;
    	        case 2:
    	            gun.remat2_xrote[matid] = gun.remat2_xrote[matid] + 10F;
    	            break;
    	        case 3:
    	        	gun.remat3_xrote[matid] = gun.remat3_xrote[matid] + 10F;
    	            break;
    	        case 4:
    	        	gun.remat22_xrote[matid] = gun.remat22_xrote[matid] + 10F;
    	            break;
    	        case 5:
    	        	gun.remat24_xrote[matid] = gun.remat24_xrote[matid] + 10F;
    	            break;
    	        case 6:
    	        	gun.remat25_xrote[matid] = gun.remat25_xrote[matid] + 10F;
    	            break;
    	        case 7:
    	        	gun.remat31_xrote[matid] = gun.remat31_xrote[matid] + 10F;
    	            break;
    	        case 8:
    	        	gun.remat32_xrote[matid] = gun.remat32_xrote[matid] + 10F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_xrote[matid] = gun.rematlefthand_xrote[matid] + 10F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xrote[matid] = gun.rematrighthand_xrote[matid] + 10F;
    	            break;
    	        default:
    	        	gun.remat0_xrote[matid] = gun.remat0_xrote[matid] + 10F;
    	        }
         }
    	 if (button.id == 31)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_xrote[matid] = gun.remat1_xrote[matid] - 10F;
 	            break;
    	        case 2:
    	            gun.remat2_xrote[matid] = gun.remat2_xrote[matid] - 10F;
    	            break;
    	        case 3:
    	        	gun.remat3_xrote[matid] = gun.remat3_xrote[matid] - 10F;
    	            break;
    	        case 4:
    	        	gun.remat22_xrote[matid] = gun.remat22_xrote[matid] - 10F;
    	            break;
    	        case 5:
    	        	gun.remat24_xrote[matid] = gun.remat24_xrote[matid] - 10F;
    	            break;
    	        case 6:
    	        	gun.remat25_xrote[matid] = gun.remat25_xrote[matid] - 10F;
    	            break;
    	        case 7:
    	        	gun.remat31_xrote[matid] = gun.remat31_xrote[matid] - 10F;
    	            break;
    	        case 8:
    	        	gun.remat32_xrote[matid] = gun.remat32_xrote[matid] - 10F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_xrote[matid] = gun.rematlefthand_xrote[matid] - 10F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xrote[matid] = gun.rematrighthand_xrote[matid] - 10F;
    	            break;
    	        default:
    	        	gun.remat0_xrote[matid] = gun.remat0_xrote[matid] - 10F;
    	        }
         }
    	 
    	 if (button.id == 32)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_yrote[matid] = gun.remat1_yrote[matid] + 10F;
 	            break;
    	        case 2:
    	            gun.remat2_yrote[matid] = gun.remat2_yrote[matid] + 10F;
    	            break;
    	        case 3:
    	        	gun.remat3_yrote[matid] = gun.remat3_yrote[matid] + 10F;
    	            break;
    	        case 4:
    	        	gun.remat22_yrote[matid] = gun.remat22_yrote[matid] + 10F;
    	            break;
    	        case 5:
    	        	gun.remat24_yrote[matid] = gun.remat24_yrote[matid] + 10F;
    	            break;
    	        case 6:
    	        	gun.remat25_yrote[matid] = gun.remat25_yrote[matid] + 10F;
    	            break;
    	        case 7:
    	        	gun.remat31_yrote[matid] = gun.remat31_yrote[matid] + 10F;
    	            break;
    	        case 8:
    	        	gun.remat32_yrote[matid] = gun.remat32_yrote[matid] + 10F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_yrote[matid] = gun.rematlefthand_yrote[matid] + 10F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_yrote[matid] = gun.rematrighthand_yrote[matid] + 10F;
    	            break;
    	        default:
    	        	gun.remat0_yrote[matid] = gun.remat0_yrote[matid] + 10F;
    	        }
         }
    	 if (button.id == 33)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_yrote[matid] = gun.remat1_yrote[matid] - 10F;
 	            break;
    	        case 2:
    	            gun.remat2_yrote[matid] = gun.remat2_yrote[matid] - 10F;
    	            break;
    	        case 3:
    	        	gun.remat3_yrote[matid] = gun.remat3_yrote[matid] - 10F;
    	            break;
    	        case 4:
    	        	gun.remat22_yrote[matid] = gun.remat22_yrote[matid] - 10F;
    	            break;
    	        case 5:
    	        	gun.remat24_yrote[matid] = gun.remat24_yrote[matid] - 10F;
    	            break;
    	        case 6:
    	        	gun.remat25_yrote[matid] = gun.remat25_yrote[matid] - 10F;
    	            break;
    	        case 7:
    	        	gun.remat31_yrote[matid] = gun.remat31_yrote[matid] - 10F;
    	            break;
    	        case 8:
    	        	gun.remat32_yrote[matid] = gun.remat32_yrote[matid] - 10F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_yrote[matid] = gun.rematlefthand_yrote[matid] - 10F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_yrote[matid] = gun.rematrighthand_yrote[matid] - 10F;
    	            break;
    	        default:
    	        	gun.remat0_yrote[matid] = gun.remat0_yrote[matid] - 10F;
    	        }
         }
    	 
    	 if (button.id == 34)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_zrote[matid] = gun.remat1_zrote[matid] + 10F;
 	            break;
    	        case 2:
    	            gun.remat2_zrote[matid] = gun.remat2_zrote[matid] + 10F;
    	            break;
    	        case 3:
    	        	gun.remat3_zrote[matid] = gun.remat3_zrote[matid] + 10F;
    	            break;
    	        case 4:
    	        	gun.remat22_zrote[matid] = gun.remat22_zrote[matid] + 10F;
    	            break;
    	        case 5:
    	        	gun.remat24_zrote[matid] = gun.remat24_zrote[matid] + 10F;
    	            break;
    	        case 6:
    	        	gun.remat25_zrote[matid] = gun.remat25_zrote[matid] + 10F;
    	            break;
    	        case 7:
    	        	gun.remat31_zrote[matid] = gun.remat31_zrote[matid] + 10F;
    	            break;
    	        case 8:
    	        	gun.remat32_zrote[matid] = gun.remat32_zrote[matid] + 10F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_zrote[matid] = gun.rematlefthand_zrote[matid] + 10F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_zrote[matid] = gun.rematrighthand_zrote[matid] + 10F;
    	            break;
    	        default:
    	        	gun.remat0_zrote[matid] = gun.remat0_zrote[matid] + 10F;
    	        }
         }
    	 if (button.id == 35)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_zrote[matid] = gun.remat1_zrote[matid] - 10F;
 	            break;
    	        case 2:
    	            gun.remat2_zrote[matid] = gun.remat2_zrote[matid] - 10F;
    	            break;
    	        case 3:
    	        	gun.remat3_zrote[matid] = gun.remat3_zrote[matid] - 10F;
    	            break;
    	        case 4:
    	        	gun.remat22_zrote[matid] = gun.remat22_zrote[matid] - 10F;
    	            break;
    	        case 5:
    	        	gun.remat24_zrote[matid] = gun.remat24_zrote[matid] - 10F;
    	            break;
    	        case 6:
    	        	gun.remat25_zrote[matid] = gun.remat25_zrote[matid] - 10F;
    	            break;
    	        case 7:
    	        	gun.remat31_zrote[matid] = gun.remat31_zrote[matid] - 10F;
    	            break;
    	        case 8:
    	        	gun.remat32_zrote[matid] = gun.remat32_zrote[matid] - 10F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_zrote[matid] = gun.rematlefthand_zrote[matid] - 10F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_zrote[matid] = gun.rematrighthand_zrote[matid] - 10F;
    	            break;
    	        default:
    	        	gun.remat0_zrote[matid] = gun.remat0_zrote[matid] - 10F;
    	        }
         }
    	 
    	 
    	 
    	 
    	 if (button.id == 19)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_xmove[matid] = gun.remat1_xmove[matid] + 0.1F;
 	            break;
    	        case 2:
    	            gun.remat2_xmove[matid] = gun.remat2_xmove[matid] + 0.1F;
    	            break;
    	        case 3:
    	        	gun.remat3_xmove[matid] = gun.remat3_xmove[matid] + 0.1F;
    	            break;
    	        case 4:
    	        	gun.remat22_xmove[matid] = gun.remat22_xmove[matid] + 0.1F;
    	            break;
    	        case 5:
    	        	gun.remat24_xmove[matid] = gun.remat24_xmove[matid] + 0.1F;
    	            break;
    	        case 6:
    	        	gun.remat25_xmove[matid] = gun.remat25_xmove[matid] + 0.1F;
    	            break;
    	        case 7:
    	        	gun.remat31_xmove[matid] = gun.remat31_xmove[matid] + 0.1F;
    	            break;
    	        case 8:
    	        	gun.remat32_xmove[matid] = gun.remat32_xmove[matid] + 0.1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_xmove[matid] = gun.rematlefthand_xmove[matid] + 0.1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xmove[matid] = gun.rematrighthand_xmove[matid] + 0.1F;
    	            break;
    	        default:
    	        	gun.remat0_xmove[matid] = gun.remat0_xmove[matid] + 0.1F;
    	        }
         }
    	 if (button.id == 20)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_xmove[matid] = gun.remat1_xmove[matid] - 0.1F;
 	            break;
    	        case 2:
    	            gun.remat2_xmove[matid] = gun.remat2_xmove[matid] - 0.1F;
    	            break;
    	        case 3:
    	        	gun.remat3_xmove[matid] = gun.remat3_xmove[matid] - 0.1F;
    	            break;
    	        case 4:
    	        	gun.remat22_xmove[matid] = gun.remat22_xmove[matid] - 0.1F;
    	            break;
    	        case 5:
    	        	gun.remat24_xmove[matid] = gun.remat24_xmove[matid] - 0.1F;
    	            break;
    	        case 6:
    	        	gun.remat25_xmove[matid] = gun.remat25_xmove[matid] - 0.1F;
    	            break;
    	        case 7:
    	        	gun.remat31_xmove[matid] = gun.remat31_xmove[matid] - 0.1F;
    	            break;
    	        case 8:
    	        	gun.remat32_xmove[matid] = gun.remat32_xmove[matid] - 0.1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_xmove[matid] = gun.rematlefthand_xmove[matid] - 0.1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xmove[matid] = gun.rematrighthand_xmove[matid] - 0.1F;
    	            break;
    	        default:
    	        	gun.remat0_xmove[matid] = gun.remat0_xmove[matid] - 0.1F;
    	        }
         }
    	 
    	 if (button.id == 21)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_ymove[matid] = gun.remat1_ymove[matid] + 0.1F;
 	            break;
    	        case 2:
    	            gun.remat2_ymove[matid] = gun.remat2_ymove[matid] + 0.1F;
    	            break;
    	        case 3:
    	        	gun.remat3_ymove[matid] = gun.remat3_ymove[matid] + 0.1F;
    	            break;
    	        case 4:
    	        	gun.remat22_ymove[matid] = gun.remat22_ymove[matid] + 0.1F;
    	            break;
    	        case 5:
    	        	gun.remat24_ymove[matid] = gun.remat24_ymove[matid] + 0.1F;
    	            break;
    	        case 6:
    	        	gun.remat25_ymove[matid] = gun.remat25_ymove[matid] + 0.1F;
    	            break;
    	        case 7:
    	        	gun.remat31_ymove[matid] = gun.remat31_ymove[matid] + 0.1F;
    	            break;
    	        case 8:
    	        	gun.remat32_ymove[matid] = gun.remat32_ymove[matid] + 0.1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_ymove[matid] = gun.rematlefthand_ymove[matid] + 0.1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_ymove[matid] = gun.rematrighthand_ymove[matid] + 0.1F;
    	            break;
    	        default:
    	        	gun.remat0_ymove[matid] = gun.remat0_ymove[matid] + 0.1F;
    	        }
         }
    	 if (button.id == 22)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_ymove[matid] = gun.remat1_ymove[matid] - 0.1F;
 	            break;
    	        case 2:
    	            gun.remat2_ymove[matid] = gun.remat2_ymove[matid] - 0.1F;
    	            break;
    	        case 3:
    	        	gun.remat3_ymove[matid] = gun.remat3_ymove[matid] - 0.1F;
    	            break;
    	        case 4:
    	        	gun.remat22_ymove[matid] = gun.remat22_ymove[matid] - 0.1F;
    	            break;
    	        case 5:
    	        	gun.remat24_ymove[matid] = gun.remat24_ymove[matid] - 0.1F;
    	            break;
    	        case 6:
    	        	gun.remat25_ymove[matid] = gun.remat25_ymove[matid] - 0.1F;
    	            break;
    	        case 7:
    	        	gun.remat31_ymove[matid] = gun.remat31_ymove[matid] - 0.1F;
    	            break;
    	        case 8:
    	        	gun.remat32_ymove[matid] = gun.remat32_ymove[matid] - 0.1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_ymove[matid] = gun.rematlefthand_ymove[matid] - 0.1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_ymove[matid] = gun.rematrighthand_ymove[matid] - 0.1F;
    	            break;
    	        default:
    	        	gun.remat0_ymove[matid] = gun.remat0_ymove[matid] - 0.1F;
    	        }
         }
    	 
    	 if (button.id == 23)
         {
    		 switch (mat) {
    		  case 1:
  	            gun.remat1_zmove[matid] = gun.remat1_zmove[matid] + 0.1F;
  	            break;
    	        case 2:
    	            gun.remat2_zmove[matid] = gun.remat2_zmove[matid] + 0.1F;
    	            break;
    	        case 3:
    	        	gun.remat3_zmove[matid] = gun.remat3_zmove[matid] + 0.1F;
    	            break;
    	        case 4:
    	        	gun.remat22_zmove[matid] = gun.remat22_zmove[matid] + 0.1F;
    	            break;
    	        case 5:
    	        	gun.remat24_zmove[matid] = gun.remat24_zmove[matid] + 0.1F;
    	            break;
    	        case 6:
    	        	gun.remat25_zmove[matid] = gun.remat25_zmove[matid] + 0.1F;
    	            break;
    	        case 7:
    	        	gun.remat31_zmove[matid] = gun.remat31_zmove[matid] + 0.1F;
    	            break;
    	        case 8:
    	        	gun.remat32_zmove[matid] = gun.remat32_zmove[matid] + 0.1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_zmove[matid] = gun.rematlefthand_zmove[matid] + 0.1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_zmove[matid] = gun.rematrighthand_zmove[matid] + 0.1F;
    	            break;
    	        default:
    	        	gun.remat0_zmove[matid] = gun.remat0_zmove[matid] + 0.1F;
    	        }
         }
    	 if (button.id == 24)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_zmove[matid] = gun.remat1_zmove[matid] - 0.1F;
 	            break;
    	        case 2:
    	            gun.remat2_zmove[matid] = gun.remat2_zmove[matid] - 0.1F;
    	            break;
    	        case 3:
    	        	gun.remat3_zmove[matid] = gun.remat3_zmove[matid] - 0.1F;
    	            break;
    	        case 4:
    	        	gun.remat22_zmove[matid] = gun.remat22_zmove[matid] - 0.1F;
    	            break;
    	        case 5:
    	        	gun.remat24_zmove[matid] = gun.remat24_zmove[matid] - 0.1F;
    	            break;
    	        case 6:
    	        	gun.remat25_zmove[matid] = gun.remat25_zmove[matid] - 0.1F;
    	            break;
    	        case 7:
    	        	gun.remat31_zmove[matid] = gun.remat31_zmove[matid] - 0.1F;
    	            break;
    	        case 8:
    	        	gun.remat32_zmove[matid] = gun.remat32_zmove[matid] - 0.1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_zmove[matid] = gun.rematlefthand_zmove[matid] - 0.1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_zmove[matid] = gun.rematrighthand_zmove[matid] - 0.1F;
    	            break;
    	        default:
    	        	gun.remat0_zmove[matid] = gun.remat0_zmove[matid] - 0.1F;
    	        }
         }
    	 
    	 
    	 if (button.id == 36)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_xmove[matid] = gun.remat1_xmove[matid] + 1F;
 	            break;
    	        case 2:
    	            gun.remat2_xmove[matid] = gun.remat2_xmove[matid] + 1F;
    	            break;
    	        case 3:
    	        	gun.remat3_xmove[matid] = gun.remat3_xmove[matid] + 1F;
    	            break;
    	        case 4:
    	        	gun.remat22_xmove[matid] = gun.remat22_xmove[matid] + 1F;
    	            break;
    	        case 5:
    	        	gun.remat24_xmove[matid] = gun.remat24_xmove[matid] + 1F;
    	            break;
    	        case 6:
    	        	gun.remat25_xmove[matid] = gun.remat25_xmove[matid] + 1F;
    	            break;
    	        case 7:
    	        	gun.remat31_xmove[matid] = gun.remat31_xmove[matid] + 1F;
    	            break;
    	        case 8:
    	        	gun.remat32_xmove[matid] = gun.remat32_xmove[matid] + 1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_xmove[matid] = gun.rematlefthand_xmove[matid] + 1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xmove[matid] = gun.rematrighthand_xmove[matid] + 1F;
    	            break;
    	        default:
    	        	gun.remat0_xmove[matid] = gun.remat0_xmove[matid] + 1F;
    	        }
         }
    	 if (button.id == 37)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_xmove[matid] = gun.remat1_xmove[matid] - 1F;
 	            break;
    	        case 2:
    	            gun.remat2_xmove[matid] = gun.remat2_xmove[matid] - 1F;
    	            break;
    	        case 3:
    	        	gun.remat3_xmove[matid] = gun.remat3_xmove[matid] - 1F;
    	            break;
    	        case 4:
    	        	gun.remat22_xmove[matid] = gun.remat22_xmove[matid] - 1F;
    	            break;
    	        case 5:
    	        	gun.remat24_xmove[matid] = gun.remat24_xmove[matid] - 1F;
    	            break;
    	        case 6:
    	        	gun.remat25_xmove[matid] = gun.remat25_xmove[matid] - 1F;
    	            break;
    	        case 7:
    	        	gun.remat31_xmove[matid] = gun.remat31_xmove[matid] - 1F;
    	            break;
    	        case 8:
    	        	gun.remat32_xmove[matid] = gun.remat32_xmove[matid] - 1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_xmove[matid] = gun.rematlefthand_xmove[matid] - 1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xmove[matid] = gun.rematrighthand_xmove[matid] - 1F;
    	            break;
    	        default:
    	        	gun.remat0_xmove[matid] = gun.remat0_xmove[matid] - 1F;
    	        }
         }
    	 
    	 if (button.id ==38)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_ymove[matid] = gun.remat1_ymove[matid] + 1F;
 	            break;
    	        case 2:
    	            gun.remat2_ymove[matid] = gun.remat2_ymove[matid] + 1F;
    	            break;
    	        case 3:
    	        	gun.remat3_ymove[matid] = gun.remat3_ymove[matid] + 1F;
    	            break;
    	        case 4:
    	        	gun.remat22_ymove[matid] = gun.remat22_ymove[matid] + 1F;
    	            break;
    	        case 5:
    	        	gun.remat24_ymove[matid] = gun.remat24_ymove[matid] + 1F;
    	            break;
    	        case 6:
    	        	gun.remat25_ymove[matid] = gun.remat25_ymove[matid] + 1F;
    	            break;
    	        case 7:
    	        	gun.remat31_ymove[matid] = gun.remat31_ymove[matid] + 1F;
    	            break;
    	        case 8:
    	        	gun.remat32_ymove[matid] = gun.remat32_ymove[matid] + 1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_ymove[matid] = gun.rematlefthand_ymove[matid] + 1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_ymove[matid] = gun.rematrighthand_ymove[matid] + 1F;
    	            break;
    	        default:
    	        	gun.remat0_ymove[matid] = gun.remat0_ymove[matid] + 1F;
    	        }
         }
    	 if (button.id == 39)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_ymove[matid] = gun.remat1_ymove[matid] - 1F;
 	            break;
    	        case 2:
    	            gun.remat2_ymove[matid] = gun.remat2_ymove[matid] - 1F;
    	            break;
    	        case 3:
    	        	gun.remat3_ymove[matid] = gun.remat3_ymove[matid] - 1F;
    	            break;
    	        case 4:
    	        	gun.remat22_ymove[matid] = gun.remat22_ymove[matid] - 1F;
    	            break;
    	        case 5:
    	        	gun.remat24_ymove[matid] = gun.remat24_ymove[matid] - 1F;
    	            break;
    	        case 6:
    	        	gun.remat25_ymove[matid] = gun.remat25_ymove[matid] - 1F;
    	            break;
    	        case 7:
    	        	gun.remat31_ymove[matid] = gun.remat31_ymove[matid] - 1F;
    	            break;
    	        case 8:
    	        	gun.remat32_ymove[matid] = gun.remat32_ymove[matid] - 1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_ymove[matid] = gun.rematlefthand_ymove[matid] - 1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_ymove[matid] = gun.rematrighthand_ymove[matid] - 1F;
    	            break;
    	        default:
    	        	gun.remat0_ymove[matid] = gun.remat0_ymove[matid] - 1F;
    	        }
         }
    	 
    	 if (button.id == 40)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_zmove[matid] = gun.remat1_zmove[matid] + 1F;
 	            break;
    	        case 2:
    	            gun.remat2_zmove[matid] = gun.remat2_zmove[matid] + 1F;
    	            break;
    	        case 3:
    	        	gun.remat3_zmove[matid] = gun.remat3_zmove[matid] + 1F;
    	            break;
    	        case 4:
    	        	gun.remat22_zmove[matid] = gun.remat22_zmove[matid] + 1F;
    	            break;
    	        case 5:
    	        	gun.remat24_zmove[matid] = gun.remat24_zmove[matid] + 1F;
    	            break;
    	        case 6:
    	        	gun.remat25_zmove[matid] = gun.remat25_zmove[matid] + 1F;
    	            break;
    	        case 7:
    	        	gun.remat31_zmove[matid] = gun.remat31_zmove[matid] + 1F;
    	            break;
    	        case 8:
    	        	gun.remat32_zmove[matid] = gun.remat32_zmove[matid] + 1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_zmove[matid] = gun.rematlefthand_zmove[matid] + 1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_zmove[matid] = gun.rematrighthand_zmove[matid] + 1F;
    	            break;
    	        default:
    	        	gun.remat0_zmove[matid] = gun.remat0_zmove[matid] + 1F;
    	        }
         }
    	 if (button.id == 41)
         {
    		 switch (mat) {
    		 case 1:
 	            gun.remat1_zmove[matid] = gun.remat1_zmove[matid] - 1F;
 	            break;
    	        case 2:
    	            gun.remat2_zmove[matid] = gun.remat2_zmove[matid] - 1F;
    	            break;
    	        case 3:
    	        	gun.remat3_zmove[matid] = gun.remat3_zmove[matid] - 1F;
    	            break;
    	        case 4:
    	        	gun.remat22_zmove[matid] = gun.remat22_zmove[matid] - 1F;
    	            break;
    	        case 5:
    	        	gun.remat24_zmove[matid] = gun.remat24_zmove[matid] - 1F;
    	            break;
    	        case 6:
    	        	gun.remat25_zmove[matid] = gun.remat25_zmove[matid] - 1F;
    	            break;
    	        case 7:
    	        	gun.remat31_zmove[matid] = gun.remat31_zmove[matid] - 1F;
    	            break;
    	        case 8:
    	        	gun.remat32_zmove[matid] = gun.remat32_zmove[matid] - 1F;
    	            break;
    	        case 9:
    	        	gun.rematlefthand_zmove[matid] = gun.rematlefthand_zmove[matid] - 1F;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_zmove[matid] = gun.rematrighthand_zmove[matid] - 1F;
    	            break;
    	        default:
    	        	gun.remat0_zmove[matid] = gun.remat0_zmove[matid] - 1F;
    	        }
         }
    	 
    	 if (button.id == 46)
         {
    		 switch (mat) {
    		 case 1:
				if (matid > 0) {
					gun.remat1_xpoint[matid] = gun.remat1_xpoint[matid + 1];
					gun.remat1_ypoint[matid] = gun.remat1_ypoint[matid + 1];
					gun.remat1_zpoint[matid] = gun.remat1_zpoint[matid + 1];
					gun.remat1_xrote[matid] = gun.remat1_xrote[matid+ 1];
					gun.remat1_yrote[matid] = gun.remat1_yrote[matid+ 1];
					gun.remat1_zrote[matid] = gun.remat1_zrote[matid+ 1];
					gun.remat1_xmove[matid] = gun.remat1_xmove[matid + 1];
					gun.remat1_ymove[matid] = gun.remat1_ymove[matid + 1];
					gun.remat1_zmove[matid] = gun.remat1_zmove[matid + 1];
				}
 	            break;
    	        case 2:
    	        	if (matid > 0) {
    					gun.remat2_xpoint[matid] = gun.remat2_xpoint[matid + 1];
    					gun.remat2_ypoint[matid] = gun.remat2_ypoint[matid + 1];
    					gun.remat2_zpoint[matid] = gun.remat2_zpoint[matid + 1];
    					gun.remat2_xrote[matid] = gun.remat2_xrote[matid+ 1];
    					gun.remat2_yrote[matid] = gun.remat2_yrote[matid+ 1];
    					gun.remat2_zrote[matid] = gun.remat2_zrote[matid+ 1];
    					gun.remat2_xmove[matid] = gun.remat2_xmove[matid + 1];
    					gun.remat2_ymove[matid] = gun.remat2_ymove[matid + 1];
    					gun.remat2_zmove[matid] = gun.remat2_zmove[matid + 1];
    				}
    	            break;
    	        case 3:
    	        	if (matid > 0) {
    					gun.remat3_xpoint[matid] = gun.remat3_xpoint[matid + 1];
    					gun.remat3_ypoint[matid] = gun.remat3_ypoint[matid + 1];
    					gun.remat3_zpoint[matid] = gun.remat3_zpoint[matid + 1];
    					gun.remat3_xrote[matid] = gun.remat3_xrote[matid+ 1];
    					gun.remat3_yrote[matid] = gun.remat3_yrote[matid+ 1];
    					gun.remat3_zrote[matid] = gun.remat3_zrote[matid+ 1];
    					gun.remat3_xmove[matid] = gun.remat3_xmove[matid + 1];
    					gun.remat3_ymove[matid] = gun.remat3_ymove[matid + 1];
    					gun.remat3_zmove[matid] = gun.remat3_zmove[matid + 1];
    				}
    	            break;
    	        case 4:
    	        	if (matid > 0) {
    					gun.remat22_xpoint[matid] = gun.remat22_xpoint[matid + 1];
    					gun.remat22_ypoint[matid] = gun.remat22_ypoint[matid + 1];
    					gun.remat22_zpoint[matid] = gun.remat22_zpoint[matid + 1];
    					gun.remat22_xrote[matid] = gun.remat22_xrote[matid+ 1];
    					gun.remat22_yrote[matid] = gun.remat22_yrote[matid+ 1];
    					gun.remat22_zrote[matid] = gun.remat22_zrote[matid+ 1];
    					gun.remat22_xmove[matid] = gun.remat22_xmove[matid + 1];
    					gun.remat22_ymove[matid] = gun.remat22_ymove[matid + 1];
    					gun.remat22_zmove[matid] = gun.remat22_zmove[matid + 1];
    				}
    	            break;
    	        case 5:
    	        	if (matid > 0) {
    					gun.remat24_xpoint[matid] = gun.remat24_xpoint[matid + 1];
    					gun.remat24_ypoint[matid] = gun.remat24_ypoint[matid + 1];
    					gun.remat24_zpoint[matid] = gun.remat24_zpoint[matid + 1];
    					gun.remat24_xrote[matid] = gun.remat24_xrote[matid+ 1];
    					gun.remat24_yrote[matid] = gun.remat24_yrote[matid+ 1];
    					gun.remat24_zrote[matid] = gun.remat24_zrote[matid+ 1];
    					gun.remat24_xmove[matid] = gun.remat24_xmove[matid + 1];
    					gun.remat24_ymove[matid] = gun.remat24_ymove[matid + 1];
    					gun.remat24_zmove[matid] = gun.remat24_zmove[matid + 1];
    				}
    	            break;
    	        case 6:
    	        	if (matid > 0) {
    					gun.remat25_xpoint[matid] = gun.remat25_xpoint[matid + 1];
    					gun.remat25_ypoint[matid] = gun.remat25_ypoint[matid + 1];
    					gun.remat25_zpoint[matid] = gun.remat25_zpoint[matid + 1];
    					gun.remat25_xrote[matid] = gun.remat25_xrote[matid+ 1];
    					gun.remat25_yrote[matid] = gun.remat25_yrote[matid+ 1];
    					gun.remat25_zrote[matid] = gun.remat25_zrote[matid+ 1];
    					gun.remat25_xmove[matid] = gun.remat25_xmove[matid + 1];
    					gun.remat25_ymove[matid] = gun.remat25_ymove[matid + 1];
    					gun.remat25_zmove[matid] = gun.remat25_zmove[matid + 1];
    				}
    	            break;
    	        case 7:
    	        	if (matid > 0) {
    					gun.remat31_xpoint[matid] = gun.remat31_xpoint[matid + 1];
    					gun.remat31_ypoint[matid] = gun.remat31_ypoint[matid + 1];
    					gun.remat31_zpoint[matid] = gun.remat31_zpoint[matid + 1];
    					gun.remat31_xrote[matid] = gun.remat31_xrote[matid+ 1];
    					gun.remat31_yrote[matid] = gun.remat31_yrote[matid+ 1];
    					gun.remat31_zrote[matid] = gun.remat31_zrote[matid+ 1];
    					gun.remat31_xmove[matid] = gun.remat31_xmove[matid + 1];
    					gun.remat31_ymove[matid] = gun.remat31_ymove[matid + 1];
    					gun.remat31_zmove[matid] = gun.remat31_zmove[matid + 1];
    				}
    	            break;
    	        case 8:
    	        	if (matid > 0) {
    					gun.remat32_xpoint[matid] = gun.remat32_xpoint[matid + 1];
    					gun.remat32_ypoint[matid] = gun.remat32_ypoint[matid + 1];
    					gun.remat32_zpoint[matid] = gun.remat32_zpoint[matid + 1];
    					gun.remat32_xrote[matid] = gun.remat32_xrote[matid+ 1];
    					gun.remat32_yrote[matid] = gun.remat32_yrote[matid+ 1];
    					gun.remat32_zrote[matid] = gun.remat32_zrote[matid+ 1];
    					gun.remat32_xmove[matid] = gun.remat32_xmove[matid + 1];
    					gun.remat32_ymove[matid] = gun.remat32_ymove[matid + 1];
    					gun.remat32_zmove[matid] = gun.remat32_zmove[matid + 1];
    				}
    	            break;
    	        case 9:
    	        	if (matid > 0) {
    					gun.rematlefthand_xpoint[matid] = gun.rematlefthand_xpoint[matid + 1];
    					gun.rematlefthand_ypoint[matid] = gun.rematlefthand_ypoint[matid + 1];
    					gun.rematlefthand_zpoint[matid] = gun.rematlefthand_zpoint[matid + 1];
    					gun.rematlefthand_xrote[matid] = gun.rematlefthand_xrote[matid+ 1];
    					gun.rematlefthand_yrote[matid] = gun.rematlefthand_yrote[matid+ 1];
    					gun.rematlefthand_zrote[matid] = gun.rematlefthand_zrote[matid+ 1];
    					gun.rematlefthand_xmove[matid] = gun.rematlefthand_xmove[matid + 1];
    					gun.rematlefthand_ymove[matid] = gun.rematlefthand_ymove[matid + 1];
    					gun.rematlefthand_zmove[matid] = gun.rematlefthand_zmove[matid + 1];
    				}
    	            break;
    	        case 10:
    	        	if (matid > 0) {
    					gun.rematrighthand_xpoint[matid] = gun.rematrighthand_xpoint[matid + 1];
    					gun.rematrighthand_ypoint[matid] = gun.rematrighthand_ypoint[matid + 1];
    					gun.rematrighthand_zpoint[matid] = gun.rematrighthand_zpoint[matid + 1];
    					gun.rematrighthand_xrote[matid] = gun.rematrighthand_xrote[matid+ 1];
    					gun.rematrighthand_yrote[matid] = gun.rematrighthand_yrote[matid+ 1];
    					gun.rematrighthand_zrote[matid] = gun.rematrighthand_zrote[matid+ 1];
    					gun.rematrighthand_xmove[matid] = gun.rematrighthand_xmove[matid + 1];
    					gun.rematrighthand_ymove[matid] = gun.rematrighthand_ymove[matid + 1];
    					gun.rematrighthand_zmove[matid] = gun.rematrighthand_zmove[matid + 1];
    				}
    	            break;
    	        default:
    	        	if (matid > 0) {
    					gun.remat0_xpoint[matid] = gun.remat0_xpoint[matid + 1];
    					gun.remat0_ypoint[matid] = gun.remat0_ypoint[matid + 1];
    					gun.remat0_zpoint[matid] = gun.remat0_zpoint[matid + 1];
    					gun.remat0_xrote[matid] = gun.remat0_xrote[matid+ 1];
    					gun.remat0_yrote[matid] = gun.remat0_yrote[matid+ 1];
    					gun.remat0_zrote[matid] = gun.remat0_zrote[matid+ 1];
    					gun.remat0_xmove[matid] = gun.remat0_xmove[matid + 1];
    					gun.remat0_ymove[matid] = gun.remat0_ymove[matid + 1];
    					gun.remat0_zmove[matid] = gun.remat0_zmove[matid + 1];
    				}
    	        }
         }
    	 if (button.id == 47)
         {
    		 switch (mat) {
    		 case 1:
				if (matid > 0) {
					gun.remat1_xpoint[matid] = gun.remat1_xpoint[matid - 1];
					gun.remat1_ypoint[matid] = gun.remat1_ypoint[matid - 1];
					gun.remat1_zpoint[matid] = gun.remat1_zpoint[matid - 1];
					gun.remat1_xrote[matid] = gun.remat1_xrote[matid-1];
					gun.remat1_yrote[matid] = gun.remat1_yrote[matid-1];
					gun.remat1_zrote[matid] = gun.remat1_zrote[matid-1];
					gun.remat1_xmove[matid] = gun.remat1_xmove[matid - 1];
					gun.remat1_ymove[matid] = gun.remat1_ymove[matid - 1];
					gun.remat1_zmove[matid] = gun.remat1_zmove[matid - 1];
				}
 	            break;
    	        case 2:
    	        	if (matid > 0) {
    					gun.remat2_xpoint[matid] = gun.remat2_xpoint[matid - 1];
    					gun.remat2_ypoint[matid] = gun.remat2_ypoint[matid - 1];
    					gun.remat2_zpoint[matid] = gun.remat2_zpoint[matid - 1];
    					gun.remat2_xrote[matid] = gun.remat2_xrote[matid-1];
    					gun.remat2_yrote[matid] = gun.remat2_yrote[matid-1];
    					gun.remat2_zrote[matid] = gun.remat2_zrote[matid-1];
    					gun.remat2_xmove[matid] = gun.remat2_xmove[matid - 1];
    					gun.remat2_ymove[matid] = gun.remat2_ymove[matid - 1];
    					gun.remat2_zmove[matid] = gun.remat2_zmove[matid - 1];
    				}
    	            break;
    	        case 3:
    	        	if (matid > 0) {
    					gun.remat3_xpoint[matid] = gun.remat3_xpoint[matid - 1];
    					gun.remat3_ypoint[matid] = gun.remat3_ypoint[matid - 1];
    					gun.remat3_zpoint[matid] = gun.remat3_zpoint[matid - 1];
    					gun.remat3_xrote[matid] = gun.remat3_xrote[matid-1];
    					gun.remat3_yrote[matid] = gun.remat3_yrote[matid-1];
    					gun.remat3_zrote[matid] = gun.remat3_zrote[matid-1];
    					gun.remat3_xmove[matid] = gun.remat3_xmove[matid - 1];
    					gun.remat3_ymove[matid] = gun.remat3_ymove[matid - 1];
    					gun.remat3_zmove[matid] = gun.remat3_zmove[matid - 1];
    				}
    	            break;
    	        case 4:
    	        	if (matid > 0) {
    					gun.remat22_xpoint[matid] = gun.remat22_xpoint[matid - 1];
    					gun.remat22_ypoint[matid] = gun.remat22_ypoint[matid - 1];
    					gun.remat22_zpoint[matid] = gun.remat22_zpoint[matid - 1];
    					gun.remat22_xrote[matid] = gun.remat22_xrote[matid-1];
    					gun.remat22_yrote[matid] = gun.remat22_yrote[matid-1];
    					gun.remat22_zrote[matid] = gun.remat22_zrote[matid-1];
    					gun.remat22_xmove[matid] = gun.remat22_xmove[matid - 1];
    					gun.remat22_ymove[matid] = gun.remat22_ymove[matid - 1];
    					gun.remat22_zmove[matid] = gun.remat22_zmove[matid - 1];
    				}
    	            break;
    	        case 5:
    	        	if (matid > 0) {
    					gun.remat24_xpoint[matid] = gun.remat24_xpoint[matid - 1];
    					gun.remat24_ypoint[matid] = gun.remat24_ypoint[matid - 1];
    					gun.remat24_zpoint[matid] = gun.remat24_zpoint[matid - 1];
    					gun.remat24_xrote[matid] = gun.remat24_xrote[matid-1];
    					gun.remat24_yrote[matid] = gun.remat24_yrote[matid-1];
    					gun.remat24_zrote[matid] = gun.remat24_zrote[matid-1];
    					gun.remat24_xmove[matid] = gun.remat24_xmove[matid - 1];
    					gun.remat24_ymove[matid] = gun.remat24_ymove[matid - 1];
    					gun.remat24_zmove[matid] = gun.remat24_zmove[matid - 1];
    				}
    	            break;
    	        case 6:
    	        	if (matid > 0) {
    					gun.remat25_xpoint[matid] = gun.remat25_xpoint[matid - 1];
    					gun.remat25_ypoint[matid] = gun.remat25_ypoint[matid - 1];
    					gun.remat25_zpoint[matid] = gun.remat25_zpoint[matid - 1];
    					gun.remat25_xrote[matid] = gun.remat25_xrote[matid-1];
    					gun.remat25_yrote[matid] = gun.remat25_yrote[matid-1];
    					gun.remat25_zrote[matid] = gun.remat25_zrote[matid-1];
    					gun.remat25_xmove[matid] = gun.remat25_xmove[matid - 1];
    					gun.remat25_ymove[matid] = gun.remat25_ymove[matid - 1];
    					gun.remat25_zmove[matid] = gun.remat25_zmove[matid - 1];
    				}
    	            break;
    	        case 7:
    	        	if (matid > 0) {
    					gun.remat31_xpoint[matid] = gun.remat31_xpoint[matid - 1];
    					gun.remat31_ypoint[matid] = gun.remat31_ypoint[matid - 1];
    					gun.remat31_zpoint[matid] = gun.remat31_zpoint[matid - 1];
    					gun.remat31_xrote[matid] = gun.remat31_xrote[matid-1];
    					gun.remat31_yrote[matid] = gun.remat31_yrote[matid-1];
    					gun.remat31_zrote[matid] = gun.remat31_zrote[matid-1];
    					gun.remat31_xmove[matid] = gun.remat31_xmove[matid - 1];
    					gun.remat31_ymove[matid] = gun.remat31_ymove[matid - 1];
    					gun.remat31_zmove[matid] = gun.remat31_zmove[matid - 1];
    				}
    	            break;
    	        case 8:
    	        	if (matid > 0) {
    					gun.remat32_xpoint[matid] = gun.remat32_xpoint[matid - 1];
    					gun.remat32_ypoint[matid] = gun.remat32_ypoint[matid - 1];
    					gun.remat32_zpoint[matid] = gun.remat32_zpoint[matid - 1];
    					gun.remat32_xrote[matid] = gun.remat32_xrote[matid-1];
    					gun.remat32_yrote[matid] = gun.remat32_yrote[matid-1];
    					gun.remat32_zrote[matid] = gun.remat32_zrote[matid-1];
    					gun.remat32_xmove[matid] = gun.remat32_xmove[matid - 1];
    					gun.remat32_ymove[matid] = gun.remat32_ymove[matid - 1];
    					gun.remat32_zmove[matid] = gun.remat32_zmove[matid - 1];
    				}
    	            break;
    	        case 9:
    	        	if (matid > 0) {
    					gun.rematlefthand_xpoint[matid] = gun.rematlefthand_xpoint[matid - 1];
    					gun.rematlefthand_ypoint[matid] = gun.rematlefthand_ypoint[matid - 1];
    					gun.rematlefthand_zpoint[matid] = gun.rematlefthand_zpoint[matid - 1];
    					gun.rematlefthand_xrote[matid] = gun.rematlefthand_xrote[matid-1];
    					gun.rematlefthand_yrote[matid] = gun.rematlefthand_yrote[matid-1];
    					gun.rematlefthand_zrote[matid] = gun.rematlefthand_zrote[matid-1];
    					gun.rematlefthand_xmove[matid] = gun.rematlefthand_xmove[matid - 1];
    					gun.rematlefthand_ymove[matid] = gun.rematlefthand_ymove[matid - 1];
    					gun.rematlefthand_zmove[matid] = gun.rematlefthand_zmove[matid - 1];
    				}
    	            break;
    	        case 10:
    	        	if (matid > 0) {
    					gun.rematrighthand_xpoint[matid] = gun.rematrighthand_xpoint[matid - 1];
    					gun.rematrighthand_ypoint[matid] = gun.rematrighthand_ypoint[matid - 1];
    					gun.rematrighthand_zpoint[matid] = gun.rematrighthand_zpoint[matid - 1];
    					gun.rematrighthand_xrote[matid] = gun.rematrighthand_xrote[matid-1];
    					gun.rematrighthand_yrote[matid] = gun.rematrighthand_yrote[matid-1];
    					gun.rematrighthand_zrote[matid] = gun.rematrighthand_zrote[matid-1];
    					gun.rematrighthand_xmove[matid] = gun.rematrighthand_xmove[matid - 1];
    					gun.rematrighthand_ymove[matid] = gun.rematrighthand_ymove[matid - 1];
    					gun.rematrighthand_zmove[matid] = gun.rematrighthand_zmove[matid - 1];
    				}
    	            break;
    	        default:
    	        	if (matid > 0) {
    					gun.remat0_xpoint[matid] = gun.remat0_xpoint[matid - 1];
    					gun.remat0_ypoint[matid] = gun.remat0_ypoint[matid - 1];
    					gun.remat0_zpoint[matid] = gun.remat0_zpoint[matid - 1];
    					gun.remat0_xrote[matid] = gun.remat0_xrote[matid-1];
    					gun.remat0_yrote[matid] = gun.remat0_yrote[matid-1];
    					gun.remat0_zrote[matid] = gun.remat0_zrote[matid-1];
    					gun.remat0_xmove[matid] = gun.remat0_xmove[matid - 1];
    					gun.remat0_ymove[matid] = gun.remat0_ymove[matid - 1];
    					gun.remat0_zmove[matid] = gun.remat0_zmove[matid - 1];
    				}
    	        }
         }
    	 
    	 if (button.id == 48)
         {
    		 switch (mat) {
    	        case 9:
    	        	gun.rematlefthand_xmove[matid] = gun.arm_l_posx;
    	        	gun.rematlefthand_ymove[matid] = gun.arm_l_posy;
    	        	gun.rematlefthand_zmove[matid] = gun.arm_l_posz;
    	            break;
    	        case 10:
    	        	gun.rematrighthand_xmove[matid] = gun.arm_r_posx;
    	        	gun.rematrighthand_ymove[matid] = gun.arm_r_posy;
    	        	gun.rematrighthand_zmove[matid] = gun.arm_r_posz;
    	            break;
    	        default:
    	        }
         }
    	 
    	 
    	 
    	 
    	 if (button.id == 29)
         {
    		 String time = String.valueOf(player.world.getWorldTime());
			try {
				File newfile = new File(mod_GVCLib.proxy.ProxyFile(),
						"mods" + File.separatorChar + "reloadmotion" + time + ".txt");
				newfile.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
				{
					for(int i = 0; i < 200; ++i) {
						bw.write("Remat0,");
						bw.write(gun.remat0_time[i] + ",");
						bw.write(gun.remat0_xpoint[i] + "F,");
						bw.write(gun.remat0_ypoint[i] + "F,");
						bw.write(gun.remat0_zpoint[i] + "F,");
						bw.write(gun.remat0_xrote[i] + "F,");
						bw.write(gun.remat0_yrote[i] + "F,");
						bw.write(gun.remat0_zrote[i] + "F,");
						bw.write(gun.remat0_xmove[i] + "F,");
						bw.write(gun.remat0_ymove[i] + "F,");
						bw.write(gun.remat0_zmove[i] + "F");
						bw.newLine();
					}
					for(int i = 0; i < 200; ++i) {
						bw.write("Remat1,");
						bw.write(gun.remat1_time[i] + ",");
						bw.write(gun.remat1_xpoint[i] + "F,");
						bw.write(gun.remat1_ypoint[i] + "F,");
						bw.write(gun.remat1_zpoint[i] + "F,");
						bw.write(gun.remat1_xrote[i] + "F,");
						bw.write(gun.remat1_yrote[i] + "F,");
						bw.write(gun.remat1_zrote[i] + "F,");
						bw.write(gun.remat1_xmove[i] + "F,");
						bw.write(gun.remat1_ymove[i] + "F,");
						bw.write(gun.remat1_zmove[i] + "F");
						bw.newLine();
					}
					for(int i = 0; i < 200; ++i) {
						bw.write("Remat2,");
						bw.write(gun.remat2_time[i] + ",");
						bw.write(gun.remat2_xpoint[i] + "F,");
						bw.write(gun.remat2_ypoint[i] + "F,");
						bw.write(gun.remat2_zpoint[i] + "F,");
						bw.write(gun.remat2_xrote[i] + "F,");
						bw.write(gun.remat2_yrote[i] + "F,");
						bw.write(gun.remat2_zrote[i] + "F,");
						bw.write(gun.remat2_xmove[i] + "F,");
						bw.write(gun.remat2_ymove[i] + "F,");
						bw.write(gun.remat2_zmove[i] + "F");
						bw.newLine();
					}
					for(int i = 0; i < 200; ++i) {
						bw.write("Remat3,");
						bw.write(gun.remat3_time[i] + ",");
						bw.write(gun.remat3_xpoint[i] + "F,");
						bw.write(gun.remat3_ypoint[i] + "F,");
						bw.write(gun.remat3_zpoint[i] + "F,");
						bw.write(gun.remat3_xrote[i] + "F,");
						bw.write(gun.remat3_yrote[i] + "F,");
						bw.write(gun.remat3_zrote[i] + "F,");
						bw.write(gun.remat3_xmove[i] + "F,");
						bw.write(gun.remat3_ymove[i] + "F,");
						bw.write(gun.remat3_zmove[i] + "F");
						bw.newLine();
					}
					for(int i = 0; i < 200; ++i) {
						bw.write("Remat22,");
						bw.write(gun.remat22_time[i] + ",");
						bw.write(gun.remat22_xpoint[i] + "F,");
						bw.write(gun.remat22_ypoint[i] + "F,");
						bw.write(gun.remat22_zpoint[i] + "F,");
						bw.write(gun.remat22_xrote[i] + "F,");
						bw.write(gun.remat22_yrote[i] + "F,");
						bw.write(gun.remat22_zrote[i] + "F,");
						bw.write(gun.remat22_xmove[i] + "F,");
						bw.write(gun.remat22_ymove[i] + "F,");
						bw.write(gun.remat22_zmove[i] + "F");
						bw.newLine();
					}
					for(int i = 0; i < 200; ++i) {
						bw.write("Remat24,");
						bw.write(gun.remat24_time[i] + ",");
						bw.write(gun.remat24_xpoint[i] + "F,");
						bw.write(gun.remat24_ypoint[i] + "F,");
						bw.write(gun.remat24_zpoint[i] + "F,");
						bw.write(gun.remat24_xrote[i] + "F,");
						bw.write(gun.remat24_yrote[i] + "F,");
						bw.write(gun.remat24_zrote[i] + "F,");
						bw.write(gun.remat24_xmove[i] + "F,");
						bw.write(gun.remat24_ymove[i] + "F,");
						bw.write(gun.remat24_zmove[i] + "F");
						bw.newLine();
					}
					for(int i = 0; i < 200; ++i) {
						bw.write("Remat25,");
						bw.write(gun.remat25_time[i] + ",");
						bw.write(gun.remat25_xpoint[i] + "F,");
						bw.write(gun.remat25_ypoint[i] + "F,");
						bw.write(gun.remat25_zpoint[i] + "F,");
						bw.write(gun.remat25_xrote[i] + "F,");
						bw.write(gun.remat25_yrote[i] + "F,");
						bw.write(gun.remat25_zrote[i] + "F,");
						bw.write(gun.remat25_xmove[i] + "F,");
						bw.write(gun.remat25_ymove[i] + "F,");
						bw.write(gun.remat25_zmove[i] + "F");
						bw.newLine();
					}
					for(int i = 0; i < 200; ++i) {
						bw.write("Remat31,");
						bw.write(gun.remat31_time[i] + ",");
						bw.write(gun.remat31_xpoint[i] + "F,");
						bw.write(gun.remat31_ypoint[i] + "F,");
						bw.write(gun.remat31_zpoint[i] + "F,");
						bw.write(gun.remat31_xrote[i] + "F,");
						bw.write(gun.remat31_yrote[i] + "F,");
						bw.write(gun.remat31_zrote[i] + "F,");
						bw.write(gun.remat31_xmove[i] + "F,");
						bw.write(gun.remat31_ymove[i] + "F,");
						bw.write(gun.remat31_zmove[i] + "F");
						bw.newLine();
					}
					for(int i = 0; i < 200; ++i) {
						bw.write("Remat32,");
						bw.write(gun.remat32_time[i] + ",");
						bw.write(gun.remat32_xpoint[i] + "F,");
						bw.write(gun.remat32_ypoint[i] + "F,");
						bw.write(gun.remat32_zpoint[i] + "F,");
						bw.write(gun.remat32_xrote[i] + "F,");
						bw.write(gun.remat32_yrote[i] + "F,");
						bw.write(gun.remat32_zrote[i] + "F,");
						bw.write(gun.remat32_xmove[i] + "F,");
						bw.write(gun.remat32_ymove[i] + "F,");
						bw.write(gun.remat32_zmove[i] + "F");
						bw.newLine();
					}
					for(int i = 0; i < 200; ++i) {
						bw.write("Rematlefthand,");
						bw.write(gun.rematlefthand_time[i] + ",");
						bw.write(gun.rematlefthand_xpoint[i] + "F,");
						bw.write(gun.rematlefthand_ypoint[i] + "F,");
						bw.write(gun.rematlefthand_zpoint[i] + "F,");
						bw.write(gun.rematlefthand_xrote[i] + "F,");
						bw.write(gun.rematlefthand_yrote[i] + "F,");
						bw.write(gun.rematlefthand_zrote[i] + "F,");
						bw.write(gun.rematlefthand_xmove[i] + "F,");
						bw.write(gun.rematlefthand_ymove[i] + "F,");
						bw.write(gun.rematlefthand_zmove[i] + "F");
						bw.newLine();
					}
					for(int i = 0; i < 200; ++i) {
						bw.write("Rematrighthand,");
						bw.write(gun.rematrighthand_time[i] + ",");
						bw.write(gun.rematrighthand_xpoint[i] + "F,");
						bw.write(gun.rematrighthand_ypoint[i] + "F,");
						bw.write(gun.rematrighthand_zpoint[i] + "F,");
						bw.write(gun.rematrighthand_xrote[i] + "F,");
						bw.write(gun.rematrighthand_yrote[i] + "F,");
						bw.write(gun.rematrighthand_zrote[i] + "F,");
						bw.write(gun.rematrighthand_xmove[i] + "F,");
						bw.write(gun.rematrighthand_ymove[i] + "F,");
						bw.write(gun.rematrighthand_zmove[i] + "F");
						bw.newLine();
					}
				}
				bw.close();
				player.sendMessage(new TextComponentTranslation("reloadmotion" + time + ".txt", new Object[0]));
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} /**/
         }
    }

	/*
	背景の描画
	*/
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		int k = (this.width) / 2;
		int l = (this.height) / 2;
		FontRenderer fontReader = mc.fontRenderer;
		mc.fontRenderer.setUnicodeFlag(mc.isUnicode());
		
		GL11.glPushMatrix();
		{
			String d1 = String.format("%1$3d", (int)gun.retime);
			fontReader.drawString("retime : "  + d1,  k - 20, l -40, 0xFFFFFF);
		}
		{
			 if(!gun.reloadmotion_test) {
				 fontReader.drawString("reloadtest_false",  k - 20, l - 30, 0xFFFFFF);
    		 }else {
    			 fontReader.drawString("reloadtest_true",  k - 20, l - 30, 0xFFFFFF);
    		 }
		}
		{
			int count = 0;
			String mats = "";
			switch (mat) {
			case 1:
	        	mats = "mat1";
	            break;
			case 2:
	        	mats = "mat2";
	            break;
	        case 3:
	        	mats = "mat3";
	            break;
	        case 4:
	        	mats = "mat22";
	            break;
	        case 5:
	        	mats = "mat24";
	            break;
	        case 6:
	        	mats = "mat25";
	            break;
	        case 7:
	        	mats = "mat31";
	            break;
	        case 8:
	        	mats = "mat32";
	            break;
	        case 9:
	        	mats = "matlefthand";
	            break;
	        case 10:
	        	mats = "matrighthand";
	            break;
	        default:
	        	mats = "mat0";
	        }
			fontReader.drawString(mats, 30, y + 0, 0xFFFFFF);
		}
		{
			int count = 0;
			String mats = "";
			switch (mat) {
			 case 1:
		        	count = gun.remat1;
		        	mats = "mat1";
		            break;
	        case 2:
	        	count = gun.remat2;
	        	mats = "mat2";
	            break;
	        case 3:
	        	count = gun.remat3;
	        	mats = "mat3";
	            break;
	        case 4:
	        	count = gun.remat22;
	        	mats = "mat22";
	            break;
	        case 5:
	        	count = gun.remat24;
	        	mats = "mat24";
	            break;
	        case 6:
	        	count = gun.remat25;
	        	mats = "mat25";
	            break;
	        case 7:
	        	count = gun.remat311;
	        	mats = "mat31";
	            break;
	        case 8:
	        	count = gun.remat32;
	        	mats = "mat32";
	            break;
	        case 9:
	        	count = gun.rematlefthand;
	        	mats = "matlefthand";
	            break;
	        case 10:
	        	count = gun.rematrighthand;
	        	mats = "matrighthand";
	            break;
	        default:
	        	count = gun.remat0;
	        	mats = "mat0";
	        }
			fontReader.drawString(mats + " : "  + String.format("%1$3d", (int)200),  30, y + 20, 0xFFFFFF);
		}
		{
			int count = matid;
			String mats = "Part";
			
			fontReader.drawString(mats + " : "  + String.format("%1$3d", (int)count),  30, y + 40, 0xFFFFFF);
		}
		
		{
			int count = 0;
			String mats = "";
			switch (mat) {
			case 1:
	        	count = gun.remat1_time[matid];
	        	mats = "mat1";
	            break;
	        case 2:
	        	count = gun.remat2_time[matid];
	        	mats = "mat2";
	            break;
	        case 3:
	        	count = gun.remat3_time[matid];
	        	mats = "mat3";
	            break;
	        case 4:
	        	count = gun.remat22_time[matid];
	        	mats = "mat22";
	            break;
	        case 5:
	        	count = gun.remat24_time[matid];
	        	mats = "mat24";
	            break;
	        case 6:
	        	count = gun.remat25_time[matid];
	        	mats = "mat25";
	            break;
	        case 7:
	        	count = gun.remat31_time[matid];
	        	mats = "mat31";
	            break;
	        case 8:
	        	count = gun.remat32_time[matid];
	        	mats = "mat32";
	            break;
	        case 9:
	        	count = gun.rematlefthand_time[matid];
	        	mats = "matlefthand";
	            break;
	        case 10:
	        	count = gun.rematrighthand_time[matid];
	        	mats = "matrighthand";
	            break;
	        default:
	        	count = gun.remat0_time[matid];
	        	mats = "mat0";
	        }
			fontReader.drawString(mats + "_time : "  + String.format("%1$3d", (int)count),  10, y + 60, 0xFFFFFF);
		}
		
		
		{
			float count = 0;
			String mats = "";
			switch (mat) {
			 case 1:
		        	count = gun.remat1_xpoint[matid];
		        	mats = "mat1";
		            break;
	        case 2:
	        	count = gun.remat2_xpoint[matid];
	        	mats = "mat2";
	            break;
	        case 3:
	        	count = gun.remat3_xpoint[matid];
	        	mats = "mat3";
	            break;
	        case 4:
	        	count = gun.remat22_xpoint[matid];
	        	mats = "mat22";
	            break;
	        case 5:
	        	count = gun.remat24_xpoint[matid];
	        	mats = "mat24";
	            break;
	        case 6:
	        	count = gun.remat25_xpoint[matid];
	        	mats = "mat25";
	            break;
	        case 7:
	        	count = gun.remat31_xpoint[matid];
	        	mats = "mat31";
	            break;
	        case 8:
	        	count = gun.remat32_xpoint[matid];
	        	mats = "mat32";
	            break;
	        case 9:
	        	count = gun.rematlefthand_xpoint[matid];
	        	mats = "matlefthand";
	            break;
	        case 10:
	        	count = gun.rematrighthand_xpoint[matid];
	        	mats = "matrighthand";
	            break;
	        default:
	        	count = gun.remat0_xpoint[matid];
	        	mats = "mat0";
	        }
			fontReader.drawString(mats + "_xpoint : "  + String.format("%1$.2f", count),  10, y + 80, 0xFFFFFF);
		}
		{
			float count = 0;
			String mats = "";
			switch (mat) {
			case 1:
	        	count = gun.remat1_ypoint[matid];
	        	mats = "mat1";
	            break;
	        case 2:
	        	count = gun.remat2_ypoint[matid];
	        	mats = "mat2";
	            break;
	        case 3:
	        	count = gun.remat3_ypoint[matid];
	        	mats = "mat3";
	            break;
	        case 4:
	        	count = gun.remat22_ypoint[matid];
	        	mats = "mat22";
	            break;
	        case 5:
	        	count = gun.remat24_ypoint[matid];
	        	mats = "mat24";
	            break;
	        case 6:
	        	count = gun.remat25_ypoint[matid];
	        	mats = "mat25";
	            break;
	        case 7:
	        	count = gun.remat31_ypoint[matid];
	        	mats = "mat31";
	            break;
	        case 8:
	        	count = gun.remat32_ypoint[matid];
	        	mats = "mat32";
	            break;
	        case 9:
	        	count = gun.rematlefthand_ypoint[matid];
	        	mats = "matlefthand";
	            break;
	        case 10:
	        	count = gun.rematrighthand_ypoint[matid];
	        	mats = "matrighthand";
	            break;
	        default:
	        	count = gun.remat0_ypoint[matid];
	        	mats = "mat0";
	        }
			fontReader.drawString(mats + "_ypoint : "  + String.format("%1$.2f", count),  10, y + 95, 0xFFFFFF);
		}
		{
			float count = 0;
			String mats = "";
			switch (mat) {
			  case 1:
		        	count = gun.remat1_zpoint[matid];
		        	mats = "mat1";
		            break;
	        case 2:
	        	count = gun.remat2_zpoint[matid];
	        	mats = "mat2";
	            break;
	        case 3:
	        	count = gun.remat3_zpoint[matid];
	        	mats = "mat3";
	            break;
	        case 4:
	        	count = gun.remat22_zpoint[matid];
	        	mats = "mat22";
	            break;
	        case 5:
	        	count = gun.remat24_zpoint[matid];
	        	mats = "mat24";
	            break;
	        case 6:
	        	count = gun.remat25_zpoint[matid];
	        	mats = "mat25";
	            break;
	        case 7:
	        	count = gun.remat31_zpoint[matid];
	        	mats = "mat31";
	            break;
	        case 8:
	        	count = gun.remat32_zpoint[matid];
	        	mats = "mat32";
	            break;
	        case 9:
	        	count = gun.rematlefthand_zpoint[matid];
	        	mats = "matlefthand";
	            break;
	        case 10:
	        	count = gun.rematrighthand_zpoint[matid];
	        	mats = "matrighthand";
	            break;
	        default:
	        	count = gun.remat0_zpoint[matid];
	        	mats = "mat0";
	        }
			fontReader.drawString(mats + "_zpoint : "  + String.format("%1$.2f", count),  10, y + 110, 0xFFFFFF);
		}
		
		{
			float count = 0;
			String mats = "";
			switch (mat) {
			case 1:
	        	count = gun.remat1_xrote[matid];
	        	mats = "mat1";
	            break;
	        case 2:
	        	count = gun.remat2_xrote[matid];
	        	mats = "mat2";
	            break;
	        case 3:
	        	count = gun.remat3_xrote[matid];
	        	mats = "mat3";
	            break;
	        case 4:
	        	count = gun.remat22_xrote[matid];
	        	mats = "mat22";
	            break;
	        case 5:
	        	count = gun.remat24_xrote[matid];
	        	mats = "mat24";
	            break;
	        case 6:
	        	count = gun.remat25_xrote[matid];
	        	mats = "mat25";
	            break;
	        case 7:
	        	count = gun.remat31_xrote[matid];
	        	mats = "mat31";
	            break;
	        case 8:
	        	count = gun.remat32_xrote[matid];
	        	mats = "mat32";
	            break;
	        case 9:
	        	count = gun.rematlefthand_xrote[matid];
	        	mats = "matlefthand";
	            break;
	        case 10:
	        	count = gun.rematrighthand_xrote[matid];
	        	mats = "matrighthand";
	            break;
	        default:
	        	count = gun.remat0_xrote[matid];
	        	mats = "mat0";
	        }
			fontReader.drawString(mats + "_xrote : "  + String.format("%1$.2f", count),  10, y + 130, 0xFFFFFF);
		}
		{
			float count = 0;
			String mats = "";
			switch (mat) {
			 case 1:
		        	count = gun.remat1_yrote[matid];
		        	mats = "mat1";
		            break;
	        case 2:
	        	count = gun.remat2_yrote[matid];
	        	mats = "mat2";
	            break;
	        case 3:
	        	count = gun.remat3_yrote[matid];
	        	mats = "mat3";
	            break;
	        case 4:
	        	count = gun.remat22_yrote[matid];
	        	mats = "mat22";
	            break;
	        case 5:
	        	count = gun.remat24_yrote[matid];
	        	mats = "mat24";
	            break;
	        case 6:
	        	count = gun.remat25_yrote[matid];
	        	mats = "mat25";
	            break;
	        case 7:
	        	count = gun.remat31_yrote[matid];
	        	mats = "mat31";
	            break;
	        case 8:
	        	count = gun.remat32_yrote[matid];
	        	mats = "mat32";
	            break;
	        case 9:
	        	count = gun.rematlefthand_yrote[matid];
	        	mats = "matlefthand";
	            break;
	        case 10:
	        	count = gun.rematrighthand_yrote[matid];
	        	mats = "matrighthand";
	            break;
	        default:
	        	count = gun.remat0_yrote[matid];
	        	mats = "mat0";
	        }
			fontReader.drawString(mats + "_yrote : "  + String.format("%1$.2f", count),  10, y + 145, 0xFFFFFF);
		}
		{
			float count = 0;
			String mats = "";
			switch (mat) {
			 case 1:
		        	count = gun.remat1_zrote[matid];
		        	mats = "mat1";
		            break;
	        case 2:
	        	count = gun.remat2_zrote[matid];
	        	mats = "mat2";
	            break;
	        case 3:
	        	count = gun.remat3_zrote[matid];
	        	mats = "mat3";
	            break;
	        case 4:
	        	count = gun.remat22_zrote[matid];
	        	mats = "mat22";
	            break;
	        case 5:
	        	count = gun.remat24_zrote[matid];
	        	mats = "mat24";
	            break;
	        case 6:
	        	count = gun.remat25_zrote[matid];
	        	mats = "mat25";
	            break;
	        case 7:
	        	count = gun.remat31_zrote[matid];
	        	mats = "mat31";
	            break;
	        case 8:
	        	count = gun.remat32_zrote[matid];
	        	mats = "mat32";
	            break;
	        case 9:
	        	count = gun.rematlefthand_zrote[matid];
	        	mats = "matlefthand";
	            break;
	        case 10:
	        	count = gun.rematrighthand_zrote[matid];
	        	mats = "matrighthand";
	            break;
	        default:
	        	count = gun.remat0_zrote[matid];
	        	mats = "mat0";
	        }
			fontReader.drawString(mats + "_zrote : "  + String.format("%1$.2f", count),  10, y + 160, 0xFFFFFF);
		}
		
		{
			float count = 0;
			String mats = "";
			switch (mat) {
			case 1:
	        	count = gun.remat1_xmove[matid];
	        	mats = "mat1";
	            break;
	        case 2:
	        	count = gun.remat2_xmove[matid];
	        	mats = "mat2";
	            break;
	        case 3:
	        	count = gun.remat3_xmove[matid];
	        	mats = "mat3";
	            break;
	        case 4:
	        	count = gun.remat22_xmove[matid];
	        	mats = "mat22";
	            break;
	        case 5:
	        	count = gun.remat24_xmove[matid];
	        	mats = "mat24";
	            break;
	        case 6:
	        	count = gun.remat25_xmove[matid];
	        	mats = "mat25";
	            break;
	        case 7:
	        	count = gun.remat31_xmove[matid];
	        	mats = "mat31";
	            break;
	        case 8:
	        	count = gun.remat32_xmove[matid];
	        	mats = "mat32";
	            break;
	        case 9:
	        	count = gun.rematlefthand_xmove[matid];
	        	mats = "matlefthand";
	            break;
	        case 10:
	        	count = gun.rematrighthand_xmove[matid];
	        	mats = "matrighthand";
	            break;
	        default:
	        	count = gun.remat0_xmove[matid];
	        	mats = "mat0";
	        }
			fontReader.drawString(mats + "_xmove : "  + String.format("%1$.2f", count),  10, y + 180, 0xFFFFFF);
		}
		{
			float count = 0;
			String mats = "";
			switch (mat) {
			case 1:
	        	count = gun.remat1_ymove[matid];
	        	mats = "mat1";
	            break;
	        case 2:
	        	count = gun.remat2_ymove[matid];
	        	mats = "mat2";
	            break;
	        case 3:
	        	count = gun.remat3_ymove[matid];
	        	mats = "mat3";
	            break;
	        case 4:
	        	count = gun.remat22_ymove[matid];
	        	mats = "mat22";
	            break;
	        case 5:
	        	count = gun.remat24_ymove[matid];
	        	mats = "mat24";
	            break;
	        case 6:
	        	count = gun.remat25_ymove[matid];
	        	mats = "mat25";
	            break;
	        case 7:
	        	count = gun.remat31_ymove[matid];
	        	mats = "mat31";
	            break;
	        case 8:
	        	count = gun.remat32_ymove[matid];
	        	mats = "mat32";
	            break;
	        case 9:
	        	count = gun.rematlefthand_ymove[matid];
	        	mats = "matlefthand";
	            break;
	        case 10:
	        	count = gun.rematrighthand_ymove[matid];
	        	mats = "matrighthand";
	            break;
	        default:
	        	count = gun.remat0_ymove[matid];
	        	mats = "mat0";
	        }
			fontReader.drawString(mats + "_ymove : "  + String.format("%1$.2f", count),  10, y + 195, 0xFFFFFF);
		}
		{
			float count = 0;
			String mats = "";
			switch (mat) {
			 case 1:
		        	count = gun.remat1_zmove[matid];
		        	mats = "mat1";
		            break;
	        case 2:
	        	count = gun.remat2_zmove[matid];
	        	mats = "mat2";
	            break;
	        case 3:
	        	count = gun.remat3_zmove[matid];
	        	mats = "mat3";
	            break;
	        case 4:
	        	count = gun.remat22_zmove[matid];
	        	mats = "mat22";
	            break;
	        case 5:
	        	count = gun.remat24_zmove[matid];
	        	mats = "mat24";
	            break;
	        case 6:
	        	count = gun.remat25_zmove[matid];
	        	mats = "mat25";
	            break;
	        case 7:
	        	count = gun.remat31_zmove[matid];
	        	mats = "mat31";
	            break;
	        case 8:
	        	count = gun.remat32_zmove[matid];
	        	mats = "mat32";
	            break;
	        case 9:
	        	count = gun.rematlefthand_zmove[matid];
	        	mats = "matlefthand";
	            break;
	        case 10:
	        	count = gun.rematrighthand_zmove[matid];
	        	mats = "matrighthand";
	            break;
	        default:
	        	count = gun.remat0_zmove[matid];
	        	mats = "mat0";
	        }
			fontReader.drawString(mats + "_zmove : "  + String.format("%1$.2f", count),  10, y + 210, 0xFFFFFF);
		}
		
		
		
		/*************/
		GL11.glPopMatrix();
	}
    
	
	public int getmatid() {
		int idd = 0;
		 switch (mat) {
		 case 1:
             idd =gun.remat1;
            break;
	        case 2:
	             idd =gun.remat2;
	            break;
	        case 3:
	        	 idd =gun.remat3;
	            break;
	        case 4:
	        	 idd =gun.remat22;
	            break;
	        case 5:
	        	 idd =gun.remat24;
	            break;
	        case 6:
	        	 idd =gun.remat25;
	            break;
	        case 7:
	        	 idd =gun.remat311;
	            break;
	        case 8:
	        	 idd =gun.remat32;
	            break;
	        case 9:
	        	 idd =gun.rematlefthand;
	            break;
	        case 10:
	        	 idd =gun.rematrighthand;
	            break;
	        default:
	        	 idd =gun.remat0;
	        }
		 return idd;
	}
	
	/*
        ChestとかInventoryとか文字を描画する
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int p_146979_2_)
    {
    	
        //描画する文字, X, Y, 色
   //     this.fontRenderer.drawString("AR Attachment", 8, 6, 4210752);
   //     this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
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
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}