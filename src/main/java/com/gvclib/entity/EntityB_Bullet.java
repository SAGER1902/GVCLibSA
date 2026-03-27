package gvclib.entity;

import gvclib.mod_GVCLib;
import gvclib.entity.living.EntityVehicleBase;
import gvclib.entity.living.ISoldier;
import gvclib.event.GVCSoundEvent;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Loader;
import techguns.client.ClientProxy;
import techguns.packets.PacketSpawnParticle;
import techguns.damagesystem.TGExplosion;
import techguns.Techguns;
import techguns.TGPackets;

import ra2sa.entity.special.ISRSoldier;
import net.minecraft.scoreboard.Team;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPAncient;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityCanShoot;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityCanMelt;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityCanClimb;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityCanFly;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityCanSummon;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityCutomAttack;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityBodyParts;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPCrude;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPPreeminent;

public class EntityB_Bullet extends EntityBBase {
	public EntityB_Bullet(World worldIn) {
		super(worldIn);
	}

	public EntityB_Bullet(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityB_Bullet(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189662_a(DataFixer p_189662_0_) {
		EntityB_Bullet.func_189661_a(p_189662_0_, "EntityB_Bullet");
	}

	public double distance = -1d;
	public float laserPitch = 0.0f;
	public float laserYaw = 0.0f;
	public short maxTicks = 200;
	public float speed = 1.0f;
	
	public void onUpdate()
    {
        super.onUpdate();
        //if(this.smoke)
        if (!this.inGround) {
        	 if(this.world.getWorldTime() %2 == 0 && time > 5 && fly_sound) {
        		 if(!this.inGround)
        		 this.playSound(GVCSoundEvent.sound_hit, 0.5F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        	 }
        }
    }
	
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
		Entity entity = result.entityHit;
		if (entity != null){
			boolean damage_flag = true;
			boolean dead_flag = true;
			boolean ap = false;
			if(Loader.isModLoaded("srparasites")) {
				if ((result.entityHit instanceof EntityParasiteBase||result.entityHit instanceof EntityPAncient||result.entityHit instanceof EntityCanShoot||result.entityHit instanceof EntityCanMelt
					||result.entityHit instanceof EntityCanClimb||result.entityHit instanceof EntityCanFly||result.entityHit instanceof EntityCanSummon||result.entityHit instanceof EntityCutomAttack
					||result.entityHit instanceof EntityBodyParts||result.entityHit instanceof EntityPCrude||result.entityHit instanceof EntityPPreeminent) && 
					(this.getThrower() != null && this.getThrower() instanceof EntityParasiteBase)||(this.getThrower() != null && this.getThrower() instanceof ISRSoldier)&&
					(result.entityHit instanceof ISRSoldier)) {//停火
					dead_flag = false;
					damage_flag = false;
				}
			}
			if(result.entityHit instanceof EntityLivingBase && (this.getThrower() != null && this.getThrower() instanceof EntityLivingBase)) {
				EntityLivingBase hitentity = (EntityLivingBase)result.entityHit;
				EntityLivingBase shootentity = (EntityLivingBase)this.getThrower();
				Team hitteam = hitentity.getTeam();
				Team shootteam = shootentity.getTeam();
				if(shootteam == null && hitteam == null){
					if(hitentity instanceof ISoldier && shootentity instanceof ISoldier){
						dead_flag = false;
						damage_flag = false;
					}
				}else if(shootteam == hitteam){
					dead_flag = false;
					damage_flag = false;
				}
			}
			{
				int i = Bdamege;
				if (this.muteki) {
					result.entityHit.hurtResistantTime = 0;
				}
				if(this.bulletDameID == 1 || this.bulletDameID == 3){//穿透
					//i = (int) (i *0.75);
					if(i < 0) {
						if(result.entityHit instanceof EntityLivingBase) {
							EntityLivingBase en = (EntityLivingBase) result.entityHit;
							if(en.getHealth() < en.getMaxHealth()) {
								en.setHealth(en.getHealth() + (i * -1));
								GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(801, result.entityHit.getEntityId()));
		        			}
						}
					}else if(i > 0){
						if(damage_flag)result.entityHit.attackEntityFrom(GVCDamageSource.causeBulletAP(this, this.getThrower()), (float) i);
						ap = true;
					}else {
						
					}
				}else if(this.P_ID != 0){
					if(result.entityHit instanceof EntityLivingBase) {//药水效果
						EntityLivingBase en = (EntityLivingBase) result.entityHit;
						//en.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 10));
						en.addPotionEffect(new PotionEffect(Potion.getPotionById(this.P_ID), this.P_TIME, this.P_LEVEL));
					}
					if(i < 0) {
						if(result.entityHit instanceof EntityLivingBase) {
							EntityLivingBase en = (EntityLivingBase) result.entityHit;
							if(en.getHealth() < en.getMaxHealth()) {
								en.setHealth(en.getHealth() + (i * -1));//治疗
								GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(801, result.entityHit.getEntityId()));
		        			}
						}
					}else {
						if(damage_flag)result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);//1
					}
					
				}else {//常规
					if(i < 0) {
						if(result.entityHit instanceof EntityLivingBase) {
							EntityLivingBase en = (EntityLivingBase) result.entityHit;
							if(en.getHealth() < en.getMaxHealth()) {
								en.setHealth(en.getHealth() + (i * -1));//治疗
								GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(801, result.entityHit.getEntityId()));
		        			}
						}
					}else {
						if(damage_flag)result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) i);
						if(damage_flag && this.bulletDameID == 6)result.entityHit.setFire(this.P_TIME);
					}
				}
			}
			
			//this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 0D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
			if(result.entityHit instanceof EntityVehicleBase) {
				EntityVehicleBase vehicle = (EntityVehicleBase) result.entityHit;
				if(Bdamege > vehicle.normal_anti_bullet) {
					dead_flag = false;
				}
			}

			if (!this.world.isRemote && !ap) {
				if(dead_flag)this.setDead();
			}
			if(this.bulletDameID == 4 || this.exlevel >= 1) {
				if (!this.world.isRemote) {
					if(dead_flag)this.world.createExplosion(this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, false);
				}
			}
			
			if(Loader.isModLoaded("techguns")){
				if(this.usetgfx && damage_flag){
					if(this.Bdamege<=20F){
					Techguns.proxy.createFX("Impact_IncendiaryBullet", world, this.posX,this.posY,this.posZ, 0,0,0);
					}else{
					Techguns.proxy.createFX("GaussRifleImpact_Block", world,this.posX,this.posY,this.posZ,0,0,0);
					}
				}
			}
		}else {
			{
				if(!this.inGround) {
					if(this.bulletDameID == 4 || this.exlevel >= 1) {
						if (!this.world.isRemote) {
							this.world.createExplosion(this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, false);
							this.setDead();//爆炸子弹
						}
					}
				}
	            BlockPos blockpos = result.getBlockPos();
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
	            this.playSound(GVCSoundEvent.sound_hit, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	            this.inGround = true;
	           // this.arrowShake = 7;
	           // this.setIsCritical(false);

	            if (iblockstate.getMaterial() != Material.AIR)
	            {
	                this.inTile.onEntityCollidedWithBlock(this.world, blockpos, iblockstate, this);
	            }
	        }
		}
	}
	
	protected void onBreak(RayTraceResult result) {
		/*BlockPos blockpos = result.getBlockPos();
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        Block block2 = iblockstate.getBlock();
		if(block2 != null){
			if (block2 == Blocks.GLASS_PANE || block2 == Blocks.TALLGRASS){
				Block block = this.world.getBlockState(result.getBlockPos()).getBlock();
				int blockmeta = this.world.getBlockState(result.getBlockPos())
						.getBlock().getMetaFromState(this.world.getBlockState(result.getBlockPos()));
			//	BlockPos blockpos = result.getBlockPos();
				IBlockState iblock = this.world.getBlockState(result.getBlockPos()).getBlock().getStateFromMeta(blockmeta);

				block.onBlockDestroyedByPlayer(this.world, blockpos, iblock);
				block.dropBlockAsItem(this.world, blockpos, iblock, blockmeta);
				this.world.setBlockToAir(blockpos);
			}
		}
		*/
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	public void writeSpawnData(ByteBuf buffer){
		super.writeSpawnData(buffer);
		buffer.writeDouble(this.distance);
		buffer.writeFloat(this.laserPitch);
		buffer.writeFloat(this.laserYaw);
		buffer.writeShort(this.maxTicks);
	}
	public void readSpawnData(ByteBuf additionalData){
		super.readSpawnData(additionalData);
		this.distance=additionalData.readDouble();
		this.laserPitch = additionalData.readFloat();
		this.laserYaw = additionalData.readFloat();
		this.maxTicks = additionalData.readShort();
	}
}