package Core;
import org.lwjgl.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.ovr.OVRMatrix4f;
import org.lwjgl.system.MemoryUtil;

public class WindowManager {
    public static final float FOV=(float) Math.toRadians(60);
    public static final float Z_NEAR=0.01f;
    public static final float Z_FAR=1000f;
    private final String title;
    private int width,height;
    private long window;
    private boolean resize,vSync;

    public WindowManager(java.lang.String title, int width, int height, boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
    }
    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        if(!GLFW.glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE,GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE,GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR,3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR,2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE,GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT,GLFW.GLFW_TRUE);
        boolean maximized=false;
        if(width==0||height==0){
            width=100;
            height=100;
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED,GLFW.GLFW_TRUE);
            maximized=true;
        }
        window=GLFW.glfwCreateWindow(width,height,title, MemoryUtil.NULL,MemoryUtil.NULL);
        if(window==MemoryUtil.NULL){
            throw new RuntimeException("Failed to create GLFW window");
        }
        GLFW.glfwSetFramebufferSizeCallback(window,(window,width,height)->{
           this.width=width;
           this.height=height;
           this.setResize(true);
        });
        GLFW.glfwSetKeyCallback(window,(window,key,scancode,action,mods)->{
           if(key==GLFW.GLFW_KEY_ESCAPE&&action == GLFW.GLFW_RELEASE){
               GLFW.glfwSetWindowShouldClose(window,true);
           }
        });
        if(maximized){
            GLFW.glfwMaximizeWindow(window);
        }else{
            GLFWVidMode vidMode=GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(window,(vidMode.width()-width)/2,
                    (vidMode.height()-height)/2);

        }
        GLFW.glfwMakeContextCurrent(window);
        if(isvSync()){
            GLFW.glfwSwapInterval(1);
        }
        GLFW.glfwShowWindow(window);
        GL.createCapabilities();
        GL11.glClearColor(0.0f,0.0f,0.0f,0.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_STENCIL);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BACK);
    }

    public void update(){
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    public void cleanUp(){
        GLFW.glfwDestroyWindow(window);
    }

    public void setClearColor(float r,float g,float b,float a){
        GL11.glClearColor(r,g,b,a);
    }

    public boolean isKeyPressed(int keyCode){
        return GLFW.glfwGetKey(window,keyCode)==GLFW.GLFW_PRESS;
    }

    public boolean windowShouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    public java.lang.String getTitle() {
        return title;
    }

    public boolean isResize() {
        return resize;
    }
    public void setTitle(String title){
        GLFW.glfwSetWindowTitle(window,title);
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

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}