package gvclib.entity;

import java.util.List;

import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import gvclib.world.GVCExplosionBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import gvclib.mod_GVCLib;
import net.minecraftforge.fml.common.Loader;

public class EntityT_Grenade extends EntityBBase {
	public EntityT_Grenade(World worldIn) {
		super(worldIn);
	}

	public EntityT_Grenade(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityT_Grenade(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189662_a(DataFixer p_189662_0_) {
		EntityBBase.func_189661_a(p_189662_0_, "Snowball");
	}

	protected void entityInit()
    {
		//super.entityInit();
    	this.dataManager.register(Model, new String("gvclib:textures/entity/dynamite.mqo"));
        this.dataManager.register(Tex, new String("gvclib:textures/entity/dynamite.png"));
        this.dataManager.register(ModelF, new String("gvclib:textures/entity/mflash.mqo"));
        this.dataManager.register(TexF, new String("gvclib:textures/entity/mflash.png"));
    }
	
	public void onUpdate()
    {
        //super.onUpdate();
		if (!this.world.isRemote)
        {
            this.setFlag(6, this.isGlowing());
        }

        this.onEntityUpdate();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (!this.hasNoGravity())
        {
            //this.motionY -= 0.03999999910593033D;
        	this.motionY -= 0.03999999910593033D - this.gra;
        }

        this.move(MoverType.SELF,this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= -0.5D;
        }

        ++time;
        if(this.exsmoke) {
        	if(time > timemax * 10){
        		if(!this.world.isRemote){
        			this.setDead();
                }
        	}else
        	if(time > timemax){
        		if(!this.world.isRemote){
                	this.explode();
                }
        	}
        }else {
        	if(time > timemax){
            	
                Entity entity = null;
                List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, 
                		this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow((double)exlevel*2));
                double d0 = 0.0D;

                for (int i = 0; i < list.size(); ++i)
                {
                    Entity entity1 = (Entity)list.get(i);

                    if (entity1.canBeCollidedWith())
                    {
                        if (entity1 == this.ignoreEntity)
                        {}
                        else if (this.ticksExisted < 2 && this.ignoreEntity == null)
                        {
                            this.ignoreEntity = entity1;
                        }
                        else
                        {
                            if (entity1 instanceof EntityLivingBase)
                            {
                            	double dx = Math.abs(entity1.posX - this.posX);
                        		double dz = Math.abs(entity1.posZ - this.posZ);
                        		float dd = (float)Math.sqrt((dx * dx) + (dz * dz));
                        		if(dd > Bdamege)dd = Bdamege;
                        		float dame = Bdamege - dd;
                				//entity1.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), Bdamege);
                            }
                            
                        }
                    }
                }
            	
            	if(!this.world.isRemote){
            	this.setDead();
            	this.explode();
            	}
            }else
            {
                this.handleWaterMovement();
                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }
    }
	
	private void explode()
    {
        //this.world.createExplosion(this, this.posX, this.posY + (double)(this.height / 16.0F), this.posZ, exlevel, ex);
        if(this.getThrower() != null) {
        	if(this.exsmoke) {
        		
        		
        		if(time %5 == 0)GVCLPacketHandler.INSTANCE2.sendToAll(
        				new GVCLClientMessageKeyPressed(0, this.getEntityId(), exlevel, this.posX, this.posY, this.posZ));
        		 
        		
        		
        		{
        			 Entity entity = null;
                     List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, 
                     		this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow((double)exlevel*2));
                     for (int i = 0; i < list.size(); ++i)
                     {
                         Entity entity1 = (Entity)list.get(i);

                         if (entity1.canBeCollidedWith())
                         {
                             if (entity1 == this.ignoreEntity)
                             {}
                             else if (this.ticksExisted < 2 && this.ignoreEntity == null)
                             {
                                 this.ignoreEntity = entity1;
                             }
                             else
                             {
                                 if (entity1 instanceof EntityLivingBase)
                                 {
                                	 EntityLivingBase en = (EntityLivingBase) entity1;
                                	 en.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 1));
                                	 if (!(entity1 instanceof EntityPlayer))
                                     {
                                		 en.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 1));
                                     }
                                 }
                             }
                         }
                     }
        		 }
        	}
        	else if(this.exflash){
        		GVCExplosionBase.ExplosionKai(this.getThrower(), this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, this.exfire,  this.ex);
        		{
       			 Entity entity = null;
                    List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, 
                    		this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow((double)exlevel*2));
                    for (int i = 0; i < list.size(); ++i)
                    {
                        Entity entity1 = (Entity)list.get(i);

                        if (entity1.canBeCollidedWith())
                        {
                            if (entity1 == this.ignoreEntity)
                            {}
                            else if (this.ticksExisted < 2 && this.ignoreEntity == null)
                            {
                                this.ignoreEntity = entity1;
                            }
                            else
                            {
                                if (entity1 instanceof EntityLivingBase)
                                {
                               	 EntityLivingBase en = (EntityLivingBase) entity1;
                               	 en.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40, 1));
                                }
                            }
                        }
                    }
       		 }
        	}
        	else if(this.exgas){
        		//  ItemStack itemstack = new ItemStack(Items.POTIONITEM);
                //  PotionType potiontype = PotionUtils.getPotionFromItem(itemstack);
        		EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
                entityareaeffectcloud.setOwner(this.getThrower());
                entityareaeffectcloud.setRadius(exlevel*2);
                entityareaeffectcloud.setDuration(timemax * 10);
                entityareaeffectcloud.setRadiusOnUse(-0.5F);
                entityareaeffectcloud.setWaitTime(10);
                entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
                //entityareaeffectcloud.setPotion(potiontype);
                entityareaeffectcloud.setPotion(PotionTypes.POISON);

                /*for (PotionEffect potioneffect : PotionUtils.getFullEffectsFromItem(p_190542_1_))
                {
                    entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
                }*/

              /*  NBTTagCompound nbttagcompound = p_190542_1_.getTagCompound();

                if (nbttagcompound != null && nbttagcompound.hasKey("CustomPotionColor", 99))
                {
                    entityareaeffectcloud.setColor(nbttagcompound.getInteger("CustomPotionColor"));
                }*/

                this.world.spawnEntity(entityareaeffectcloud);
                this.setDead();
        	}
        	else {
        		GVCExplosionBase.ExplosionKai(this.getThrower(), this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, this.exfire,  this.ex);
        	}
        }
    }
	
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}