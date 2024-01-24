package Core.Managers;

import org.joml.Vector3f;
import org.joml.Vector4f;
/**
 * The Camera class represents a virtual camera in a 3D environment.
 */
public class Camera {

    private Vector3f position;
    private Vector3f rotation;

    /**
     * Constructs a new Camera with the default position (0,0,0) and rotation (0,0,0).
     */
    public Camera() {
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
    }

    /**
     * Constructs a new Camera with the specified position and rotation.
     *
     * @param pos      The initial position of the camera represented by a Vector3f.
     * @param rotation The initial rotation of the camera represented by a Vector3f.
     */
    public Camera(Vector3f pos, Vector3f rotation) {
        this.position = pos;
        this.rotation = rotation;
    }

    /**
     * Moves the camera's position by the specified amounts along the x, y, and z axes.
     *
     * @param x The amount to move along the x-axis.
     * @param y The amount to move along the y-axis.
     * @param z The amount to move along the z-axis.
     */
    public void movePosition(float x, float y, float z) {
        if (z != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * z;
            position.z += (float) Math.cos(Math.toRadians(rotation.y)) * z;
        }
        if (x != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * x;
            position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * x;
        }
        position.y += y;
    }

    /**
     * Sets the position of the camera to the specified coordinates.
     *
     * @param x The x-coordinate of the new position.
     * @param y The y-coordinate of the new position.
     * @param z The z-coordinate of the new position.
     */
    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    /**
     * Sets the rotation of the camera to the specified angles around the x, y, and z axes.
     *
     * @param x The angle of rotation around the x-axis.
     * @param y The angle of rotation around the y-axis.
     * @param z The angle of rotation around the z-axis.
     */
    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    /**
     * Moves the camera's rotation by the specified amounts around the x, y, and z axes.
     *
     * @param x The amount to rotate around the x-axis.
     * @param y The amount to rotate around the y-axis.
     * @param z The amount to rotate around the z-axis.
     */
    public void moveRotation(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    /**
     * Gets the position of the camera.
     *
     * @return The position of the camera represented by a Vector3f.
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Gets the rotation of the camera.
     *
     * @return The rotation of the camera represented by a Vector3f.
     */
    public Vector3f getRotation() {
        return rotation;
    }
}
