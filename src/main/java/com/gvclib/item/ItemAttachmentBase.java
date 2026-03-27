package gvclib.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import objmodel.IModelCustom;
import net.minecraft.entity.Entity;

import gvclib.mod_GVCLib;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;

import gvclib.item.gunbase.AttachmentNBT;

public class ItemAttachmentBase extends Item
{
	public String obj_model_string = "gvclib:textures/model/ar.png";
	public IModelCustom obj_model = null;//AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/ar.obj"));
    public String obj_tex = "gvclib:textures/model/ar.png";
    public boolean objtrue = false;
    public boolean zoomrender = true;
    public boolean zoomrendertex = false;
    public String ads_tex = "gvclib:textures/misc/scope.png";
    public float zoom = 1.0F;
	public float x = 0;
	public float y = 0;
	public float z = 0;
	
	public float eye_relief = - 2.0F;
	
	public float xr = 0;
	public float yr = 0;
	public float zr = 0;
	public float[] colorlevel = new float[1024];
	public int light_kazu = 1;

	/**使用用途不*/
	public boolean notpelletbase = false;
	public boolean pellet_priority = true;
	public int pellet = 1;
	
	//19/1/14
	public boolean rendering_light = true;
	
	//19/2/16
	public boolean grip_gripping_point = false;
	public float grip_gripping_point_x = 0;
	public float grip_gripping_point_y = 0;
	public float grip_gripping_point_z = 0;
	
	public String bullet_name;
	
	public String designated_attachment_name = null;
	
	
	public String information1 = null;
	public String information2 = null;
	public String information3 = null;
	
	public TextFormatting information1_color = TextFormatting.WHITE;
	public TextFormatting information2_color = TextFormatting.WHITE;
	public TextFormatting information3_color = TextFormatting.WHITE;
	
