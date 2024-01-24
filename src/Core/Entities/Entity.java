package Core.Entities;

import org.joml.Vector3f;

/**
 * The Entity class represents an object in a 3D space with a model, position, rotation, and scale.
 */
public class Entity {

    private Model model;
    private Vector3f pos, rotation;
    private float scale;

    /**
     * Constructs a new Entity with the specified parameters.
     *
     * @param model    The model associated with the entity.
     * @param pos      The initial position of the entity represented by a Vector3f.
     * @param rotation The initial rotation of the entity represented by a Vector3f.
     * @param scale    The scale factor of the entity.
     */
    public Entity(Model model, Vector3f pos, Vector3f rotation, float scale) {
        this.model = model;
        this.pos = pos;
        this.rotation = rotation;
        this.scale = scale;
    }

    /**
     * Increments the position of the entity by the specified amounts along the x, y, and z axes.
     *
     * @param x The amount to increment along the x-axis.
     * @param y The amount to increment along the y-axis.
     * @param z The amount to increment along the z-axis.
     */
    public void inc_pos(float x, float y, float z) {
        this.pos.x += x;
        this.pos.y += y;
        this.pos.z += z;
    }

    /**
     * Sets the position of the entity to the specified coordinates.
     *
     * @param x The x-coordinate of the new position.
     * @param y The y-coordinate of the new position.
     * @param z The z-coordinate of the new position.
     */
    public void setPos(float x, float y, float z) {
        this.pos.x = x;
        this.pos.y = y;
        this.pos.z = z;
    }

    /**
     * Increments the rotation of the entity by the specified amounts around the x, y, and z axes.
     *
     * @param x The amount to increment the rotation around the x-axis.
     * @param y The amount to increment the rotation around the y-axis.
     * @param z The amount to increment the rotation around the z-axis.
     */
    public void incRotation(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    /**
     * Sets the rotation of the entity to the specified angles around the x, y, and z axes.
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
     * Gets the model associated with the entity.
     *
     * @return The model of the entity.
     */
    public Model getModel() {
        return model;
    }

    /**
     * Gets the position of the entity.
     *
     * @return The position of the entity represented by a Vector3f.
     */
    public Vector3f getPos() {
        return pos;
    }

    /**
     * Gets the rotation of the entity.
     *
     * @return The rotation of the entity represented by a Vector3f.
     */
    public Vector3f getRotation() {
        return rotation;
    }

    /**
     * Gets the scale factor of the entity.
     *
     * @return The scale factor of the entity.
     */
    public float getScale() {
        return scale;
    }
}