package Core.Lightning;

import org.joml.Vector3f;
/**
 * The SpotLight class represents a spotlight in a 3D scene, which is essentially a directional PointLight
 * with a defined cone of influence.
 */
public class SpotLight {

    private PointLight pointLight;
    private Vector3f coneDirection;
    private float cutOff;

    /**
     * Constructs a new SpotLight with the specified PointLight, cone direction, and cutoff angle.
     *
     * @param pointLight    The PointLight source associated with the spotlight.
     * @param coneDirection The direction of the spotlight cone represented by a Vector3f.
     * @param cutOff        The angle, in degrees, defining the cutoff of the spotlight cone.
     */
    public SpotLight(PointLight pointLight, Vector3f coneDirection, float cutOff) {
        this.pointLight = pointLight;
        this.coneDirection = coneDirection;
        this.cutOff = cutOff;
    }

    /**
     * Constructs a new SpotLight by copying an existing SpotLight.
     *
     * @param spotLight The SpotLight to copy.
     */
    public SpotLight(SpotLight spotLight) {
        this.pointLight = spotLight.getPointLight();
        this.coneDirection = spotLight.getConeDirection();
        setCutOff(spotLight.getCutOff());
    }

    /**
     * Gets the PointLight source associated with the spotlight.
     *
     * @return The PointLight of the spotlight.
     */
    public PointLight getPointLight() {
        return pointLight;
    }

    /**
     * Sets the PointLight source for the spotlight.
     *
     * @param pointLight The new PointLight source for the spotlight.
     */
    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    /**
     * Gets the direction of the spotlight cone.
     *
     * @return The direction of the spotlight cone represented by a Vector3f.
     */
    public Vector3f getConeDirection() {
        return coneDirection;
    }

    /**
     * Sets the direction of the spotlight cone.
     *
     * @param coneDirection The new direction of the spotlight cone represented by a Vector3f.
     */
    public void setConeDirection(Vector3f coneDirection) {
        this.coneDirection = coneDirection;
    }

    /**
     * Gets the cutoff angle of the spotlight cone in degrees.
     *
     * @return The cutoff angle of the spotlight cone.
     */
    public float getCutOff() {
        return cutOff;
    }

    /**
     * Sets the cutoff angle of the spotlight cone in degrees.
     *
     * @param cutOff The new cutoff angle of the spotlight cone.
     */
    public void setCutOff(float cutOff) {
        this.cutOff = cutOff;
    }
}
