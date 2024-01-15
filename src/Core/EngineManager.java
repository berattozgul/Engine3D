package Core;

import Core.Utils.Constants;
import Launcher.Main;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.openxr.MSFTPerceptionAnchorInterop;

public class EngineManager {
    public static final long NANOSECOUNDS = 1000000000L;
    public static final float FRAMERATE = 60;
    private static int fps;
    private static float frameTime = 1.0f / FRAMERATE;
    private boolean isRunning;
    private WindowManager window;
    private MouseInput mouseInput;
    private GLFWErrorCallback errorCallback;
    private ILogic gameLogic;

    private void init() throws Exception {
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        window = Main.getWindow();
        gameLogic = Main.getGame();
        mouseInput=new MouseInput();
        window.init();
        gameLogic.init();
        mouseInput.init();
    }

    public void start() throws Exception {
        init();
        if (isRunning) {
            return;
        }
        run();
    }

    public void run() {
        this.isRunning = true;
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;
        while (isRunning) {
            boolean render = false;
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;
            unprocessedTime += passedTime / (double) NANOSECOUNDS;
            frameCounter += passedTime;

            input();

            while (unprocessedTime > frameTime) {
                render = true;
                unprocessedTime -= frameTime;
                if (window.windowShouldClose()) {
                    stop();
                }
                if (frameCounter >= NANOSECOUNDS) {
                    setFps(frames);
                    window.setTitle(Constants.TITLE + getFps());
                    frames = 0;
                    frameCounter = 0;
                }
            }
            if (render) {
                update(frameTime,mouseInput);
                render();
                frames++;
            }

        }
        cleanUp();
    }

    private void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
    }

    private void input() {
        mouseInput.input();
        gameLogic.input();
    }

    private void render() {

        gameLogic.render();
        window.update();
    }

    public void update(float interval,MouseInput mouseInput) {
        gameLogic.update(interval,mouseInput);
    }

    private void cleanUp() {
        window.cleanUp();
        gameLogic.cleanUp();
        errorCallback.free();
        GLFW.glfwTerminate();
    }

    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        EngineManager.fps = fps;
    }
}
