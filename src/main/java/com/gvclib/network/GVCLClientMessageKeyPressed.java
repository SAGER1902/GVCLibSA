package gvclib.network;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import gvclib.entity.EntityBBase;
import gvclib.util.SendEntitydata;

import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
 
public class GVCLClientMessageKeyPressed implements IMessage {
 
    public int key;
    public Entity keyentity;
    public double posx;
    public double posy;
    public double posz;
    public double posx1;
    public double posy1;
    public double posz1;
    public int da;
    public float gra;
    public float sp;
    public float kaku;
    public int fre;
    
    public int enid;
    public float strength;
	
    public float count;
    public int hit_name;
	
    public boolean boolean_hantei;
    
    public SendEntitydata data;
 
    public GVCLClientMessageKeyPressed(){}
 
    public GVCLClientMessageKeyPressed(int i) {
        this.key = i;
    }
    
    public GVCLClientMessageKeyPressed(int i, int entity) {
        this.key = i;
        this.fre = entity;
    }
    public GVCLClientMessageKeyPressed(int i, int entity, int id) {
        this.key = i;
        this.fre = entity;
        this.da = id;
    }
	
    public GVCLClientMessageKeyPressed(int i, int entity, int name, float damage) {//伤害数值
        this.key = i;
        this.fre = entity;
		this.hit_name = name;
        this.count = damage;
    }
    
    //test
    public GVCLClientMessageKeyPressed(int i, int entity, EntityBBase id) {
        this.key = i;
        this.fre = entity;
        data = new SendEntitydata(id);
    }
    
    public GVCLClientMessageKeyPressed(int i, int entity, int slot, int id, int kazu) {
        this.key = i;
        this.fre = entity;
        this.da = id;
        this.enid = slot;
        this.kaku = kazu;
    }
    
    public GVCLClientMessageKeyPressed(int i, int e, float s, double posx, double posy, double posz) {
    	this.key = i;
        this.enid = e;
        this.strength = s;
        this.posx = posx;
        this.posy = posy;
        this.posz = posz;
    }
    
    public GVCLClientMessageKeyPressed(int i, int e, double posx, double posy, double posz) {
    	this.key = i;
        this.enid = e;
        this.posx = posx;
        this.posy = posy;
        this.posz = posz;
    }
    
    public GVCLClientMessageKeyPressed(int i, int e, double posx) {
    	this.key = i;
        this.fre = e;
        this.posx = posx;
    }
    
    public GVCLClientMessageKeyPressed(int i, int e, boolean boo) {
    	this.key = i;
        this.fre = e;
        this.boolean_hantei = boo;
    }
    
    
    @Override
    public void fromBytes(ByteBuf buf) {
        this.key = buf.readInt();
        this.posx = buf.readDouble();
        this.posy = buf.readDouble();
        this.posz = buf.readDouble();
        this.posx1 = buf.readDouble();
        this.posy1 = buf.readDouble();
        this.posz1 = buf.readDouble();
        this.da = buf.readInt();
        this.gra = buf.readFloat();
        this.sp = buf.readFloat();
        this.kaku = buf.readFloat();
        this.fre = buf.readInt();
        this.enid = buf.readInt();
        this.strength = buf.readFloat();
        this.count = buf.readFloat();
        this.hit_name = buf.readInt();
        this.boolean_hantei = buf.readBoolean();
        
        byte[] temp = new byte[buf.readInt()];
        buf.readBytes(temp);
        try{
            data = (SendEntitydata) toObject(temp);
        }catch (OptionalDataException e){
            e.printStackTrace();
        }catch (StreamCorruptedException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }
 
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.key);
        buf.writeDouble(this.posx);
        buf.writeDouble(this.posy);
        buf.writeDouble(this.posz);
        buf.writeDouble(this.posx1);
        buf.writeDouble(this.posy1);
        buf.writeDouble(this.posz1);
        buf.writeInt(this.da);
        buf.writeFloat(this.gra);
        buf.writeFloat(this.sp);
        buf.writeFloat(this.kaku);
        buf.writeInt(this.fre);
        buf.writeInt(this.enid);
        buf.writeFloat(this.strength);
		
        buf.writeFloat(this.count);
        buf.writeInt(this.hit_name);
        buf.writeBoolean(boolean_hantei);
        
        try {
            buf.writeInt(fromObject(data).length);
            buf.writeBytes(fromObject(data));
        } catch (NotSerializableException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static byte[] fromObject(Object o) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(o);
        byte[] bytes = bos.toByteArray();
        out.close();
        bos.close();
        return bytes;
    }
    public static Object toObject(byte[] bytes) throws OptionalDataException, StreamCorruptedException, ClassNotFoundException, IOException{
        return new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
    }
}