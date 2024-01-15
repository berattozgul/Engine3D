//TestGame.Java
package Launcher;

import Core.*;
import Core.Entities.Entity;
import Core.Entities.Model;
import Core.Entities.Texture;
import Core.Managers.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import static Core.Utils.Constants.CAMERA_STEP;
import static Core.Utils.Constants.MOUSE_SENSITIVITY;

public class TestGame implements ILogic {


    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private Entity entity;
    private Entity entityBunny;
    private Camera camera;

    Vector3f cameraInc;

    public TestGame(){
        renderer = new RenderManager();
        window = Main.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        //  Cube

        Model model=loader.loadOBJModel("/models/bunny.obj");
        model.setTexture(new Texture(loader.loadTexture("textures/grassblock.png")));
        entity = new Entity(model, new Vector3f(0,0,-5), new Vector3f(0,0,0), 1);
    }

    @Override
    public void input() {
        cameraInc.set(0,0,0);

        if(window.isKeyPressed(GLFW.GLFW_KEY_W))
            cameraInc.z = -1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_S))
            cameraInc.z = 1;

        if(window.isKeyPressed(GLFW.GLFW_KEY_A))
            cameraInc.x = -1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_D))
            cameraInc.x = 1;

        if(window.isKeyPressed(GLFW.GLFW_KEY_Z))
            cameraInc.y = -1;
        if(window.isKeyPressed(GLFW.GLFW_KEY_X))
            cameraInc.y = 1;

        if(window.isKeyPressed(GLFW.GLFW_KEY_ENTER))
            System.out.println(cameraInc);

    }

    @Override
    public void update(MouseInput mouseInput) {
        camera.movePosition(cameraInc.x * CAMERA_STEP, cameraInc.y * CAMERA_STEP, cameraInc.z * CAMERA_STEP);
//        entity.incRotation(0.0f, 0.5f, 0.0f);

        if(mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
    }

    @Override
    public void render() {
        if(window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColour(0.0f, 0.0f, 0.0f, 0.0f);
        renderer.clear();
//        renderer.render(shaftModel);
        renderer.render(entity, camera);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }
}
