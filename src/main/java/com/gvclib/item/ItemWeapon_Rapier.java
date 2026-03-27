package gvclib.item;

import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ItemWeapon_Rapier extends ItemWeaponBase// implements IReachItem
{
    private final float attackdamage;
    private final Item.ToolMaterial material;
    public float dame;
    public double attackspeed;
    public double reach;

    public ItemWeapon_Rapier(float dame, double as, Item.ToolMaterial material)
    {
    	super(material);
        this.material = material;
        this.setMaxDamage(material.getMaxUses());
        this.attackdamage = dame;
        this.attackspeed = as;
        this.movespeed = 1.0F;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }
    
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        target.hurtResistantTime = 0;
        if(target != null && attacker != null) {
        	target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(attacker, attacker), this.attackdamage);
        }
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