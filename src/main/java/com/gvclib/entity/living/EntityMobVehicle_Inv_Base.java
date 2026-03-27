package gvclib.entity.living;

import java.util.List;

import gvclib.mod_GVCLib;
import gvclib.entity.trader.ContainerInventoryBox;
import gvclib.event.GVCSoundEvent;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
//import mcdungeons.mod_MCDungeons;
//import mcdungeons.entity.MCDContainerInventoryBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMobVehicle_Inv_Base extends EntityMobVehicleBase implements IAnimals,IInventoryChangedListener
{
	
	public ContainerInventoryBox horseChest;
	
	public int slot_max = 27;
	
	/**乗り物のタイプ
	 * 0=普通の戦車
	 * 1=対空戦車
	 * 2=自走砲系
	 * */
	public int vehicle_type = 0;
	
	public boolean can_fuel = false;
	public int fuel = 0;
	public int fuel_max = 1200;
	public Item fuel_item = null;
	
	public boolean can_weapon1 = false;
	public Item weapon1_item = null;
	public boolean can_weapon2 = false;
	public Item weapon2_item = null;
	
	
    public EntityMobVehicle_Inv_Base(World worldIn)
    {
        super(worldIn);
        this.initHorseChest();
        
    }

    
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		//cooltime = 0;
		if (player.isSneaking()) {
			NBTTagCompound nbt = player.getEntityData();
			nbt.setInteger("vi", this.getEntityId());
			player.openGui(mod_GVCLib.INSTANCE, 4,
					player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
			{
				this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, 
		    			SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.NEUTRAL, 0.5F, 1.0F);
			}
		}else {
			super.processInteract(player, hand);
		}
		return true;
	}
    
	public boolean rogin = false;
    public void onLivingUpdate() {
		super.onLivingUpdate();
		if(!this.world.isRemote && !rogin){
			ItemStack fuel_stack = this.horseChest.getStackInSlot(63);
			if(!fuel_stack.isEmpty()) {
				Item item = this.horseChest.getStackInSlot(63).getItem();
				GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(3063, 
						this.getEntityId(), 63, item.getIdFromItem(item), fuel_stack.getCount()));
			}
			for (int i = 0; i < 5; ++i) {
				ItemStack soi1 = (ItemStack) this.horseChest.getStackInSlot(40 + i);
				ItemStack soi2 = (ItemStack) this.horseChest.getStackInSlot(50 + i);
				if(!soi1.isEmpty()) {
					Item item = this.horseChest.getStackInSlot(40 + i).getItem();
					GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(3063, 
							this.getEntityId(), 40 + i, item.getIdFromItem(item), soi1.getCount()));
				}
				if(!soi2.isEmpty()) {
					Item item = this.horseChest.getStackInSlot(50 + i).getItem();
					GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(3063, 
							this.getEntityId(), 50 + i, item.getIdFromItem(item), soi2.getCount()));
				}
			}
			//rogin = true;
		}
		{
			if(can_fuel && this.throttle != 0) {
				if(fuel > 0) {
					--fuel;
				}
				ItemStack fuel_stack = this.horseChest.getStackInSlot(63);
				if(!fuel_stack.isEmpty() && fuel_stack.getItem() == this.fuel_item) {
					if(fuel == 0) {
						fuel = fuel_max;
						fuel_stack.shrink(1);
						
					}
				}
			}
		}
		{
			if(this.getRemain_L() <= 0 && !weapon1_auto_reload){
				ItemStack shell_stack = this.horseChest.getStackInSlot(40);
				if(!shell_stack.isEmpty() && shell_stack.getItem() == this.weapon1_item) {
					++reload1;
					if(reload1 == reload_time1 - reloadsoundset1){
						this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, reloadsound1, SoundCategory.WEATHER, 1.0F, 1.0F);
					}
					if(reload1 >= reload_time1){
						this.setRemain_L(this.magazine);
						reload1 = 0;
						shell_stack.shrink(1);
					}
				}
			}
			{
				for (int i = 0; i < 4; ++i) {
					ItemStack soi = (ItemStack) this.horseChest.getStackInSlot(40 + i);
					ItemStack soi2 = (ItemStack) this.horseChest.getStackInSlot(41 + i);
					if (!soi2.isEmpty() && soi2.getItem() == this.weapon1_item) {
						if (soi.isEmpty()) {
							this.horseChest.setInventorySlotContents(40 + i, soi2);
							this.horseChest.setInventorySlotContents(41 + i, soi2.EMPTY);
						} else {
							if (soi.getCount() < 64 && soi2.getCount() >= 1) {
								soi.setCount(soi.getCount() + 1);
								this.horseChest.setInventorySlotContents(40 + i, soi);
								soi2.shrink(1);
								if (soi2.getCount() <= 0) {
									this.horseChest.setInventorySlotContents(41 + i, soi2.EMPTY);
								}
							}
						}
					}
				}
			}
			
			if(this.getRemain_R() <= 0 && !weapon2_auto_reload){
				ItemStack shell_stack = this.horseChest.getStackInSlot(50);
				if(!shell_stack.isEmpty() && shell_stack.getItem() == this.weapon2_item) {
					++reload2;
					if(reload2 == reload_time2 - reloadsoundset2){
						this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, reloadsound2, SoundCategory.WEATHER, 1.0F, 1.0F);
					}
					if(reload2 >= reload_time2){
						this.setRemain_R(this.magazine2);
						reload2 = 0;
						shell_stack.shrink(1);
					}
				}
			}
			{
				for (int i = 0; i < 4; ++i) {
					ItemStack soi = (ItemStack) this.horseChest.getStackInSlot(50 + i);
					ItemStack soi2 = (ItemStack) this.horseChest.getStackInSlot(51 + i);
					if (!soi2.isEmpty() && soi2.getItem() == this.weapon2_item) {
						if (soi.isEmpty()) {
							this.horseChest.setInventorySlotContents(50 + i, soi2);
							this.horseChest.setInventorySlotContents(51 + i, soi2.EMPTY);
						} else {
							if (soi.getCount() < 64 && soi2.getCount() >= 1) {
								soi.setCount(soi.getCount() + 1);
								this.horseChest.setInventorySlotContents(50 + i, soi);
								soi2.shrink(1);
								if (soi2.getCount() <= 0) {
									this.horseChest.setInventorySlotContents(51 + i, soi2.EMPTY);
								}
							}
						}
					}
				}
			}
		}
		/*if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			if(this.getControllingPassenger() instanceof EntityGVCLivingBase)
			{
				fuel = fuel_max;
				if(this.getRemain_L() <= 0 && !weapon1_auto_reload){
					{
						++reload1;
						if(reload1 == reload_time1 - reloadsoundset1){
							this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, reloadsound1, SoundCategory.WEATHER, 1.0F, 1.0F);
						}
						if(reload1 >= reload_time1){
							this.setRemain_L(this.magazine);
							reload1 = 0;
						}
					}
				}
			}
		}*/
		
		/*{
			fuel = fuel_max;
			if(this.getRemain_L() <= 0){
					++reload1;
					if(reload1 == reload_time1 - reloadsoundset1){
						this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, reloadsound1, SoundCategory.WEATHER, 1.0F, 1.0F);
					}
					if(reload1 >= reload_time1){
						this.setRemain_L(this.magazine);
						reload1 = 0;
					}
			}
			if(this.getRemain_R() <= 0 && !weapon2_auto_reload){
					++reload2;
					if(reload2 == reload_time2 - reloadsoundset2){
						this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, reloadsound2, SoundCategory.WEATHER, 1.0F, 1.0F);
					}
					if(reload2 >= reload_time2){
						this.setRemain_R(this.magazine2);
						reload2 = 0;
					}
			}
		}*/
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 0; i < this.horseChest.getSizeInventory(); ++i)
            {
                ItemStack itemstack = this.horseChest.getStackInSlot(i);

                if (!itemstack.isEmpty())
                {
                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    nbttagcompound.setByte("Slot", (byte)i);
                    itemstack.writeToNBT(nbttagcompound);
                    nbttaglist.appendTag(nbttagcompound);
                }
            }

            compound.setTag("Items", nbttaglist);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        {
            NBTTagList nbttaglist = compound.getTagList("Items", 10);
            this.initHorseChest();

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound.getByte("Slot") & 255;

                if (j >= 0 && j < this.horseChest.getSizeInventory())
                {
                    this.horseChest.setInventorySlotContents(j, new ItemStack(nbttagcompound));
                }
            }
        }

        this.updateHorseSlots();
    }
    
    
    
    public boolean CanAttack(Entity entity){
    	if(entity instanceof IMob && ((EntityLivingBase) entity).getHealth() > 0.0F){
    		return true;
    	}else
    	{
    		return false;
    	}
    }
    /*public boolean CanAttack(Entity entity){
    	{
    		return false;
    	}
    }*/
    
    
    
  
    
    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
    	if(this.getcanDespawn() == 0 && !this.getVehicleLock() && !this.getattacktask()) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    /**
     * Drop the equipment for this entity.
     */
    protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier)
    {
    	ItemStack fuel_stack = this.horseChest.getStackInSlot(63);
    	if(!fuel_stack.isEmpty()) {
    		this.entityDropItem(fuel_stack, 0.0F);
    	}
    	ItemStack w1_stack = this.horseChest.getStackInSlot(40);
    	if(!w1_stack.isEmpty()) {
    		this.entityDropItem(w1_stack, 0.0F);
    	}
    	ItemStack w2_stack = this.horseChest.getStackInSlot(50);
    	if(!w2_stack.isEmpty()) {
    		this.entityDropItem(w2_stack, 0.0F);
    	}
    }
    
   
    protected int getInventorySize()
    {
        return 64;
    }

    protected void initHorseChest()
    {
    	ContainerInventoryBox containerhorsechest = this.horseChest;
        this.horseChest = new ContainerInventoryBox("HorseChest", this.getInventorySize());
        this.horseChest.setCustomName(this.getName());

        if (containerhorsechest != null)
        {
            containerhorsechest.removeInventoryChangeListener(this);
            int i = Math.min(containerhorsechest.getSizeInventory(), this.horseChest.getSizeInventory());

            for (int j = 0; j < i; ++j)
            {
                ItemStack itemstack = containerhorsechest.getStackInSlot(j);

                if (!itemstack.isEmpty())
                {
                    this.horseChest.setInventorySlotContents(j, itemstack.copy());
                }
            }
        }

        this.horseChest.addInventoryChangeListener(this);
        this.updateHorseSlots();
    }
    
    public void onInventoryChanged(IInventory invBasic)
    {
    	this.updateHorseSlots();
    }
    /**
     * Updates the items in the saddle and armor slots of the horse's inventory.
     */
    protected void updateHorseSlots()
    {
    	ContainerInventoryBox containerhorsechest = this.horseChest;
    	if (containerhorsechest != null)
        {
    		//this.horseChest.setInventorySlotContents(11, new ItemStack(Items.EMERALD));
        }
    }
    public void onUpdate()
    {
        super.onUpdate();
        this.updateHorseSlots();
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.getcanDespawn() == 0)
        {
            this.setDead();
        }
    }
    
    /*public int getInventoryColumns()
    {
        return 5;
    }*/
    
    public void typePCTank() {
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
			PL_TankMove.move2(entitylivingbase, this, sp, turnspeed);
			
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
		    
		    if(this.serverspace)
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
			}
			
			
			
			}//player
			else if(this.getControllingPassenger() instanceof EntityGVCLivingBase) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) this.getControllingPassenger();
				this.rotation  = entitylivingbase.rotationYawHead;
				this.rotationp = this.rotationPitch = entitylivingbase.rotationPitch;
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
		
    	if(fuel > 0)AI_TankSet.set2(this, GVCSoundEvent.sound_tank, f1, sp, 0.1F);
		
		this.catchEntityBiped();
		
        
    }
    
    public void typePCSPG() {
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
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
							xx = xx + (this.turnspeed * 0.2F);
						}
						if (entitylivingbase.moveForward < 0.0F && (spg_yaw > -range && spg_picth > -range)) {
							xx = xx - (this.turnspeed * 0.2F);
						}
						if (entitylivingbase.moveStrafing > 0.0F && (spg_yaw < range && spg_picth < range)) {
							zz = zz + (this.turnspeed * 0.2F);
						}
						if (entitylivingbase.moveStrafing < 0.0F && (spg_yaw > -range && spg_picth > -range)) {
							zz = zz - (this.turnspeed * 0.2F);
						}
						spg_yaw -= MathHelper.sin(yaw) * xx;
						spg_picth += MathHelper.cos(yaw) * xx;
						spg_yaw -= MathHelper.sin(yaw - 1.57F) * zz;
						spg_picth += MathHelper.cos(yaw - 1.57F) * zz;
						double d5 = this.spg_yaw;
						double d7 = this.spg_picth;
						float yawoffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						this.rotation = this.rotationYaw = yawoffset;
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
				this.serverg = true;
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
		    
		    if(this.serverspace)
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
			}
		    if (this.serverg) {
				if (cooltime6 > 1 && this.getWeaponChange() > 0) {
					if(this.getFTMode() >= 1){
						this.setFTMode(0);
					}else
					{
						this.setFTMode(1);
						float yaw = this.rotationYaw * (2 * (float) Math.PI / 360);
						//spg_yaw = 0;
						//spg_picth = 0;
						spg_yaw -= MathHelper.sin(yaw) * 40;
						spg_picth += MathHelper.cos(yaw) * 40;
					}
					cooltime6 = 0;
					this.setWeaponChange(this.getWeaponChange() - 1);
				}
				{
					this.serverg = false;
				}
			}
			
			
			}//player
			else if(this.getControllingPassenger() instanceof EntityGVCLivingBase) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) this.getControllingPassenger();
				this.rotation  = entitylivingbase.rotationYawHead;
				this.rotationp =  -40;
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
				this.rotationp =  -40;
			}
		}
		
    	if(fuel > 0)AI_TankSet.set2(this, GVCSoundEvent.sound_tank, f1, sp, 0.1F);
		
		this.catchEntityBiped();
		
        
    }
    
    
    public void roadattackPC(int level) {
    	double dx = Math.abs(this.posX - this.prevPosX);
		double dz = Math.abs(this.posZ - this.prevPosZ);
		float dd = (float)Math.sqrt((dx * dx) + (dz * dz)) * 20;
    	if(dd != 0){
			this.swingArm(EnumHand.MAIN_HAND);
			AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(
					this.posX - this.width, this.posY, this.posZ - this.width, 
					this.posX + this.width, this.posY + this.height, this.posZ + this.width)
	        		.expand(this.width, this.height, this.width);
			List<Entity> llist = this.world.getEntitiesWithinAABBExcludingEntity(this,axisalignedbb2);
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith() && entity1 instanceof EntityLivingBase && entity1 != null) {
						if(entity1 != this && entity1 != this.getControllingPassenger() && entity1 instanceof IMob && !entity1.isRiding()){
							entity1.attackEntityFrom(DamageSource.causeMobDamage(this), 10);
						}
					}
				}
			}
		}
    	if(!this.world.isRemote && roodbreak){
    		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
    		double xx = width;
        		for(double x = 0; x < width*2; ++x) {
        			for(double y = -1; y < height*1.2; ++y) {
        				for(double z = 0; z < width*2; ++z) {
        					BlockPos pos = new BlockPos(this.posX -xx + x, this.posY + y, this.posZ - xx + z);
        					if(canbreak(level, pos)) 
        					{
        						IBlockState iblockstate = this.world.getBlockState(pos);
        		                Block block = iblockstate.getBlock();
        		                if (iblockstate.getMaterial() != Material.AIR)
        		                {
        		                    block.dropBlockAsItemWithChance(this.world, pos, this.world.getBlockState(pos), 1.0F, 0);
        		                    world.setBlockToAir(pos);
        		                }
        					}
        				}
        			}
        		}
    	}
    }
    
    public boolean canbreak(int level, BlockPos pos) {
    	boolean re = false;
    	if(level == 0) {
    		if(this.world.getBlockState(pos).getBlock() instanceof BlockOldLeaf	
					|| this.world.getBlockState(pos).getBlock() instanceof BlockPane	
					|| this.world.getBlockState(pos).getBlock() instanceof BlockNewLeaf) 
				{
    			re = true;
				}
    	}
    	if(level == 1) {
    		if(this.world.getBlockState(pos).getBlock() instanceof BlockOldLog
					|| this.world.getBlockState(pos).getBlock() instanceof BlockOldLeaf	
					|| this.world.getBlockState(pos).getBlock() instanceof BlockPane	
					|| this.world.getBlockState(pos).getBlock() instanceof BlockLog
					|| this.world.getBlockState(pos).getBlock() instanceof BlockNewLeaf	
					|| this.world.getBlockState(pos).getBlock() instanceof BlockPlanks	
					|| this.world.getBlockState(pos).getBlock() instanceof BlockLilyPad	
						) 
				{
    			re = true;
				}
    	}
    	if(level == 2) {
    		if(this.world.getBlockState(pos).getBlock() instanceof BlockOldLog
					|| this.world.getBlockState(pos).getBlock() instanceof BlockOldLeaf	
					|| this.world.getBlockState(pos).getBlock() instanceof BlockPane	
					|| this.world.getBlockState(pos).getBlock() instanceof BlockLog
					|| this.world.getBlockState(pos).getBlock() instanceof BlockNewLeaf	
					|| this.world.getBlockState(pos).getBlock() instanceof BlockPlanks	
					|| this.world.getBlockState(pos).getBlock() instanceof BlockLilyPad	
					|| this.world.getBlockState(pos).getBlock() instanceof BlockStoneBrick
					|| this.world.getBlockState(pos).getBlock() == Blocks.COBBLESTONE
					|| (this.world.getBlockState(pos).getBlock() instanceof BlockStone && this.world.getBlockState(pos).getBlock() != Blocks.STONE)
						) 
				{
    			re = true;
				}
    	}
    	return re;
    }
    
}