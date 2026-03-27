package gvclib.item;

import java.util.List;

import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemWeapon_Spear extends ItemWeaponBase// implements IReachItem
{
    private final float attackdamage;
    private final Item.ToolMaterial material;
    public float dame;
    public double attackspeed;
    public double reach;

    public ItemWeapon_Spear(float dame, double as, Item.ToolMaterial material)
    {
    	super(material);
        this.material = material;
        this.setMaxDamage(material.getMaxUses());
        this.attackdamage = dame;
        this.attackspeed = as;
        this.reach = 6;
        this.movespeed = 1.0F;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }
    
    /*public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(playerIn.isSneaking()){
			return new ActionResult(EnumActionResult.PASS, itemstack);
		}else
        {
            playerIn.setActiveHand(handIn);
            //this.attackSwinging(worldIn, playerIn);
            //playerIn.getCooldownTracker().setCooldown(this, 20);
            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
        }
    }*/
    
    public void onUpdate(ItemStack itemstack, World worldIn, Entity entity, int i, boolean flag) {
    	if(entity instanceof EntityPlayer && entity != null) {
    		EntityPlayer playerIn = (EntityPlayer) entity;
    		if(flag && playerIn.swingProgressInt == 1) {
    			if(itemstack != null) {
    				this.ItemReach(itemstack, worldIn, playerIn);
    			}
    		}
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

    public void ItemReach(ItemStack itemstack, World worldIn, EntityPlayer playerIn) {
    	//RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);
		//playerIn.playerc
		Vec3d pos = playerIn.getPositionEyes((float) this.reach);
		double d = this.reach;
		Vec3d lock = playerIn.getLookVec();
		Vec3d var8 = pos.addVector(lock.x * d, lock.y * d, lock.z * d);
		List<Entity> llist = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn,
				playerIn.getEntityBoundingBox()
						.expand(lock.x * d, lock.y * d, lock.z * d)
						.grow(0.15D));
		if(playerIn != null){
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						float bordersize = entity1.getCollisionBorderSize();
						AxisAlignedBB aabb = entity1.getEntityBoundingBox().expand(bordersize, bordersize, bordersize);
						RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);
						boolean en = false;
						if (entity1 != null)
						{
							if (entity1 instanceof EntityLivingBase && entity1 != playerIn) {
								if (aabb.contains(pos))
								{
									if (0.0D < d || d == 0.0D)
									{
										//pointedEntity = entity;
										en = true;
										d = 0.0D;
									}
								} else if (mop0 != null)
								{
									double d1 = pos.distanceTo(mop0.hitVec);
									
									if (d1 < d || d == 0.0D)
									{
										//pointedEntity = entity;
										en = true;
										d = d1;
									}
								}
								if(en) {
								entity1.attackEntityFrom(DamageSource.causeMobDamage(playerIn), this.attackdamage);
								break;
								}
							}
						}
					}
				}
			}
		}
    }
    
	/*@Override
	public float getExtendedReach(World world, EntityLivingBase entity, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return 8;
	}*/
    
}