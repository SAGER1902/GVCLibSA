package gvclib.entity.living.cnt.ai;

import java.util.List;

import gvclib.entity.living.EntityGVCLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class VehicleAI_RotationYawOffset {
	public static void offset(EntityLivingBase entity, EntityLivingBase ridding) {
		if (ridding.rotationYawHead > 360F || ridding.rotationYawHead < -360F) {
			ridding.rotationYawHead = 0;
			ridding.rotationYaw = 0;
			ridding.prevRotationYaw = 0;
			ridding.prevRotationYawHead = 0;
			ridding.renderYawOffset = 0;
		}
		if (ridding.rotationYawHead > 180F) {
			ridding.rotationYawHead = -179F;
			ridding.rotationYaw = -179F;
			ridding.prevRotationYaw = -179F;
			ridding.prevRotationYawHead = -179F;
			ridding.renderYawOffset = -179F;
		}
		if (ridding.rotationYawHead < -180F) {
			ridding.rotationYawHead = 179F;
			ridding.rotationYaw = 179F;
			ridding.prevRotationYaw = 179F;
			ridding.prevRotationYawHead = 179F;
			ridding.renderYawOffset = 179F;
		}
		if (entity.rotationYawHead > 360F || entity.rotationYawHead < -360F) {
			entity.rotationYawHead = 0;
			entity.rotationYaw = 0;
			entity.prevRotationYaw = 0;
			entity.prevRotationYawHead = 0;
			entity.renderYawOffset = 0;
		}
		if (entity.rotationYawHead > 180F) {
			entity.rotationYawHead = -179F;
			entity.rotationYaw = -179F;
			entity.prevRotationYaw = -179F;
			entity.prevRotationYawHead = -179F;
			entity.renderYawOffset = -179F;
		}
		if (entity.rotationYawHead < -180F) {
			entity.rotationYawHead = 179F;
			entity.rotationYaw = 179F;
			entity.prevRotationYaw = 179F;
			entity.prevRotationYawHead = 179F;
			entity.renderYawOffset = 179F;
		}
	}
}