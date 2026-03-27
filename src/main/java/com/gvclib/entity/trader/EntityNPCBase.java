package gvclib.entity.trader;

import gvclib.mod_GVCLib;
import gvclib.entity.living.EntityGVCLivingBase;
//import mcdungeons.mod_MCDungeons;
//import mcdungeons.entity.MCDContainerInventoryBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityNPCBase extends EntityGVCLivingBase implements IAnimals,IInventoryChangedListener
{
	
	public Item currency = Items.EMERALD;
	
	public ContainerInventoryBox horseChest;
	
	/**売り物*/
	public Item[] sell = new Item[256];//売り物
	/**売り物*/
	public int[] sell_id = new int[256];//売り物
	/**売り物
	 * 買える量*/
	public int[] sell_size = new int[256];//売り物の量
	/**売り物
	 * 必要なエメラルドの数*/
	public int[] sellm = new int[256];//販売価格
	
	/**プレイヤーが売れるもの*/
	public int buyid = 0;
	/**プレイヤーが売れるもの*/
	public Item[] buy = new Item[256];//売り物]
	/**プレイヤーが売れるもの
	 * 帰ってくるエメラルド*/
	public int[] buy_size = new int[256];//売り物の量
	/**プレイヤーが売れるもの
	 * 必要数*/
	public int[] buym = new int[256];//販売価格
	
	/**手に持っているかどうか*/
	public boolean[] hand  = new boolean[256];//手に持っているかどうか
	
	/**手に持っているかどうか_アイテムの買い取り*/
	public boolean[] hand_buy  = new boolean[256];//手に持っているかどうか
	
    public EntityNPCBase(World worldIn)
    {
        super(worldIn);
        this.initHorseChest();
        for(int x = 0; x < 9; ++x) {
			hand[0 + x] = false;
		}
        hand[10] = false;
        hand_buy[10] = false;
    }
    
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
	 cooltime = 0;
	NBTTagCompound nbt = player.getEntityData();
	nbt.setInteger("vi", this.getEntityId());
	player.openGui(mod_GVCLib.INSTANCE, 2,
			player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
            return true;
    }
    
    public void onLivingUpdate() {
		super.onLivingUpdate();
		{
			this.trade();
			this.sellitem();
			//this.DefaultSellItem();
		}
    }
    
    /*public void DefaultSellItem() {
    	this.buy_default_[0] = Items.IRON_INGOT;
		this.buy_default__size[0] = 1;
		this.buy_default_m[0] = 4;
		this.buy_default_[1] = Items.GOLD_INGOT;
		this.buy_default__size[1] = 1;
		this.buy_default_m[1] = 2;
		this.buy_default_[2] = Items.DIAMOND;
		this.buy_default__size[2] = 1;
		this.buy_default_m[2] = 1;
    }*/
    
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
    
    public int[] sell_slotcount = new int[256];//
    
    public int sell_page = 1;
    public int sell_now_id = 0;
    
	public void trade() {
		 ItemStack itemstack = this.horseChest.getStackInSlot(0);
		 int page = 10 * this.sell_now_id;
	        if (!itemstack.isEmpty() && itemstack.getItem() == currency)
	        {
	        	
	        	for(int x = 0; x < 9; ++x) {
	        		ItemStack sell_slot = this.horseChest.getStackInSlot(1 + x);
        			int sellslotkosuu = sell_slot.getCount();
	        		if(itemstack.getCount() >= this.sellm[0 + x + page] ){
	        			if((sell_slot.isEmpty() || this.sell_size[0 + x + page] > sellslotkosuu) && hand[0 + x + page]) 
			        	{
			        		itemstack.shrink(this.sellm[0 + x + page]);
			        		hand[0 + x + page] = false;
			        	}else
	        			{
	        				this.horseChest.setInventorySlotContents(1 + x, 
	        						new ItemStack(this.sell[0 + x + page], this.sell_size[0 + x + page], this.sell_id[0 + x + page]));
	        				hand[0 + x + page] = true;
	        			}
		        	}else {
		        		this.horseChest.setInventorySlotContents(1 + x, this.horseChest.getStackInSlot(1 + x).EMPTY);
		        	}
	        		//sell_slotcount[0 + x] = sellslotkosuu;
	        	}
	        }else {
	        	for(int x = 0; x < 9; ++x) {
	        		this.horseChest.setInventorySlotContents(1 + x, this.horseChest.getStackInSlot(1 + x).EMPTY);
	        		hand[0 + x + page] = false;
	        	}
	        	
	        }
	}
	
	public int buy_page = 1;
    public int buy_now_id = 0;
	
	public void sellitem() {
		ItemStack itemstack = this.horseChest.getStackInSlot(10);
		if (!itemstack.isEmpty()) {
			int idbuy = 0;
			
			for (int ite = 0; ite < 256; ++ite) 
			{
				if ( itemstack.getItem() == buy[ite] && buy[ite] != null) {
					idbuy = ite;
					break;
				}
			}
			if ( itemstack.getItem() == buy[idbuy] && buy[idbuy] != null) {
				if (itemstack.getCount() >= this.buym[idbuy]) {
					if (this.horseChest.getStackInSlot(11).isEmpty() && hand_buy[10]) {
						itemstack.shrink(this.buym[idbuy]);
						hand_buy[10] = false;
					} else {
						this.horseChest.setInventorySlotContents(11, new ItemStack(Items.EMERALD, this.buy_size[idbuy]));
						hand_buy[10] = true;
					}
				} else {
					this.horseChest.setInventorySlotContents(11, this.horseChest.getStackInSlot(11).EMPTY);
				}
			} else {
				this.horseChest.setInventorySlotContents(11, this.horseChest.getStackInSlot(11).EMPTY);
				hand_buy[10] = false;
			}
		} else {
			this.horseChest.setInventorySlotContents(11, this.horseChest.getStackInSlot(11).EMPTY);
			hand_buy[10] = false;
		}
	}
	/*public void selldefaultitem() {
		ItemStack itemstack = this.horseChest.getStackInSlot(10);
		if (!itemstack.isEmpty()) {
			int idbuy_default_ = 0;
			
			for (int ite = 0; ite < 256; ++ite) 
			{
				if ( itemstack.getItem() == buy_default_[ite] && buy_default_[ite] != null) {
					idbuy_default_ = ite;
					break;
				}
			}
			if ( itemstack.getItem() == buy_default_[idbuy_default_] && buy_default_[idbuy_default_] != null) {
				if (itemstack.getCount() >= this.buy_default_m[idbuy_default_]) {
					if (this.horseChest.getStackInSlot(11).isEmpty() && hand[10]) {
						itemstack.shrink(this.buy_default_m[idbuy_default_]);
						hand[10] = false;
					} else {
						this.horseChest.setInventorySlotContents(11, new ItemStack(Items.EMERALD, this.buy_default__size[idbuy_default_]));
						hand[10] = true;
					}
				} else {
					this.horseChest.setInventorySlotContents(11, this.horseChest.getStackInSlot(11).EMPTY);
				}
			} else {
				this.horseChest.setInventorySlotContents(11, this.horseChest.getStackInSlot(11).EMPTY);
				hand[10] = false;
			}
		} else {
			this.horseChest.setInventorySlotContents(11, this.horseChest.getStackInSlot(11).EMPTY);
			hand[10] = false;
		}
	}*/
    
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
    	return false;
    }
    
   
    protected int getInventorySize()
    {
        return 32;
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
       
    }
    
    public int getInventoryColumns()
    {
        return 5;
    }
}