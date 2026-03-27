package gvclib.gui;
 
import java.util.Map;

import gvclib.item.ItemGunBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
 
public class GVCInventoryItem_old implements IInventory
{
    private InventoryPlayer inventoryPlayer;
    private ItemStack currentItem;
    //private ItemStack[] items;
    private NonNullList<ItemStack> stackList = NonNullList.<ItemStack>withSize(54, ItemStack.EMPTY);
 
    public GVCInventoryItem_old(InventoryPlayer inventory, ItemStack stack)
    {
        inventoryPlayer = inventory;
        EntityPlayer entityplayer = inventory.player;
        //currentItem = inventoryPlayer.getCurrentItem();
        if(!entityplayer.getHeldItemMainhand().isEmpty() && entityplayer.getHeldItemMainhand().getItem() instanceof ItemGunBase){
        	currentItem = ((EntityPlayer)(entityplayer)).getHeldItemMainhand();
        }else if(!entityplayer.getHeldItemOffhand().isEmpty() && entityplayer.getHeldItemOffhand().getItem() instanceof ItemGunBase){
        	currentItem = ((EntityPlayer)(entityplayer)).getHeldItemOffhand();
        }
        //currentItem = ((EntityPlayer)(entityplayer)).getHeldItemMainhand();
        //currentItem = stack;
 
        //InventorySize
    }
 
    @Override
    public int getSizeInventory()
    {
        return this.stackList.size();
    }
 
