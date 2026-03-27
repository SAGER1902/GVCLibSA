package gvclib.event.gun;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import gvclib.item.ItemGunBase;
import net.blacklab.lmr.entity.littlemaid.EntityLittleMaid;
import net.blacklab.lmr.entity.maidmodel.ModelBaseSolo;
import net.minecraft.client.Minecraft;
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

@SideOnly(Side.CLIENT)
public class LayerGunBaseLMM extends LayerHeldItem
{
    protected final RenderLivingBase<?> livingEntityRenderer;
    private static final ResourceLocation rightt = new ResourceLocation("gvclib:textures/model/Right.png");
	private static final IModelCustom rightm = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/Right.mqo"));
	private static final ResourceLocation lasert = new ResourceLocation("gvclib:textures/model/RightLaser.png");
	private static final IModelCustom laserm = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/RightLaser.mqo"));

    public LayerGunBaseLMM(RenderLivingBase<?> livingEntityRendererIn)
    {
    	super(livingEntityRendererIn);
        this.livingEntityRenderer = livingEntityRendererIn;
        //GL11.glScalef(5F, 5F, 5F);
        modelMain = (ModelBaseSolo)livingEntityRenderer.getMainModel();
        //mainModel = modelMain;
    }

    public ModelBaseSolo modelMain;
    
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
    	//GL11.glScalef(5F, 5F, 5F);
        //boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
        //ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
        //ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();

        //if (itemstack != null || itemstack1 != null)
        {
      //  	GL11.glPushMatrix();//glstart

        	EntityLittleMaid lmm = (EntityLittleMaid) entitylivingbaseIn;
        	Iterator<ItemStack> heldItemIterator = lmm.getHeldEquipment().iterator();
			int i = 0, handindexes[] = {lmm.getDominantArm(), lmm.getDominantArm() == 1 ? 0 : 1};

			while (heldItemIterator.hasNext()) {
				ItemStack itemstack = (ItemStack) heldItemIterator.next();

				if (itemstack != null && modelMain != null && modelMain.model != null)
				{
					GlStateManager.pushMatrix();

					// Use dominant arm as mainhand.
					modelMain.model.Arms[handindexes[i]].postRender(0.0625F);

					if (lmm.isSneaking()) {
						GlStateManager.translate(0.0F, 0.2F, 0.0F);
					}
					boolean flag = handindexes[i] == 1;

					GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
					GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
					/* 初期モデル構成で
					 * x: 手の甲に垂直な方向(-で向かって右に移動)
					 * y: 体の面に垂直な方向(-で向かって背面方向に移動)
					 * z: 腕に平行な方向(-で向かって手の先方向に移動)
					 */
					GL11.glScalef(0.1875F, 0.1875F, 0.1875F);
					//GlStateManager.translate((float)((flag ? -1 : 1) / 16.0F) * -5.33F, 0.125F * -3.33F, -0.625F * -4.5F);
					GlStateManager.translate(flag ? -0.0125F : 0.0125F, 0.05f, -0.15f);
		            if (itemstack != null && itemstack.getItem() instanceof ItemGunBase) {//item
						ItemGunBase gun = (ItemGunBase) itemstack.getItem();
						Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
					gun.obj_model.renderPart("mat1");
					gun.obj_model.renderPart("mat2");
					gun.obj_model.renderPart("mat3");
					rendersight( entitylivingbaseIn,  itemstack,  gun);
					renderattachment( entitylivingbaseIn,  itemstack,  gun);
					gun.obj_model.renderPart("mat25");
					gun.obj_model.renderPart("mat31");
					gun.obj_model.renderPart("mat32");/**/
					//GVCEventsGunRender.rendermat(p_188358_1_, itemstack, gun);
		            }
					GlStateManager.popMatrix();
				}

				i++;
			
        	}
            
            //this.renderHeldItem(entitylivingbaseIn, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            //this.renderHeldItem(entitylivingbaseIn, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
  //          GL11.glPopMatrix();//glend
            
        }
    }

   
    
    private void renderHeldItem(EntityLivingBase p_188358_1_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide)
    {
    	EntityLittleMaid lmm = (EntityLittleMaid) p_188358_1_;
        Iterator<ItemStack> heldItemIterator = lmm.getHeldEquipment().iterator();
    	while (heldItemIterator.hasNext()) {
			ItemStack itemstack = (ItemStack) heldItemIterator.next();
			 if (itemstack != null)
		        {
		            GlStateManager.pushMatrix();
		            if (p_188358_1_.isSneaking())
		            {
		                GlStateManager.translate(0.0F, 0.2F, 0.0F);
		            }
		            // Forge: moved this call down, fixes incorrect offset while sneaking.
		           // ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, handSide);
		            
					int i = 0, handindexes[] = {lmm.getDominantArm(), lmm.getDominantArm() == 1 ? 0 : 1};
		            modelMain.model.Arms[handindexes[i]].postRender(0.0625F);

		            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
		            //GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
		            GL11.glScalef(0.1875F, 0.1875F, 0.1875F);
		            boolean flag = handSide == EnumHandSide.LEFT;
		            GlStateManager.translate((float)((flag ? -1 : 1) / 16.0F) * -5.33F, 0.125F * -3.33F, -0.625F * -4.5F);
		            if (itemstack != null && itemstack.getItem() instanceof ItemGunBase) {//item
						ItemGunBase gun = (ItemGunBase) itemstack.getItem();
						Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
					gun.obj_model.renderPart("mat1");
					gun.obj_model.renderPart("mat2");
					gun.obj_model.renderPart("mat3");
					rendersight( p_188358_1_,  itemstack,  gun);
					renderattachment( p_188358_1_,  itemstack,  gun);
					gun.obj_model.renderPart("mat25");
					gun.obj_model.renderPart("mat31");
					gun.obj_model.renderPart("mat32");/**/
					//GVCEventsGunRender.rendermat(p_188358_1_, itemstack, gun);
		            }
		            GlStateManager.popMatrix();
		        }
    	}
       
    }

    public static void rendersight(EntityLivingBase entityplayer, ItemStack itemstack, ItemGunBase gun){
		if(gun.am_sight){
			GL11.glPushMatrix();//glstart11
			GL11.glTranslatef(gun.am_sight_x, gun.am_sight_y, gun.am_sight_z);
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.am_tex));
			gun.am_model.renderPart("sight");
			GL11.glTranslatef( - gun.am_sight_x,  - gun.am_sight_y,  - gun.am_sight_z);
			GL11.glPopMatrix();//glend11
		}else{
			if(gun.true_mat4){
				gun.obj_model.renderPart("mat4");
			}else if(gun.true_mat5){
				gun.obj_model.renderPart("mat5");
			}else{
				gun.obj_model.renderPart("mat20");
			}
		}
		gun.obj_model.renderPart("mat22");
	}
    
    private static void renderattachment(EntityLivingBase entityplayer, ItemStack itemstack, ItemGunBase gun){
		
		if(gun.true_mat6){
			gun.obj_model.renderPart("mat6");
		}else if(gun.true_mat7){
			gun.obj_model.renderPart("mat7");
		}
		if(gun.true_mat8){
			gun.obj_model.renderPart("mat8");
		}
		
		if(gun.true_mat9){
			gun.obj_model.renderPart("mat9");
		}else if(gun.true_mat10){
			gun.obj_model.renderPart("mat10");
		}else if(gun.true_mat11){
			gun.obj_model.renderPart("mat11");
		}else{
			gun.obj_model.renderPart("mat21");
		}
	}
    
    public boolean shouldCombineTextures()
    {
        return false;
    }
}