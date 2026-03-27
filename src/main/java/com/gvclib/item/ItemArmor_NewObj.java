package gvclib.item;
 
import gvclib.ClientProxyGVClib;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.IModelCustom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
public class ItemArmor_NewObj extends ItemArmor {
 
	//public static final String armor_layer1 = "hmggvc:textures/armor/praarmor_layer_1.png";
    //public static final String armor_layer2 = "hmggvc:textures/armor/praarmor_layer_2.png";
	public String armor_layer1;
    public String armor_layer2;
    public IModelCustom obj_model = null;//AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/armor/gas.obj"));
    /*public String obj_texh = "gvclib:textures/armor/gas.png";
    public String obj_texc = "gvclib:textures/armor/gas.png";
    public String obj_texl = "gvclib:textures/armor/gas.png";
    public String obj_texb = "gvclib:textures/armor/gas.png";*/
    public String obj_tex = "gvclib:textures/armor/nulll.png";
    
    //public IModelCustom obj_model = null;
    //public String obj_tex = "su01.obj";
    
    public boolean render_head = true;
    public boolean render_body = true;
    public boolean render_rarm = true;
    public boolean render_larm = true;
    public boolean render_rleg = true;
    public boolean render_lleg = true;
    
    public double motion = 1D;
    public boolean booster = false;
    public boolean para = false;
    public boolean fall = false;
    public double paraspeed = 0.75;
    
    //0305
    public boolean gazo = false;
    public String gazotex = null;
    public float tou = 1.0F;
    public boolean tps = true;

	//@Override
    protected static void renderEnchantedGlint(RenderLivingBase<?> p_188364_0_, EntityLivingBase p_188364_1_, ModelBase model, float p_188364_3_, float p_188364_4_, float p_188364_5_, float p_188364_6_, float p_188364_7_, float p_188364_8_, float p_188364_9_)
    {
		//return null;
	}
	
	public ItemArmor_NewObj(ItemArmor.ArmorMaterial armorMaterial, EntityEquipmentSlot type) {
		super(armorMaterial, 0, type);
	}
 
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		/*if (this.armorType == slot.HEAD) {
			return obj_texh;
		}else if (this.armorType == slot.CHEST) {
			return obj_texc;
		}else if (this.armorType == slot.LEGS) {
			return obj_texl;
		}else if (this.armorType == slot.FEET) {
			return obj_texb;
		}
		else{
		return obj_texh;
		}*/
		return obj_tex;
	}
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, net.minecraft.client.model.ModelBiped _default)
	  {
	    return ClientProxyGVClib.ArmorNewObj;
	  }
 
}
