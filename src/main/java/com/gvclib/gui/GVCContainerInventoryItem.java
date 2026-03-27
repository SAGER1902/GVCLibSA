package gvclib.gui;
 

import gvclib.item.ItemGunBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
 
public class GVCContainerInventoryItem extends Container
{
    private GVCInventoryItem inventory;
	//private final IInventory inventory;
 
    private IInventory holderInventory;
    
    public GVCContainerInventoryItem(InventoryPlayer inventoryPlayer, ItemStack itemstack)
    {
    	inventory = new GVCInventoryItem(inventoryPlayer, itemstack);
    	//inventory = inventoryPlayer;
    	//inventory = new GVCInventoryItem(inventoryPlayer.player);
        inventory.openInventory(inventoryPlayer.player);
        if(itemstack.getItem() instanceof  ItemGunBase){
        	ItemGunBase gun = (ItemGunBase) itemstack.getItem();
            {
				int w = 0;
				int x = 20;
				int w2 = 1;
				int x2 = 9;
                //this.addSlotToContainer(new Slot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));

            	if(gun.canuse_muss)this.addSlotToContainer(new SlotGun(inventory, 3, 11+w * x, 20, itemstack));//枪口
            	if(gun.canuse_light)this.addSlotToContainer(new SlotGun(inventory, 2, 11 + (w+1) * x, 20, itemstack));//激光
            	if(gun.canuse_grip)this.addSlotToContainer(new SlotGun(inventory, 4, 11 + (w+2) * x, 20, itemstack));//下挂/握把
            	if(gun.canuse_sight)this.addSlotToContainer(new SlotGun(inventory, 1, 11 + (w+3) * x, 20, itemstack));//瞄具
            	/***************/this.addSlotToContainer(new SlotGun(inventory, 5, 11 + (w+4) * x, 20, itemstack));//弹药
				if(gun.canuse_plugin_a){
					if(gun.plugin_a_count>=4){
						this.addSlotToContainer(new SlotGun(inventory, 6, 8 + w * x, 18 + 98, itemstack));//A1
						this.addSlotToContainer(new SlotGun(inventory, 7, 8 + (w+1) * x, 18 + 98, itemstack));//A2
						this.addSlotToContainer(new SlotGun(inventory, 8, 8 + (w+2) * x, 18 + 98, itemstack));//A3
						this.addSlotToContainer(new SlotGun(inventory, 9, 8 + (w+3) * x, 18 + 98, itemstack));//A4
					}else if(gun.plugin_a_count==3){
						this.addSlotToContainer(new SlotGun(inventory, 6, 8 + w * x, 18 + 98, itemstack));//A1
						this.addSlotToContainer(new SlotGun(inventory, 7, 8 + (w+1) * x, 18 + 98, itemstack));//A2
						this.addSlotToContainer(new SlotGun(inventory, 8, 8 + (w+2) * x, 18 + 98, itemstack));//A3
					}else if(gun.plugin_a_count==2){
						this.addSlotToContainer(new SlotGun(inventory, 6, 8 + w * x, 18 + 98, itemstack));//A1
						this.addSlotToContainer(new SlotGun(inventory, 7, 8 + (w+1) * x, 18 + 98, itemstack));//A2
					}else if(gun.plugin_a_count==1){
						this.addSlotToContainer(new SlotGun(inventory, 6, 8 + w * x, 18 + 98, itemstack));//A1
					}
				}
				if(gun.canuse_plugin_b){
					if(gun.plugin_b_count>=4){
						this.addSlotToContainer(new SlotGun(inventory, 10, 10 + (w+4) * x, 18 + 98, itemstack));//B1
						this.addSlotToContainer(new SlotGun(inventory, 11, 10 + (w+5) * x, 18 + 98, itemstack));//B2
						this.addSlotToContainer(new SlotGun(inventory, 12, 10 + (w+6) * x, 18 + 98, itemstack));//B3
						this.addSlotToContainer(new SlotGun(inventory, 13, 10 + (w+7) * x, 18 + 98, itemstack));//B4
					}else if(gun.plugin_b_count==3){
						this.addSlotToContainer(new SlotGun(inventory, 10, 10 + (w+4) * x, 18 + 98, itemstack));//B1
						this.addSlotToContainer(new SlotGun(inventory, 11, 10 + (w+5) * x, 18 + 98, itemstack));//B2
						this.addSlotToContainer(new SlotGun(inventory, 12, 10 + (w+6) * x, 18 + 98, itemstack));//B3
					}else if(gun.plugin_b_count==2){
						this.addSlotToContainer(new SlotGun(inventory, 10, 10 + (w+4) * x, 18 + 98, itemstack));//B1
						this.addSlotToContainer(new SlotGun(inventory, 11, 10 + (w+5) * x, 18 + 98, itemstack));//B2
					}else if(gun.plugin_b_count==1){
						this.addSlotToContainer(new SlotGun(inventory, 10, 10 + (w+4) * x, 18 + 98, itemstack));//B1
					}
				}
            }
        }
 
        int i = 2 * 18 + 1;
        for (int j = 0; j < 3; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new GVCSlotInventoryItem(inventoryPlayer, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }
 
        for (int j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new GVCSlotInventoryItem(inventoryPlayer, j, 8 + j * 18, 161 + i));
        }

        //System.out.println(String.format("open"));
    }
 

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.inventory.isUsableByPlayer(playerIn);
    }
 
    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
    	 ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);
 
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
 
            if (p_82846_2_ < this.inventory.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true))
                {
                	return ItemStack.EMPTY;
                }
            }
            //シフトクリック時に、このアイテムだったら動かさない。
            else if(slot.getStack() != ItemStack.EMPTY && slot.getStack().getItem()instanceof ItemGunBase)
            {
            	return ItemStack.EMPTY;
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.inventory.getSizeInventory(), false))
            {
            	return ItemStack.EMPTY;
            }
            if (itemstack1.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
 
        return itemstack;
    }
 
    /*
        Containerを閉じるときに呼ばれる
     */
    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);
        this.inventory.closeInventory(p_75134_1_);
    }
    
    /**
     * Return this chest container's lower chest inventory.
     */
    public IInventory getLowerChestInventory()
    {
        return this.inventory;
    }
}