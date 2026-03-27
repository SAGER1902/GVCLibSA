package gvclib.entity;
import gvclib.mod_GVCLib;
import gvclib.world.GVCExplosionBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraft.scoreboard.Team;
import gvclib.entity.living.ISoldier;
public class EntityB_Shell extends EntityBBase {
	public EntityB_Shell(World worldIn) {
		super(worldIn);
	}
	
	public EntityB_Shell(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityB_Shell(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189662_a(DataFixer p_189662_0_) {
		EntityBBase.func_189661_a(p_189662_0_, "Snowball");
	}

	protected void entityInit()
    {
		//super.entityInit();
    	this.dataManager.register(Model, new String("gvclib:textures/entity/BulletRocket.obj"));
        this.dataManager.register(Tex, new String("gvclib:textures/entity/BulletRocket.png"));
        this.dataManager.register(ModelF, new String("gvclib:textures/entity/mflash.mqo"));
        this.dataManager.register(TexF, new String("gvclib:textures/entity/mflash.png"));
    }
	
	public void onUpdate()
    {
        super.onUpdate();
        //if (this.world.isRemote) this.world.spawnParticle(EnumParticleTypes.CLOUD, true, this.posX, this.posY + 0D, this.posZ, 0.0D, 0.0D, 0.0D, 100);
        //this.world.spawnParticle(EnumParticleTypes.SWEEP_ATTACK, this.posX, this.posY + 0D, this.posZ, 0.0D, 0.0D, 0.0D, 10);
		float f111 = this.rotationYaw * (2 * (float) Math.PI / 360);
		float sp = 0.05F;
		this.hitbullet();
		if(time > timemax - 1){
			if(!this.world.isRemote && this.getThrower() != null){
				GVCExplosionBase.ExplosionKai(this.getThrower(), this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, this.exfire,  this.ex);
				this.setDead();
			}
		}
    }
	
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
		//if (result.entityHit != this.friend && result.entityHit != this.getThrower()) 
		{
			boolean damage_flag = true;
			boolean dead_flag = true;
			boolean ap = false;

			if (result.entityHit != null) 
			//if (result.entityHit != this.friend && result.entityHit != this.getThrower() && result.entityHit != null)
			{
				if(result.entityHit instanceof EntityLivingBase && (this.getThrower() != null && this.getThrower() instanceof EntityLivingBase)) {
					EntityLivingBase hitentity = (EntityLivingBase)result.entityHit;
					EntityLivingBase shootentity = (EntityLivingBase)this.getThrower();
					Team hitteam = hitentity.getTeam();
					Team shootteam = shootentity.getTeam();
					if(shootteam == null && hitteam == null){
						if(hitentity instanceof ISoldier && shootentity instanceof ISoldier){
							dead_flag = false;
							damage_flag = false;
						}
					}else if(shootteam == hitteam){
						dead_flag = false;
						damage_flag = false;
					}
				}
				
				if(damage_flag){
					int i = Bdamege;
					if (this.muteki) {
						result.entityHit.hurtResistantTime = 0;
					}
					if(this.bulletDameID == 1){
						result.entityHit.attackEntityFrom(GVCDamageSource.causeBulletAP(this, this.getThrower()), (float) i);
						ap = true;
					}else{
						result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) i);
					}
					if(this.exflash){
						if (result.entityHit instanceof EntityLivingBase)
						{
						 EntityLivingBase en = (EntityLivingBase) result.entityHit;
						 en.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40, 1));
						}
					}
				}
			}
			if(this.getThrower() != null && (dead_flag||damage_flag))
			{
				 if(this.bulletDameID == 5) {
					 GVCExplosionBase.ExplosionKai(this.getThrower(), this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, true,  false);
				 }else {
					 GVCExplosionBase.ExplosionKai(this.getThrower(), this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, this.exfire,  this.ex);
				 }
			}
			if (!this.world.isRemote && !ap && dead_flag) {
				this.setDead();
			}
		}
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}