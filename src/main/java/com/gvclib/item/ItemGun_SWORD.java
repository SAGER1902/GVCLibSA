package gvclib.item;

import com.google.common.collect.Multimap;

import gvclib.item.gunbase.IGun_Sword;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;


	public class ItemGun_SWORD extends ItemGunBase implements IGun_Sword{
		public static String ads;
		
		public ItemGun_SWORD() {
			super();
			this.maxStackSize = 1;
		}
		
		public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	    {
			EntityPlayer entityplayer = (EntityPlayer)entity;
			int s;
			int li = getMaxDamage() - itemstack.getItemDamage();
			boolean lflag = cycleBolt(itemstack);
			boolean var5 = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0;
			Item item = itemstack.getItem();
			ItemStack item_m = entityplayer.getHeldItemMainhand();
			ItemStack item_o = entityplayer.getHeldItemOffhand();
			
			NBTTagCompound nbt = itemstack.getTagCompound();
			boolean recoiled = nbt.getBoolean("Recoiled");
			int recoiledtime = nbt.getInteger("RecoiledTime");
			{
				boolean cocking = nbt.getBoolean("Cocking");
				int cockingtime = nbt.getInteger("CockingTime");
				if(!cocking && flag){
					++cockingtime;
					nbt.setInteger("CockingTime", cockingtime);
					if(cockingtime == 2 && flag && !this.semi){
						//world.playSoundAtEntity(entityplayer, this.soundcock, 1.0F, 1.0F);
						world.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
								this.fires_cock, SoundCategory.NEUTRAL, 1.0F, 1.0F);
					}
					if(cockingtime > cocktime && flag){
						nbt.setInteger("CockingTime", 0);
						nbt.setBoolean("Cocking", true);
					}
				}
			}
			
			{
				if(!recoiled){
					++recoiledtime;
					nbt.setInteger("RecoiledTime", recoiledtime);
					if(recoiledtime > 0){
						nbt.setInteger("RecoiledTime", 0);
						nbt.setBoolean("Recoiled", true);
					}
				}
			}
			
			
			{
				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
				if (itemstack.getItemDamage() == itemstack.getMaxDamage() && flag && entityplayer.inventory.hasItemStack(new ItemStack(this.magazine))) {
						if (entity != null && entity instanceof EntityPlayer) {
							if (itemstack == entityplayer.getHeldItemMainhand()) {
								int reloadti = nbt.getInteger("RloadTime");
								{
									gun.retime = reloadti;
									++reloadti;
									if (reloadti == gun.reloadtime) {
										gun.retime = reloadti = 0;
										nbt.setInteger("RloadTime", 0);
										{
										getReload(itemstack, world, entityplayer);
										}
										gun.resc = 0;
									}else{
										nbt.setInteger("RloadTime", reloadti);
									}
								}
							}
						}
				}
			}
			if(!world.isRemote && this.retime == 2 && flag){
				world.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
						this.fires_reload, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			}

			if (flag) {
				entityplayer.motionX = entityplayer.motionX * this.motion;
				//entityplayer.motionY = entityplayer.motionY * 0.1;
				entityplayer.motionZ = entityplayer.motionZ * this.motion;
			}
			
			if (flag && !itemstack.isEmpty() && entityplayer != null) {
				if(itemstack.getItem() instanceof ItemGunBase) {
					ItemGunBase gun = (ItemGunBase) itemstack.getItem();
					this.Attachment(gun, itemstack, world, entityplayer, i, flag);
				}
			}
			super.onUpdate(itemstack, world, entity, i, flag);
	    }
		
		@Override
		public byte getCycleCountrecoilre(ItemStack pItemstack) {
			return 3;
		}
		
		
		public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityLivingBase par3EntityPlayer, int par4){
        }
		
		public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	    {
			boolean flag = this.func_185060_a(playerIn) != null;

	        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, hand, flag);
	        if (ret != null) return ret;

	        if (!playerIn.capabilities.isCreativeMode && !flag)
	        {
	            return !flag ? new ActionResult(EnumActionResult.FAIL, itemStackIn) : new ActionResult(EnumActionResult.PASS, itemStackIn);
	        }
	        else
	        {
	            playerIn.setActiveHand(hand);
	            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	        }
	    }

        public void FireBullet(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) 
		{
        	//par1ItemStack.damageItem(1, par3EntityPlayer);
		}
		
		public void getReload(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) 
		{
			//par2World.playSound((EntityPlayer)null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ,
        	//		GVCSoundEvent.Reload, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			
			int li = getMaxDamage() - par1ItemStack.getItemDamage();
			boolean linfinity = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, par1ItemStack) > 0;
			ItemStack item = this.func_185060_a(par3EntityPlayer);
			if(!par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItemStack(new ItemStack(this.magazine))){
			        par1ItemStack.damageItem(li, par3EntityPlayer);
					setDamage(par1ItemStack, -this.getMaxDamage());
					if (!linfinity) {
						item.shrink(1);
						if (item.isEmpty())
		                {
							par3EntityPlayer.inventory.deleteStack(item);
		                }
					}
			}
		}
    
		public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	    {
	        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

	        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
	        {
	            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
	            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
	        }

	        return multimap;
	    }
		
}
