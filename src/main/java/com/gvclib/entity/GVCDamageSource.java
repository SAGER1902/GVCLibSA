package gvclib.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.Explosion;

public class GVCDamageSource extends DamageSource
{
    public GVCDamageSource(String damageTypeIn) {
		super(damageTypeIn);
	}

	/** This kind of damage can be blocked or not. */
    private boolean isUnblockable;
    private boolean isDamageAllowedInCreativeMode;
    /** Whether or not the damage ignores modification by potion effects or enchantments. */
    private boolean damageIsAbsolute;
    private float hungerDamage = 0.3F;
    /** This kind of damage is based on fire or not. */
    private boolean fireDamage;
    /** This kind of damage is based on a projectile or not. */
    private boolean projectile;
    /** Whether this damage source will have its damage amount scaled based on the current difficulty. */
    private boolean difficultyScaled;
    /** Whether the damage is magic based. */
    private boolean magicDamage;
    private boolean explosion;
    public String damageType;

    public static EntityDamageSource causeMobDamage(EntityLivingBase mob)
    {
        return new EntityDamageSource("mob", mob);
    }

    public static EntityDamageSource causeIndirectDamage(Entity source, EntityLivingBase indirectEntityIn)
    {
        return new EntityDamageSourceIndirect("mob", source, indirectEntityIn);
    }

    /**
     * returns an EntityDamageSource of type player
     */
    public static EntityDamageSource causePlayerDamage(EntityPlayer player)
    {
        return new EntityDamageSource("player", player);
    }

    /**
     * returns EntityDamageSourceIndirect of an arrow
     */
    public static EntityDamageSource causeArrowDamage(EntityArrow arrow, @Nullable Entity indirectEntityIn)
    {
        return (EntityDamageSource) (new EntityDamageSourceIndirect("arrow", arrow, indirectEntityIn)).setProjectile();
    }

    /**
     * returns EntityDamageSourceIndirect of a fireball
     */
    public static EntityDamageSource causeFireballDamage(EntityFireball fireball, @Nullable Entity indirectEntityIn)
    {
        return (EntityDamageSource) (indirectEntityIn == null ? (new EntityDamageSourceIndirect("onFire", fireball, fireball)).setFireDamage().setProjectile() : (new EntityDamageSourceIndirect("fireball", fireball, indirectEntityIn)).setFireDamage().setProjectile());
    }

    public static EntityDamageSource causeThrownDamage(Entity source, @Nullable Entity indirectEntityIn)
    {
        return (EntityDamageSource) (new EntityDamageSourceIndirect("thrown", source, indirectEntityIn)).setProjectile();
    }

    public static EntityDamageSource causeBulletAP(Entity source, @Nullable Entity indirectEntityIn)
    {
        return (EntityDamageSource) (new EntityDamageSourceIndirect("newapbullet", source, indirectEntityIn)).setDamageBypassesArmor().setMagicDamage();
    }
    public static EntityDamageSource causeBulletFire(Entity source, @Nullable Entity indirectEntityIn)
    {
        return (EntityDamageSource) (new EntityDamageSourceIndirect("newfirebullet", source, indirectEntityIn)).setDamageBypassesArmor().setFireDamage();
    }
    /**
     * Returns the EntityDamageSource of the Thorns enchantment
     */
    public static EntityDamageSource causeThornsDamage(Entity source)
    {
        return (EntityDamageSource) (new EntityDamageSource("thorns", source)).setIsThornsDamage().setMagicDamage();
    }

    public static EntityDamageSource causeExplosionDamage(@Nullable Explosion explosionIn)
    {
        return (EntityDamageSource) (explosionIn != null && explosionIn.getExplosivePlacedBy() != null ? (new EntityDamageSource("explosion.player", explosionIn.getExplosivePlacedBy())).setDifficultyScaled().setExplosion() : (new GVCDamageSource("explosion")).setDifficultyScaled().setExplosion());
    }

    public static EntityDamageSource causeExplosionDamage(@Nullable EntityLivingBase entityLivingBaseIn)
    {
        return (EntityDamageSource) (entityLivingBaseIn != null ? (new EntityDamageSource("explosion.player", entityLivingBaseIn)).setDifficultyScaled().setExplosion() : (new GVCDamageSource("explosion")).setDifficultyScaled().setExplosion());
    }

    
}