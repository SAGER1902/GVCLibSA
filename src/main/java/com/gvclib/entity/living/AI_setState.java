package gvclib.entity.living;

import gvclib.item.ItemGun_Shield;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class AI_setState {
	public static void set(EntityGVCLivingBase entity) {
		ItemStack main = entity.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
		ItemStack off = entity.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
		boolean getshield = false;
		if(!main.isEmpty() && main.getItem() instanceof ItemGun_Shield) {
			getshield = true;
		}
		if(!off.isEmpty() && off.getItem() instanceof ItemGun_Shield) {
			getshield = true;
		}
		if(getshield && entity.getattacktask()) {
			if(entity.getAIType() == 0) {
				entity.sneak = true;
				entity.setSneak(true);
				//System.out.println(String.format("0"));
			}else {
				entity.setSneak(false);
				entity.sneak = false;
			}
		}
		if(getshield && entity.hurtResistantTime == 1) {
			entity.sneak = true;
			entity.setSneak(true);
			entity.setAIType(0);
			entity.aitypetime = 0;
		}
	}
}
