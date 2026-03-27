package gvclib.render;

import gvclib.entity.EntityT_Grenade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

@SideOnly(Side.CLIENT)
public class RenderT_Grenade<T extends EntityT_Grenade> extends Render<T>
{
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/entity/mflash.mqo"));
    private static final ResourceLocation boatTextures0 = new ResourceLocation("gvclib:textures/entity/mflash.png");

    public RenderT_Grenade(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        //this.rendermanager = renderManagerIn;
    }

    public  int moi = 0;
	public  String[] modelt = new String[64];
	public  IModelCustom[] model = new IModelCustom[64];
    /**
     * Renders the desired {@code T} type Entity.
     */
	float timemuki;
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	Minecraft minecraft = Minecraft.getMinecraft();
        //this.bindEntityTexture(new ResourceLocation(entity.getModel()));
        GlStateManager.pushMatrix();
        //GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);
        
        if(entity.model == null && entity.getModel() != null){
        	boolean mo = false;
        	
        	{
        		entity.model = AdvancedModelLoader
						.loadModel(new ResourceLocation(entity.getModel()));
        		
        	}
			
		}
        if(!entity.onGround){
        	timemuki = entity.time*10;
        	//GlStateManager.rotate(entity.time*10, 1.0F, 0.0F, 0.0F);
        }
        GlStateManager.rotate(timemuki, 1.0F, 0.0F, 0.0F);
        if(entity.model != null){
            minecraft.getTextureManager().bindTexture(new ResourceLocation(entity.getTex()));
            entity.model.renderPart("bullet");
			entity.model.renderPart("mat1");
            {
            	GlStateManager.disableLighting();
            	float size = partialTicks * 0.4F + 1;
            	GlStateManager.scale(size, size, size);
            	entity.model.renderPart("bullet_e_1");
            	 GlStateManager.enableLighting();
            }
        }
        GlStateManager.translate((float)-x, (float)-y, (float)-z);
        GlStateManager.popMatrix();
        
        
        
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return boatTextures0;
	}
}