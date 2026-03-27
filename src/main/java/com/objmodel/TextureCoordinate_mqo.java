package objmodel;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextureCoordinate_mqo
{
	public float u, v, w;

	public TextureCoordinate_mqo(float u, float v)
	{
		this(u, v, 0F);
	}

	public TextureCoordinate_mqo(float u, float v, float w)
	{
		this.u = u;
		this.v = v;
		this.w = w;
	}
}