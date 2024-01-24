package Core.Managers;

import Core.ILogic;
import Core.Utils.Constants;
import Launcher.Main;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import static Core.Utils.Constants.FRAMERATE;
/**
 * The EngineManager class is responsible for managing the game engine, including initialization,
 * the main game loop, and cleanup procedures.
 */
public class EngineManager {

    /**
     * Constant representing one second in nanoseconds.
     */
    public static final long NANOSECOND = 1000000000L;

    private static int fps;
    private static final float frametime = 1.0f / Constants.FRAMERATE;

    private boolean isRunning;

    private WindowManager window;
    private GLFWErrorCallback errorCallback;
    private ILogic gameLogic;
    private MouseInput mouseInput;

    /**
     * Initializes the engine by setting up error callbacks, creating the window, and initializing input.
     *
     * @throws Exception If an error occurs during initialization.
     */
    private void init() throws Exception {
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        window = Main.getWindow();
        gameLogic = Main.getGame();
        mouseInput = new MouseInput();
        window.init();
        gameLogic.init();
        mouseInput.init();
    }

    /**
     * Starts the game engine, initializing it if necessary and then entering the main game loop.
     *
     * @throws Exception If an error occurs during engine startup.
     */
    public void start() throws Exception {
        init();

        if (isRunning)
            return;
        run();
    }

    /**
     * The main game loop responsible for updating and rendering the game until the engine is stopped.
     */
    private void run() {
        this.isRunning = true;

        int frames = 0;
        long frameCounter = 0;

        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        // Rendering loop
        while (isRunning) {
            boolean render = false;

            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) NANOSECOND;
            frameCounter += passedTime;

            input();

            while (unprocessedTime > frametime) {
                render = true;
                unprocessedTime -= frametime;

                if (window.windowShouldClose())
                    stop();

                if (frameCounter >= NANOSECOND) {
                    setFps(frames);
                    window.setTitle(Constants.TITLE + " FPS: " + getFps());
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if (render) {
                input();
                update();
                render();
                frames++;
            }
        }
        cleanup();
    }

    /**
     * Stops the game engine.
     */
    private void stop() {
        if (!isRunning)
            return;
        isRunning = false;
    }

    /**
     * Handles user input.
     */
    private void input() {
        mouseInput.input();
        gameLogic.input();
    }

    /**
     * Renders the game.
     */
    private void render() {
        gameLogic.render();
        window.update();
    }

    /**
     * Updates the game logic.
     */
    private void update() {
        gameLogic.update(mouseInput);
    }

    /**
     * Cleans up resources and terminates the game engine.
     */
    private void cleanup() {
        window.cleanup();
        gameLogic.cleanup();
        errorCallback.free();
        GLFW.glfwTerminate();
    }

    /**
     * Gets the current frames per second (FPS) of the game.
     *
     * @return The current FPS.
     */
    public static int getFps() {
        return fps;
    }

    /**
     * Sets the current frames per second (FPS) of the game.
     *
     * @param fps The new FPS value.
     */
    public static void setFps(int fps) {
        EngineManager.fps = fps;
    }
}
