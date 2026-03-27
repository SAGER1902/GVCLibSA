package gvclib.util;

import java.io.Serializable;

import gvclib.entity.EntityBBase;

public class SendEntitydata implements Serializable{
    public double motionX;
    public double motionY;
    public double motionZ;
    public boolean inGround;
    public double posX;
    public double posY;
    public double posZ;
    public SendEntitydata(EntityBBase entityin){
        motionX = entityin.motionX;
        motionY = entityin.motionY;
        motionZ = entityin.motionZ;
        inGround = entityin.inGround;
        posX = entityin.posX;
        posY = entityin.posY;
        posZ = entityin.posZ;
    }
}