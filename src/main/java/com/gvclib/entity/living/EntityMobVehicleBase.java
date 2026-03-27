package gvclib.entity.living;

import gvclib.mod_GVCLib;
import gvclib.entity.living.cnt.VehicleCNT_Boat;
import gvclib.entity.living.cnt.VehicleCNT_Tank;
import gvclib.entity.living.cnt.VehicleCNT_Tank_SPG;
import gvclib.entity.living.cnt.ai.AI_GetTarget_OnRidding;
import gvclib.event.GVCSoundEvent;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class EntityMobVehicleBase extends EntityVehicleBase implements IAnimals
{
	
	
    public EntityMobVehicleBase(World worldIn)
    {
        super(worldIn);
    }
    
    public int v_on = 0;
    public void onUpdate()
    {
        super.onUpdate();
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.getcanDespawn() == 0)
        {
            this.setDead();
        }
        if(this.getcanDespawn() == 0) {
			if(!(this.getControllingPassenger() instanceof EntityLivingBase)) {
				++despawn_count;
				if(despawn_count > 200 && !this.world.isRemote ) {
					this.setDead();
				}
			}else {
				despawn_count = 0;
			}
		}
        if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			if(this.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) this.getControllingPassenger();
			boolean kv = mod_GVCLib.proxy.keyv();
			if(v_on < 10)++v_on;
			if (kv) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(20, this.getEntityId()));
				if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
					for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
						GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(20, this.getEntityId()), playermp);//this.targetentity
					}
	    		}
				this.serverv = true;
			}
			 if(this.serverv && !this.world.isRemote) {
					if(v_on > 2){
						if(this.getBoosttime() == 1) {
							this.setBoosttime(0);
						}else {
							this.setBoosttime(1);
						}
					}
					v_on = 0;
					this.serverv = false;
			}
		}
		}
    }
    
    public boolean CanAttack(Entity entity){
		if (this.getControllingPassenger() instanceof EntityPlayer || this.getControllingPassenger() instanceof ISoldier) {
			if (entity instanceof IMob && ((EntityLivingBase) entity).getHealth() > 0.0F) {
				return true;
			} else {
				return false;
			}
		} else if (this.getControllingPassenger() instanceof IMob) {
			if ((entity instanceof ISoldier || entity instanceof EntityGolem || entity instanceof EntityVillager)
					&& ((EntityLivingBase) entity).getHealth() > 0.0F) {
				return true;
			} else if (entity instanceof EntityPlayer && ((EntityLivingBase) entity).getHealth() > 0.0F) {
				EntityPlayer entityplayer = (EntityPlayer) entity;
				ItemStack itemstack = entityplayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
				if (itemstack != null && itemstack.getItem() == Items.GOLDEN_HELMET) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
		    }
		} else {
			return false;
		}
    }
    
    public void typecar() {
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
    	if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			if(this.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) this.getControllingPassenger();
			this.setcanDespawn(1);
			this.rotation = entitylivingbase.rotationYawHead;
			this.rotationp = entitylivingbase.rotationPitch;
			Vec3d looked = entitylivingbase.getLookVec();
			PL_TankMove.movecar(entitylivingbase, this, sp, turnspeed);
			
			}//player
			else if(this.getControllingPassenger() instanceof EntityGVCLivingBase) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) this.getControllingPassenger();
				this.rotation = entitylivingbase.rotationYawHead;
				this.rotationp = entitylivingbase.rotationPitch;
				Vec3d looked = entitylivingbase.getLookVec();
				AI_EntityMoveTank.move(this, entitylivingbase, f1, sp, turnspeed, this.mob_min_range, this.mob_max_range);
				
			}
		}
		
		AI_TankSet.set2(this, GVCSoundEvent.sound_car, f1, sp, 0.1F);
		
		this.catchEntityBiped();
		
        
    }
    
    
    public void typeTurret() {
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
    	if(this.getMoveX() == 0) {
    		this.setMoveX((int)this.posX);
    		this.setMoveY((int)this.posY);
    		this.setMoveZ((int)this.posZ);
    	}

    	if(mod_GVCLib.cfg_turret_lockpoint) {
    		if(this.getMoveX() != 0 
    				&& this.world.getBlockState(
    						new BlockPos(this.getMoveX() + 0.5,this.getMoveY() - 1,this.getMoveZ() + 0.5)).getBlock() != Blocks.AIR) {
    			this.setPositionAndUpdate(this.getMoveX() + 0.5,this.getMoveY(),this.getMoveZ() + 0.5);
    		}else {
    			this.setPositionAndUpdate(this.getMoveX() + 0.5,this.posY,this.getMoveZ() + 0.5);
    		}
    	}

		if (this.world.getBlockState(new BlockPos(this.getMoveX() - 0.5, this.getMoveY() - 1, this.getMoveZ() + 0.5))
				.getBlock() == Blocks.AIR) {
			//this.motionY = 0.03;
		}
    	if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			if(this.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) this.getControllingPassenger();
			this.setcanDespawn(1);
			this.rotation  = entitylivingbase.rotationYawHead;
			this.rotationp = this.rotationPitch = entitylivingbase.rotationPitch;
			
			PL_TankMove.move2(entitylivingbase, this, 0, turnspeed);
			Vec3d looked = entitylivingbase.getLookVec();
			
			boolean left = mod_GVCLib.proxy.leftclick();
			boolean right = mod_GVCLib.proxy.rightclick();
			boolean jump = mod_GVCLib.proxy.jumped();
			boolean kx = mod_GVCLib.proxy.keyx();
			boolean kg = mod_GVCLib.proxy.keyg();
			boolean kc = mod_GVCLib.proxy.keyc();
			if (left) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(11, this.getEntityId()));
				this.server1 = true;
			}
			if (right) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(12, this.getEntityId()));
			}
			if (jump) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(13, this.getEntityId()));
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
			if(this.server1)
		    {
		    	{
    				if(cooltime >= ammo1){
    					this.counter1 = true;
    		            cooltime = 0;
    				}
    				if(this.weapon1true) {
    						this.weapon1active(looked, entitylivingbase);
        		    }
    			}
		    	this.server1 = false;
			}
			
			
			
			}//player
			else if(this.getControllingPassenger() instanceof EntityGVCLivingBase) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) this.getControllingPassenger();
				this.rotation  = entitylivingbase.rotationYawHead;
				this.rotationp = this.rotationPitch = entitylivingbase.rotationPitch;
				
				Vec3d looked = entitylivingbase.getLookVec();
				AI_EntityMoveTank.move(this, entitylivingbase, f1, 0, turnspeed, this.mob_min_range, this.mob_max_range);
				if(AI_EntityWeapon.getRange(entitylivingbase, this.mob_w1range, this.mob_w1range_max_y, this.mob_w1range_min_y)){
					
					if(entitylivingbase.targetentity != null) {
						double xxz = entitylivingbase.targetentity.posX - this.posX;
						double zzx = entitylivingbase.targetentity.posZ - this.posZ;
						double ddx = Math.abs(xxz);
						double ddz = Math.abs(zzx);
						if ((ddx > mob_min_range || ddz > mob_min_range)) {
							if(cooltime >= ammo1 && this.getRemain_L() > 0){
								this.counter1 = true;
					            cooltime = 0;
							}
							if(this.weapon1true) {
			    		    	this.weapon1activeMob(looked, entitylivingbase, this.weapon1type);
			    		    }
						}
					}
				}
			}
			else if (this.getControllingPassenger() instanceof EntityLivingBase) {
 				EntityLivingBase entitylivingbase = (EntityLivingBase)this.getControllingPassenger();
 				Vec3d looked = entitylivingbase.getLookVec();
 				if(AI_GetTarget_OnRidding.load_LivingBase(entitylivingbase, this, 3, this.mob_w1range, this.mob_w1range_max_y, this.mob_w1range_min_y, this.rotationYaw, 0, 180))
 				{
 					//if ((ddx > mob_min_range || ddz > mob_min_range)) 
 					{
						if(cooltime >= ammo1 && this.getRemain_L() > 0){
							this.counter1 = true;
				            cooltime = 0;
						}
						if(this.weapon1true) {
		    		    	this.weapon1activeMob(looked, this, this.weapon1type);
		    		    }
					}
     				entitylivingbase.rotationYawHead = this.rotation;
     				entitylivingbase.rotationPitch = this.rotationp;
 				}
 			}
		}
		
		this.catchEntityBiped();
		
        
    }
    
    public void typeTank() {
    	VehicleCNT_Tank.load(this, GVCSoundEvent.sound_tank);
    }
    
    public void typeSPG2() {
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
    	ridding_canzoom = false;
    	if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			if(this.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) this.getControllingPassenger();
			Vec3d looked = entitylivingbase.getLookVec();
				if (this.getFTMode() == 1) {
					spg_mode = true;
					
					{
						int range = 120;
						//double d5 = spg_yaw - this.posX;
						//double d7 = spg_picth - this.posZ;
						//float yawoffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						float yaw = entitylivingbase.rotationYawHead * (2 * (float) Math.PI / 360);
						float xx = 0;
						float zz = 0;
						if (entitylivingbase.moveForward > 0.0F && (spg_yaw < range && spg_picth < range)) {
							xx = xx + 1;
						}
						if (entitylivingbase.moveForward < 0.0F && (spg_yaw > -range && spg_picth > -range)) {
							xx = xx - 1;
						}
						if (entitylivingbase.moveStrafing > 0.0F && (spg_yaw < range && spg_picth < range)) {
							zz = zz + 1;
						}
						if (entitylivingbase.moveStrafing < 0.0F && (spg_yaw > -range && spg_picth > -range)) {
							zz = zz - 1;
						}
						spg_yaw -= MathHelper.sin(yaw) * xx;
						spg_picth += MathHelper.cos(yaw) * xx;
						spg_yaw -= MathHelper.sin(yaw - 1.57F) * zz;
						spg_picth += MathHelper.cos(yaw - 1.57F) * zz;
						double d5 = this.spg_yaw;
						double d7 = this.spg_picth;
						float yawoffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						this.rotation = yawoffset;
						this.rotationp = -40;
						BlockPos bp = world.getHeight(new BlockPos(spg_yaw + this.posX, this.posY, spg_picth + this.posZ));
						spg_y = bp.getY();
					}
				} else {
					spg_mode = false;
					spg_yaw = 0;
					spg_picth = 0;
					this.rotation = entitylivingbase.rotationYawHead;
					this.rotationp = entitylivingbase.rotationPitch;
					PL_TankMove.move2(entitylivingbase, this, sp, turnspeed);
				}
			
			boolean left = mod_GVCLib.proxy.leftclick();
			boolean right = mod_GVCLib.proxy.rightclick();
			boolean jump = mod_GVCLib.proxy.jumped();
			boolean kx = mod_GVCLib.proxy.keyx();
			boolean kg = mod_GVCLib.proxy.keyg();
			boolean kc = mod_GVCLib.proxy.keyc();
			if (left) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(11, this.getEntityId()));
				this.server1 = true;
			}
			if (right) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(12, this.getEntityId()));
			}
			if (jump) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(13, this.getEntityId()));
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
			
			if (serverx) {
				if (cooltime5 > 1 && this.getRemain_A() > 0) {
					if(this.getArmID_R() == 0 && this.weapon11true){
						this.setArmID_R(1);
					}else if(this.getArmID_R() == 1 && this.weapon12true){
						this.setArmID_R(2);
					}else
					{
						this.setArmID_R(0);
					}
					cooltime5 = 0;
					this.setRemain_A(this.getRemain_A() - 1);
					this.playSound(GVCSoundEvent.reload_shell, 1.0F, 1.0F);
				}
				{
					this.serverx = false;
				}
			}
			
			if(this.server1)
		    {
		    	{
    				if(cooltime >= ammo1){
    					this.counter1 = true;
    		            cooltime = 0;
    				}
    				if(this.weapon1true) {
    					if(this.getArmID_R() == 1 && this.weapon11true){
    						this.weapon11active(looked, entitylivingbase);
    					}else if(this.getArmID_R() == 2 && this.weapon12true){
    						this.weapon12active(looked, entitylivingbase);
    					}else {
    						this.weapon1active(looked, entitylivingbase);
    					}
        		    }
    			}
		    	this.server1 = false;
			}
		    
		    /*if(this.serverspace)
		    {
		    	{
    				if(cooltime2 >= ammo2){
    					this.counter2 = true;
    		            cooltime2 = 0;
    				}
    				if(this.weapon2true) {
        		    	this.weapon2active(looked, entitylivingbase);
        		    }
    			}
		    	this.serverspace = false;
			}*/
		    if (this.server2) {
				if (cooltime6 > 1 && this.getWeaponChange() > 0) {
					if(this.getFTMode() >= 1){
						this.setFTMode(0);
					}else
					{
						this.setFTMode(1);
					}
					cooltime6 = 0;
					this.setWeaponChange(this.getWeaponChange() - 1);
				}
		    	//this.setFTMode(1);
				{
					this.server2 = false;
				}
			}else {
				//this.setFTMode(0);
			}
			
			
			}//player
			else if(this.getControllingPassenger() instanceof EntityGVCLivingBase) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) this.getControllingPassenger();
				/*this.rotation  = entitylivingbase.rotationYawHead;
				this.rotationp = this.rotationPitch = entitylivingbase.rotationPitch;*/
				
			float f3 = (float) (entitylivingbase.rotationYawHead - this.rotation);// -180 ~ 0 ~ 180
			if(f3>0){//右移 +1
				if(f3>180F){
					this.rotation_turret-=this.turnspeed;
				}else{
					this.rotation_turret+=this.turnspeed;
				}
			}else if(f3<0){//左移 -1
				if(f3<-180F){
					this.rotation_turret+=this.turnspeed;
				}else{
					this.rotation_turret-=this.turnspeed;
				}
			}
			if(this.rotation>180||this.rotation<-180||f3<2&&f3>0||f3<0&&f3>-2){
				this.rotation_turret = entitylivingbase.rotationYawHead;
			}
			this.rotation = this.rotation_turret;//yaw
			
			float f2 = (float) (entitylivingbase.rotationPitch - this.rotationp);// -180 ~ 0 ~ 180
			if(this.rotationp_turret<entitylivingbase.rotationPitch){
				this.rotationp_turret+=1;
			}else if(this.rotationp_turret>entitylivingbase.rotationPitch){
				this.rotationp_turret-=1;
			}
			if(f2<2&&f2>0||f2<0&&f2>-2){
				this.rotationp_turret = entitylivingbase.rotationPitch;
			}
			this.rotationp = this.rotationPitch = this.rotationp_turret;//pitch
				
				Vec3d looked = entitylivingbase.getLookVec();
				AI_EntityMoveTank.move(this, entitylivingbase, f1, sp, turnspeed, this.mob_min_range, this.mob_max_range);
				if(AI_EntityWeapon.getRange(entitylivingbase, this.mob_w1range, this.mob_w1range_max_y, this.mob_w1range_min_y)){
					if(cooltime >= ammo1 && this.getRemain_L() > 0){
						this.counter1 = true;
			            cooltime = 0;
					}
					if(this.weapon1true) {
	    		    	this.weapon1activeMob(looked, entitylivingbase, 0);
	    		    }
				}
			}
		}
		
		AI_TankSet.set2(this, GVCSoundEvent.sound_tank, f1, sp, 0.1F);
		
		this.catchEntityBiped();
		
        
    }
    
    public void typeBoat() {
    	VehicleCNT_Boat.load(this, GVCSoundEvent.sound_car);
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
    }
    
    
    public void typeAir(SoundEvent sound) {
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
    	if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			if(this.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) this.getControllingPassenger();
			this.setcanDespawn(1);
			this.rotation  = entitylivingbase.rotationYawHead;
			this.rotationp = entitylivingbase.rotationPitch;
			Vec3d looked = entitylivingbase.getLookVec();
			if(this.getFTMode() != 1 && this.getFTMode() != 2){
				PL_AirCraftMove.movefighter(entitylivingbase, this, sp, turnspeed);
			}
			if(this.getFTMode() == 2){
				if (entitylivingbase.moveForward > 0.0F) {
					this.rotationPitch = this.rotationPitch + 0.2F;
				}
				if (entitylivingbase.moveForward < 0.0F) {
					this.rotationPitch = this.rotationPitch - 0.2F;
				}
				if (entitylivingbase.moveStrafing < 0.0F) {
					this.rotationYawHead = this.rotationYawHead + turnspeed* 1F;
					this.rotationYaw = this.rotationYaw + turnspeed* 1F;
				}
				if (entitylivingbase.moveStrafing > 0.0F) {
					this.rotationYawHead = this.rotationYawHead - turnspeed* 1F;
					this.rotationYaw = this.rotationYaw - turnspeed* 1F;
				}
			}
			
			
			boolean left = mod_GVCLib.proxy.leftclick();
			boolean right = mod_GVCLib.proxy.rightclick();
			boolean jump = mod_GVCLib.proxy.jumped();
			boolean kx = mod_GVCLib.proxy.keyx();
			boolean kg = mod_GVCLib.proxy.keyg();
			boolean kc = mod_GVCLib.proxy.keyc();
			if (left) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(11, this.getEntityId()));
				this.server1 = true;
			}
			if (right) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(12, this.getEntityId()));
			}
			if (jump) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(13, this.getEntityId()));
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
			
			 if (this.serverg) {
					if (cooltime6 > 1 && this.getWeaponChange() > 0) {
						if(this.getFTMode() >= 2){
							this.setFTMode(1);
						}else if(this.getFTMode() == 1){
								this.setFTMode(0);
						}else
						{
							this.setFTMode(1);
						}
						cooltime6 = 0;
						this.setWeaponChange(this.getWeaponChange() - 1);
					}
					{
						this.serverg = false;
					}
				}
			 if (this.serverc) {
					if (cooltime6 > 1 && this.getWeaponChange() > 0) {
						if(this.getFTMode() >= 2){
							this.setFTMode(0);
						}else
						{
							this.setFTMode(2);
						}
						cooltime6 = 0;
						this.setWeaponChange(this.getWeaponChange() - 1);
					}
					{
						this.serverc = false;
					}
				}
			
			if (serverx) {
				if (cooltime5 > 1 && this.getRemain_A() > 0) {
					if(this.getArmID_R() == 0 && this.weapon11true){
						this.setArmID_R(1);
					}else if(this.getArmID_R() == 1 && this.weapon12true){
						this.setArmID_R(2);
					}else
					{
						this.setArmID_R(0);
					}
					cooltime5 = 0;
					this.setRemain_A(this.getRemain_A() - 1);
					this.playSound(GVCSoundEvent.reload_shell, 1.0F, 1.0F);
				}
				{
					this.serverx = false;
				}
			}
			
			if(this.server1)
		    {
				if(weapon1key == 0){
    				if(cooltime >= ammo1){
    					this.counter1 = true;
    		            cooltime = 0;
    				}
    				if(this.weapon1true) {
    					if(this.getArmID_R() == 1 && this.weapon11true){
    						this.weapon11active(looked, entitylivingbase);
    					}else if(this.getArmID_R() == 2 && this.weapon12true){
    						this.weapon12active(looked, entitylivingbase);
    					}else {
    						this.weapon1active(looked, entitylivingbase);
    					}
        		    }
    			}
				if(weapon2key == 0){
    				if(cooltime2 >= ammo2){
    					this.counter2 = true;
    		            cooltime2 = 0;
    				}
    				if(this.weapon2true) {
        		    	this.weapon2active(looked, entitylivingbase);
        		    }
    			}
		    	this.server1 = false;
			}
		    
		    if(this.serverspace)
		    {
		    	if(weapon1key == 2){
    				if(cooltime >= ammo1){
    					this.counter1 = true;
    		            cooltime = 0;
    				}
    				if(this.weapon1true) {
    					if(this.getArmID_R() == 1 && this.weapon11true){
    						this.weapon11active(looked, entitylivingbase);
    					}else if(this.getArmID_R() == 2 && this.weapon12true){
    						this.weapon12active(looked, entitylivingbase);
    					}else {
    						this.weapon1active(looked, entitylivingbase);
    					}
        		    }
    			}
		    	if(weapon2key == 2){
    				if(cooltime2 >= ammo2){
    					this.counter2 = true;
    		            cooltime2 = 0;
    				}
    				if(this.weapon2true) {
        		    	this.weapon2active(looked, entitylivingbase);
        		    }
    			}
		    	this.serverspace = false;
			}
			
			
			
			}//player
			else if(this.getControllingPassenger() instanceof EntityGVCLivingBase) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) this.getControllingPassenger();
				this.rotation  = entitylivingbase.rotationYawHead;
				this.rotationp = entitylivingbase.rotationPitch;
				Vec3d looked = entitylivingbase.getLookVec();
				AI_EntityMoveAirCraft.movefighter(this, entitylivingbase, f1, sp, turnspeed, this.mob_min_range, this.mob_max_range, this.mob_min_height);
				if(AI_EntityWeapon.getRange(entitylivingbase, this.mob_w1range, this.mob_w1range_max_y, this.mob_w1range_min_y)){
					///System.out.println(String.format("input"));
					if(cooltime >= ammo1 && this.getRemain_L() > 0){
						this.counter1 = true;
			            cooltime = 0;
					}
					if(this.weapon1true) {
	    		    	this.weapon1activeMob(looked, entitylivingbase, this.weapon1type);
	    		    }
				}
				if(AI_EntityWeapon.getRange(entitylivingbase, this.mob_w2range, this.mob_w2range_max_y, this.mob_w2range_min_y)){
					if(cooltime2 >= ammo2 && this.getRemain_R() > 0){
						this.counter2 = true;
			            cooltime2 = 0;
					}
					if(this.weapon2true) {
	    		    	this.weapon2activeMob(looked, entitylivingbase, this.weapon2type);
	    		    }
				}
			}
		}
		
    	AI_AirCraftSet.setfighter(this, sound, f1, sp, 0.1F);
		
		this.catchEntityBiped();
		
        
    }
    
    public void typeHeli(SoundEvent sound) {
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
    	if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			if(this.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) this.getControllingPassenger();
			this.setcanDespawn(1);
			this.rotation  = entitylivingbase.rotationYawHead;
			this.rotationp = entitylivingbase.rotationPitch;
			Vec3d looked = entitylivingbase.getLookVec();
			if(this.getFTMode() != 1){
				PL_AirCraftMove.moveheli(entitylivingbase, this, sp, turnspeed);
			}else {
				PL_AirCraftMove.moveheligunner(entitylivingbase, this, sp, turnspeed);
			}
			
			boolean left = mod_GVCLib.proxy.leftclick();
			boolean right = mod_GVCLib.proxy.rightclick();
			boolean jump = mod_GVCLib.proxy.jumped();
			boolean kx = mod_GVCLib.proxy.keyx();
			boolean kg = mod_GVCLib.proxy.keyg();
			boolean kc = mod_GVCLib.proxy.keyc();
			if (left) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(11, this.getEntityId()));
				this.server1 = true;
			}
			if (right) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(12, this.getEntityId()));
			}
			if (jump) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(13, this.getEntityId()));
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
			
			 if (this.serverg) {
					if (cooltime6 > 1 && this.getWeaponChange() > 0) {
						if(this.getFTMode() >= 1){
							this.setFTMode(0);
						}else
						{
							this.setFTMode(1);
						}
						cooltime6 = 0;
						this.setWeaponChange(this.getWeaponChange() - 1);
					}
					{
						this.serverg = false;
					}
				}
			
			if (serverx) {
				if (cooltime5 > 1 && this.getRemain_A() > 0) {
					if(this.getArmID_R() == 0 && this.weapon11true){
						this.setArmID_R(1);
					}else if(this.getArmID_R() == 1 && this.weapon12true){
						this.setArmID_R(2);
					}else
					{
						this.setArmID_R(0);
					}
					cooltime5 = 0;
					this.setRemain_A(this.getRemain_A() - 1);
					this.playSound(GVCSoundEvent.reload_shell, 1.0F, 1.0F);
				}
				{
					this.serverx = false;
				}
			}
			
			if(this.server1)
		    {
				if(weapon1key == 0){
    				if(cooltime >= ammo1){
    					this.counter1 = true;
    		            cooltime = 0;
    				}
    				if(this.weapon1true) {
    					if(this.getArmID_R() == 1 && this.weapon11true){
    						this.weapon11active(looked, entitylivingbase);
    					}else if(this.getArmID_R() == 2 && this.weapon12true){
    						this.weapon12active(looked, entitylivingbase);
    					}else {
    						this.weapon1active(looked, entitylivingbase);
    					}
        		    }
    			}
				if(weapon2key == 0){
    				if(cooltime2 >= ammo2){
    					this.counter2 = true;
    		            cooltime2 = 0;
    				}
    				if(this.weapon2true) {
        		    	this.weapon2active(looked, entitylivingbase);
        		    }
    			}
		    	this.server1 = false;
			}
		    
		    if(this.serverspace)
		    {
		    	if(weapon1key == 2){
    				if(cooltime >= ammo1){
    					this.counter1 = true;
    		            cooltime = 0;
    				}
    				if(this.weapon1true) {
    					if(this.getArmID_R() == 1 && this.weapon11true){
    						this.weapon11active(looked, entitylivingbase);
    					}else if(this.getArmID_R() == 2 && this.weapon12true){
    						this.weapon12active(looked, entitylivingbase);
    					}else {
    						this.weapon1active(looked, entitylivingbase);
    					}
        		    }
    			}
		    	if(weapon2key == 2){
    				if(cooltime2 >= ammo2){
    					this.counter2 = true;
    		            cooltime2 = 0;
    				}
    				if(this.weapon2true) {
        		    	this.weapon2active(looked, entitylivingbase);
        		    }
    			}
		    	this.serverspace = false;
			}
			
			
			
			}//player
			else if(this.getControllingPassenger() instanceof EntityGVCLivingBase) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) this.getControllingPassenger();
				this.rotation  = entitylivingbase.rotationYawHead;
				//this.rotationp = this.rotationPitch = entitylivingbase.rotationPitch;
				
				Vec3d looked = entitylivingbase.getLookVec();
				AI_EntityMoveAirCraft.moveheli(this, entitylivingbase, f1, sp, turnspeed, this.mob_min_range, this.mob_max_range, this.mob_min_height);
				if(AI_EntityWeapon.getRange(entitylivingbase, this.mob_w1range, this.mob_w1range_max_y, this.mob_w1range_min_y)){
					if(cooltime >= ammo1 && this.getRemain_L() > 0){
						this.counter1 = true;
			            cooltime = 0;
					}
					if(this.weapon1true) {
	    		    	this.weapon1activeMob(looked, entitylivingbase, this.weapon1type);
	    		    }
				}
				if(AI_EntityWeapon.getRange(entitylivingbase, this.mob_w2range, this.mob_w2range_max_y, this.mob_w2range_min_y)){
					if(cooltime2 >= ammo2 && this.getRemain_R() > 0){
						this.counter2 = true;
			            cooltime2 = 0;
					}
					if(this.weapon2true) {
	    		    	this.weapon2activeMob(looked, entitylivingbase, this.weapon2type);
	    		    }
				}
			}
		}
		
    	if(this.getFTMode() != 1){
			AI_AirCraftSet.setheli(this, sound, f1, sp, 0.1F);
		}else {
			AI_AirCraftSet.setheligunner(this, sound, f1, sp, 0.1F);
		}
		
		this.catchEntityBiped();
		
        
    }
    
    public void typeHeli_NEW(SoundEvent sound) {
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
    	if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			if(this.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) this.getControllingPassenger();
			this.setcanDespawn(1);
			this.rotation  = entitylivingbase.rotationYawHead;
			this.rotationp = this.rotationPitch = entitylivingbase.rotationPitch;
			
			Vec3d looked = entitylivingbase.getLookVec();
			if(this.getFTMode() != 1){
				PL_AirCraftMove.moveheli_NEW(entitylivingbase, this, sp, turnspeed);
			}else {
				PL_AirCraftMove.moveheligunner(entitylivingbase, this, sp, turnspeed);
			}
			
			boolean left = mod_GVCLib.proxy.leftclick();
			boolean right = mod_GVCLib.proxy.rightclick();
			boolean jump = mod_GVCLib.proxy.jumped();
			boolean kx = mod_GVCLib.proxy.keyx();
			boolean kg = mod_GVCLib.proxy.keyg();
			boolean kc = mod_GVCLib.proxy.keyc();
			if (left) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(11, this.getEntityId()));
				this.server1 = true;
			}
			if (right) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(12, this.getEntityId()));
			}
			if (jump) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(13, this.getEntityId()));
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
			
			 if (this.serverg) {
					if (cooltime6 > 1 && this.getWeaponChange() > 0) {
						if(this.getFTMode() >= 1){
							this.setFTMode(0);
						}else
						{
							this.setFTMode(1);
						}
						cooltime6 = 0;
						this.setWeaponChange(this.getWeaponChange() - 1);
					}
					{
						this.serverg = false;
					}
				}
			
			if (serverx) {
				if (cooltime5 > 1 && this.getRemain_A() > 0) {
					if(this.getArmID_R() == 0 && this.weapon11true){
						this.setArmID_R(1);
					}else if(this.getArmID_R() == 1 && this.weapon12true){
						this.setArmID_R(2);
					}else
					{
						this.setArmID_R(0);
					}
					cooltime5 = 0;
					this.setRemain_A(this.getRemain_A() - 1);
					this.playSound(GVCSoundEvent.reload_shell, 1.0F, 1.0F);
				}
				{
					this.serverx = false;
				}
			}
			
			if(this.server1)
		    {
				if(weapon1key == 0){
    				if(cooltime >= ammo1){
    					this.counter1 = true;
    		            cooltime = 0;
    				}
    				if(this.weapon1true) {
    					if(this.getArmID_R() == 1 && this.weapon11true){
    						this.weapon11active(looked, entitylivingbase);
    					}else if(this.getArmID_R() == 2 && this.weapon12true){
    						this.weapon12active(looked, entitylivingbase);
    					}else {
    						this.weapon1active(looked, entitylivingbase);
    					}
        		    }
    			}
				if(weapon2key == 0){
    				if(cooltime2 >= ammo2){
    					this.counter2 = true;
    		            cooltime2 = 0;
    				}
    				if(this.weapon2true) {
        		    	this.weapon2active(looked, entitylivingbase);
        		    }
    			}
		    	this.server1 = false;
			}
			if(this.server2)
		    {
				if(weapon1key == 1){
    				if(cooltime >= ammo1){
    					this.counter1 = true;
    		            cooltime = 0;
    				}
    				if(this.weapon1true) {
    					if(this.getArmID_R() == 1 && this.weapon11true){
    						this.weapon11active(looked, entitylivingbase);
    					}else if(this.getArmID_R() == 2 && this.weapon12true){
    						this.weapon12active(looked, entitylivingbase);
    					}else {
    						this.weapon1active(looked, entitylivingbase);
    					}
        		    }
    			}
				if(weapon2key == 1){
    				if(cooltime2 >= ammo2){
    					this.counter2 = true;
    		            cooltime2 = 0;
    				}
    				if(this.weapon2true) {
        		    	this.weapon2active(looked, entitylivingbase);
        		    }
    			}
		    	this.server2 = false;
			}
		    
		    if(this.serverspace)
		    {
		    	if(weapon1key == 2){
    				if(cooltime >= ammo1){
    					this.counter1 = true;
    		            cooltime = 0;
    				}
    				if(this.weapon1true) {
    					if(this.getArmID_R() == 1 && this.weapon11true){
    						this.weapon11active(looked, entitylivingbase);
    					}else if(this.getArmID_R() == 2 && this.weapon12true){
    						this.weapon12active(looked, entitylivingbase);
    					}else {
    						this.weapon1active(looked, entitylivingbase);
    					}
        		    }
    			}
		    	if(weapon2key == 2){
    				if(cooltime2 >= ammo2){
    					this.counter2 = true;
    		            cooltime2 = 0;
    				}
    				if(this.weapon2true) {
        		    	this.weapon2active(looked, entitylivingbase);
        		    }
    			}
		    	this.serverspace = false;
			}
			
			
			
			}//player
			else if(this.getControllingPassenger() instanceof EntityGVCLivingBase) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) this.getControllingPassenger();
				this.rotation  = entitylivingbase.rotationYawHead;
				//this.rotationp = this.rotationPitch = entitylivingbase.rotationPitch;
				
				Vec3d looked = entitylivingbase.getLookVec();
				AI_EntityMoveAirCraft.moveheli(this, entitylivingbase, f1, sp, turnspeed, this.mob_min_range, this.mob_max_range, this.mob_min_height);
				if(AI_EntityWeapon.getRange(entitylivingbase, this.mob_w1range, this.mob_w1range_max_y, this.mob_w1range_min_y)){
					if(cooltime >= ammo1 && this.getRemain_L() > 0){
						this.counter1 = true;
			            cooltime = 0;
					}
					if(this.weapon1true) {
	    		    	this.weapon1activeMob(looked, entitylivingbase, this.weapon1type);
	    		    }
				}
				if(AI_EntityWeapon.getRange(entitylivingbase, this.mob_w2range, this.mob_w2range_max_y, this.mob_w2range_min_y)){
					if(cooltime2 >= ammo2 && this.getRemain_R() > 0){
						this.counter2 = true;
			            cooltime2 = 0;
					}
					if(this.weapon2true) {
	    		    	this.weapon2activeMob(looked, entitylivingbase, this.weapon2type);
	    		    }
				}
			}
		}
		
    	if(this.getFTMode() != 1){
			AI_AirCraftSet.setheli_NEW(this, sound, f1, sp, 0.1F);
		}else {
			AI_AirCraftSet.setheligunner(this, sound, f1, sp, 0.1F);
		}
		
		this.catchEntityBiped();
		
        
    }
    
    
    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (this.world.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int i = this.world.getLightFromNeighbors(blockpos);

            if (this.world.isThundering())
            {
                int j = this.world.getSkylightSubtracted();
                this.world.setSkylightSubtracted(10);
                i = this.world.getLightFromNeighbors(blockpos);
                this.world.setSkylightSubtracted(j);
            }

            return i <= this.rand.nextInt(8);
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.getCanSpawnHere();
    }
    
    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
    	if(this.getcanDespawn() == 0) {
    		return true;
    	}else {
    		return false;
    	}
    	
    }
    
}