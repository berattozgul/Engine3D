package Core.Materials;

import Core.Entities.Texture;
import Core.Utils.Constants;
import org.joml.Vector4f;
import org.w3c.dom.Text;
/**
 * The Material class represents the visual properties of an object in the scene, including ambient,
 * diffuse, and specular colors, reflectance, and an optional texture.
 */
public class Material {
    public Vector4f ambientColor,diffuseColor,specularColor;
    /** The reflectance coefficient of the material. */
    private float reflectance;
    /** The texture applied to the material. Can be null if no texture is applied. */
    private Texture texture;
    /**
     * Constructs a Material with default properties: ambient, diffuse, and specular colors set to
     * {@link Constants#DEFAULT_COLOR}, reflectance set to 0, and no texture.
     */
    public Material() {
        this.ambientColor= Constants.DEFAULT_COLOR;
        this.diffuseColor= Constants.DEFAULT_COLOR;
        this.specularColor= Constants.DEFAULT_COLOR;
        this.texture=null;
        this.reflectance=0;
    }
    /**
     * Constructs a Material with a single color for ambient, diffuse, and specular components,
     * a specified reflectance, and no texture.
     *
     * @param color      The color for ambient, diffuse, and specular components.
     * @param reflectance The reflectance coefficient.
     */
    public Material(Vector4f color,float reflectance) {
        this(color,color,color,reflectance,null);
    }
    /**
     * Constructs a Material with a single color for ambient, diffuse, and specular components,
     * a specified reflectance, and a texture.
     *
     * @param color      The color for ambient, diffuse, and specular components.
     * @param reflectance The reflectance coefficient.
     * @param texture     The texture applied to the material.
     */
    public Material(Vector4f color,float reflectance,Texture texture) {
        this(color,color,color,reflectance,texture);
    }

    public Material(Texture texture) {
        this(Constants.DEFAULT_COLOR,Constants.DEFAULT_COLOR,Constants.DEFAULT_COLOR,0,texture);
    }
    /**
     * Constructs a Material with separate ambient, diffuse, and specular colors, a specified reflectance,
     * and a texture.
     *
     * @param ambientColor  The ambient color of the material.
     * @param diffuseColor  The diffuse color of the material.
     * @param specularColor The specular color of the material.
     * @param reflectance   The reflectance coefficient.
     * @param texture       The texture applied to the material.
     */
    public Material(Vector4f ambientColor, Vector4f diffuseColor, Vector4f specularColor, float reflectance, Texture texture) {
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.reflectance = reflectance;
        this.texture = texture;
    }

    public Vector4f getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Vector4f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Vector4f getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    /**
     * Checks whether the material has a texture applied.
     *
     * @return True if the material has a texture; false otherwise.
     */
    public boolean hasTexture(){
        return texture!=null;
    }
}
