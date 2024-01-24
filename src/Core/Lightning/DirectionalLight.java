package Core.Lightning;

import org.joml.Vector3f;
/**
 * The DirectionalLight class represents a directional light source in a 3D scene.
 */
public class DirectionalLight {

    private Vector3f color;
    private Vector3f direction;
    private float intensity;

    /**
     * Constructs a new DirectionalLight with the specified color, direction, and intensity.
     *
     * @param color     The color of the light represented by a Vector3f.
     * @param direction The direction of the light represented by a Vector3f.
     * @param intensity The intensity of the light.
     */
    public DirectionalLight(Vector3f color, Vector3f direction, float intensity) {
        this.color = color;
        this.direction = direction;
        this.intensity = intensity;
    }

    /**
     * Gets the color of the directional light.
     *
     * @return The color of the light represented by a Vector3f.
     */
    public Vector3f getColor() {
        return color;
    }

    /**
     * Sets the color of the directional light.
     *
     * @param color The new color of the light represented by a Vector3f.
     */
    public void setColor(Vector3f color) {
        this.color = color;
    }

    /**
     * Gets the direction of the directional light.
     *
     * @return The direction of the light represented by a Vector3f.
     */
    public Vector3f getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the directional light.
     *
     * @param direction The new direction of the light represented by a Vector3f.
     */
    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    /**
     * Gets the intensity of the directional light.
     *
     * @return The intensity of the light.
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * Sets the intensity of the directional light.
     *
     * @param intensity The new intensity of the light.
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}