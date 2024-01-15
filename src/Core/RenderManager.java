//
package Core;

import Core.Entities.Entity;
import Core.Entities.Model;
import Core.Utils.Transformation;
import Core.Utils.Utils;
import Launcher.Main;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class RenderManager {
    private final WindowManager window;
    private ShaderManager shaderManager;

    public RenderManager() {
        window = Main.getWindow();
    }

    public void init() throws Exception {
        shaderManager = new ShaderManager();
        shaderManager.createVertexShader(Utils.loadResurce("/shaders/vertex.vs"));
        shaderManager.createFragmentShader(Utils.loadResurce("/shaders/fragment.fs"));
        shaderManager.link();
        shaderManager.createUniform("textureSampler");
        shaderManager.createUniform("transformationMatrix");
        shaderManager.createUniform("projectionMatrix");
        shaderManager.createUniform("viewMatrix");

    }

    public void render(Entity entity,Camera camera) {
        clear();
        shaderManager.bind();
        shaderManager.setUniform("textureSampler",0);
        shaderManager.setUniform("transformationMatrix", Transformation.createTransformationMatrix(entity));
        shaderManager.setUniform("projectionMatrix",window.updateProjectionMatrix());
        shaderManager.setUniform("viewMatrix",Transformation.getViewMatrix(camera));
        GL30.glBindVertexArray(entity.getModel().getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,entity.getModel().getTexture().getId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(),
                GL11.GL_UNSIGNED_INT,0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        shaderManager.unbind();
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanUp() {
        shaderManager.cleanUp();
    }
}
