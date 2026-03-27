package gvclib.block.tile;

import java.util.List;

import gvclib.gui.ContainerInventoryEntityGVC;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntityInvasion extends TileEntityLockable implements ITickable, ISidedInventory
{
    private static final int[] SLOTS_TOP = new int[] {0};
    private static final int[] SLOTS_BOTTOM = new int[] {2, 1};
    private static final int[] SLOTS_SIDES = new int[] {1};
    /** The ItemStacks that hold the items currently being used in the furnace */
    public NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack>withSize(16, ItemStack.EMPTY);
    /** The number of ticks that the furnace will keep burning */
    private int furnaceBurnTime;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;
    private String furnaceCustomName;
    
    public int ticks;
    public int level;
    public boolean canspawn = false;
    public boolean e_can = false;
    public boolean w_can = false;
    public boolean s_can = false;
    public boolean n_can = false;
    public int spwan_range = 64;
    
    public int spawntime = 1200;
    public boolean endress = false;
    
    public int uticks;
    public int hp = 0;
    
    public int mob_type = 0;
    public int mob_level = 0;

    public int getSizeInventory()
    {
        return this.furnaceItemStacks.size();
    }

    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.furnaceItemStacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getStackInSlot(int index)
    {
        return this.furnaceItemStacks.get(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        ItemStack itemstack = this.furnaceItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.furnaceItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag)
        {
            this.totalCookTime = this.getCookTime(stack);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
        //return this.hasCustomName() ? this.furnaceCustomName : "container.furnace";
    	return this.hasCustomName() ? this.furnaceCustomName : "InvasionBlock";
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName()
    {
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String p_145951_1_)
    {
        this.furnaceCustomName = p_145951_1_;
    }

    public static void registerFixesFurnace(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityInvasion.class, new String[] {"Items"}));
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.furnaceItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);
        this.furnaceBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
       // this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks.get(1));

        this.ticks = compound.getInteger("Ticks");
    	this.level = compound.getInteger("Level");
    	
    	this.e_can = compound.getBoolean("E_spawn");
    	this.w_can = compound.getBoolean("W_spawn");
    	this.s_can = compound.getBoolean("S_spawn");
    	this.n_can = compound.getBoolean("N_spawn");
    	this.spwan_range = compound.getInteger("Spawn_Range");
    	
    	this.uticks = compound.getInteger("UniversalTicks");
    	this.hp = compound.getInteger("HP");
        
        if (compound.hasKey("CustomName", 8))
        {
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        //compound.setInteger("BurnTime", (short)this.furnaceBurnTime);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);

        compound.setInteger("Ticks", ticks);
    	compound.setInteger("Level", level);
    	
    	compound.setBoolean("E_spawn", e_can);
    	compound.setBoolean("W_spawn", w_can);
    	compound.setBoolean("S_spawn", s_can);
    	compound.setBoolean("N_spawn", n_can);
            
    	compound.setInteger("Spawn_Range", spwan_range);
    	compound.setInteger("UniversalTicks", uticks);
    	compound.setInteger("HP", hp);
        
        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.furnaceCustomName);
        }

        return compound;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Furnace isBurning
     */
    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }
    
    @SideOnly(Side.CLIENT)
    public static int getClientTick(TileEntityInvasion inv)
    {
        return inv.getTicks();
    }
    @SideOnly(Side.CLIENT)
    public static int getClientLevel(TileEntityInvasion inv)
    {
        return inv.getLevel();
    }
    @SideOnly(Side.CLIENT)
    public static int getClientHP(TileEntityInvasion inv)
    {
        return inv.getHP();
    }
    @SideOnly(Side.CLIENT)
    public static int getClientRange(TileEntityInvasion inv)
    {
        return inv.spwan_range;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update()
    {
    	{
    		GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(100, spwan_range,
    				this.getPos().getX(), this.getPos().getY(), this.getPos().getZ()));
    		GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(101, getHP(),
    				this.getPos().getX(), this.getPos().getY(), this.getPos().getZ()));
    		GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(102, getLevel(),
    				this.getPos().getX(), this.getPos().getY(), this.getPos().getZ()));
    		GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(103, getTicks(),
    				this.getPos().getX(), this.getPos().getY(), this.getPos().getZ()));
    		ItemStack e = (ItemStack)this.furnaceItemStacks.get(0);
        	ItemStack w = (ItemStack)this.furnaceItemStacks.get(1);
        	ItemStack n = (ItemStack)this.furnaceItemStacks.get(2);
        	ItemStack s = (ItemStack)this.furnaceItemStacks.get(3);
        	ItemStack can = (ItemStack)this.furnaceItemStacks.get(4);
        	if(((!e.isEmpty() && getMobLevel(e.getItem()))
        			|| (!w.isEmpty() && getMobLevel(w.getItem()))
        			|| (!n.isEmpty() && getMobLevel(n.getItem()))
        			|| (!s.isEmpty() && getMobLevel(s.getItem())))
        			&& !can.isEmpty() && getCan(can.getItem())) {
        		this.canspawn = true;
        	}else {
        		this.canspawn = false;
        	}
        	if(!e.isEmpty() && getMobLevel(e.getItem())) {
        		e_can = true;
        	}else {
        		e_can = false;
        	}
        	if(!w.isEmpty() && getMobLevel(w.getItem())) {
        		w_can = true;
        	}else {
        		w_can = false;
        	}
        	if(!n.isEmpty() && getMobLevel(n.getItem())) {
        		n_can = true;
        	}else {
        		n_can = false;
        	}
        	if(!s.isEmpty() && getMobLevel(s.getItem())) {
        		s_can = true;
        	}else {
        		s_can = false;
        	}
        	//mob_type = getType(this.furnaceItemStacks.get(4).getItem());
    	}
    	if (this.canspawn) {
			Block block = this.world.getBlockState(this.pos).getBlock();
			Entity entity = null;
			int x = this.pos.getX();
			int y = this.pos.getY();
			int z = this.pos.getZ();

			if (this.getLevel() > 10  && !this.endress) {
				this.PlayerMessage(3);
				this.getBonus(world, x, y, z, 0);
			}

			if (this.getUTicks() >= 10) {
				this.setUTicks(0);
				int k = this.pos.getX();
				int l = this.pos.getY();
				int i = this.pos.getZ();
				boolean dame = false;
				int han = 2;
				{
					AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - han),
							(double) (i - han), (double) (k + han), (double) (l + han), (double) (i + han)))
									.grow(1);
					List llist = this.world.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb);
					double d0 = 8.0D;
					if (llist != null) {
						for (int lj = 0; lj < llist.size(); lj++) {
							Entity entity1 = (Entity) llist.get(lj);
							if (entity1.canBeCollidedWith()) {
								if (entity1 instanceof IMob && entity1 != null && !this.world.isRemote) {
									dame = true;
									break;
								}
							}
						}
					}
				}
				if (dame) {
					this.setHP(this.getHP() + 1);
					this.PlayerMessage(1);
				}
			} else {
				this.setUTicks(this.getUTicks() + 1);
			}

			if (this.getTicks() >= spawntime && (this.getLevel() <= 10 || this.endress)) {
				this.setTicks(0);
				this.setLevel(getLevel() + 1);
				this.setHP(this.getHP() - 10);
				if (this.getHP() < 0) {
					this.setHP(0);
				}
				if(this.getLevel() <= 10 || this.endress) {
					if (this.e_can) {
						this.EntitySpwan(world, this.spwan_range, 0, 0, this.getLevel());
					}
					if (this.w_can) {
						this.EntitySpwan(world, -this.spwan_range, 0, 0, this.getLevel());
					}
					if (this.s_can) {
						this.EntitySpwan(world, 0, 0, this.spwan_range, this.getLevel());
					}
					if (this.n_can) {
						this.EntitySpwan(world, 0, 0, -this.spwan_range, this.getLevel());
					}
					this.PlayerMessage(0);
				}
			} else {
				if(this.getLevel() < 1 && this.getTicks() < 1) {
					this.PlayerMessage(-1);
				}
				this.setTicks(this.getTicks() + 1);
			}
			if((this.spawntime - this.getTicks()) == 60){
				this.PlayerMessage(4);
			}
			if((this.spawntime - this.getTicks()) == 40){
				this.PlayerMessage(5);
			}
			if((this.spawntime - this.getTicks()) == 20){
				this.PlayerMessage(6);
			}

			if (this.getHP() >= 100) {
				if (!this.world.isRemote) {
					this.world.setBlockToAir(pos);
				}
				this.PlayerMessage(2);
			}

		}
    }
    public abstract boolean getCan(Item item);
    //public abstract int getType(Item item);
    public abstract boolean getMobLevel(Item item);
    public abstract void EntitySpwan(World world,int xx, int yy, int zz, int id);
    
    public abstract void getBonus(World world,int xx, int yy, int zz, int id);
    
    
    public int getTicks() {
		return ticks;
	}
	public void setTicks(int i) {
		this.ticks = i;
	}
	public int getUTicks() {
		return uticks;
	}
	public void setUTicks(int i) {
		this.uticks = i;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int i) {
		this.level = i;
	}
	public int getHP() {
		return hp;
	}
	public void setHP(int i) {
		this.hp = i;
	}
    
	 public void PlayerMessage(int id){
	    	Entity entity = null;
	    	int k = this.pos.getX();
	        int l = this.pos.getY();
	        int i = this.pos.getZ();
	        AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double)(k-100), (double)(l-20), (double)(i-100), (double)(k + 100), (double)(l + 20), (double)(i + 100)))
	        		.grow(200);
	        List llist = this.world.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb);
	        double d0 = 8.0D;
	        if(llist!=null){
	            for (int lj = 0; lj < llist.size(); lj++) {
	            	
	            	Entity entity1 = (Entity)llist.get(lj);
	            	if (entity1.canBeCollidedWith())
	                {
	            		if (entity1 instanceof EntityPlayer && entity1 != null && !this.world.isRemote)
	                    {
	            			EntityPlayer placer = (EntityPlayer) entity1;
	            			if(id == -1){
	            				placer.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
	                			((EntityPlayer)placer).sendMessage(new TextComponentTranslation("Wave : Ready", new Object[0]));
	            			}
	            			if(id == 0){
	            				placer.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
	            				String le = String.valueOf(this.getLevel());
	                			((EntityPlayer)placer).sendMessage(new TextComponentTranslation("Wave : " + le, new Object[0]));
	                			if(!this.e_can){
	                        		//((EntityPlayer)placer).addChatMessage(new TextComponentTranslation("East false", new Object[0]));
	                        	}
	                			String le1 = String.valueOf(100 - this.getHP());
	                			((EntityPlayer)placer).sendMessage(new TextComponentTranslation("Base HP : " + le1, new Object[0]));
	            			}
	            			if(id == 1){
	            				String le = String.valueOf(100 - this.getHP());
	                			((EntityPlayer)placer).sendMessage(new TextComponentTranslation("Base HP : " + le, new Object[0]));
	            			}
	            			if(id == 2){
	                			((EntityPlayer)placer).sendMessage(new TextComponentTranslation("Base was broken!", new Object[0]));
	            			}
	            			if(id == 3){
	                			((EntityPlayer)placer).sendMessage(new TextComponentTranslation("Mission Clear!", new Object[0]));
	            			}
	            			if(id == 4){
	                			((EntityPlayer)placer).sendMessage(new TextComponentTranslation("Next : 3", new Object[0]));
	            			}
	            			if(id == 5){
	                			((EntityPlayer)placer).sendMessage(new TextComponentTranslation("Next : 2", new Object[0]));
	            			}
	            			if(id == 6){
	                			((EntityPlayer)placer).sendMessage(new TextComponentTranslation("Next : 1", new Object[0]));
	            			}
	                    }
	            		
	                }
	            }
	        }
	    }
	
	
    public int getCookTime(ItemStack stack)
    {
        return 200;
    }

    

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    public void openInventory(EntityPlayer player)
    {
    }

    public void closeInventory(EntityPlayer player)
    {
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        if (index == 2)
        {
            return false;
        }
        else if (index != 1)
        {
            return true;
        }
        else
        {
            ItemStack itemstack = this.furnaceItemStacks.get(1);
            //return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
            return false;
        }
    }

    public int[] getSlotsForFace(EnumFacing side)
    {
        if (side == EnumFacing.DOWN)
        {
            return SLOTS_BOTTOM;
        }
        else
        {
            return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
        }
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        if (direction == EnumFacing.DOWN && index == 1)
        {
            Item item = stack.getItem();

            if (item != Items.WATER_BUCKET && item != Items.BUCKET)
            {
                return false;
            }
        }

        return true;
    }

    public String getGuiID()
    {
        return "minecraft:furnace";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerInventoryEntityGVC(playerInventory, this);
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.furnaceBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    public int getFieldCount()
    {
        return 4;
    }

    public void clear()
    {
        this.furnaceItemStacks.clear();
    }
}