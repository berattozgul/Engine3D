//TestGame.Java
package Launcher;

import Core.Entities.Model;
import Core.ILogic;
import Core.ObjectLoader;
import Core.RenderManager;
import Core.WindowManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestGame implements ILogic {
    private int direction = 0;
    private float color = 0.0f;
    private final RenderManager renderManager;
    private final WindowManager windowManager;
    private final ObjectLoader loader;
    private Model model;

    public TestGame() {
        renderManager = new RenderManager();
        windowManager = Main.getWindow();
        loader=new ObjectLoader();
    }

    @java.lang.Override
    public void init() throws Exception {
        renderManager.init();
        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };
        int[] indices={
            0,1,3,
            3,1,2
        };
        model=loader.loadModel(vertices,indices);
    }

    @java.lang.Override
    public void input() {
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_UP)) {
            direction = 1;
        } else if (windowManager.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @java.lang.Override
    public void update() {
        color += direction * 0.01f;
        if (color > 1) {
            color = 1.0f;
        } else if (color <= 0) {
            color = 0.0f;
        }
    }

    @java.lang.Override
    public void render() {
        if (windowManager.isResize()) {
            GL11.glViewport(0, 0, windowManager.getWidth(), windowManager.getHeight());
            windowManager.setResize(true);
        }
        windowManager.setClearColor(color,color,color,0.0f);
        renderManager.render(model);
    }

    @java.lang.Override
    public void cleanUp() {
        renderManager.cleanUp();
        loader.cleanUp();
    }
}
