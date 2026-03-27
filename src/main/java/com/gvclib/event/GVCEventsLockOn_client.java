package gvclib.event;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.item.ItemGunBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

public class GVCEventsLockOn_client {
	
	
	private static final ResourceLocation lockon = new ResourceLocation("gvclib:textures/marker/lockon.png");
	private static final ResourceLocation lock = new ResourceLocation("gvclib:textures/marker/lock.png");
    private static final IModelCustom doll_class = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/marker/class.mqo"));
    
    
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	  public void renderLockOn(RenderLivingEvent.Post event)
	  {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.player;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
		Entity target = event.getEntity();
		if(target != null && target instanceof EntityLivingBase) {
			NBTTagCompound target_nbt = target.getEntityData();
			if(target_nbt != null && target_nbt.getInteger("lockon") > 0){
				if (entityplayer.getRidingEntity() instanceof EntityGVCLivingBase && entityplayer.getRidingEntity() != null) {// 1
					EntityGVCLivingBase vehicle = (EntityGVCLivingBase) entityplayer.getRidingEntity();
					if(vehicle.mitarget == target) {
						render(event, minecraft, target, entityplayer, lockon);
					}
				}
				if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase) {
					ItemGunBase gun = (ItemGunBase) itemstack.getItem();
					if (itemstack.hasTagCompound()) {
						NBTTagCompound nbt = itemstack.getTagCompound();
						if (nbt.getInteger("missile_target") != 0) {
							Entity tgtEntity = entityplayer.world.getEntityByID(nbt.getInteger("missile_target"));
							if (tgtEntity == target) {
								render(event, minecraft, target, entityplayer, lockon);
							}
						}
					}
				}
			}
		}
	  }
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	  public void renderVehicle_Rader(RenderLivingEvent.Post event)
	  {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.player;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
		Entity target = event.getEntity();
		if(target != null && target instanceof EntityLivingBase) {
			if (entityplayer.getRidingEntity() instanceof EntityGVCLivingBase && entityplayer.getRidingEntity() != null) {// 1
				EntityGVCLivingBase vehicle = (EntityGVCLivingBase) entityplayer.getRidingEntity();
				if(vehicle != target && vehicle.aarader && target instanceof IMob) {
					BlockPos bp = target.world.getHeight(new BlockPos(target.posX, target.posY, target.posZ));
					if(target.posY > bp.getY() + 10 && !target.onGround)
					{
						render(event, minecraft, target, entityplayer, lock);
					}
				}
				if(vehicle != target && vehicle.asrader && target instanceof IMob) {
					BlockPos bp = target.world.getHeight(new BlockPos(target.posX, target.posY, target.posZ));
					boolean flag = vehicle.getEntitySenses().canSee(target);
					if(target.posY < bp.getY() + 5 && flag)
					{
						render(event, minecraft, target, entityplayer, lock);
					}
				}
			}
		}
			/*if (entityplayer.getRidingEntity() instanceof EntityGVCLivingBase && entityplayer.getRidingEntity() != null) {// 1
				EntityGVCLivingBase vehicle = (EntityGVCLivingBase) entityplayer.getRidingEntity();
				//if(vehicle.mitarget != null) 
				if(vehicle.aarader){
					List<Entity> llist = vehicle.world.getEntitiesWithinAABBExcludingEntity(vehicle,
							vehicle.getEntityBoundingBox().expand(vehicle.motionX, vehicle.motionY, vehicle.motionZ).grow(120));
					if (llist != null) {
						for (int lj = 0; lj < llist.size(); lj++) {
							Entity entity1 = (Entity) llist.get(lj);
							if (entity1.canBeCollidedWith()) {
								if(entity1 == target  && vehicle != target){
									if (entity1 instanceof IMob && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && vehicle.getHealth() > 0.0F) 
									{
										BlockPos bp = entity1.world.getHeight(new BlockPos(entity1.posX, entity1.posY, entity1.posZ));
										if(entity1.posY > bp.getY() + 10 && !entity1.onGround)
										{
											render(event, minecraft, entity1, entityplayer, lock);
										}
									}
								}
							}
						}
					}
				}
			}
		}*/
	  }
	
	private void render(RenderLivingEvent.Post event, Minecraft minecraft, Entity entity, EntityPlayer entityplayer, ResourceLocation resource) {
//			 System.out.println("3");
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			// GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
			GL11.glTranslatef((float) event.getX(), (float) event.getY(), (float) event.getZ());
			RenderManager manager = minecraft.getRenderManager();
			GlStateManager.rotate(-manager.playerViewY, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate((float)(manager.options.thirdPersonView == 2 ? -1 : 1) * manager.playerViewX, 1.0F, 0.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			{
				GlStateManager.depthFunc(519);
				GlStateManager.disableFog();
				// this.entityOutlineFramebuffer.bindFramebuffer(false);
				RenderHelper.disableStandardItemLighting();
				event.getRenderer().getRenderManager().setRenderOutlines(true);
				// GL11.glutInitDisplayMode(GL11.GLUT_SINGLE | GL11.GLUT_RGBA |
				// GL11.GLUT_DEPTH);
				// GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				// GL11.glEnable(GL11.GL_DEPTH_TEST);

				GL11.glTranslatef(0, entity.height / 2, 0);
				GL11.glScalef(2.0f * entity.height, 2.0f * entity.height, 2.0f * entity.height);
				double d5 = entity.posX - entityplayer.posX;
				double d7 = entity.posZ - entityplayer.posZ;
				double d6 = entity.posY - entityplayer.posY;
				double ddx = Math.abs(d5);
				double ddz = Math.abs(d7);
				double dxz = MathHelper.sqrt(d5 * d5 + d7 * d7);
				double dxzy = MathHelper.sqrt(dxz * dxz + d6 * d6);
				GL11.glScalef(1.0F + ((float) dxzy * 0.05F), 1.0F + ((float) dxzy * 0.05F),
						1.0F + ((float) dxzy * 0.05F));
				GlStateManager.disableLighting();
				Minecraft.getMinecraft().renderEngine.bindTexture(resource);
				doll_class.renderPart("mat1");
				GlStateManager.enableLighting();

				// GL11.glDisable(GL11.GL_DEPTH_TEST);
				event.getRenderer().getRenderManager().setRenderOutlines(false);
				RenderHelper.enableStandardItemLighting();
				GlStateManager.depthMask(false);
				// this.entityOutlineShader.render(event.getPartialRenderTick());
				GlStateManager.enableLighting();
				GlStateManager.depthMask(true);
				GlStateManager.enableFog();
				GlStateManager.enableBlend();
				GlStateManager.enableColorMaterial();
				GlStateManager.depthFunc(515);
				GlStateManager.enableDepth();
				GlStateManager.enableAlpha();
			}
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			// GL11.glPopAttrib();
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
	}
	
}
