package gvclib.item;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemMagazine extends Item {
	
	public String text1 = null;
	public String text2 = null;
	public String text3 = null;
	
	public String magazine_tab = null;
	
	public ItemMagazine() {
		super();
	}
	
	 public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	  {
		 tooltip.add(TextFormatting.WHITE +"MagazineTab ::: " + magazine_tab);//0
   	/*if(text1 != null) {
   		TextComponentTranslation information = new TextComponentTranslation(text1, new Object[0]);
   		tooltip.add(TextFormatting.WHITE + information.getFormattedText());//0
   	}
   	if(text2 != null) {
   		TextComponentTranslation information = new TextComponentTranslation(text2, new Object[0]);
   		tooltip.add(TextFormatting.WHITE + information.getFormattedText());//0
   	}
   	if(text3 != null) {
   		TextComponentTranslation information = new TextComponentTranslation(text3, new Object[0]);
   		tooltip.add(TextFormatting.WHITE + information.getFormattedText());//0
   	}
   	*/
	  }
	
}