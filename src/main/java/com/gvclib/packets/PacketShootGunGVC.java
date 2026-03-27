package gvclib.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import gvclib.item.ItemGunBase;
import gvclib.mod_GVCLib;
import gvclib.item.ItemGun_AR;
import net.minecraft.nbt.NBTTagCompound;
/**
 * Tells the server that the player who sent this message wants to shoot with his current gun
 *
 */
public class PacketShootGunGVC implements IMessage {
	public boolean isZooming=false;
	public boolean offHand=false;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.isZooming=buf.readBoolean();
		this.offHand=buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(isZooming);
		buf.writeBoolean(offHand);
	}

	public PacketShootGunGVC() {
	}

	public EnumHand getHand(){
		return offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
	}
	
	public PacketShootGunGVC(boolean isZooming,EnumHand hand) {
		this.isZooming = isZooming;
		this.offHand = hand == EnumHand.OFF_HAND;
	}

	public static class Handler implements IMessageHandler<PacketShootGunGVC, IMessage> {
		@Override
		public IMessage onMessage(PacketShootGunGVC message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketShootGunGVC message, MessageContext ctx) {
						
			EntityPlayer ply = mod_GVCLib.getPlayerFromContext(ctx);
			ItemStack stack = ply.getHeldItem(message.getHand());
			if(!stack.isEmpty() && stack.getItem() instanceof ItemGunBase){
				//((IGenericGun) stack.getItem()).shootGunPrimary(stack, ply.world, ply, message.isZooming, message.getHand(), null);
				NBTTagCompound nbt = stack.getTagCompound();
				ItemGunBase gun = (ItemGunBase) stack.getItem();
				int li = gun.getMaxDamage() - stack.getItemDamage();
				//gun.Fire_Base(stack, ply.world, ply, li, false, nbt);
				if(gun instanceof ItemGun_AR){
					//mod_GVCLib.proxy.onShootAnimation(ply, stack);
					gun.Fire_AR_Left(stack, ply.world, ply, li, true, true);
					//gun.Fire_AR(stack, ply.world, ply, li, true);
					//gun.FireBullet(stack, ply.world, ply);
				}else{
					//mod_GVCLib.proxy.onShootAnimation(ply, stack);
					gun.Fire_SR_Left(stack, ply.world, ply, li, true, true);
					//gun.Fire_SR(stack, ply.world, ply, li, true);
					//gun.FireBullet(stack, ply.world, ply);
				}
			}
		}
	}
}
