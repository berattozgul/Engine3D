package Core.Utils;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Constants {
    public static final String TITLE = "ENGINE";
    public static final float CAMERA_STEP = 0.05f;
    public static final float MOUSE_SENSITIVITY = 0.08f;
    public static final float Z_NEAR = 0.05f;
    public static final float Z_FAR = 1000f;
    public static final float FOV = (float) Math.toRadians(60);
    public static final float FRAMERATE = 120;
    public static final Vector4f DEFAULT_COLOR=new Vector4f(1.0f,1.0f,1.0f,0f);
    public static final Vector3f AMBIENT_LIGHT =new Vector3f(1.5f,1.5f,1.5f);
    public static final float SPECULAR_POWER=10f;
}
