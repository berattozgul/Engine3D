//
package Core.Managers;

import Core.Entities.Entity;
import Core.Entities.Model;
import Core.Lightning.DirectionalLight;
import Core.Lightning.PointLight;
import Core.Lightning.SpotLight;
import Core.Utils.Constants;
import Core.Utils.Transformation;
import Core.Utils.Utils;
import Launcher.Main;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * The RenderManager class manages rendering operations, including initializing shaders,
 * binding models, preparing entities, rendering lights, and rendering entities.
 */
public class RenderManager {
    private final WindowManager window;
    private ShaderManager shader;
    private Map<Model, List<Entity>> entities = new HashMap<>();
    /**
     * Constructs a new RenderManager instance with a reference to the WindowManager.
     */
    public RenderManager() {
        window = Main.getWindow();
    }
    /**
     * Initializes the RenderManager by creating and linking shaders, and setting up uniforms.
     *
     * @throws Exception If an error occurs during initialization.
     */
    public void init() throws Exception {
        shader = new ShaderManager();
        shader.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shader.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shader.link();
        shader.createUniform("textureSampler");
        shader.createUniform("transformationMatrix");
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");
        shader.createUniform("ambientLight");
        shader.createMaterialUniform("material");
        shader.createUniform("specularPower");
        shader.createDirectionalLightUniform("directionalLight");
        shader.createPointLightListUniform("pointLights", 5);
        shader.createSpotLightListUniform("spotLights", 5);
    }
    /**
     * Binds the specified model, enabling vertex attribute arrays and setting material and texture.
     *
     * @param model The Model to bind.
     */
    public void bind(Model model) {
        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        shader.setUniform("material", model.getMaterial());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());

    }
    /**
     * Unbinds the currently bound model, disabling vertex attribute arrays.
     */
    public void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    /**
     * Prepares an entity for rendering by setting transformation and view matrices.
     *
     * @param entity The Entity to prepare.
     * @param camera The Camera used for rendering.
     */
    public void prepare(Entity entity, Camera camera) {
        shader.setUniform("textureSampler", 0);
        shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix(entity));
        shader.setUniform("viewMatrix", Transformation.getViewMatrix(camera));

    }
    /**
     * Renders lights, including ambient light, point lights, spot lights, and directional light.
     *
     * @param camera           The Camera used for rendering.
     * @param pointLights      An array of PointLight objects.
     * @param spotLights       An array of SpotLight objects.
     * @param directionalLight The DirectionalLight object.
     */
    public void renderLights(Camera camera, PointLight[] pointLights,
                             SpotLight[] spotLights,
                             DirectionalLight directionalLight) {
        shader.setUniform("ambientLight", Constants.AMBIENT_LIGHT);
        shader.setUniform("specularPower", Constants.SPECULAR_POWER);
        int numLights = spotLights != null ? spotLights.length : 0;
        for (int i = 0; i < numLights; i++) {
            shader.setUniform("spotLights", spotLights[i], i);
        }
        numLights = spotLights != null ? pointLights.length : 0;
        for (int i = 0; i < numLights; i++) {
            shader.setUniform("pointLights", pointLights[i], i);
        }
        shader.setUniform("directionalLight", directionalLight);

    }

    /**
     * Renders entities with specified lights and camera.
     *
     * @param camera           The Camera used for rendering.
     * @param directionalLight The DirectionalLight object.
     * @param pointLights      An array of PointLight objects.
     * @param spotLights       An array of SpotLight objects.
     */
    public void render( Camera camera,
                       DirectionalLight directionalLight,
                       PointLight[] pointLights,
                       SpotLight[] spotLights) {
        clear();
        shader.bind();
        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());
        renderLights(camera, pointLights, spotLights, directionalLight);
        for (Model model : entities.keySet()) {
            bind(model);
            List<Entity> entityList = entities.get(model);
            for (Entity entity: entityList){
                prepare(entity,camera);
                GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

            }
            unbind();
        }
        entities.clear();
        shader.unbind();
    }
    /**
     * Processes an entity for rendering by adding it to the list of entities for its model.
     *
     * @param entity The Entity to process.
     */
    public void processEntity(Entity entity){
        List<Entity>entityList=entities.get(entity.getModel());
        if(entityList!=null){
            entityList.add(entity);
        }else{
            List<Entity> newEntityList=new ArrayList<>();
            newEntityList.add(entity);
            entities.put(entity.getModel(),newEntityList);
        }
    }
    /**
     * Clears the color and depth buffers for rendering.
     */
    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }
    /**
     * Cleans up resources, including shader cleanup.
     */
    public void cleanup() {
        shader.cleanup();
    }
}