    public ItemAttachmentBase()
    {
        //this.maxStackSize = 1;
    }
    
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
    	tooltip.add(TextFormatting.BLUE + "手持枪械时按X键打开GUI");
    	String am = "";
    	if(information1 != null) {
			TextComponentTranslation information = new TextComponentTranslation(information1, new Object[0]);
			tooltip.add(information1_color + information.getFormattedText());
		}
		if(information2 != null) {
			TextComponentTranslation information = new TextComponentTranslation(information2, new Object[0]);
			tooltip.add(information2_color + information.getFormattedText());
		}
		if(information3 != null) {
			TextComponentTranslation information = new TextComponentTranslation(information3, new Object[0]);
			tooltip.add(information3_color + information.getFormattedText());
		}
		{
			if(designated_attachment_name != null) {
				tooltip.add(TextFormatting.YELLOW + "配件组" + " : " + designated_attachment_name);
			}
		}
    	if(id == 101 || id == 4 || id == 5) {
    		am = "瞄具";
    		tooltip.add(TextFormatting.YELLOW + "配件" + " : " + I18n.translateToLocal(am));
    		tooltip.add(TextFormatting.GREEN + "瞄准缩放倍数 " + " : x" + I18n.translateToLocal(String.valueOf(this.zoom)));
    	}
    	if(id == 102 || id == 6 || id == 7) {
    		am = "照明/激光瞄准";
    		tooltip.add(TextFormatting.YELLOW + "配件" + " : " + I18n.translateToLocal(am));
    	}
    	if(id == 8) {
    		am = "枪口";
    		tooltip.add(TextFormatting.YELLOW + "配件" + " : " + I18n.translateToLocal(am));
    	}
    	if(id == 9) {
    		am = "下挂";
    		tooltip.add(TextFormatting.YELLOW + "配件" + " : " + I18n.translateToLocal(am));
    		tooltip.add(TextFormatting.GREEN + "后坐力 " + " : " + I18n.translateToLocal(String.valueOf(this.recoil)));
    		tooltip.add(TextFormatting.GREEN + "后坐力-瞄准 " + " : " + I18n.translateToLocal(String.valueOf(this.recoil_ads)));
    		tooltip.add(TextFormatting.GREEN + "散布 " + " : " + I18n.translateToLocal(String.valueOf(this.bure)));
    		tooltip.add(TextFormatting.GREEN + "散布-瞄准 " + " : " + I18n.translateToLocal(String.valueOf(this.bure_ads)));
    	}
    	if(id == 50) {
    		am = "Bullet";
    		tooltip.add(TextFormatting.YELLOW + "配件" + " : " + I18n.translateToLocal(am));
    		tooltip.add(TextFormatting.YELLOW + "特殊子弹类型 " + " : " + I18n.translateToLocal(bullet_name));
    		tooltip.add(TextFormatting.GREEN + "威力倍数 " + " : x" + I18n.translateToLocal(String.format("%1$.2f ms", this.power)));
    		tooltip.add(TextFormatting.GREEN + "散弹数量 " + " : " + I18n.translateToLocal(String.valueOf(this.pellet)));
    		if(exlevel >= 1) {
    			tooltip.add(TextFormatting.GREEN + "爆炸范围倍数 " + " : " + I18n.translateToLocal(String.valueOf(this.exlevel)));
    		}
    		if(bulletid == 1) {
    			tooltip.add(TextFormatting.YELLOW + "穿透");
    		}
    		if(p_id != 0) {
    			tooltip.add(TextFormatting.GREEN + "药水ID " + " :"  + I18n.translateToLocal(String.valueOf(this.p_id)));
        		tooltip.add(TextFormatting.GREEN + "药水等级 " + " : " + I18n.translateToLocal(String.valueOf(this.p_level)));
        		tooltip.add(TextFormatting.GREEN + "药水持续时间 " + " : " + I18n.translateToLocal(String.valueOf(this.p_time)));
    		}
    		
    	}
    	if(id == 51) {
    		am = "穿透子弹";
    		tooltip.add(TextFormatting.YELLOW + "配件" + " : " + I18n.translateToLocal(am));
    	}
    	if(id == 52) {
    		am = "药水效果子弹";
    		tooltip.add(TextFormatting.YELLOW + "配件" + " : " + I18n.translateToLocal(am));
    	}
    	if(id == 53) {
    		am = "药水效果子弹";
    		tooltip.add(TextFormatting.YELLOW + "配件" + " : " + I18n.translateToLocal(am));
    	}
    	if(id == 54) {
    		am = "高爆子弹";
    		tooltip.add(TextFormatting.YELLOW + "配件" + " : " + I18n.translateToLocal(am));
    	}
    	if(id == 56) {
    		am = "A类加成插件";
    		tooltip.add(TextFormatting.YELLOW + "配件" + " : " + I18n.translateToLocal(am));
    		tooltip.add(TextFormatting.GREEN + "额外伤害 " + " : +" + I18n.translateToLocal(String.valueOf(this.extra_power * 100)) + "％");
    		if(extra_exlevel >= 1) {
    			tooltip.add(TextFormatting.GREEN + "额外爆炸范围" + " : +" + I18n.translateToLocal(String.valueOf(this.extra_exlevel)));
    		}
			if(this.extra_recoil != 0){
    		tooltip.add(TextFormatting.GREEN + "减少后坐力 " + " : " + I18n.translateToLocal(String.valueOf(this.extra_recoil * 100)) + "％");
    		tooltip.add(TextFormatting.GREEN + "减少散布 " + " : " + I18n.translateToLocal(String.valueOf(this.extra_bure * 100)) + "％");
			}
    	}
    	if(id == 57) {
    		am = "B类加成插件";
    		tooltip.add(TextFormatting.YELLOW + "配件" + " : " + I18n.translateToLocal(am));
    		tooltip.add(TextFormatting.GREEN + "减少后坐力 " + " : " + I18n.translateToLocal(String.valueOf(this.extra_recoil * 100)) + "％");
    		tooltip.add(TextFormatting.GREEN + "减少散布 " + " : " + I18n.translateToLocal(String.valueOf(this.extra_bure * 100)) + "％");
			if(this.extra_power != 0){
    		tooltip.add(TextFormatting.GREEN + "额外伤害 " + " : +" + I18n.translateToLocal(String.valueOf(this.extra_power * 100)) + "％");
			}
    	}
	}
	
	public double recoil = 0.8F;//子弹总体后座倍数
	public double recoil_ads = 0.6F;//子弹总体瞄准后座倍数
	public float bure = 0.8F;//子弹总体散布倍数
	public float bure_ads = 0.4F;//子弹总体瞄准散布倍数
	
	public int p_id = 0;//子弹药水效果ID
	public int p_level = 0;//子弹药水效果等级
	public int p_time = 0;//子弹药水效果持续时间
	
	public float exlevel = 0F;//子弹总体爆炸范围
	public float power = 1F;//子弹总体伤害倍数
	public int bulletid = 0;//子弹伤害类型-用于GVC内部伤害判定
	
	public float extra_power = 0F;//增加额外伤害-可叠加
	public float extra_exlevel = 0F;//增加额外爆炸范围-可叠加
	public float extra_recoil = 0F;//额外减少后座
	public float extra_bure = 0F;//额外减少散布
	public int bullet_solt_id = 0;//0-默认 1-A类 2-B类
	
	public int id = 0;//配件类型ID
	
	/*public String name_id = "";//随机ID，用于区分NBT
	public static boolean hasNBT = false;//随机ID，用于区分NBT
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
    {
    	super.onUpdate(itemstack, world, entity, i, flag);
	}
	
	public void AddNBT(ItemAttachmentBase gun, ItemStack itemstack, World world, EntityPlayer entityplayer, int i, boolean flag) {
		AttachmentNBT.load(gun, itemstack, world, entityplayer, i, flag);
	}
	
	public void readNBT(ItemAttachmentBase gun, ItemStack par1ItemStack, World world, EntityPlayer entityplayer, int i, boolean flag) {
		AttachmentNBT.read(gun, par1ItemStack, world, entityplayer, i, flag);
	}*/
}