package gvclib.entity.living;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import gvclib.entity.EntityBBase;
import gvclib.entity.living.AI_EntityMoveS_Squad;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.ISoldier;
import gvclib.world.GVCExplosionBase;
/*import hmggirlfront.mod_GirlFront;
import hmggirlfront.items.ItemDollAttachment;
import hmggirlfront.items.ItemDollCN;
import hmggirlfront.items.ItemDollGunBase;
import hmggirlfront.network.GFClientMessageKeyPressed;
import hmggirlfront.network.GFPacketHandler;*/
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import objmodel.IModelCustom;

public class EntityGVC_PlayerDummy extends EntityGVCLivingBase implements IAnimals, ISoldier {
	
	
	public EntityGVC_PlayerDummy(World worldIn) {
		super(worldIn);
		//this.setSize(0.8F, 0.6F);
		this.biped = true;
	}

	private EntityLivingBase thrower;
    private String throwerName;
	
	protected void initEntityAI() {
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
		// this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(20D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	
	@Nullable
    public EntityLivingBase getThrower()
    {
        if (this.thrower == null && this.throwerName != null && !this.throwerName.isEmpty())
        {
            this.thrower = this.world.getPlayerEntityByName(this.throwerName);

            if (this.thrower == null && this.world instanceof WorldServer)
            {
                try
                {
                    Entity entity = ((WorldServer)this.world).getEntityFromUuid(UUID.fromString(this.throwerName));

                    if (entity instanceof EntityLivingBase)
                    {
                        this.thrower = (EntityLivingBase)entity;
                    }
                }
                catch (Throwable var2)
                {
                    this.thrower = null;
                }
            }
        }

        return this.thrower;
    }
	
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);
		
	}
	
	public boolean attackEntityFrom(DamageSource source, float par2)
    {
		if(this.getOwner() != null) {
			this.getOwner().attackEntityFrom(source, par2);
			if (this.getOwner().getRidingEntity() != null && this.getOwner().getRidingEntity() instanceof EntityMAVBase) {
				EntityMAVBase vehicle = (EntityMAVBase) this.getOwner().getRidingEntity();
				this.getOwner().dismountRidingEntity();
				this.getOwner().setPositionAndUpdate(vehicle.getMoveX(), vehicle.getMoveY(), vehicle.getMoveZ());
				if(vehicle.return_basepoint) {
					vehicle.setPositionAndUpdate(vehicle.getMoveX(), vehicle.getMoveY(), vehicle.getMoveZ());
				}
				if(this.getOwner()  instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) this.getOwner();
					if(this.world.isRemote)
						player.sendMessage(new TextComponentTranslation("Body is attacked!", new Object[0]));
				}
				if (!this.world.isRemote) {
					this.setDead();
				}
			}
		}
		return super.attackEntityFrom(source, par2);
    }
	
	protected void onDeathUpdate() {
		/*if(this.getOwner() != null) {
			if (!this.world.isRemote) {
				this.getOwner().setDead();
			}
		}*/
		if (!this.world.isRemote) {
			this.setDead();
		}
	}
	
	/**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
    	return false;
    }
	
	
	
	/*public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
		if(!player.world.isRemote) {
	//		this.setDead();
		}
		
        	return true;
    }*/
	 
	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	 public String sound;
	 public String modid;
	 public boolean reset = true;
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.biped = false;
		this.updateArmSwingProgress();
		
		if(this.getMoveX() == 0 && this.getMoveY() == 0 && this.getMoveZ() == 0){
			this.setMoveX((int)this.posX);
			this.setMoveY((int)this.posY);
			this.setMoveZ((int)this.posZ);
		}
		else
		{
			this.setPositionAndUpdate(this.getMoveX(), this.getMoveY(), this.getMoveZ());
		}
		
		float sp = 0.08F;
		int max = 15;
		int range = 30;
		int range2 = 30;
		newmove(this, 0, sp, 0, max, range, range2, 10);
		
	}
	
	public static void newmove(EntityGVCLivingBase entity, int id, float sp, float turnspeed, double max, double range1, double range2, double followrange) {
		
	}
	
	
}