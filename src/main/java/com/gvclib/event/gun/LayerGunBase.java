package gvclib.event.gun;

import org.lwjgl.opengl.GL11;

import gvclib.mod_GVCLib;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.item.ItemGunBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

import net.minecraft.entity.player.EntityPlayer;

import gvclib.item.ItemGun_SR;
import gvclib.RenderParameters;
import static gvclib.RenderParameters.*;

import net.minecraft.nbt.NBTTagCompound;

@SideOnly(Side.CLIENT)
public class LayerGunBase extends LayerHeldItem//implements LayerRenderer<EntityLivingBase>
{
    protected final RenderLivingBase<?> livingEntityRenderer;

    private static final ResourceLocation rightt = new ResourceLocation("gvclib:textures/model/Right.png");
	private static final IModelCustom rightm = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/Right.mqo"));
	private static final ResourceLocation lasert = new ResourceLocation("gvclib:textures/model/RightLaser.png");
	private static final IModelCustom laserm = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/RightLaser.mqo"));
    
    public LayerGunBase(RenderLivingBase<?> livingEntityRendererIn)
    {
    	super(livingEntityRendererIn);
        this.livingEntityRenderer = livingEntityRendererIn;
    }

	public boolean recoiled = false;
	public int cockingtime = 0;
	public float cock = 0;
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
        ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
        ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();

		NBTTagCompound nbt = itemstack1.getTagCompound();
		NBTTagCompound nbt2 = itemstack.getTagCompound();
		if(nbt!=null){
			recoiled = nbt.getBoolean("Recoiled");
			cockingtime = nbt.getInteger("CockingTime");
			cock=cockRecoil;
		}
		
