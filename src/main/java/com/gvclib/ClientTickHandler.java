package gvclib;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import gvclib.RenderParameters;
import gvclib.ClientProxyGVClib;
import net.minecraft.client.Minecraft;
import gvclib.mod_GVCLib;
import static gvclib.RenderParameters.*;
import gvclib.item.ItemGunBase;
import org.lwjgl.input.Mouse;
import net.minecraft.util.EnumHand;

import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import gvclib.util.ForgeEvent;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.client.FMLClientHandler;
public class ClientTickHandler extends ForgeEvent {
    public ClientTickHandler() {
    }
	
    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
		onClientTickStart(Minecraft.getMinecraft());
        onClientTickEnd(Minecraft.getMinecraft());
    }
	
    public void onClientTickStart(Minecraft minecraft) {
        if (minecraft.player == null || minecraft.world == null)
            return;
		
        GUN_ROT_X_LAST = GUN_ROT_X;
        GUN_ROT_Y_LAST = GUN_ROT_Y;
        GUN_ROT_Z_LAST = GUN_ROT_Z;
		
		Minecraft mc = FMLClientHandler.instance().getClient();
		
        if (mc.getRenderViewEntity() != null) {
            if (mc.getRenderViewEntity().getRotationYawHead() > mc.getRenderViewEntity().prevRotationYaw) {
                GUN_ROT_X += (mc.getRenderViewEntity().getRotationYawHead() - mc.getRenderViewEntity().prevRotationYaw) / 1.5;
            } else if (mc.getRenderViewEntity().getRotationYawHead() < mc.getRenderViewEntity().prevRotationYaw) {
                GUN_ROT_X -= (mc.getRenderViewEntity().prevRotationYaw - mc.getRenderViewEntity().getRotationYawHead()) / 1.5;
            }
            if (mc.getRenderViewEntity().rotationPitch > prevPitch) {
                GUN_ROT_Y += (mc.getRenderViewEntity().rotationPitch - prevPitch) / 5;
            } else if (mc.getRenderViewEntity().rotationPitch < prevPitch) {
                GUN_ROT_Y -= (prevPitch - mc.getRenderViewEntity().rotationPitch) / 5;
            }
            prevPitch = mc.getRenderViewEntity().rotationPitch;
        }

        GUN_ROT_X *= .2F;
        GUN_ROT_Y *= .2F;
        GUN_ROT_Z *= .2F;

        if (GUN_ROT_X > 20) {
            GUN_ROT_X = 20;
        } else if (GUN_ROT_X < -20) {
            GUN_ROT_X = -20;
        }

        if (GUN_ROT_Y > 20) {
            GUN_ROT_Y = 20;
        } else if (GUN_ROT_Y < -20) {
            GUN_ROT_Y = -20;
        }
    }
	
	//顺序执行，从上往下
    public void onClientTickEnd(Minecraft minecraft) {//客户端tick事件
        if (minecraft.player == null || minecraft.world == null)//玩家/世界为空时停止加载
            return;

        EntityPlayerSP player = minecraft.player;//单机玩家 SinglePlayer

        if (playerRecoilPitch > 0)// 抬头值 0.8速度减少   上下
            playerRecoilPitch *= 0.8F;

        if (playerRecoilYaw > 0)// 转向值 0.8速度减少    左右
            playerRecoilYaw *= 0.8F; 
		
		if (cockRecoil>0)//枪械前后动画需要的值 0.8速度减少
			cockRecoil *= 0.8F;
			
		if (amination_bulllet>10)//动画弹链需要的值
			amination_bulllet = 0;

		if(bulletstart)++RenderParameters.amination_bulllet;//动画弹链需要的值
		
        player.rotationPitch -= playerRecoilPitch;//玩家执行 抬头   上下
        player.rotationYaw -= playerRecoilYaw;//玩家执行 转向    左右
		
        antiRecoilPitch += playerRecoilPitch;//反向抬头值增加 抬头   上下
        antiRecoilYaw += playerRecoilYaw;//反向转向值增加 转向    左右

        player.rotationPitch += antiRecoilPitch * 0.25F;//玩家执行 反向抬头   上下
        player.rotationYaw += antiRecoilYaw * 0.25F;//玩家执行 反向转向   左右
		
        antiRecoilPitch *= 0.725F;//反向抬头值 0.725速度减少   上下
        antiRecoilYaw *= 0.75F;//反向转向值 0.75速度减少   左右
		
		bulletstart = false;//动画弹链需要的值
    }
}
