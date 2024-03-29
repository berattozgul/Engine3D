//TestGame.Java
package Launcher;

import Core.*;
import Core.Entities.Entity;
import Core.Entities.Model;
import Core.Entities.Texture;
import Core.Lightning.DirectionalLight;
import Core.Lightning.PointLight;
import Core.Lightning.SpotLight;
import Core.Managers.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import static Core.Utils.Constants.CAMERA_STEP;
import static Core.Utils.Constants.MOUSE_SENSITIVITY;
/**
 * The TestGame class implements the game logic for the 3D engine.
 * It initializes the renderer, window, loader, camera, lights, and entities,
 * handles user input, updates the game state, and renders the scene.
 */
public class TestGame implements ILogic {


    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private List<Entity> entities;
    private Camera camera;

    Vector3f cameraInc;
    private float lightAngle;
    private DirectionalLight directionalLight;
    private PointLight[] pointLights;
    private SpotLight[] spotLights;
    /**
     * Constructs a TestGame object.
     * Initializes the renderer, window, loader, camera, and lights.
     */
    public TestGame(){
        renderer = new RenderManager();
        window = Main.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
        lightAngle=-90;
    }
    /**
     * Initializes the game, including renderer, loader, camera, lights, and entities.
     *
     * @throws Exception If an error occurs during initialization.
     */
    @Override
    public void init() throws Exception {
        renderer.init();

        Model model=loader.loadOBJModel("/models/cube.obj");
        model.setTexture(new Texture(loader.loadTexture("textures/blue.png")),1f);

        entities=new ArrayList<>();
        Random rand=new Random();
        for(int i=0;i<200;i++) {
            float x = rand.nextFloat() * 100 - 50;
            float y = rand.nextFloat() * 100 - 50;
            float z = rand.nextFloat() * 300;
            entities.add(new Entity(model, new Vector3f(x, y, z), new Vector3f(rand.nextFloat() * 180, rand.nextFloat() * 180, z), 2));
        }
        entities.add(new Entity(model,new Vector3f(0,0,-5f),new Vector3f(0,0,0),2));


        model=loader.loadOBJModel("/models/bunny.obj");
        model.setTexture(new Texture(loader.loadTexture("textures/world.png")),5);
        entities.add(new Entity(model,new Vector3f(0,-3,-5),new Vector3f(0,0,0),2));


        model=loader.loadOBJModel("/models/alligator.obj");
        model.setTexture(new Texture(loader.loadTexture("textures/grassblock.png")),1);
        entities.add(new Entity(model,new Vector3f(0,-10,-5),new Vector3f(0,0,0),2));



        float lightIntensity=1.0f;
        //pointlight
        Vector3f lightPosition =new Vector3f(-0.5f,-0.5f,-3.2f);
        Vector3f lightColor=new Vector3f(1,1,1);
        PointLight pointLight=new PointLight(lightColor,lightPosition,lightIntensity,0,0,1);
        //spotLight
        Vector3f coneDir=new Vector3f(0,0,1);
        float cutoff=(float)Math.cos(Math.toRadians(180));
        SpotLight spotLight=new SpotLight(new PointLight(lightColor,new Vector3f(0,0,3.6f)
                ,lightIntensity,0,0
                ,1),coneDir,cutoff);

        SpotLight spotLight1 = new SpotLight (new PointLight (lightColor, lightPosition,
                lightIntensity, 0, 0,  1), coneDir, cutoff);
        spotLight1.getPointLight().setPosition(new Vector3f(0.5f,0.5f,-3.6f));
        //directionalLight
        lightPosition = new Vector3f(-1,-10,0);
        lightColor = new Vector3f(1,1,1);
        directionalLight=new DirectionalLight(lightColor,lightPosition,lightIntensity);

        pointLights=new PointLight[]{pointLight};
        spotLights=new SpotLight[]{spotLight,spotLight1};
    }
    /**
     * Handles user input for camera movement and light adjustments.
     */
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


        float lightPos=pointLights[0].getPosition().z;
        if(window.isKeyPressed(GLFW.GLFW_KEY_N)){
            pointLights[0].getColor().z=lightPos+0.01f;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_M)){
            pointLights[0].getColor().z=lightPos-0.01f;
            System.out.println(pointLights[0].getColor().z);
        }
    }
    /**
     * Updates the game state, including camera movement, entity rotation, and light adjustments.
     *
     * @param mouseInput The mouse input for handling camera rotation.
     */
    @Override
    public void update(MouseInput mouseInput) {
        camera.movePosition(cameraInc.x * CAMERA_STEP, cameraInc.y * CAMERA_STEP, cameraInc.z * CAMERA_STEP);
        entities.get(0).incRotation(0.0f, 0.5f, 0.0f);

        if(mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }

        lightAngle+=1.5f;
        if(lightAngle>90){
            directionalLight.setIntensity(0);
            if(lightAngle>=360){
                lightAngle=-90;
            }
        } else if (lightAngle<=-80 ||lightAngle>=80) {
            float factor=1-(Math.abs(lightAngle)-80)/10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColor().y=Math.max(factor,0.9f);
            directionalLight.getColor().z=Math.max(factor,0.5f);

        }else{
            directionalLight.setIntensity(1);
            directionalLight.getColor().x=1;
            directionalLight.getColor().y=1;
            directionalLight.getColor().z=1;
        }
        double angRad=Math.toRadians(lightAngle);
        directionalLight.getDirection().x=(float)Math.sin(angRad);
        directionalLight.getDirection().y=(float)Math.cos(angRad);
        for(Entity entity:entities){
            renderer.processEntity(entity);
        }
    }
    /**
     * Renders the scene using the renderer.
     */
    @Override
    public void render() {
        if(window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        renderer.clear();
//        renderer.render(shaftModel);
        renderer.render( camera,directionalLight,pointLights,spotLights);
    }
    /**
     * Cleans up resources used by the renderer and loader.
     */
    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }
}