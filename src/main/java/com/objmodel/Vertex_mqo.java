package objmodel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Vertex_mqo
{

	public float x, y, z;

	public Vertex_mqo(float x, float y)
	{
		this(x, y, 0F);
	}

	public Vertex_mqo(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void normalize()
	{
		double d = Math.sqrt(x*x + y*y + z*z);
		this.x /= d;
		this.y /= d;
		this.z /= d;
	}
	
	public void add(Vertex_mqo v)
	{
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
	}
	
	public boolean equal(Vertex_mqo v)
	{
		return this.x==v.x && this.y==v.y && this.z==v.z;
	}
}