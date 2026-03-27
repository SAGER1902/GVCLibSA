package objmodel;

import net.minecraft.util.ResourceLocation;

public class MqoModelLoader implements IModelCustomLoader
{
	@Override
	public String getType()
	{
		return "Metasequoia model";
	}

	private static final String[] types = { "mqo" };

	@Override
	public String[] getSuffixes()
	{
		return types;
	}

	public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException
	{
		return new WavefrontObject_mqo(resource);
	}
}