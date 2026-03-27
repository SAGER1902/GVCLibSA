package gvclib.entity;

import java.util.List;

import gvclib.world.GVCExplosionBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import gvclib.mod_GVCLib;
public class EntityB_Missile extends EntityB_Shell {
	public EntityB_Missile(World worldIn) {
		super(worldIn);
	}

	public EntityB_Missile(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityB_Missile(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189662_a(DataFixer p_189662_0_) {
		EntityBBase.func_189661_a(p_189662_0_, "Snowball");
	}
	
	protected void entityInit()
    {
		//super.entityInit();
    	this.dataManager.register(Model, new String("gvclib:textures/entity/BulletMissile.obj"));
        this.dataManager.register(Tex, new String("gvclib:textures/entity/BulletMissile.png"));
        this.dataManager.register(ModelF, new String("gvclib:textures/entity/mflash.mqo"));
        this.dataManager.register(TexF, new String("gvclib:textures/entity/mflash.png"));
    }
	private double targetDeltaX;
    private double targetDeltaY;
    private double targetDeltaZ;
    public double speedd = 1;
    public boolean autoaim = true;
    public boolean aim_lock = false;
	public void onUpdate()
    {
        super.onUpdate();
        if(autoaim) {
        	if(this.mitargetid != -1 && time > 5) 
        	{
        		Entity en = this.world.getEntityByID(this.mitargetid);
        		if(en != null && en instanceof EntityLivingBase) {
        			this.mitarget((EntityLivingBase) en);
//        			System.out.println("lockonID");
        		}
        	}
        	else {
        	List llist = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()
            		.expand(this.motionX, this.motionY, this.motionZ).grow(100.0D));
            EntityLivingBase entitylivingbase = this.getThrower();
            if(llist!=null){
                for (int lj = 0; lj < llist.size(); lj++) {
                	
                	Entity entity1 = (Entity)llist.get(lj);
                	if (entity1.canBeCollidedWith() && (entity1 != entitylivingbase))
                    {
                		if ((entity1 != entitylivingbase && entity1 != this.friend)
                				&& entity1 != null)
                        {
                			if (entity1 instanceof EntityLivingBase)
                            {
                				if(time > 5){
                					//System.out.println("on");
                					double var4 = entity1.posX - this.posX;
    								double var6 = (entity1.posY + (double) entity1.getEyeHeight())- this.posY;
    								double var8 = entity1.posZ - this.posZ;
    								if(entity1 instanceof EntityLivingBase){
    		            				EntityLivingBase en = (EntityLivingBase) entity1;
    		            				//NBTTagCompound nbt = en.getEntityData();
    		            				//int time = nbt.getInteger("LockOnTime");
    		            				if(entity1 instanceof Entity_Flare){
    		            					this.mitarget((EntityLivingBase) entity1);
    		    							break;
    		            				}
    									if (entity1 == this.mitarget) {
    										this.mitarget((EntityLivingBase) entity1);
 //   										System.out.println("lockon");
    										break;
    		            				}
    								}
                                 }
                            }
                        }
                    }
                }
            }
            }
        }else {
        	if(this.getThrower() != null) {
        		EntityLivingBase entitylivingbase = this.getThrower();
        		Vec3d lock = entitylivingbase.getLookVec();
        		if(!aim_lock){
    				double x = 150D;
    				double ix = (int) (entitylivingbase.posX + lock.x * x);
					double iy = (int) (entitylivingbase.posY + 1.5 + lock.y * x);
					double iz = (int) (entitylivingbase.posZ + lock.z * x);
					this.manual(ix, iy, iz);
				}else {
					for (int x = 0; x < 100; ++x) {
						if (entitylivingbase.world != null && entitylivingbase.world
								.getBlockState(new BlockPos(entitylivingbase.posX + lock.x * x,
										entitylivingbase.posY + 1.5 + lock.y * x,
										entitylivingbase.posZ + lock.z * x))
								.getBlock() != Blocks.AIR) {
							double ix = (int) (entitylivingbase.posX + lock.x * x);
							double iy = (int) (entitylivingbase.posY + 1.5 + lock.y * x);
							double iz = (int) (entitylivingbase.posZ + lock.z * x);
							this.manual(ix, iy, iz);
							break;
						}
					}
				}
        	}
        }
        
        this.hitbullet();
    }
	
	public void mitarget(EntityLivingBase entity1){
//		entity1.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 10));
		double d6 = entity1.posX - this.posX;
        double d7 = (entity1.posY + (double) entity1.height/2 + 0.3D) - this.posY;
        double d4 = entity1.posZ - this.posZ;
        double d5 = (double)MathHelper.sqrt(d6 * d6 + d7 * d7 + d4 * d4);
        
        if (d5 == 0.0D)
        {
            this.targetDeltaX = 0.0D;
            this.targetDeltaY = 0.0D;
            this.targetDeltaZ = 0.0D;
        }
        else
        {
        	double speeded = 1.0D;
        	speeded = speedd;
            this.targetDeltaX = d6 / d5 * speeded;
            this.targetDeltaY = d7 / d5 * speeded;
            this.targetDeltaZ = d4 / d5 * speeded;
        }
		
		 this.targetDeltaX = MathHelper.clamp(this.targetDeltaX * 1.025D, -1.0D, 1.0D);
            this.targetDeltaY = MathHelper.clamp(this.targetDeltaY * 1.025D, -1.0D, 1.0D);
            this.targetDeltaZ = MathHelper.clamp(this.targetDeltaZ * 1.025D, -1.0D, 1.0D);
            this.motionX += (this.targetDeltaX - this.motionX) * 0.2D;
            this.motionY += (this.targetDeltaY - this.motionY) * 0.2D;
            this.motionZ += (this.targetDeltaZ - this.motionZ) * 0.2D;
	}
	
	public void manual(double x, double y, double z) {
		double d6 = x - this.posX;
        double d7 = (y + 0D) - this.posY;
        double d4 = z - this.posZ;
        double d5 = (double)MathHelper.sqrt(d6 * d6 + d7 * d7 + d4 * d4);
        
        if (d5 == 0.0D)
        {
            this.targetDeltaX = 0.0D;
            this.targetDeltaY = 0.0D;
            this.targetDeltaZ = 0.0D;
        }
        else
        {
        	double speeded = 1.0D;
        	speeded = speedd;
            this.targetDeltaX = d6 / d5 * speeded;
            this.targetDeltaY = d7 / d5 * speeded;
            this.targetDeltaZ = d4 / d5 * speeded;
        }
		
		    this.targetDeltaX = MathHelper.clamp(this.targetDeltaX * 2.025D, -1.0D, 1.0D);
            this.targetDeltaY = MathHelper.clamp(this.targetDeltaY * 2.025D, -1.0D, 1.0D);
            this.targetDeltaZ = MathHelper.clamp(this.targetDeltaZ * 2.025D, -1.0D, 1.0D);
            this.motionX += (this.targetDeltaX - this.motionX) * 0.5D;
            this.motionY += (this.targetDeltaY - this.motionY) * 0.5D;
            this.motionZ += (this.targetDeltaZ - this.motionZ) * 0.5D;
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
		//if (result.entityHit != this.friend && result.entityHit != this.getThrower()) 
		{
			boolean ap = false;
			if (!this.world.isRemote) {
				//this.worldObj.createExplosion(this, this.posX + 0, this.posY + 0, this.posZ + 0, 0.0F, false);
				//this.worldObj.createExplosion(this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, this.ex);
			}
			if(this.getThrower() != null)
			{
				 if(this.bulletDameID == 5) {
					 GVCExplosionBase.ExplosionKai(this.getThrower(), this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, true,  false);
				 }else {
					 GVCExplosionBase.ExplosionKai(this.getThrower(), this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, this.exfire,  this.ex);
				 }
			}
			if (result.entityHit != null) 
			//if (result.entityHit != this.friend && result.entityHit != this.getThrower() && result.entityHit != null)
			{
				int i = Bdamege;
				if (this.muteki) {
					result.entityHit.hurtResistantTime = 0;
				}
				if(this.bulletDameID == 1){
					result.entityHit.attackEntityFrom(GVCDamageSource.causeBulletAP(this, this.getThrower()), (float) i);
					ap = true;
				}else{
					result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) i);
				}
				if(this.exflash){
					if (result.entityHit instanceof EntityLivingBase)
                    {
                   	 EntityLivingBase en = (EntityLivingBase) result.entityHit;
                   	 en.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40, 1));
                    }
				}
			}

			if (!this.world.isRemote && !ap) {
				this.setDead();
			}
		}
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}