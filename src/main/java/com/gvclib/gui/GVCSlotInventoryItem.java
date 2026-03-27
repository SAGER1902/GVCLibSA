package gvclib.gui;
 
import gvclib.item.ItemAttachment;
import gvclib.item.ItemGunBase;
import gvclib.item.ItemGun_AR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
 
public class GVCSlotInventoryItem extends Slot
{
    public GVCSlotInventoryItem(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_)
    {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }
	
    @Override
    public boolean canTakeStack(EntityPlayer entityplayer)
    {
        boolean flag = false;
        if(getHasStack() && getStack().getItem() instanceof ItemGunBase) {
        	ItemGunBase gun = (ItemGunBase) getStack().getItem();
        	if(gun.gun_can_set_underbarrel && entityplayer.getHeldItemMainhand() != getStack()) {
        		flag = true;
        	}
        }
        if(getHasStack() && getStack().getItem() instanceof ItemAttachment) {
        	flag = true;
        }
    	return flag;
    }
}