//TestGame.Java
package Launcher;

import Core.*;
import Core.Entities.Entity;
import Core.Entities.Model;
import Core.Entities.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.Vector;

public class TestGame implements ILogic {
    private static final float CAMERA_MOVE_SPEED = 0.05f;
    private final RenderManager renderManager;
    private final WindowManager windowManager;
    private final ObjectLoader loader;
    private Entity entity;
    private Camera camera;

    Vector3f cameraInc;

    public TestGame() {
        renderManager = new RenderManager();
        windowManager = Main.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
    }

    @java.lang.Override
    public void init() throws Exception {
        renderManager.init();

        Model model = loader.loadOBJModel("/models/bunny.obj");
        model.setTexture(new Texture(loader.loadTextures("textures/blue.png")));
        entity = new Entity(model, new Vector3f(0, 0, -5f), new Vector3f(0, 0, 0), 1);
    }

    @java.lang.Override
    public void input() {
        cameraInc.set(0, 0, 0);
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_W)) {
            cameraInc.z = -1;
        }
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_A)) {
            cameraInc.x = -1;
        }
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_Z)) {
            cameraInc.y = -1;
        }
        if (windowManager.isKeyPressed(GLFW.GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
    }

    @java.lang.Override
    public void update(float interval, MouseInput mouseInput) {
        camera.movePosition(cameraInc.x * CAMERA_MOVE_SPEED,
                cameraInc.y * CAMERA_MOVE_SPEED, cameraInc.z * CAMERA_MOVE_SPEED);
        if (mouseInput.isRightButtonPress()) {
            Vector2f rotVec = mouseInput.getDisplayVec();
            camera.moveRotation(rotVec.x * 0.2f, rotVec.y * 0.2f, 0);
            mouseInput.setDisplVec(0, 0);
        }
        entity.incRotation(0.0f, 0.5f, 0.0f);
    }

    @java.lang.Override
    public void render() {
        if (windowManager.isResize()) {
            GL11.glViewport(0, 0, windowManager.getWidth(), windowManager.getHeight());
            windowManager.setResize(true);
        }
        windowManager.setClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        renderManager.render(entity, camera);
    }

    @java.lang.Override
    public void cleanUp() {
        renderManager.cleanUp();
        loader.cleanUp();
    }
}
