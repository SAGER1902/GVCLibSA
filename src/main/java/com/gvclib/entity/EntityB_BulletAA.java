package gvclib.entity;

import java.util.List;

import gvclib.world.GVCExplosionBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import gvclib.mod_GVCLib;
import net.minecraft.scoreboard.Team;
import gvclib.entity.living.ISoldier;
import net.minecraftforge.fml.common.Loader;

public class EntityB_BulletAA extends EntityBBase {
	public EntityB_BulletAA(World worldIn) {
		super(worldIn);
	}

	public EntityB_BulletAA(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityB_BulletAA(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189662_a(DataFixer p_189662_0_) {
		EntityBBase.func_189661_a(p_189662_0_, "Snowball");
	}
	
	protected void entityInit()
    {
		//super.entityInit();
    	this.dataManager.register(Model, new String("gvclib:textures/entity/BulletAAA.obj"));
        this.dataManager.register(Tex, new String("gvclib:textures/entity/BulletAAA.png"));
        this.dataManager.register(ModelF, new String("gvclib:textures/entity/mflash.mqo"));
        this.dataManager.register(TexF, new String("gvclib:textures/entity/mflash.png"));
    }
	
	public boolean exnear = false;
	public void onUpdate()
    {
        super.onUpdate();
        //if (this.world.isRemote) this.world.spawnParticle(EnumParticleTypes.CLOUD, true, this.posX, this.posY + 0D, this.posZ, 0.0D, 0.0D, 0.0D, 100);
        if(exnear) {
        	Entity entity = null;
        	double k = this.posX;
        	double l = this.posY;
			double i = this.posZ;
			int han = 8;
        	AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - han),
					(double) (i - han), (double) (k + han), (double) (l + han), (double) (i + han)))
							.grow(1);
			List<Entity> llist = this.world.getEntitiesWithinAABBExcludingEntity(this,axisalignedbb);
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity1 instanceof EntityLivingBase && entity1 != null && (this.getThrower() != null && this.getThrower() instanceof EntityLivingBase)) {
							if(this.time > 5 && entity1 != this.friend && entity1 != this.getThrower()) {
								EntityLivingBase hitentity = (EntityLivingBase)entity1;
								EntityLivingBase shootentity = (EntityLivingBase)this.getThrower();
								Team hitteam = hitentity.getTeam();
								Team shootteam = shootentity.getTeam();
								if(shootteam == null && hitteam == null){
									if(hitentity instanceof ISoldier && shootentity instanceof ISoldier){

									}else{
										if(this.world.rand.nextInt(10) > 7) {
											 if (!this.world.isRemote) 
											 {
												 GVCExplosionBase.ExplosionKai(this.getThrower(), 
														 this.getThrower(), this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, false,  false);
													this.setDead();
												}
										}
									}
								}else if(shootteam == hitteam){

								}else{
									if(this.world.rand.nextInt(10) > 7) {
										 if (!this.world.isRemote) 
										 {
											 GVCExplosionBase.ExplosionKai(this.getThrower(), 
													 this.getThrower(), this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, false,  false);
												this.setDead();
											}
									}
								}
							}
						}
					}
				}
			}
        }
    }
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
		{
			boolean ap = false;
			boolean damage_flag = true;
			boolean dead_flag = true;
			if (result.entityHit != null) {
				Entity entity = result.entityHit;
				if (entity != null){
					
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
						if(this.bulletDameID == 3){
							result.entityHit.attackEntityFrom(GVCDamageSource.causeBulletAP(this, this.getThrower()), (float) i);
							ap = true;
						}else{
							result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) i);
						}
						if (this.exlevel >= 0) {
							GVCExplosionBase.ExplosionKai(this.getThrower(), this, this.posX + 0, this.posY + 0, this.posZ + 0, 1.0F, false,  false);
						}
						if (!this.world.isRemote && !ap && dead_flag) {
							this.setDead();
						}
					}
				}
			}else{
				if (this.exlevel >= 0) {
					GVCExplosionBase.ExplosionKai(this.getThrower(), this, this.posX + 0, this.posY + 0, this.posZ + 0, 1.0F, false,  false);
				}
				if (!this.world.isRemote && !ap && dead_flag) {
					this.setDead();
				}
			}
		}
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}