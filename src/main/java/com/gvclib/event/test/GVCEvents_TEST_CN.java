package gvclib.event.test;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.mod_GVCLib;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import firesupport.entity.EntitySoldierBase;
import gvclib.entity.living.EntityGVCLivingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
import net.minecraftforge.fml.common.Optional;
public class GVCEvents_TEST_CN {
	
	private static final ResourceLocation class_0 = new ResourceLocation("gvclib:textures/marker/marker0.png");
	private static final ResourceLocation class_1 = new ResourceLocation("gvclib:textures/marker/marker1.png");
	private static final ResourceLocation class_2 = new ResourceLocation("gvclib:textures/marker/marker2.png");
	private static final ResourceLocation class_3 = new ResourceLocation("gvclib:textures/marker/marker3.png");
    private static final IModelCustom doll_class = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/marker/marker.obj"));

	@Optional.Method(modid = "firesupport")//
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void rendergunthird(RenderLivingEvent.Post event) {
		EntityLivingBase entity = (EntityLivingBase) event.getEntity();
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityPlayer entityplayer = minecraft.player;

		if(entity instanceof  EntitySoldierBase && entity.getHealth() > 0.0F){
			EntitySoldierBase gvcentity = (EntitySoldierBase) entity;
			GL11.glPushMatrix();
			GL11.glTranslatef((float) event.getX(), (float) event.getY(), (float) event.getZ());
			RenderManager manager = minecraft.getRenderManager();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			if(gvcentity.getOwner() != null){
				GL11.glTranslatef(0, entity.height + 0.5F, 0);
				GlStateManager.disableLighting();
				if (gvcentity.getMoveT() == 1 ) {
					Minecraft.getMinecraft().renderEngine.bindTexture(class_1);
				}else if (gvcentity.getMoveT() == 2) {
					Minecraft.getMinecraft().renderEngine.bindTexture(class_2);
				}else if (gvcentity.getMoveT() == 3) {
					Minecraft.getMinecraft().renderEngine.bindTexture(class_3);
				}else {
					Minecraft.getMinecraft().renderEngine.bindTexture(class_0);
				}
				doll_class.renderPart("mat1");
				GlStateManager.enableLighting();
			}
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			//GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onUPEvent(LivingUpdateEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		
		float bai = 1F;
		
		boolean flag = false;//使用控制器时将其设置为真
		
		if(flag && target != null && target.world.isRemote) {
			//if(target.world.getWorldTime() %5 == 0) 
			{
				if (mod_GVCLib.proxy.left()) {
					mod_GVCLib.test_x = mod_GVCLib.test_x + bai;
					String dd = String.format("%1$.2f", mod_GVCLib.test_x);
					System.out.println("x : " + String.format(dd));
				}
				if (mod_GVCLib.proxy.right()) {
					mod_GVCLib.test_x = mod_GVCLib.test_x - bai;
					String dd = String.format("%1$.2f", mod_GVCLib.test_x);
					System.out.println("x : " + String.format(dd));
				}
				if (mod_GVCLib.proxy.up()) {
					mod_GVCLib.test_y = mod_GVCLib.test_y + bai;
					String dd = String.format("%1$.2f", mod_GVCLib.test_y);
					System.out.println("y : " + String.format(dd));
				}
				if (mod_GVCLib.proxy.down()) {
					mod_GVCLib.test_y = mod_GVCLib.test_y - bai;
					String dd = String.format("%1$.2f", mod_GVCLib.test_y);
					System.out.println("y : " + String.format(dd));
				}
				if (mod_GVCLib.proxy.LBRACKET()) {
					mod_GVCLib.test_z = mod_GVCLib.test_z + bai;
					String dd = String.format("%1$.2f", mod_GVCLib.test_z);
					System.out.println("z : " + String.format(dd));
				}
				if (mod_GVCLib.proxy.RBRACKET()) {
					mod_GVCLib.test_z = mod_GVCLib.test_z - bai;
					String dd = String.format("%1$.2f", mod_GVCLib.test_z);
					System.out.println("z : " + String.format(dd));
				}
			}
		}
	}
}
