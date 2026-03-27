package gvclib;

import java.lang.reflect.Type;
import java.util.Locale;

import org.apache.commons.lang3.Validate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class GVCResourceLocation extends ResourceLocation
{
    protected final String resourceDomain;
    protected final String resourcePath;

    protected GVCResourceLocation(int unused, String... resourceName)
    {
    	super(0, resourceName);
        this.resourceDomain = resourceName[0].toLowerCase(Locale.ROOT);
        this.resourcePath = resourceName[1].toLowerCase(Locale.ROOT);
        Validate.notNull(this.resourcePath);
    }

    public GVCResourceLocation(String resourceName)
    {
        this(0, resourceName);
    }

    public GVCResourceLocation(String resourceDomainIn, String resourcePathIn)
    {
        this(0, resourceDomainIn, resourcePathIn);
    }


    public static class Serializer implements JsonDeserializer<GVCResourceLocation>, JsonSerializer<GVCResourceLocation>
        {
            public GVCResourceLocation deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
            {
                return new GVCResourceLocation(JsonUtils.getString(p_deserialize_1_, "location"));
            }

            public JsonElement serialize(GVCResourceLocation p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
            {
                return new JsonPrimitive(p_serialize_1_.toString());
            }
        }
}