        if (!itemstack.isEmpty() || !itemstack1.isEmpty())
        {
            GlStateManager.pushMatrix();

            if (this.livingEntityRenderer.getMainModel().isChild)
            {
                float f = 0.5F;
                GlStateManager.translate(0.0F, 0.625F, 0.0F);
                GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
            }
            
            this.renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            this.renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            GlStateManager.popMatrix();
            
        }
    }

    //public static int gllist =  GLAllocation.generateDisplayLists(1);
    
    private void renderHeldItem(EntityLivingBase useliving, ItemStack itemstack, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide)
    {
        if (!itemstack.isEmpty())
        {
            GlStateManager.pushMatrix();
            if (useliving.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            if (useliving instanceof EntityGVCLivingBase && useliving != null) {
             	EntityGVCLivingBase en = (EntityGVCLivingBase) useliving;
             	if (en.getSneak())
                {
                    GlStateManager.translate(0.0F, 0.2F, 0.0F);
                }
            }
            // Forge: moved this call down, fixes incorrect offset while sneaking.
            ModelBiped model = (ModelBiped)this.livingEntityRenderer.getMainModel();
            model.bipedLeftArm.isHidden = false;
            model.bipedRightLeg.isHidden = false;
            ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, handSide);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            
            GL11.glScalef(0.1875F, 0.1875F, 0.1875F);
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float)((flag ? -1 : 1) / 16.0F) * -5.33F, 0.125F * 1.33F, -0.625F * -4.5F);//-5.33,-3.33,-4.5

            if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase) {//item
				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
				gun.ModelLoad();
				boolean isreload = false;
				
				if (useliving instanceof EntityGVCLivingBase && useliving != null) {//生物的弹夹显示
	             	EntityGVCLivingBase en = (EntityGVCLivingBase) useliving;
	             	if (!en.getattacktask())
	                {
	             		if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase) {//item
	             			if(gun != null && gun.arm_l_posz > -1.0F) {
	             				GlStateManager.rotate(80.0F, 0.0F, 1.0F, 0.0F);
	             				GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
	             			}
	             		}
	                }
	             	if(en.getRemain_L() <= 0){
	             		isreload = true;
	             	}
	            }
				
				if (useliving instanceof EntityPlayer && useliving != null) {//玩家的弹夹显示
	             	if(itemstack.getItemDamage() == itemstack.getMaxDamage()){//物品损坏值=物品最大耐久度
	             		isreload = true;
	             		if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase) {//item
	             			if(gun != null && gun.arm_l_posz > -1.0F) {
	             				GlStateManager.rotate(80.0F, 0.0F, 1.0F, 0.0F);
	             				GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
	             			}
	             		}
	             	}
	            }
				
				{
					//GL11.glNewList(gllist, GL11.GL_COMPILE);
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
					gun.obj_model.renderPart("mat1");
					gun.obj_model.renderPart("mat100");
					
					float cock_size = 0;
					if(gun.mat2_cock_z == 0){
						cock_size = gun.model_cock_z * 0.5F;
					}else{
						cock_size = gun.mat2_cock_z;
					}
					
					if (useliving instanceof EntityPlayer){
						GL11.glTranslatef(0.0F, 0.0F, cock*cock_size * 0.5F);
						gun.obj_model.renderPart("mat2");
						{
							if(gun.rendering_light) {
								GL11.glDisable(GL11.GL_LIGHT1);
								GL11.glDisable(GL11.GL_LIGHTING);
							}
							gun.obj_model.renderPart("mat2_dot");
							if(gun.rendering_light) {
								GL11.glEnable(GL11.GL_LIGHTING);
								GL11.glEnable(GL11.GL_LIGHT1);
							}
						}
						if(gun.mat2) {
							Layermat.rendersight(useliving, itemstack, gun);
						}
						GL11.glTranslatef(0.0F, 0.0F, cock*-cock_size * 0.5F);
					}else{
						gun.obj_model.renderPart("mat2");
					}
					
					if(!isreload)gun.obj_model.renderPart("mat3");
					
					Layermat.rendersight( useliving,  itemstack,  gun);
					Layermat.renderattachment( useliving,  itemstack,  gun);
					//gun.obj_model.renderPart("mat25");
					
					if (useliving instanceof EntityPlayer){
						if(bulletstart){
							if(amination_bulllet<2){//向上
								aminationbullet(1,1);
								aminationbullet(6,2);
								aminationbullet(5,3);
								aminationbullet(4,4);
								aminationbullet(3,5);
								aminationbullet(2,6);
							}else if(amination_bulllet<4){//向上
								aminationbullet(1,2);
								aminationbullet(6,3);
								aminationbullet(5,4);
								aminationbullet(4,5);
								aminationbullet(3,6);
								aminationbullet(2,1);
							}else if(amination_bulllet<6){//向右上
								aminationbullet(1,3);
								aminationbullet(6,4);
								aminationbullet(5,5);
								aminationbullet(4,6);
								aminationbullet(3,1);
								aminationbullet(2,2);
							}else if(amination_bulllet<8){//向右上
								aminationbullet(1,4);
								aminationbullet(6,5);
								aminationbullet(5,6);
								aminationbullet(4,1);
								aminationbullet(3,2);
								aminationbullet(2,3);
							}else if(amination_bulllet<10){//向右
								aminationbullet(1,5);
								aminationbullet(6,6);
								aminationbullet(5,1);
								aminationbullet(4,2);
								aminationbullet(3,3);
								aminationbullet(2,4);
							}else{//复位
								aminationbullet(1,6);
								aminationbullet(6,1);
								aminationbullet(5,2);
								aminationbullet(4,3);
								aminationbullet(3,4);
								aminationbullet(2,5);
							}
						}
							
						renderbullet(1,gun);
						renderbullet(2,gun);
						renderbullet(3,gun);
						renderbullet(4,gun);
						renderbullet(5,gun);
						renderbullet(6,gun);
					}

					if (useliving instanceof EntityPlayer){
						if (/*!recoiled*/playerRecoilPitch >0.1F)
						{
							Layermat.mat31mat32(gun, itemstack, true);//加特林旋转
							if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
								Layermat.mat25(itemstack, gun, false, cockingtime);
							}else {
								Layermat.mat25(itemstack, gun, true, cockingtime);
							}
							if(!gun.mat2) {
								Layermat.rendersight(useliving, itemstack, gun);
							}
						}else{
							Layermat.mat31mat32(gun, itemstack, false);//加特林旋转
							if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
								Layermat.mat25(itemstack, gun, false, cockingtime);
								Layermat.rendersight(useliving, itemstack, gun);
							}
						}
					}else{
						gun.obj_model.renderPart("mat31");
						gun.obj_model.renderPart("mat32");
					}

				}
            }
            GlStateManager.popMatrix();
        }
    }

	public static void aminationbullet(int num, int id){
		float size = 0.3F;
		if(id == 1)RenderParameters.bulllet_y[num] += 0.05F*size;//1
		if(id == 2)RenderParameters.bulllet_y[num] += 0.05F*size;//2
		if(id == 3){
			RenderParameters.bulllet_x[num] -= 0.05F*size;//3
			RenderParameters.bulllet_y[num] += 0.05F*size;
		}
		if(id == 4){
			RenderParameters.bulllet_x[num] -= 0.05F*size;//4
			RenderParameters.bulllet_y[num] += 0.05F*size;
		}
		if(id == 5)RenderParameters.bulllet_x[num] -= 0.05F*size;//5
		if(id == 6){
			RenderParameters.bulllet_x[num] = 0;//6
			RenderParameters.bulllet_y[num] = 0;//6
		}
		if(bulllet_x[num]>0.52F||bulllet_y[num]>0.4F){
			RenderParameters.bulllet_x[num] = 0;
			RenderParameters.bulllet_y[num] = 0;
		}
	}
	public static void renderbullet(int num, ItemGunBase gun){
		GL11.glPushMatrix();//glstrt2
		{//弹链动画
			GL11.glTranslatef(bulllet_x[num], bulllet_y[num], bulllet_z);//0, 0, -0.4
			gun.obj_model.renderPart("bullet");
			GL11.glTranslatef(bulllet_x[num], bulllet_y[num], bulllet_z);
		}
		GL11.glPopMatrix();//glend2
	}
    
    public boolean shouldCombineTextures()
    {
        return false;
    }
}