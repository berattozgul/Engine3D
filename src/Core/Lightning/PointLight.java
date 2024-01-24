package Core.Lightning;

import org.joml.Vector3f;
/**
 * The PointLight class represents a point light source in a 3D scene.
 */
public class PointLight {

    private Vector3f color;
    private Vector3f position;
    private float intensity;
    private float constant;
    private float linear;
    private float exponent;

    /**
     * Constructs a new PointLight with the specified color, position, intensity, constant, linear, and exponent.
     *
     * @param color     The color of the light represented by a Vector3f.
     * @param position  The position of the light represented by a Vector3f.
     * @param intensity The intensity of the light.
     * @param constant  The constant attenuation factor.
     * @param linear    The linear attenuation factor.
     * @param exponent  The exponent attenuation factor.
     */
    public PointLight(Vector3f color, Vector3f position, float intensity, float constant, float linear, float exponent) {
        this.color = color;
        this.position = position;
        this.intensity = intensity;
        this.constant = constant;
        this.linear = linear;
        this.exponent = exponent;
    }

    /**
     * Constructs a new PointLight with the specified color, position, and intensity.
     * Uses default values for constant, linear, and exponent attenuation factors.
     *
     * @param color     The color of the light represented by a Vector3f.
     * @param position  The position of the light represented by a Vector3f.
     * @param intensity The intensity of the light.
     */
    public PointLight(Vector3f color, Vector3f position, float intensity) {
        this(color, position, intensity, 1, 0, 0);
    }

    /**
     * Gets the color of the point light.
     *
     * @return The color of the light represented by a Vector3f.
     */
    public Vector3f getColor() {
        return color;
    }

    /**
     * Sets the color of the point light.
     *
     * @param color The new color of the light represented by a Vector3f.
     */
    public void setColor(Vector3f color) {
        this.color = color;
    }

    /**
     * Gets the position of the point light.
     *
     * @return The position of the light represented by a Vector3f.
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Sets the position of the point light.
     *
     * @param position The new position of the light represented by a Vector3f.
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * Gets the intensity of the point light.
     *
     * @return The intensity of the light.
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * Sets the intensity of the point light.
     *
     * @param intensity The new intensity of the light.
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the constant attenuation factor of the point light.
     *
     * @return The constant attenuation factor.
     */
    public float getConstant() {
        return constant;
    }

    /**
     * Sets the constant attenuation factor of the point light.
     *
     * @param constant The new constant attenuation factor.
     */
    public void setConstant(float constant) {
        this.constant = constant;
    }

    /**
     * Gets the linear attenuation factor of the point light.
     *
     * @return The linear attenuation factor.
     */
    public float getLinear() {
        return linear;
    }

    /**
     * Sets the linear attenuation factor of the point light.
     *
     * @param linear The new linear attenuation factor.
     */
    public void setLinear(float linear) {
        this.linear = linear;
    }

    /**
     * Gets the exponent attenuation factor of the point light.
     *
     * @return The exponent attenuation factor.
     */
    public float getExponent() {
        return exponent;
    }

    /**
     * Sets the exponent attenuation factor of the point light.
     *
     * @param exponent The new exponent attenuation factor.
     */
    public void setExponent(float exponent) {
        this.exponent = exponent;
    }
}
