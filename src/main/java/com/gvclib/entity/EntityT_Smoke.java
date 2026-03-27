package gvclib.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityT_Smoke extends EntityTNTBase {
	public EntityT_Smoke(World worldIn) {
		super(worldIn);
	}

	public EntityT_Smoke(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityT_Smoke(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189662_a(DataFixer p_189662_0_) {
		EntityBBase.func_189661_a(p_189662_0_, "Snowball");
	}

	public void onUpdate()
    {
        super.onUpdate();
        /*this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY += 0.0003999999910593033D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;
        */
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        
        ++time;
        if(time >= 80){
        	if(!this.world.isRemote){
        	this.setDead();
        	}else{
        		this.setDead();
        	}
        }/*else
        {
            this.handleWaterMovement();
        }*/
    }
	
	private void explode()
    {
        float f = 4.0F;
        this.world.createExplosion(this, this.posX, this.posY + (double)(this.height / 16.0F), this.posZ, exlevel, ex);
    }
	
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}