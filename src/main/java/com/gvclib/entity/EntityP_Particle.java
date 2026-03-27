package gvclib.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityP_Particle extends EntityTNTBase {
	
	public double startposX = 0;
    public double startposY = 0;
    public double startposZ = 0;
    
    public String entity_tex = null;
	
	public EntityP_Particle(World worldIn) {
		super(worldIn);
		this.height = 25;
		this.setSize(0.01F, this.height);
		this.light = true;
	}

	public EntityP_Particle(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityP_Particle(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189662_a(DataFixer p_189662_0_) {
		EntityBBase.func_189661_a(p_189662_0_, "Snowball");
	}
	
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness()
    {
        return 1.0F;
    }
    
    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return true;
    }

	
	
	public void onUpdate()
    {
        super.onUpdate();
       
     //   this.setPosition(this.startposX, this.startposY, this.startposZ);
        
        if(time <= timemax)++time;
        if(time > timemax){
        	if(!this.world.isRemote){
        	this.setDead();
        	}
        }
    }
	
	private void explode()
    {
    }
	
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
	}
}