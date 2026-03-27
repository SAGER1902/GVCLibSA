package gvclib.entity.living;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import gvclib.mod_GVCLib;
import gvclib.entity.living.AI_TankSet;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityMobVehicleBase;
import gvclib.entity.living.ISoldier;
import gvclib.entity.living.PL_TankMove;
import gvclib.event.GVCSoundEvent;
import gvclib.item.ItemWrench;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import gvclib.world.GVCExplosionBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class EntityMAVBase extends EntityMobVehicleBase
{

	private EntityLivingBase thrower;
    private String throwerName;
    
    public boolean return_basepoint = false;
	
    public EntityMAVBase(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 0.8F);
        this.setcanDespawn(1);
        this.setVehicleLock(true);
        
        this.ridding_invisible = true;
        this.ridding_sneakdismount = true;
        this.render_hud_information_1 = "Z-KEY:Get off";
        
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(20D);
    }
    
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
    	ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = !itemstack.isEmpty();
        if(!player.isRiding() && !player.isSneaking() && (!(itemstack.getItem() instanceof ItemWrench))) {
        	if(!this.world.isRemote) {
        		EntityGVC_PlayerDummy ent = new EntityGVC_PlayerDummy(world);
    			ent.setLocationAndAngles(player.posX + 0, player.posY + 0, player.posZ + 0, 0, 0.0F);
    			ent.setcanDespawn(1);
    			ent.setTamedBy(player);
    			
    			if(!player.getHeldItemMainhand().isEmpty())ent.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, player.getHeldItemMainhand());
    			if(!player.getHeldItemOffhand().isEmpty())ent.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, player.getHeldItemOffhand());
    			if(!player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty())ent.setItemStackToSlot(EntityEquipmentSlot.HEAD, player.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
    			if(!player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty())ent.setItemStackToSlot(EntityEquipmentSlot.CHEST, player.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
    			if(!player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty())ent.setItemStackToSlot(EntityEquipmentSlot.LEGS, player.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
    			if(!player.getItemStackFromSlot(EntityEquipmentSlot.FEET).isEmpty())ent.setItemStackToSlot(EntityEquipmentSlot.FEET, player.getItemStackFromSlot(EntityEquipmentSlot.FEET));
    			
    			world.spawnEntity(ent);
    			this.thrower = ent;
        	}
        	super.processInteract(player, hand);
        	this.setMoveX((int)player.posX);
        	this.setMoveY((int)player.posY);
        	this.setMoveZ((int)player.posZ);
        	return true;
        }else {
        	return true;
        }
    }
    
    /**
     * Dismounts this entity from the entity it is riding.
     */
    public void dismountRidingEntity()
    {
        Entity entity = this.getRidingEntity();
        super.dismountRidingEntity();

        if (entity != null && entity != this.getRidingEntity() && !this.world.isRemote)
        {
        	//if(!this.getRidingEntity().shouldDismountInWater(this))
            this.dismountEntity(entity);
        }
        if(!this.world.isRemote && getThrower() != null) {
			getThrower().setDead();
		}
    }
    
    protected void onDeathUpdate() {
		++this.deathTicks;
		int x = this.world.rand.nextInt(4);
		int y = this.world.rand.nextInt(5);
		int z = this.world.rand.nextInt(4);
		
		if(this.getControllingPassenger() != null) {
			if(this.getControllingPassenger()  instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) this.getControllingPassenger();
				if(this.world.isRemote)
					player.sendMessage(new TextComponentTranslation("Break Vehicle!", new Object[0]));
			}
			if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
				for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
	            {
					GVCLPacketHandler.INSTANCE2.sendTo((new GVCLClientMessageKeyPressed(444, this.getEntityId())), player);
					GVCLPacketHandler.INSTANCE.sendToServer((new GVCLMessageKeyPressed(444, this.getEntityId())));
	            }
			}else {
				if(this.getControllingPassenger() != null) {
					this.getControllingPassenger().setPositionAndUpdate(this.getMoveX(), this.getMoveY(), this.getMoveZ());
					this.getControllingPassenger().dismountRidingEntity();
				}
			}
		}
		
		if (this.deathTicks == 1 && !this.world.isRemote) {
			GVCExplosionBase.ExplosionKai(this, this, this.posX + 0, this.posY + 0, this.posZ + 0, 3, false,  false);
		}
		//if(this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityLivingBase) {
		/*if(this.getControllingPassenger() != null) {
			//EntityLivingBase entity = (EntityLivingBase) this.getControllingPassenger();
			this.getControllingPassenger().setPositionAndUpdate(this.getMoveX(), this.getMoveY(), this.getMoveZ());
			this.getControllingPassenger().dismountRidingEntity();
			
		}*/
		
		if(!this.world.isRemote && getThrower() != null) {
			getThrower().setDead();
		}
		if (this.deathTicks >= 20 && !this.world.isRemote && this.onGround) {
			GVCExplosionBase.ExplosionKai(this, this, this.posX + 0, this.posY + 0, this.posZ + 0, 3, false,  false);
			this.setDead();
		}
		if (this.deathTicks == 20 && !this.world.isRemote) {
			this.setDead();
		}
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
    
    
    public void onUpdate() {
		super.onUpdate();
    }

   
    
    public void move_mav() {
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
    	if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			if(this.getControllingPassenger() instanceof EntityPlayer)
			{
				EntityPlayer entitylivingbase = (EntityPlayer) this.getControllingPassenger();
				
				//entitylivingbase.height = 0.8F;
				{
					double x = this.posX;
					double y = this.posY;
					double z = this.posZ;
			  //      if (this.isAddedToWorld() && !this.world.isRemote) this.world.updateEntityWithOptionalForce(this, false); // Forge - Process chunk registration after moving.
			        float f = this.width / 2.0F;
			        float f12 = this.height;
			        entitylivingbase.setEntityBoundingBox(new AxisAlignedBB(x - (double)f, y, z - (double)f, x + (double)f, y + (double)f12, z + (double)f));
				}
				
				this.setcanDespawn(1);
				//entitylivingbase.rotationYawHead = this.rotationYaw;
				this.rotation = this.rotationYaw = this.renderYawOffset = entitylivingbase.rotationYawHead;
				this.rotationp = this.rotationPitch = entitylivingbase.rotationPitch;
				Vec3d looked = entitylivingbase.getLookVec();
				//PL_TankMove.movecar(entitylivingbase, this, sp, turnspeed);

				double xmove = 0;
				double zmove = 0;
				if (entitylivingbase.moveForward > 0.0F) {
					xmove -= MathHelper.sin(f1) * 0.05;
					zmove += MathHelper.cos(f1) * 0.05;
				}
				if (entitylivingbase.moveForward < 0.0F) {
					xmove -= MathHelper.sin(f1) * -0.05;
					zmove += MathHelper.cos(f1) * -0.05;
				}
				
				if (entitylivingbase.moveStrafing > 0.0F) {
					xmove -= MathHelper.sin(f1 - 1.57F) * 0.05;
					zmove += MathHelper.cos(f1 - 1.57F) * 0.05;
				}
				if (entitylivingbase.moveStrafing < 0.0F) {
					xmove -= MathHelper.sin(f1 - 1.57F) * -0.05;
					zmove += MathHelper.cos(f1 - 1.57F) * -0.05;
				}
				
				{
					this.motionX = xmove;
					this.motionZ = zmove;
					//entity.motionY = y;
					this.move(MoverType.PLAYER, this.motionX, this.motionY, this.motionZ);
				}
				boolean left = mod_GVCLib.proxy.leftclick();
				boolean right = mod_GVCLib.proxy.rightclick();
				boolean jump = mod_GVCLib.proxy.jumped();
				boolean kx = mod_GVCLib.proxy.keyx();
				boolean kg = mod_GVCLib.proxy.keyg();
				boolean kc = mod_GVCLib.proxy.keyc();
				boolean kz = mod_GVCLib.proxy.keyz();
				if (left) {
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(11, this.getEntityId()));
					this.server1 = true;
				}
				if (right) {
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(12, this.getEntityId()));
				}
				if (jump) {
					this.serverspace = true;
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(13, this.getEntityId()));
					GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(13, this.getEntityId()));
				}
				if (kx) {
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(14, this.getEntityId()));
				}
				if (kg) {
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(15, this.getEntityId()));
				}
				if (kc) {
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(16, this.getEntityId()));
				}
				if (kz) {
					this.serverz = true;
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(17, this.getEntityId()));
					GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(17, this.getEntityId()));
				}
				double ymove = 0.0265;
				if (this.serverspace) {
					ymove = 0.10;
					this.serverspace = false;
				}
				/*if(this.serverz) {
					ymove = -0.1;
					this.serverz = false;
				}*/
				if(entitylivingbase.isSneaking()) {
					ymove = -0.1;
				}
				{
					this.motionY = ymove;
					this.move(MoverType.PLAYER, this.motionX, this.motionY, this.motionZ);
				}
				
				
				if (this.server1) {
					
					{
		    			Vec3d lock = Vec3d.fromPitchYaw(entitylivingbase.rotationPitch, entitylivingbase.rotationYaw);
						int ix = 0;
						int iy = 0;
						int iz = 0;
						for(int x = 0; x < 120; ++x) {
							if (entitylivingbase.world
									.getBlockState(new BlockPos(entitylivingbase.posX + lock.x * x,
											entitylivingbase.posY + 1.5 + lock.y * x,
											entitylivingbase.posZ + lock.z * x))
									.getBlock() != Blocks.AIR) {
								ix = (int) (entitylivingbase.posX + lock.x * x);
								iy = (int) (entitylivingbase.posY + 1.5 + lock.y * x);
								iz = (int) (entitylivingbase.posZ + lock.z * x);
								entitylivingbase.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, 
										entitylivingbase.posX +lock.x * x,  entitylivingbase.posY + 1.5 +lock.y * x, entitylivingbase.posZ +lock.z * x, 0, 0, 0);
								break;
							}
						}
						if(ix != 0 && iz != 0 && iy != 0) {
							EntityLivingBase target = null;
							{
								int han = 1;
				            	AxisAlignedBB axisalignedbb2 = (new AxisAlignedBB((double)(ix-han), (double)(iy-han), (double)(iz-han), 
				            			(double)(ix + han), (double)(iy + han), (double)(iz+ han)))
				                		.grow(3);
				                List llist2 = entitylivingbase.world.getEntitiesWithinAABBExcludingEntity(entitylivingbase, axisalignedbb2);
				                if(llist2!=null){
				                    for (int lj = 0; lj < llist2.size(); lj++) {
				                    	
				                    	Entity entity1 = (Entity)llist2.get(lj);
				                    	if (entity1.canBeCollidedWith())
				                        {
				                    		if (entity1 instanceof IMob && entity1 != null)
				                            {
				                    			EntityLivingBase mob = (EntityLivingBase) entity1;
				                    			mob.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 400, 200));
				                    			target = mob;
				                            }
				                        }
				                    }
				                }
							}
			                if(target != null){
			                	int han = 160;
			                	double x = this.posX;
			                	double y = this.posY;
			                	double z = this.posZ;
			                	AxisAlignedBB axisalignedbb2 = (new AxisAlignedBB((double)(x-han), (double)(y-han), (double)(z-han), 
				            			(double)(x + han), (double)(y + han), (double)(z+ han)))
				                		.grow(1);
				                List llist2 = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb2);
				                if(llist2!=null){
				                    for (int lj = 0; lj < llist2.size(); lj++) {
				                    	
				                    	Entity entity1 = (Entity)llist2.get(lj);
				                    	if (entity1.canBeCollidedWith())
				                        {
				                    		if (entity1 instanceof ISoldier && entity1 != null)
				                            {
				                    			EntityGVCLivingBase mob = (EntityGVCLivingBase) entity1;
				                    			mob.targetentity = target;
				                    			mob.setMobMode(1);
				                    			mob.sneak = true;
				                    			mob.setattacktask(true);
				                    			{
				                    				mob.world.spawnParticle(EnumParticleTypes.NOTE, 
				                    						mob.posX,  mob.posY + mob.height + 1, mob.posZ, 0, 0, 0);
				                    			}
				                            }
				                        }
				                    }
				                }
			                }
			                
						}
		        		
		    		}
					
					
					this.server1 = false;
				}
				
				
			}//player
			else if(this.getControllingPassenger() instanceof EntityGVCLivingBase) {
				
			}
		}
    }
    
}