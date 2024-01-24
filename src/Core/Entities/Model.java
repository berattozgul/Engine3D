//Model.java
package Core.Entities;

import Core.Materials.Material;

/**
 * The Model class represents a 3D model with an ID, vertex count, and associated material.
 */
public class Model {

    private int id;
    private int vertexCount;
    private Material material;

    /**
     * Constructs a new Model with the specified ID and vertex count.
     *
     * @param id           The unique identifier for the model.
     * @param vertexCount  The number of vertices in the model.
     */
    public Model(int id, int vertexCount) {
        this.id = id;
        this.vertexCount = vertexCount;
        this.material = new Material();
    }

    /**
     * Constructs a new Model with the specified ID, vertex count, and material.
     *
     * @param id           The unique identifier for the model.
     * @param vertexCount  The number of vertices in the model.
     * @param material     The material associated with the model.
     */
    public Model(int id, int vertexCount, Material material) {
        this.id = id;
        this.vertexCount = vertexCount;
        this.material = material;
    }

    /**
     * Constructs a new Model by copying an existing model and assigning a new texture.
     *
     * @param model    The model to copy.
     * @param texture  The texture to set for the new model.
     */
    public Model(Model model, Texture texture) {
        this.id = model.getId();
        this.vertexCount = model.getVertexCount();
        this.material = model.getMaterial();
        this.material.setTexture(texture);
    }

    /**
     * Gets the material associated with the model.
     *
     * @return The material of the model.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material for the model.
     *
     * @param material The new material for the model.
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * Gets the texture associated with the model's material.
     *
     * @return The texture of the model.
     */
    public Texture getTexture() {
        return material.getTexture();
    }

    /**
     * Sets the texture for the model's material.
     *
     * @param texture The new texture for the model.
     */
    public void setTexture(Texture texture) {
        material.setTexture(texture);
    }

    /**
     * Gets the unique identifier of the model.
     *
     * @return The ID of the model.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the number of vertices in the model.
     *
     * @return The vertex count of the model.
     */
    public int getVertexCount() {
        return vertexCount;
    }

    /**
     * Sets the texture and reflectance for the model's material.
     *
     * @param texture     The new texture for the model.
     * @param reflectance The reflectance value for the material.
     */
    public void setTexture(Texture texture, float reflectance) {
        this.material.setTexture(texture);
        this.material.setReflectance(reflectance);
    }
}