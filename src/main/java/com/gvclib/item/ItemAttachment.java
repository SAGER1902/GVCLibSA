package gvclib.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import objmodel.IModelCustom;
import net.minecraft.entity.Entity;
import gvclib.item.ItemAttachment;
import gvclib.mod_GVCLib;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;
public class ItemAttachment extends ItemAttachmentBase
{
    public ItemAttachment()
    {
		super();
        this.maxStackSize = 1;
    }

	/*public int cooltime=0;//物品使用频率
	
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
    {
		EntityPlayer entityplayer = (EntityPlayer) entity;
		boolean kv = mod_GVCLib.proxy.keyv();
		boolean kb = mod_GVCLib.proxy.keyb();
		
		if(cooltime<10){
			++cooltime;
		}
		
		if (!itemstack.isEmpty() && entityplayer != null) {//配件【判定
			if(itemstack.getItem() instanceof ItemAttachmentBase) {
				ItemAttachmentBase gun = (ItemAttachmentBase) itemstack.getItem();
				if(flag && kv && this.cooltime==10){
					entityplayer.sendMessage(new TextComponentTranslation("NBT列表成功添加", new Object[0]));
					this.AddNBT(gun, itemstack, world, entityplayer, i, flag);
					entityplayer.getCooldownTracker().setCooldown(this, 10);
					this.cooltime=0;
				}
				NBTTagCompound nbt = itemstack.getTagCompound();
				if(nbt!=null){
					String id = nbt.getString("name_id");
					if(id.equals("")) {
						String time = String.valueOf(world.getWorldTime());
						nbt.setString("name_id", time);
					}
					if(!id.equals("")){
						if(!this.hasNBT)this.name_id = id;
						this.hasNBT = true;
						this.readNBT(gun, itemstack, world, entityplayer, i, flag);
					}
				}
			}
		}
		super.onUpdate(itemstack, world, entity, i, flag);
	}*/
}