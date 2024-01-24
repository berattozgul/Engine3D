package Core.Managers;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import static Core.Utils.Constants.*;
/**
 * The WindowManager class manages the creation, configuration, and interaction with the GLFW window
 * in an OpenGL application, including input handling, window resizing, and updating the projection matrix.
 */
public class WindowManager {
    private final String title;

    private int width, height;
    private long window;

    private boolean resize, vSync;

    private final Matrix4f projectionMatrix;
    /**
     * Constructs a new WindowManager instance with the specified title, width, height, and VSync setting.
     *
     * @param title  The title of the window.
     * @param width  The initial width of the window.
     * @param height The initial height of the window.
     * @param vSync  Whether VSync is enabled.
     */
    public WindowManager(String title, int width, int height, boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        projectionMatrix = new Matrix4f();

    }

    /**
     * Initializes GLFW, creates a window, and sets various window hints based on configuration options.
     * Sets up GLFW callbacks for window resizing and key events.
     */
    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

        boolean maximised = false;
        if (width == 0 || height == 0) {
            width = 100;
            height = 100;
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
            maximised = true;
        }

        window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL)
            throw new RuntimeException("Failed to create a GLFW Window");

        GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResize(true);
        });

        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
                GLFW.glfwSetWindowShouldClose(window, true);
        });

        if (maximised) {
            GLFW.glfwMaximizeWindow(window);
        } else {
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
//            GLFW.glfwSetWindowPos(window, (vidMode.width() - width / 2), (vidMode.height() - height / 2));
        }

        GLFW.glfwMakeContextCurrent(window);

        if (isvSync())
            GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        GL11.glClearColor(0.5f, 0.5f, 0.5f, 0.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BACK);
    }
    /**
     * Swaps the front and back buffers, and polls for events.
     */
    public void update() {
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }
    /**
     * Destroys the GLFW window when the application is closing.
     */
    public void cleanup() {
        GLFW.glfwDestroyWindow(window);
    }
    /**
     * Sets the clear color for the window.
     *
     * @param r Red component of the clear color.
     * @param g Green component of the clear color.
     * @param b Blue component of the clear color.
     * @param a Alpha component of the clear color.
     */
    public void setClearColor(float r, float g, float b, float a) {
        GL11.glClearColor(r, g, b, a);
    }
    /**
     * Checks if a specific key is currently pressed.
     *
     * @param keycode The key code to check.
     * @return True if the key is pressed; false otherwise.
     */
    public boolean isKeyPressed(int keycode) {
        return GLFW.glfwGetKey(window, keycode) == GLFW.GLFW_PRESS;
    }
    /**
     * Checks if the window should close.
     *
     * @return True if the window should close; false otherwise.
     */
    public boolean windowShouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }
    /**
     * Gets the title of the window.
     *
     * @return The title of the window.
     */
    public String getTitle() {
        return title;
    }
    /**
     * Sets the title of the window.
     *
     * @param title The new title for the window.
     */
    public void setTitle(String title) {
        GLFW.glfwSetWindowTitle(window, title);
    }

    public boolean isResize() {
        return resize;
    }

    public void setResize(boolean resize) {
        this.resize = resize;
    }

    public boolean isvSync() {
        return vSync;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindow() {
        return window;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
    /**
     * Updates and retrieves the projection matrix based on the current width and height of the window.
     *
     * @return The updated projection matrix.
     */
    public Matrix4f updateProjectionMatrix() {
        float aspectRatio = (float) width / height;
        return projectionMatrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }
    /**
     * Updates the provided matrix with the projection matrix based on the specified width and height.
     *
     * @param matrix The matrix to update.
     * @param width  The width used for the projection matrix.
     * @param height The height used for the projection matrix.
     * @return The updated matrix.
     */
    public Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height) {
        float aspectRatio = (float) width / height;
        return matrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }
}