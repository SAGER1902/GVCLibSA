package gvclib.entity;

import java.io.IOException;
import java.io.NotSerializableException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import gvclib.mod_GVCLib;
import gvclib.event.GVCSoundEvent;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import gvclib.util.SendEntitydata;
import gvclib.world.GVCExplosionBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.IModelCustom;

import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.Loader;
import techguns.client.ClientProxy;
import techguns.packets.PacketSpawnParticle;
import techguns.damagesystem.TGExplosion;
import techguns.Techguns;
import techguns.TGPackets;

import java.util.List;
import techguns.client.particle.TGFX;
import techguns.client.particle.TGParticleManager;
import techguns.client.particle.TGParticleSystem;
import techguns.util.EntityCondition;

public abstract class EntityBBase extends Entity implements IProjectile, IEntityAdditionalSpawnData
{
	private static final Predicate<Entity> ARROW_TARGETS = Predicates.and(EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity p_apply_1_)
        {
            return p_apply_1_.canBeCollidedWith();
        }
    });
	protected static final DataParameter<String> Model = 
    		EntityDataManager.<String>createKey(EntityBBase.class, DataSerializers.STRING);
    protected static final DataParameter<String> Tex = 
    		EntityDataManager.<String>createKey(EntityBBase.class, DataSerializers.STRING);
    public IModelCustom model = null;
    
    public String entity_model = null;
    public String entity_tex = null;
    
    protected static final DataParameter<String> ModelF = 
    		EntityDataManager.<String>createKey(EntityBBase.class, DataSerializers.STRING);
    protected static final DataParameter<String> TexF = 
    		EntityDataManager.<String>createKey(EntityBBase.class, DataSerializers.STRING);
    public IModelCustom modelf = null;
    
	protected static final DataParameter<String> ModelEX = 
    		EntityDataManager.<String>createKey(EntityBBase.class, DataSerializers.STRING);
    protected static final DataParameter<String> TexEX = 
    		EntityDataManager.<String>createKey(EntityBBase.class, DataSerializers.STRING);
			
	protected static final DataParameter<String> ModelTrail = 
    		EntityDataManager.<String>createKey(EntityBBase.class, DataSerializers.STRING);
    protected static final DataParameter<String> TexTrail = 
    		EntityDataManager.<String>createKey(EntityBBase.class, DataSerializers.STRING);
	
    protected int xTile;
    protected int yTile;
    protected int zTile;
    protected Block inTile;
    protected int inData;
    public boolean inGround;
    public int throwableShake;
    /** The entity that threw this throwable item. */
    private EntityLivingBase thrower;
    private String throwerName;
    protected int ticksInGround;
    protected int ticksInAir;
    public Entity ignoreEntity;
    protected int ignoreTime;
    
    public EntityLivingBase friend = null;
    public double gra = 0.029;//
    public int Bdamege = 5;
    
    /**falseで無敵時間発生*/
    public boolean muteki = false;
    
    public int time;
    public int timemax = 200;
    public boolean smoke = false;
    public boolean ex =false;
    public boolean exfire =false;
    public boolean exsmoke =false;
    public boolean exflash =false;
    public boolean exgas =false;
    public float exlevel = 0.0F;
    
    public int extime = 80;
    public boolean exinground = false;
    
    public int bulletDameID = 0;
    public int P_ID = 0;
    public int P_LEVEL = 5;
    public int P_TIME = 200;
    
    public double startposX = 0;
    public double startposY = 0;
    public double startposZ = 0;
    
    public double[] start_posX = new double[256];
    public double[] start_posY = new double[256];
    public double[] start_posZ = new double[256];
    
    //0916
    //public EntityLivingBase mitarget = null;
	public Entity mitarget = null;
    public int mitargetid = -1;
	private int timeInGround;
	private int arrowShake;
	
	//10/19
	public boolean fly_sound = true;
	
	//12/01
	public boolean spg_fly_sound = false;
	
	public boolean inwater = false;
	
    public EntityBBase(World worldIn)
    {
        super(worldIn);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.setSize(0.15F, 0.15F);
	}
	public boolean usetgfx = mod_GVCLib.gvclibsa_tgfx;
	
	public boolean lightstorm_sound = false;
	public boolean nuclear_sound = false;
	public boolean zwcj_sound = false;
	public boolean xf_sound = false;
	
	public int exp_sound_id = 0;
	public int trace_sound_id = 0;
	
	public int exp_id = 0;
	public int trace_id = 0;
	
	public int special_id = 0;
	
	//@Optional.Method(modid = "techguns")
	public void createTrailFX_SA() {
		if(Loader.isModLoaded("techguns")) {
			if(usetgfx){
				/*if(fps < fpslimit)*/{
					//mod_GVCLib.proxy.createFXOnEntitySA(this.getTexF(), this, 1f);
					Techguns.proxy.createFXOnEntity(this.getTexF(), this, 1f);
					//ClientProxyGVClib.get().createFXOnEntity(this.getTexF(), this);
				}
			}
		}
	}

    public EntityBBase(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);
    }

    public EntityBBase(World worldIn, EntityLivingBase throwerIn)
    {
        this(worldIn, throwerIn.posX, throwerIn.posY + (double)throwerIn.getEyeHeight() - 0.10000000149011612D, throwerIn.posZ);
        this.thrower = throwerIn;
        
        float f1 = 0.4F;
		motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f1;
		motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f1;
//		motionY = -MathHelper.sin(((rotationPitch + func_70183_g()) / 180F) * 3.141593F) * f1;
    }

    protected void entityInit()
    {
    	this.dataManager.register(Model, new String("gvclib:textures/entity/bulletnormal.obj"));
        this.dataManager.register(Tex, new String("gvclib:textures/entity/bulletnormal.png"));
        this.dataManager.register(ModelF, new String("gvclib:textures/entity/mflash.mqo"));
        this.dataManager.register(TexF, new String("gvclib:textures/entity/mflash.png"));
        this.dataManager.register(ModelEX, new String("gvclib:textures/entity/mflash.mqo"));
        this.dataManager.register(TexEX, new String("gvclib:textures/entity/mflash.png"));
        if (world != null) {
			isImmuneToFire = !world.isRemote;
		}
    }

    /**
     * Checks if the entity is in range to render.
     */
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength();

        if (Double.isNaN(d0))
        {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * mod_GVCLib.cfg_entity_render_range;
        return distance < d0 * d0;
    }
    
    public void setHeadingFromThrower(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy)
    {
        float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
        float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        this.setThrowableHeading((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        this.motionX += entityThrower.motionX;
        this.motionZ += entityThrower.motionZ;

        if (!entityThrower.onGround)
        {
            this.motionY += entityThrower.motionY;
        }
        if(!this.world.isRemote)if(time < 20)GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(321, this.getEntityId(), this));
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * (double)inaccuracy;
        y += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * (double)inaccuracy;
        z += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.ticksInGround = 0;
        
        if(!this.world.isRemote)if(time < 20)GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(321, this.getEntityId(), this));
    }

    /**
    * Set the position and rotation values directly without any clamping.
    */
   @SideOnly(Side.CLIENT)
   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
   {
	   if (!inGround) 
	   {
		   this.setPosition(x, y, z);
	       this.setRotation(yaw, pitch);
	   }
   }
    
    @SideOnly(Side.CLIENT)
    public void setVelocity(double x, double y, double z)
    {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (180D / Math.PI));
           // this.prevRotationYaw = this.rotationYaw;
           // this.prevRotationPitch = this.rotationPitch;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
        }
    }
	
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness()
    {
        return 1.0F;
    }
    
    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return true;
    }
    
    /**
     * Sets throwable heading based on an entity that's throwing it
     */
    public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy)
    {
        float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
        float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        this.motionX += entityThrower.motionX;
        this.motionZ += entityThrower.motionZ;

        if (!entityThrower.onGround)
        {
            this.motionY += entityThrower.motionY;
        }
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void shoot(double x, double y, double z, float velocity, float inaccuracy)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.ticksInGround = 0;
        
        if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
			for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
            {
				if(!this.world.isRemote)if(time < 20)GVCLPacketHandler.INSTANCE2.sendTo((new GVCLClientMessageKeyPressed(321, this.getEntityId(), this)), player);
            }
		}
    }

	public void setDead(){
		if(Loader.isModLoaded("techguns")) {
			if (!this.world.isRemote && this.usetgfx && this.trace_id == 0){
				/*if(mod_GVCLib.proxy.getFPS() < mod_GVCLib.gvclibsa_fps_limit)*/{
					if(this.exlevel>=2 && this.exlevel<10){
						if(this instanceof EntityB_Missile){
							TGPackets.network.sendToAllAround(new PacketSpawnParticle("GuidedMissileExplosion", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
						}else{
							if(this.exfire){
								TGPackets.network.sendToAllAround(new PacketSpawnParticle("LargeExplosionFire", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
							}else{
								if(this.world.rand.nextInt(3)==3){
									TGPackets.network.sendToAllAround(new PacketSpawnParticle("RocketExplosion4", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
								}else if(this.world.rand.nextInt(3)==2){
									TGPackets.network.sendToAllAround(new PacketSpawnParticle("RocketExplosion3", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
								}else if(this.world.rand.nextInt(3)==1){
									TGPackets.network.sendToAllAround(new PacketSpawnParticle("RocketExplosion2", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
								}else{
									TGPackets.network.sendToAllAround(new PacketSpawnParticle("RocketExplosion", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
								}
							}
						}
					}
					if(this.exlevel==1){
						/*if(this.Bdamege==10){
							TGPackets.network.sendToAllAround(new PacketSpawnParticle("FFExplosion", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
						}else*/{
							TGPackets.network.sendToAllAround(new PacketSpawnParticle("AAExplosion", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
						}
					}
					if(this.exlevel>10){
						TGPackets.network.sendToAllAround(new PacketSpawnParticle("NukeExplosion", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 150.0f));
					}
				}
			}
		}
		if(!this.usetgfx){
			if(!this.world.isRemote){
				EntityP_Particle particle = new EntityP_Particle( this.world);
				particle.setLocationAndAngles(this.posX,  this.posY,  this.posZ,  0, 0.0F);
				if(this.exlevel>=1 && this.trace_id == 0){
					particle.setModel("gvclib:textures/particle/exp.mqo");
					particle.setTex("gvclib:textures/particle/exp.png");
				}
				particle.startposX = this.posX;
				particle.startposY =  this.posY;
				particle.startposZ =  this.posZ;
				particle.timemax = 36;
				this.world.spawnEntity(particle);
			}
		}
		{
			if(!this.world.isRemote && this.trace_id != 0 && this.trace_id != 4){
				EntityP_Particle particle = new EntityP_Particle( this.world);
				particle.setLocationAndAngles(this.posX,  this.posY,  this.posZ,  0, 0.0F);
				if(this.trace_id ==7){
					particle.setModel("ra2sa:textures/particle/ylkl_exp.mqo");
					particle.setTex("ra2sa:textures/particle/ylkl_exp.png");
				}
				if(this.trace_id ==10){
					particle.setModel("ra2sa:textures/particle/zwcj.mqo");
					particle.setTex("ra2sa:textures/particle/zwcj.png");
				}
				if(this.trace_id ==11){
					particle.setModel("ra2sa:textures/particle/light_exp_big.mqo");
					particle.setTex("ra2sa:textures/particle/light_exp.png");
				}
				if(this.trace_id ==12){
					particle.setModel("ra2sa:textures/particle/nuclear_exp2.mqo");
					particle.setTex("ra2sa:textures/particle/nuclear_exp2.png");
				}
				if(this.trace_id ==13){
					particle.setModel("ra2sa:textures/particle/missile_exp.mqo");
					particle.setTex("ra2sa:textures/particle/missile_exp.png");
				}
				if(this.trace_id ==14){
					particle.setModel("ra2sa:textures/particle/shock.mqo");
					particle.setTex("ra2sa:textures/particle/shock.png");
				}
				particle.startposX = this.posX;
				particle.startposY =  this.posY;
				particle.startposZ =  this.posZ;
				particle.timemax = 36;
				this.world.spawnEntity(particle);
			}
		}
		{
			if(!this.world.isRemote && this.trace_id ==11){
				EntityP_Particle particle = new EntityP_Particle( this.world);
				particle.setLocationAndAngles(this.posX,  this.posY,  this.posZ,  0, 0.0F);
				particle.setModel("ra2sa:textures/particle/light/light.mqo");
				particle.setTex("ra2sa:textures/particle/light/light.png");
				particle.startposX = this.posX;
				particle.startposY =  this.posY;
				particle.startposZ =  this.posZ;
				particle.timemax = 36;
				this.world.spawnEntity(particle);
			}
		}
		super.setDead();
	}
	
	public int fps = mod_GVCLib.proxy.getFPS();
	public int fpslimit = mod_GVCLib.gvclibsa_fps_limit;
	 
	public int time2 = 0;
    public void onUpdate()
    {

		if(fps < fpslimit){
        	this.setDead();
		}
		
		//科技枪特效需要
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
		
		if(this.inGround){
		this.time2++;
		}
		if(!this.world.isRemote && this.trace_id == 4){
			EntityP_Particle particle = new EntityP_Particle( this.world);
			particle.setLocationAndAngles( this.posX,  this.posY,  this.posZ,  this.rotationYaw, 0.0F);
			particle.setModel("ra2sa:textures/mob/sr/trailrocket.mqo");
			particle.setTex("ra2sa:textures/mob/sr/trailrocket.png");
			particle.startposX =  this.posX;
			particle.startposY =  this.posY;
			particle.startposZ =  this.posZ;
			particle.timemax = 10;
			this.world.spawnEntity(particle);
		}
		if(!this.world.isRemote && (this instanceof EntityB_Missile||this instanceof EntityB_Shell && this.spg_fly_sound||this instanceof EntityB_Missile) && !usetgfx && this.trace_id == 0){
			EntityP_Particle particle = new EntityP_Particle( this.world);
			particle.setLocationAndAngles( this.posX,  this.posY,  this.posZ,  this.rotationYaw, 0.0F);
			particle.setModel("gvclib:textures/particle/trail.mqo");
			particle.setTex("gvclib:textures/particle/trail.png");
			particle.startposX =  this.posX;
			particle.startposY =  this.posY;
			particle.startposZ =  this.posZ;
			particle.timemax = 21;
			this.world.spawnEntity(particle);
		}
		if(!this.world.isRemote && this.trace_id == 7){
			EntityP_Particle particle = new EntityP_Particle( this.world);
			particle.setLocationAndAngles( this.posX,  this.posY,  this.posZ,  this.rotationYaw, 0.0F);
			particle.setModel("ra2sa:textures/particle/ylkl_trail.mqo");
			particle.setTex("ra2sa:textures/particle/ylkl_trail.png");
			particle.startposX =  this.posX;
			particle.startposY =  this.posY;
			particle.startposZ =  this.posZ;
			particle.timemax = 8;
			this.world.spawnEntity(particle);
		}
		
//		if(fps < 15)return;
		Chunk chunk = this.world.getChunkFromBlockCoords(new BlockPos(this.posX, this.posY, this.posZ));
		if (chunk.isEmpty())return;
		
		//試作
		//if(!this.world.isRemote)if(time < 20)GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(321, this.getEntityId(), this));
		if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
			for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
            {
				if(!this.world.isRemote)if(time < 20)GVCLPacketHandler.INSTANCE2.sendTo((new GVCLClientMessageKeyPressed(321, this.getEntityId(), this)), player);
            }
		}
		
    	if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f) * (180D / Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }

        BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        
        if (iblockstate.getMaterial() != Material.AIR)
        {
            AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);

            if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }

        if (this.inGround)
        {
            int j = block.getMetaFromState(iblockstate);

            if ((block != this.inTile || j != this.inData) && !this.world.collidesWithAnyBlock(this.getEntityBoundingBox().grow(0.05D)))
            {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.002F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.002F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.002F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
            else
            {
                ++this.ticksInGround;

                if (this.ticksInGround >= timemax/2)
                {
                    this.setDead();
                }
            }

            ++this.timeInGround;
        }
        else
        {
            this.timeInGround = 0;
            ++this.ticksInAir;
            Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
            vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
            vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (raytraceresult != null)
            {
                vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
            }

            Entity entity = this.findEntityOnPath(vec3d1, vec3d);

           // if (entity != null && entity != this.friend)
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
            	this.onImpact(raytraceresult);
            }

            

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            
            //試作
           // if(!this.world.isRemote)if(time < 20)GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(321, this.getEntityId(), this));
            if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
    			for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
                {
    				if(!this.world.isRemote)if(time < 20)GVCLPacketHandler.INSTANCE2.sendTo((new GVCLClientMessageKeyPressed(321, this.getEntityId(), this)), player);
                }
    		}
            
            float f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

            for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f4) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
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
            float f1 = 0.999F;
            float f2 = this.getGravityVelocity();

            if (this.isInWater() && !inwater)
            {
                for (int i = 0; i < 4; ++i)
                {
                    float f3 = 0.25F;
                    this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
                }

                f1 = 0.6F;
            }
            
            if (this.isWet())
            {
                this.extinguish();
            }

            this.motionX *= (double)f1;
            this.motionY *= (double)f1;
            this.motionZ *= (double)f1;

            if (!this.hasNoGravity())
            {
               // this.motionY -= 0.05000000074505806D;
            	if (this.isInWater() && inwater)
                {
            		this.motionY = 0F;
                }else {
                	this.motionY -= (double)f2 - this.gra;
                }
            }

            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
            //試作
            //if(!this.world.isRemote)if(time < 20)GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(321, this.getEntityId(), this));
            if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
    			for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
                {
    				if(!this.world.isRemote)if(time < 20)GVCLPacketHandler.INSTANCE2.sendTo((new GVCLClientMessageKeyPressed(321, this.getEntityId(), this)), player);
                }
    		}
        }
        ++time;
        if(time > timemax){
        	if(!this.world.isRemote){
        		//System.out.println(String.format("dead"));
        	this.setDead();
        	}
        }
		//流弹
		if (this.world.getWorldTime() % 10L == 0L && this.time > 8 && this.motionY < 0.0D && this.spg_fly_sound && !this.inGround)
		  playSound(GVCSoundEvent.sound_fall_shell, 12.0F, 1.0F); 
		//闪电风暴
		if (this.world.getWorldTime() % 10L == 0L && this.time > 8 && this.motionY < 0.0D && this.lightstorm_sound && !this.inGround)
		  playSound(GVCSoundEvent.lightingstorm_exp, 12.0F, 1.0F); 
	  
		if (this.time == 5 && this.motionY < 0.0D && this.lightstorm_sound)
		  playSound(GVCSoundEvent.lightingstorm_worn, 60.0F, 1.0F); 
		//战术核弹
		if (this.nuclear_sound && this.time2 ==1||this.nuclear_sound && this.isDead && this.time2 <1)
		  playSound(GVCSoundEvent.nuclear_exp, 10.0F, 1.0F); 
	  
		if (this.time == 5 && this.motionY < 0.0D && this.nuclear_sound)
		  playSound(GVCSoundEvent.nuclear_worn, 60.0F, 1.0F); 
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.03F;
    }

    @Nullable
    protected Entity findEntityOnPath(Vec3d start, Vec3d end)
    {
        Entity entity = null;
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D), ARROW_TARGETS);
        //List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D));
        double d0 = 0.0D;

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity1 = list.get(i);

            if (entity1 != this.getThrower() || this.ticksInAir >= 5)
            {
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
                RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);

                if (raytraceresult != null)
                {
                    double d1 = start.squareDistanceTo(raytraceresult.hitVec);

                    if (d1 < d0 || d0 == 0.0D)
                    {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        return entity;
    }
    
    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected abstract void onImpact(RayTraceResult result);
    
    
    public void hitbullet() {
    	{
    		double x = this.posX;
			double y = this.posY;
			double z = this.posZ;
			double han = 0.3;
			AxisAlignedBB aabb = (new AxisAlignedBB((double) (x - han), (double) (y - han),
					(double) (z - han),
					(double) (x + han), (double) (y + han), (double) (z + han)))
							.grow(han);
	        List llist2 = this.world.getEntitiesWithinAABB(Entity.class, aabb);
	        if(llist2!=null){
	            for (int lj = 0; lj < llist2.size(); lj++) {
	            	Entity entity1 = (Entity)llist2.get(lj);
	                {
	            		if ((entity1 instanceof IProjectile) && entity1 != null && entity1 != this && !(entity1 instanceof EntityTNTBase))
	                    {
	            			if(entity1 instanceof EntityBBase) {
	            				EntityBBase bullet = (EntityBBase) entity1;
	            				if(bullet.getThrower() != this.getThrower()) {
	            					if (!entity1.world.isRemote) 
									{
			            				GVCExplosionBase.ExplosionKai(this.getThrower(), this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, false,  this.ex);
			            				this.setDead();
										entity1.setDead();
									}
	            				}
	            			}else {
	            				if (!entity1.world.isRemote && this.getThrower() != null) 
								{
		            				GVCExplosionBase.ExplosionKai(this.getThrower(), this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, false,  this.ex);
		            				this.setDead();
									entity1.setDead();
								}
	            			}
	                    }
	                }
	            }
	        }
    	}
    }

    public static void func_189661_a(DataFixer fixer, String name)
    {
    }

    
 // Forge用
    //1.6.2より移植
 	@Override
 	public void writeSpawnData(ByteBuf data) {
 		// 通常の方法では速度が足りないので特別仕様
 		data.writeInt(thrower == null ? getEntityId() : thrower.getEntityId());
 		data.writeInt(Float.floatToIntBits((float)motionX));
 		data.writeInt(Float.floatToIntBits((float)motionY));
 		data.writeInt(Float.floatToIntBits((float)motionZ));
 	}
	
 	@Override
 	public void readSpawnData(ByteBuf data) {
		if(usetgfx){
			this.createTrailFX_SA();
			//this.createFXOnEntitySA2(this.getTexF(), this, 1f);
		//Techguns.proxy.createFXOnEntity(this.getTexF(), this, 1f);
		}
 		// 通常の方法では速度が足りないので特別仕様
 		int lthrower = data.readInt();
 		if (lthrower != 0) {
 			Entity lentity = world.getEntityByID(lthrower);
 			if (lentity instanceof EntityLivingBase) {
 				thrower = (EntityLivingBase)lentity;
 			}
 		}
 		motionX = (double)Float.intBitsToFloat(data.readInt());
 		motionY = (double)Float.intBitsToFloat(data.readInt());
 		motionZ = (double)Float.intBitsToFloat(data.readInt());
 		/*System.out.println(String.format("%1$.3f", motionX));
 		System.out.println(String.format("%1$.3f", motionY));
 		System.out.println(String.format("%1$.3f", motionZ));*/
// 		setVelocity(motionX, motionY, motionZ);
// 		System.out.println(String.format("read"));
 	}
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setInteger("xTile", this.xTile);
        compound.setInteger("yTile", this.yTile);
        compound.setInteger("zTile", this.zTile);
        ResourceLocation resourcelocation = (ResourceLocation)Block.REGISTRY.getNameForObject(this.inTile);
        compound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
        compound.setByte("shake", (byte)this.throwableShake);
        compound.setByte("inGround", (byte)(this.inGround ? 1 : 0));

        if ((this.throwerName == null || this.throwerName.isEmpty()) && this.thrower instanceof EntityPlayer)
        {
            this.throwerName = this.thrower.getName();
        }

        compound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
        
        compound.setString("Model", this.getModel());
        compound.setString("Tex", this.getTex());
        compound.setString("ModelF", this.getModelF());
        compound.setString("TexF", this.getTexF());
		
        compound.setString("ModelEX", this.getModelEX());
        compound.setString("TexEX", this.getTexEX());
        compound.setString("ModelTrail", this.getModelTrail());
        compound.setString("TexTrail", this.getTexTrail());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        this.xTile = compound.getInteger("xTile");
        this.yTile = compound.getInteger("yTile");
        this.zTile = compound.getInteger("zTile");

        if (compound.hasKey("inTile", 8))
        {
            this.inTile = Block.getBlockFromName(compound.getString("inTile"));
        }
        else
        {
            this.inTile = Block.getBlockById(compound.getByte("inTile") & 255);
        }

        this.throwableShake = compound.getByte("shake") & 255;
        this.inGround = compound.getByte("inGround") == 1;
        this.thrower = null;
        this.throwerName = compound.getString("ownerName");

        if (this.throwerName != null && this.throwerName.isEmpty())
        {
            this.throwerName = null;
        }

        this.thrower = this.getThrower();
        
        {
        	this.setModel(compound.getString("Model"));
        }
        {
        	this.setTex(compound.getString("Tex"));
        }
        {
        	this.setModelF(compound.getString("ModelF"));
        }
        {
        	this.setTexF(compound.getString("TexF"));
        }
        {
        	this.setModelEX(compound.getString("ModelEX"));
        }
        {
        	this.setTexEX(compound.getString("TexEX"));
        }
        {
        	this.setModelTrail(compound.getString("ModelTrail"));
        }
        {
        	this.setTexTrail(compound.getString("TexTrail"));
        }
    }

   
    public String getModel() {
		return ((this.dataManager.get(Model)));
	}
	public void setModel(String s) {
		this.dataManager.set(Model, String.valueOf(new String(s)));
	}
	public String getTex() {
		return ((this.dataManager.get(Tex)));
	}
	public void setTex(String s) {
		this.dataManager.set(Tex, String.valueOf(new String(s)));
	}
	
	public String getModelF() {
		return ((this.dataManager.get(ModelF)));
	}
	public void setModelF(String s) {
		this.dataManager.set(ModelF, String.valueOf(new String(s)));
	}
	public String getTexF() {
		return ((this.dataManager.get(TexF)));
	}
	public void setTexF(String s) {
		this.dataManager.set(TexF, String.valueOf(new String(s)));
	}
    
	public String getModelEX() {
		return ((this.dataManager.get(ModelEX)));
	}
	public void setModelEX(String s) {
		this.dataManager.set(ModelEX, String.valueOf(new String(s)));
	}
	public String getTexEX() {
		return ((this.dataManager.get(TexEX)));
	}
	public void setTexEX(String s) {
		this.dataManager.set(TexEX, String.valueOf(new String(s)));
	}
	
	public String getModelTrail() {
		return ((this.dataManager.get(ModelTrail)));
	}
	public void setModelTrail(String s) {
		this.dataManager.set(ModelTrail, String.valueOf(new String(s)));
	}
	public String getTexTrail() {
		return ((this.dataManager.get(TexTrail)));
	}
	public void setTexTrail(String s) {
		this.dataManager.set(TexTrail, String.valueOf(new String(s)));
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
    
    /** 指定した座礁のブロックをChunkLoaderとして起動する */
    /*public static boolean setBlockTicket(World world, int x, int y, int z) {
        return false;
    }
    
    /**
     * ChunkLoaderに実装するinterface<br>
     * worldがロードされた時に呼ばれる。trueを返すとChunkLoaderが始まる。
     * */
    /*public interface IChunkLoaderBlock {

        public boolean canLoad(World world, int x, int y, int z);

    }*/
}