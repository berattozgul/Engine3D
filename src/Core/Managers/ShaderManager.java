package Core.Managers;

import Core.Lightning.DirectionalLight;
import Core.Lightning.PointLight;
import Core.Lightning.SpotLight;
import Core.Materials.Material;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;
/**
 * The ShaderManager class is responsible for managing shaders in OpenGL, including
 * creating, compiling, and linking shaders, setting uniform variables, and cleaning up resources.
 */
public class ShaderManager {

    private final int programID;
    private int vertexShaderID, fragmentShaderID;
    private final Map<String, Integer> uniforms;
    /**
     * Constructs a new ShaderManager instance, creating an OpenGL program and initializing data structures.
     *
     * @throws Exception If an error occurs during shader program creation.
     */
    public ShaderManager() throws Exception {
        programID = GL20.glCreateProgram();
        if (programID == 0)
            throw new Exception("Could not create the shader!");

        uniforms = new HashMap<>();
    }

    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = GL20.glGetUniformLocation(programID, uniformName);
        if (uniformLocation < 0)
            throw new Exception("Could not find uniform " + uniformName);
        uniforms.put(uniformName, uniformLocation);
    }
    /**
     * Creates a set of material-related uniform variables based on the given uniform name.
     *
     * @param uniformName The base name for the material-related uniforms.
     * @throws Exception If any of the material-related uniforms cannot be created.
     */
    public void createMaterialUniform(String uniformName) throws Exception {
        createUniform(uniformName + ".ambient");
        createUniform(uniformName + ".diffuse");
        createUniform(uniformName + ".specular");
        createUniform(uniformName + ".hasTexture");
        createUniform(uniformName + ".reflectance");
    }

    public void setUniform(String uniformName, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            GL20.glUniformMatrix4fv(uniforms.get(uniformName), false,
                    value.get(stack.mallocFloat(16)));
        }
    }

    public void setUniform(String uniformName, Vector3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            GL20.glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
        }
    }

    public void setUniform(String uniformName, Vector4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            GL20.glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
        }
    }
    /**
     * Creates directional light-related uniform variables based on the given uniform name.
     *
     * @param uniformName The base name for the directional light-related uniforms.
     * @throws Exception If any of the directional light-related uniforms cannot be created.
     */
    public void createDirectionalLightUniform(String uniformName)throws Exception{
        createUniform(uniformName+".color");;
        createUniform(uniformName+".direction");;
        createUniform(uniformName+".intensity");;

    }
    public void setUniform(String uniformName, Material material) {
        setUniform(uniformName + ".ambient", material.getAmbientColor());
        setUniform(uniformName + ".diffuse", material.getDiffuseColor());
        setUniform(uniformName + ".specular", material.getSpecularColor());
        setUniform(uniformName + ".hasTexture", material.hasTexture() ? 1 : 0);
        setUniform(uniformName + ".reflectance", material.getReflectance());
    }
    public void creatPointLightUniform(String uniformName)throws Exception{
        createUniform(uniformName+".color");
        createUniform(uniformName+".position");
        createUniform(uniformName+".intensity");
        createUniform(uniformName+".constant");
        createUniform(uniformName+".linear");
        createUniform(uniformName+".exponent");
    }
    public void createSpotLightUniform(String uniformName)throws Exception{
        creatPointLightUniform(uniformName+".pl");
        createUniform(uniformName+".coneDir");
        createUniform(uniformName+".cutOff");
    }
    public void createPointLightListUniform(String uniformName,int size)throws Exception{
        for(int i=0;i<size;i++){
            creatPointLightUniform(uniformName+"["+i+"]");
        }
    }
    public void createSpotLightListUniform(String uniformName,int size)throws Exception{
        for(int i=0;i<size;i++){
            createSpotLightUniform(uniformName+"["+i+"]");
        }
    }

    public void setUniform(String uniformName, boolean value) {
        float res = 0;
        if (value)
            res = 1;
        GL20.glUniform1f(uniforms.get(uniformName), res);
    }

    public void setUniform(String uniformName, int value) {
        GL20.glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, float value) {
        GL20.glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, SpotLight spotLight){
        setUniform(uniformName+".pl",spotLight.getPointLight());
        setUniform(uniformName+".coneDir",spotLight.getConeDirection());
        setUniform(uniformName+".cutOff",spotLight.getCutOff());
    }
    /**
     * Creates a vertex shader with the provided shader code.
     *
     * @param shaderCode The source code for the vertex shader.
     * @throws Exception If an error occurs during vertex shader creation.
     */
    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderID = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
    }
    public void setUniform(String uniformName, DirectionalLight directionalLight){
        setUniform(uniformName+".color",directionalLight.getColor());
        setUniform(uniformName+".direction",directionalLight.getDirection());
        setUniform(uniformName+".intensity",directionalLight.getIntensity());

    }
    public void setUniform(String uniformName, PointLight pointLight){
        setUniform(uniformName+".color",pointLight.getColor());
        setUniform(uniformName+".position",pointLight.getPosition());
        setUniform(uniformName+".intensity",pointLight.getIntensity());
        setUniform(uniformName+".constant",pointLight.getConstant());
        setUniform(uniformName+".linear",pointLight.getLinear());
        setUniform(uniformName+".exponent",pointLight.getExponent());
    }
    public void setUniform(String uniformName,PointLight[] pointLights){
        int numLights=pointLights!=null? pointLights.length : 0;
        for(int i =0;i<numLights;i++){
            setUniform(uniformName,pointLights[i],i);
        }
    }
    public void setUniform(String uniformName,SpotLight[] spotLights){
        int numLights=spotLights!=null? spotLights.length : 0;
        for(int i =0;i<numLights;i++){
            setUniform(uniformName,spotLights[i],i);
        }
    }

    public void setUniform(String uniformName, SpotLight spotLight, int pos) {
        setUniform(uniformName+"["+pos+"]",spotLight);
    }

    public void setUniform(String uniformName, PointLight pointLight, int pos) {
        setUniform(uniformName+"["+pos+"]",pointLight);
    }
    /**
     * Creates a fragment shader with the provided shader code.
     *
     * @param shaderCode The source code for the fragment shader.
     * @throws Exception If an error occurs during fragment shader creation.
     */
    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderID = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
    }
    /**
     * Creates a shader of the specified type with the provided shader code.
     *
     * @param shaderCode The source code for the shader.
     * @param shaderType The type of the shader (GL_VERTEX_SHADER or GL_FRAGMENT_SHADER).
     * @return The ID of the created shader.
     * @throws Exception If an error occurs during shader creation.
     */
    public int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderID = GL20.glCreateShader(shaderType);
        if (shaderID == 0)
            throw new Exception("Error creating shader, type : " + shaderType);

        GL20.glShaderSource(shaderID, shaderCode);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0)
            throw new Exception("Error compiling shader code: TYPE : " + shaderType
                    + " info " + GL20.glGetShaderInfoLog(shaderID, 1024));

        GL20.glAttachShader(programID, shaderID);

        return shaderID;

    }
    /**
     * Links the shader program, detaches shaders, and validates the program.
     *
     * @throws Exception If an error occurs during shader program linking or validation.
     */
    public void link() throws Exception {
        GL20.glLinkProgram(programID);

        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == 0)
            throw new Exception("Error linking shader code: TYPE : " +
                    " info " + GL20.glGetProgramInfoLog(programID, 1024));

        if (vertexShaderID != 0)
            GL20.glDetachShader(programID, vertexShaderID);

        if (fragmentShaderID != 0)
            GL20.glDetachShader(programID, fragmentShaderID);

        GL20.glValidateProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == 0)
            throw new Exception("Unable to validate shader code: " + GL20.glGetProgramInfoLog(programID, 1024));


    }
    /**
     * Binds the shader program for use.
     */
    public void bind() {
        GL20.glUseProgram(programID);
    }
    /**
     * Unbinds the currently bound shader program.
     */
    public void unbind() {
        GL20.glUseProgram(0);
    }
    /**
     * Cleans up resources by unbinding the shader program and deleting it.
     */
    public void cleanup() {
        unbind();
        if (programID != 0)
            GL20.glDeleteProgram(programID);
    }
}