package Core.Entities;

/**
 * The Texture class represents a texture used in rendering 3D models.
 */
public class Texture {

    private final int id;

    /**
     * Constructs a new Texture with the specified ID.
     *
     * @param id The unique identifier for the texture.
     */
    public Texture(int id) {
        this.id = id;
    }

    /**
     * Gets the unique identifier of the texture.
     *
     * @return The ID of the texture.
     */
    public int getId() {
        return id;
    }
}