    @Override
    public ItemStack getStackInSlot(int index)
    {
        return this.stackList.get(index);
    }
 @Override
    public ItemStack decrStackSize(int index, int p_70298_2_)
    {
        if (this.stackList.get(index) != ItemStack.EMPTY)
        {
            ItemStack itemstack;
 
            if (this.stackList.get(index).getCount() <= p_70298_2_)
            {
                itemstack = this.stackList.get(index);
                this.stackList.set(index, ItemStack.EMPTY);
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.stackList.get(index).splitStack(p_70298_2_);
 
                if (this.stackList.get(index).getCount() == 0)
                {
                	this.stackList.set(index, ItemStack.EMPTY);
                }
 
                this.markDirty();
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }
 
 public ItemStack decrStackDamege(int index, int p_70298_2_, EntityLivingBase en)
 {
     if (this.stackList.get(index) != ItemStack.EMPTY)
     {
         ItemStack itemstack;
         this.stackList.get(index).damageItem(p_70298_2_, en);

         if (this.stackList.get(index).getItemDamage() >= this.stackList.get(index).getMaxDamage())
         {
             itemstack = this.stackList.get(index);
             this.stackList.set(index, ItemStack.EMPTY);
             this.markDirty();
             return itemstack;
         }
         else
         {
             itemstack = this.stackList.get(index).splitStack(p_70298_2_);

             if (this.stackList.get(index).getCount() == 0)
             {
             	this.stackList.set(index, ItemStack.EMPTY);
             }

             this.markDirty();
             return itemstack;
         }
     }
     return ItemStack.EMPTY;
 }
 
   // @Override
    public ItemStack getStackInSlotOnClosing(int index)
    {
        if (this.stackList.get(index)!= ItemStack.EMPTY)
        {
            ItemStack itemstack = this.stackList.get(index);
            this.stackList.set(index, ItemStack.EMPTY);
            return itemstack;
        }
        return null;
    }
 
    @Override
    public void setInventorySlotContents(int index, ItemStack p_70299_2_)
    {
    	this.stackList.set(index,p_70299_2_);
 
        if (p_70299_2_ != ItemStack.EMPTY && p_70299_2_.getCount() > this.getInventoryStackLimit())
        {
           // p_70299_2_ = this.getInventoryStackLimit();
        	p_70299_2_.setCount( this.getInventoryStackLimit());
        }
 
        this.markDirty();
    }
 
    //@Override
    public String getInventoryName()
    {
        return "InventoryItem";
    }
 
    //@Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }
 
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }
 
    @Override
    public void markDirty() {}
 
    @Override
    public boolean isUsableByPlayer(EntityPlayer p_70300_1_)
    {
        return true;
    }
 
    /*
        Containerが開かれたタイミングでItemStackの持っているNBTからアイテムを読み込んでいる
     */
  //  @Override
   /* public void openInventory()
    {
    	//System.out.println(String.format("1"));
        if(!currentItem.hasTagCompound())
        {
            currentItem.setTagCompound(new NBTTagCompound());
            currentItem.getTagCompound().setTag("Items", new NBTTagList());
        }
        if(currentItem.getTagCompound().getTag("Items") == null)
        {
            currentItem.setTagCompound(new NBTTagCompound());
            currentItem.getTagCompound().setTag("Items", new NBTTagList());
        }
        NBTTagList tags = (NBTTagList)currentItem.getTagCompound().getTag("Items");
 
        for(int i = 0; i < tags.tagCount(); i++)//133
        {
            NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
            int slot = tagCompound.getByte("Slot");
            if(slot >= 0 && slot < stackList.size())
            {
               // items[slot] = new ItemStack((NBTTagCompound)tagCompound);
                stackList.set(slot, new ItemStack((NBTTagCompound)tagCompound));
            }
        }
        //System.out.println(String.format("2"));
    }*/
 
    /*
        Containerを閉じるときに保存
     */
  //  @Override
   /* public void closeInventory()
    {
        NBTTagList tagList = new NBTTagList();
        for(int i = 0; i < stackList.size(); i++)
        {
        	ItemStack itemstack = this.getStackInSlot(i);

            if (!itemstack.isEmpty())
            {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setByte("Slot", (byte)i);
                itemstack.writeToNBT(compound);
                tagList.appendTag(compound);
            }
        }
        ItemStack result = new ItemStack(currentItem.getItem(), 1);
        result.setTagCompound(new NBTTagCompound());
        result.getTagCompound().setTag("Items", tagList);
 
        //ItemStackをセットする。NBTは右クリック等のタイミングでしか保存されないため再セットで保存している。
        //inventoryPlayer.mainInventory.get(inventoryPlayer.currentItem) = result;
        inventoryPlayer.mainInventory.set(inventoryPlayer.currentItem, result);
       // inventoryPlayer.mainInventory[inventoryPlayer.currentItem] = result;
        System.out.println(String.format("close"));
    }*/
 
    @Override
	public void openInventory(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ
    	//System.out.println(String.format("1"));
        if(!currentItem.hasTagCompound())
        {
            currentItem.setTagCompound(new NBTTagCompound());
            currentItem.getTagCompound().setTag("Items", new NBTTagList());
        }
        if(currentItem.getTagCompound().getTag("Items") == null)
        {
            //currentItem.setTagCompound(new NBTTagCompound());
            currentItem.getTagCompound().setTag("Items", new NBTTagList());
        }
        NBTTagList tags = (NBTTagList)currentItem.getTagCompound().getTag("Items");
 
        for(int i = 0; i < tags.tagCount(); i++)//133
        {
            NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
            int slot = tagCompound.getByte("Slot");
            if(slot >= 0 && slot < stackList.size())
            {
               // items[slot] = new ItemStack((NBTTagCompound)tagCompound);
                stackList.set(slot, new ItemStack((NBTTagCompound)tagCompound));
            }
        }
        Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(currentItem);
        for (Enchantment enchantment1 : map1.keySet())
        {
            if (enchantment1 != null)
            {
            	EnchantmentHelper.setEnchantments(map1, currentItem);
            	//System.out.println(String.format("en"));
            }
        }
        currentItem.setItemDamage(currentItem.getItemDamage());
        //System.out.println(String.format("open"));
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.stackList.size(); i++) {
		      if (!this.stackList.get(i).isEmpty())
		      {
		        NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.stackList.get(i).writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
		      }
		    }
        ItemStack result = new ItemStack(currentItem.getItem(), 1);
        result.setTagCompound(new NBTTagCompound());//20/08/13これいる？→いる
        result.getTagCompound().setTag("Items", nbttaglist);
        Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(currentItem);
        for (Enchantment enchantment1 : map1.keySet())
        {
            if (enchantment1 != null)
            {
            	EnchantmentHelper.setEnchantments(map1, result);
            	//System.out.println(String.format("enset"));
            }
        }
        //ItemStackをセットする。NBTは右クリック等のタイミングでしか保存されないため再セットで保存している。
        //inventoryPlayer.mainInventory.get(inventoryPlayer.currentItem) = result;
        result.setItemDamage(currentItem.getItemDamage());
        inventoryPlayer.mainInventory.set(inventoryPlayer.currentItem, result);
       // inventoryPlayer.mainInventory[inventoryPlayer.currentItem] = result;
       // System.out.println(String.format("close"));
	}
    
    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	

	@Override
	public int getField(int id) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public int getFieldCount() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void clear() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public boolean isEmpty() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
 
}