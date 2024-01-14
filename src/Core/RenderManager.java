//
package Core;

import Core.Entities.Model;
import Core.Utils.Utils;
import Launcher.Main;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class RenderManager {
    private final WindowManager window;
    private ShaderManager shaderManager;
    public RenderManager(){
        window= Main.getWindow();
    }
    public void init() throws Exception{
        shaderManager=new ShaderManager();
        shaderManager.createVertexShader(Utils.loadResurce("/shaders/vertex.vs"));
        shaderManager.createFragmentShader(Utils.loadResurce("/shaders/fragment.fs"));
        shaderManager.link();
    }
    public void render(Model model){
        clear();
        shaderManager.bind();
        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES,0,model.getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shaderManager.unbind();
    }
    public void clear(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
    }
    public void cleanUp(){
        shaderManager.cleanUp();
    }
}
