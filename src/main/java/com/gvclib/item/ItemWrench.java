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

public class ItemWrench extends Item {
	
	public float attackdamage = 4.0F;
    public float dame;
    public double attackspeed = -1.5F;
	public ItemWrench() {
		this.maxStackSize = 1;
		this.setMaxDamage(128);
	}
	
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	  {
		{
			TextComponentTranslation information = new TextComponentTranslation("gvclib.wrench.1.name", new Object[0]);
	 		tooltip.add(TextFormatting.WHITE + information.getFormattedText());//0
		}
		{
			TextComponentTranslation information = new TextComponentTranslation("gvclib.wrench.2.name", new Object[0]);
	 		tooltip.add(TextFormatting.WHITE + information.getFormattedText());//0
		}
		
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
	
	/**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        return true;
    }
	
	/**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackdamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackspeed, 0));
        }

        return multimap;
    }
}