package Core.Utils;

import org.joml.Vector3f;
import org.joml.Vector4f;
/**
 * The Constants class holds various static constants used in the 3D engine.
 */
public class Constants {

    /** The default title for the 3D engine. */
    public static final String TITLE = "Engine3D";

    /** The step size for camera movement. */
    public static final float CAMERA_STEP = 0.05f;

    /** The sensitivity of the mouse for camera control. */
    public static final float MOUSE_SENSITIVITY = 0.08f;

    /** The near clipping plane of the camera. */
    public static final float Z_NEAR = 0.05f;

    /** The far clipping plane of the camera. */
    public static final float Z_FAR = 1000f;

    /** The field of view for the camera, in radians. */
    public static final float FOV = (float) Math.toRadians(60);

    /** The target framerate for the 3D engine. */
    public static final float FRAMERATE = 60;

    /** The default color represented as a Vector4f (RGBA). */
    public static final Vector4f DEFAULT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 0f);

    /** The ambient light color represented as a Vector3f (RGB). */
    public static final Vector3f AMBIENT_LIGHT = new Vector3f(5.5f, 5.5f, 5.5f);

    /** The specular power used in lighting calculations. */
    public static final float SPECULAR_POWER = 50f;
}
