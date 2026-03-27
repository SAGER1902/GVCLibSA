package gvclib.entity;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityB_Grapple extends EntityTNTBase {
	
	public EntityPlayer angler;
	protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID 
	= EntityDataManager.<Optional<UUID>>createKey(EntityB_Grapple.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	
	public EntityB_Grapple(World worldIn) {
		super(worldIn);
	}

	public EntityB_Grapple(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityB_Grapple(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}
	
	public static void func_189662_a(DataFixer p_189662_0_) {
		EntityBBase.func_189661_a(p_189662_0_, "Snowball");
	}
	
	public void setTamedBy(EntityPlayer player)
    {
        this.setOwnerId(player.getUniqueID());
    }
	@Nullable
    public UUID getOwnerId()
    {
        return (UUID)((Optional)this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
    }

    public void setOwnerId(@Nullable UUID p_184754_1_)
    {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
    }
	
	@Nullable
    public EntityLivingBase getOwner()
    {
        try
        {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }

    public boolean isOwner(EntityLivingBase entityIn)
    {
        return entityIn == this.getOwner();
    }
	
	protected void entityInit()
    {
    	//this.dataManager.register(Model, new String("gvclib:textures/entity/ATGrenade.obj"));
        //this.dataManager.register(Tex, new String("gvclib:textures/entity/ATGrenade.png"));
		this.dataManager.register(Model, new String("hmggvc:textures/entity/grapple.mqo"));
        this.dataManager.register(Tex, new String("hmggvc:textures/entity/grapple.png"));
        
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
    }
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();
        if (this.getOwnerId() == null)
        {
            compound.setString("OwnerUUID", "");
        }
        else
        {
            compound.setString("OwnerUUID", this.getOwnerId().toString());
        }
    }
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        String s;
        if (compound.hasKey("OwnerUUID", 8))
        {
            s = compound.getString("OwnerUUID");
        }
        else
        {
            String s1 = compound.getString("Owner");
            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
        }

        if (!s.isEmpty())
        {
            try
            {
                this.setOwnerId(UUID.fromString(s));
            }
            catch (Throwable var4)
            {
            }
        }
    }
	
	public boolean hit = false;
	 private int xTile;
	    private int yTile;
	    private int zTile;
	    private Block inTile;
	    private int inData;
	    private Entity targeten;
	    
	    public int playerid;
	    
	    public boolean canmove = false;
	
	    public void onUpdate()
	    {
	    	this.lastTickPosX = this.posX;
	        this.lastTickPosY = this.posY;
	        this.lastTickPosZ = this.posZ;
	        super.onUpdate();

	        //this.worldObj.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 0D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
	        
	        /*if(this.getThrower() == null || this.angler == null){
	        	//if(!this.world.isRemote)
	        	{
	        	this.setDead();
	        	}
	        }*/
	        //
	        if (this.throwableShake > 0)
	        {
	            --this.throwableShake;
	        }

	        if (this.inGround)
	        {
	            if (this.world.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile)
	            {
	                ++this.ticksInGround;

	                if (this.ticksInGround == 1200)
	                {
	                    this.setDead();
	                }

	                return;
	            }
	            this.onGround = true;
	            this.inGround = false;
	            this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
	            this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
	            this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
	            this.ticksInGround = 0;
	            this.ticksInAir = 0;
	        }
	        else
	        {
	        	if(hit){
	            	List llist = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()
	                		.expand(this.motionX, this.motionY, this.motionZ).grow(100.0D));
	                EntityLivingBase entitylivingbase = this.getThrower();
	                if(llist!=null){
	                    for (int lj = 0; lj < llist.size(); lj++) {
	                    	
	                    	Entity entity1 = (Entity)llist.get(lj);
	                    	if (entity1.canBeCollidedWith() && (entity1 != entitylivingbase))
	                        {
	                    		if (entity1 != null)
	                            {
	                    			if (entity1 instanceof EntityLivingBase && entity1 == this.targeten && entity1.getEntityId() == this.inData)
	                                {
	                    				double var4 = entity1.posX - this.posX;
	    								double var6 = (entity1.posY + (double) entity1.getEyeHeight())- this.posY;
	    								double var8 = entity1.posZ - this.posZ;
	    								this.motionX = (var4) * 0.03D * (time/10);
	                                    this.motionY = (var6) * 0.03D * (time/10);
	                                    this.motionZ = (var8) * 0.03D * (time/10);
	                                    break;
	                                }
	                            }
	                        }
	                    }
	                }
	            }
	            ++this.ticksInAir;
	        }

	        Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
	        Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
	        RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1);
	        vec3d = new Vec3d(this.posX, this.posY, this.posZ);
	        vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

	        if (raytraceresult != null)
	        {
	            vec3d1 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
	        }

	        Entity entity = null;
	        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D));
	        double d0 = 0.0D;
	        boolean flag = false;

	        for (int i = 0; i < list.size(); ++i)
	        {
	            Entity entity1 = (Entity)list.get(i);

	            if (entity1.canBeCollidedWith())
	            {
	                if (entity1 == this.ignoreEntity)
	                {
	                    flag = true;
	                }
	                else if (this.ticksExisted < 2 && this.ignoreEntity == null)
	                {
	                    this.ignoreEntity = entity1;
	                    flag = true;
	                }
	                else
	                {
	                    flag = false;
	                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
	                    RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

	                    if (raytraceresult1 != null)
	                    {
	                        double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);

	                        if (d1 < d0 || d0 == 0.0D)
	                        {
	                            entity = entity1;
	                            d0 = d1;
	                        }
	                    }
	                }
	            }
	        }

	        if (this.ignoreEntity != null)
	        {
	            if (flag)
	            {
	                this.ignoreTime = 2;
	            }
	            else if (this.ignoreTime-- <= 0)
	            {
	                this.ignoreEntity = null;
	            }
	        }

	        if (entity != null)
	        {
	            raytraceresult = new RayTraceResult(entity);
	        }
	        if (raytraceresult != null && raytraceresult.entityHit instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)raytraceresult.entityHit;

                if (this.getThrower() instanceof EntityPlayer && !((EntityPlayer)this.getThrower()).canAttackPlayer(entityplayer))
                {
                    raytraceresult = null;
                }
            }
            if (raytraceresult != null && raytraceresult.entityHit instanceof EntityLivingBase)
            {
            	EntityLivingBase en = (EntityLivingBase) raytraceresult.entityHit;
            	if(this.getThrower() != null && en.getControllingPassenger() == this.getThrower()) {
            		raytraceresult = null;
            	}
            	for(int m = 0; m < en.getPassengers().size(); ++m) {
            		if(this.getThrower() != null && en.getPassengers().get(m) == this.getThrower()) {
                		raytraceresult = null;
                	}
            	}
            	
            }
	        if (raytraceresult != null)
	        {
	            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK && this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL)
	            {
	                this.setPortal(raytraceresult.getBlockPos());
	            }
	            //else if(raytraceresult.entityHit != this.friend && raytraceresult.entityHit != this.getThrower())
	            else
	            {
	              //  if(!net.minecraftforge.common.ForgeHooks.onThrowableImpact(this, raytraceresult))
	                this.onImpact(raytraceresult);
	            }
	        }

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
	        float f1 = 0.99F;
	        float f2 = this.getGravityVelocity();

	        if (this.isInWater())
	        {
	            for (int j = 0; j < 4; ++j)
	            {
	                float f3 = 0.25F;
	                this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ, new int[0]);
	            }

	            f1 = 0.8F;
	        }

	        this.motionX *= (double)f1;
	        this.motionY *= (double)f1;
	        this.motionZ *= (double)f1;

	        if (!this.hasNoGravity())
	        {
	            this.motionY -= (double)f2 - this.gra;
	        }

	        this.setPosition(this.posX, this.posY, this.posZ);
	        
	        ++time;
	        if(time > 2400){
	        	if(!this.world.isRemote){
	        	this.setDead();
	        	}
	        }/**/
	        if(time > 100 && !canmove){
	        	if(!this.world.isRemote){
	        	this.setDead();
	        	}
	        }
	    }
		/**
		 * Called when this EntityThrowable hits a block or entity.
		 */
		protected void onImpact(RayTraceResult result) {
			canmove = true;
			Entity entity = result.entityHit;
			{
				this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
				if (result.entityHit != null) {
					this.targeten = result.entityHit;
	    			this.inData = result.entityHit.getEntityId();
	    			this.hit = true;
				}else{
					BlockPos blockpos = result.getBlockPos();
	    			if(blockpos != null)
	    			{
	    				this.xTile = blockpos.getX();
	                    this.yTile = blockpos.getY();
	                    this.zTile = blockpos.getZ();
	                    IBlockState iblockstate = this.world.getBlockState(blockpos);
	                    this.inTile = iblockstate.getBlock();
	                    this.inData = this.inTile.getMetaFromState(iblockstate);
	                    this.motionX = (double)((float)(result.hitVec.x - this.posX));
	                    this.motionY = (double)((float)(result.hitVec.y - this.posY));
	                    this.motionZ = (double)((float)(result.hitVec.z - this.posZ));
	                    float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
	                    this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
	                    this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
	                    this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
	                    //this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	                    this.inGround = true;

	                    if (iblockstate.getMaterial() != Material.AIR)
	                    {
	                        this.inTile.onEntityCollidedWithBlock(this.world, blockpos, iblockstate, this);
	                    }
	        	        
	    			}
				}
			
			}
		}

		@Override
		public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	
}