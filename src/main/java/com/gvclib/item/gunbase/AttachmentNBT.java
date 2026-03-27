package gvclib.item.gunbase;

import java.io.IOException;
import java.io.InputStreamReader;

import gvclib.mod_GVCLib;
import gvclib.gui.GVCInventoryItem;
import gvclib.item.ItemAttachmentBase;
import gvclib.item.ItemGun_SWORD;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.resources.IResource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import objmodel.AdvancedModelLoader;

public class AttachmentNBT {

	public static void load(ItemAttachmentBase gun, ItemStack itemstack, World world, EntityPlayer entityplayer, int i, boolean flag) {

		/*NBTTagCompound nbt = itemstack.getTagCompound();
		
		if(!itemstack.hasTagCompound())
		{
			itemstack.setTagCompound(new NBTTagCompound());
			itemstack.getTagCompound().setTag("Plugins", new NBTTagList());
		}
		
		if(!itemstack.hasTagCompound())return;
		
		if(!flag)return;
		
		if(nbt!=null){
			String id = nbt.getString("name_id");
			if(gun.name_id == id){
				nbt.setInteger("p_id", gun.p_id);
				nbt.setInteger("p_level", gun.p_level);
				nbt.setInteger("p_time", gun.p_time);
				nbt.setString("bullet_name", gun.bullet_name);
				
				nbt.setFloat("exlevel", gun.exlevel);
				nbt.setInteger("powor", (int)gun.power);
				nbt.setInteger("bulletDameID", gun.bulletid);
				nbt.setInteger("bullet_solt_id", gun.bullet_solt_id);
				nbt.setInteger("extra_power", (int)gun.extra_power);
			}
		}*/

		//inventory.closeInventory(entityplayer);
	}
	public static void read(ItemAttachmentBase gun, ItemStack itemstack, World world, EntityPlayer entityplayer, int i, boolean flag) {

		NBTTagCompound nbt = itemstack.getTagCompound();
		
		/*if(!itemstack.hasTagCompound())
		{
			itemstack.setTagCompound(new NBTTagCompound());
			itemstack.getTagCompound().setTag("Plugins", new NBTTagList());
		}*/
		
		/*if(!itemstack.hasTagCompound())return;
		
		if(!flag)return;
		
		if(nbt!=null){
			String id = nbt.getString("name_id");
			if(gun.name_id == id){
				gun.power = nbt.getInteger("powor");
				gun.bulletid = nbt.getInteger("bulletDameID");
				gun.bullet_solt_id = nbt.getInteger("bullet_solt_id");
				gun.exlevel = nbt.getFloat("exlevel");
				gun.p_id = nbt.getInteger("p_id");
				gun.p_level = nbt.getInteger("p_level");
				gun.p_time = nbt.getInteger("p_time");
				gun.bullet_name = nbt.getString("bullet_name");
				gun.extra_power = nbt.getInteger("extra_power");
			}
		}*/

		//inventory.closeInventory(entityplayer);
	}
}
