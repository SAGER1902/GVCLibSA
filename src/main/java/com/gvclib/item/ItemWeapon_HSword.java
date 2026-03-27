package gvclib.item;

import java.util.List;

import com.google.common.collect.Multimap;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemWeapon_HSword extends ItemWeaponBase
{
    private final float attackdamage;
    private final Item.ToolMaterial material;
    public float dame;
    public double attackspeed = -1.5F;

    public ItemWeapon_HSword(float dame, double as, Item.ToolMaterial material)
    {
    	super(material);
        this.material = material;
        this.setMaxDamage(material.getMaxUses());
        this.attackdamage = dame;
        this.attackspeed = as;
        this.movespeed = 0.5F;
    }
    
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
        }
    }
    
    public void attackSwinging(World worldIn, EntityPlayer playerIn) {
			List<Entity> llist = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn,
					playerIn.getEntityBoundingBox()
							.expand(playerIn.motionX, playerIn.motionY, playerIn.motionZ)
							.grow(2.0D));
			if(playerIn != null){
				if (llist != null) {
					for (int lj = 0; lj < llist.size(); lj++) {
						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith()) {
							if (entity1 != null)
							{
								if (entity1 instanceof EntityLivingBase && entity1 != playerIn) {
									entity1.attackEntityFrom(DamageSource.causeMobDamage(playerIn), this.attackdamage);
								}
							}
						}
					}
				}
			}
    }
    public boolean onEntitySwing(EntityLivingBase entity, ItemStack par1ItemStack) {
    	World par2World = entity.world;
    	boolean linfinity = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, par1ItemStack) > 0;
    	int pluspower = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, par1ItemStack);
    	
    	boolean left = false;
    	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer playerIn = (EntityPlayer) entity;
    		if(playerIn.getCooldownTracker().getCooldown(par1ItemStack.getItem(), 0) == 0) {
    			playerIn.getCooldownTracker().setCooldown(this, 40);
    			this.attackSwinging(par2World, playerIn);
    		}
    		
    	}
    	return false;
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