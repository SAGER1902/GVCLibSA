package gvclib.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityT_TNT extends EntityTNTBase {
	public EntityT_TNT(World worldIn) {
		super(worldIn);
	}

	public EntityT_TNT(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityT_TNT(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189662_a(DataFixer p_189662_0_) {
		EntityBBase.func_189661_a(p_189662_0_, "Snowball");
	}

	public void onUpdate()
    {
        super.onUpdate();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (!this.hasNoGravity())
        {
            this.motionY -= 0.03999999910593033D;
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
        
        {
        	this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

            for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {
                ;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
            {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
            {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
            {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        }

        ++time;
        if(time > extime || this.inWater){
        	this.Exp();
        }else
        {
        	if(this.exinground){
        		if(this.onGround){
        			this.Exp();
        		}
        	}
            this.handleWaterMovement();
            this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }
	
	private void Exp(){
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
        				entity1.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), Bdamege);
                    }
                    
                }
            }
        }
    	
    	if(!this.world.isRemote){
    	this.setDead();
    	this.explode();
    	}
	}
	
	private void explode()
    {
        float f = 4.0F;
        this.world.createExplosion(this, this.posX, this.posY + (double)(this.height / 16.0F), this.posZ, exlevel, ex);
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