package gvclib.item;
 
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
 
public class ItemArmor_Grapple extends ItemArmor_NewObj {
 
	public double pointX;
    public double pointY;
    public double pointZ;
    public boolean point = false;

    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	  {
	tooltip.add(TextFormatting.WHITE + "C-key" + " : " + "Wire injection");
	tooltip.add(TextFormatting.WHITE + "Sneak" + " : " + "Wire cut");
	  }
    
	public ItemArmor_Grapple(ItemArmor.ArmorMaterial armorMaterial, EntityEquipmentSlot type) {
		super(armorMaterial, type);
	}
 
}
