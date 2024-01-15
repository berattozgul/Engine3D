package Core;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

public class ShaderManager {

    private final int programID;
    private int vertexSharedId, fragmentShaderID;
    private final Map<String, Integer> uniforms;

    public ShaderManager() throws Exception {
        programID = GL20.glCreateProgram();
        if (programID == 0) {
            throw new Exception("Couldnt create shader");
        }
        uniforms = new HashMap<>();
    }

    public void createUniform(String uniFormname) throws Exception {
        int uniformLocation = GL20.glGetUniformLocation(programID, uniFormname);
        if (uniformLocation < 0) {
            throw new Exception();
        }
        uniforms.put(uniFormname, uniformLocation);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            GL20.glUniformMatrix4fv(uniforms.get(uniformName), false,
                    value.get(stack.mallocFloat(16)));
        }
    }

    public void setUniform(String uniformName,int value){
        GL20.glUniform1i(uniforms.get(uniformName),value);
    }
    public void setUniform(String uniformName,float value){
        GL20.glUniform1f(uniforms.get(uniformName),value);
    }
    public void setUniform(String uniformName, Vector3f value){
        GL20.glUniform3f(uniforms.get(uniformName),value.x,value.y,value.z);
    }
    public void setUniform(String uniformName, Vector4f value){
        GL20.glUniform4f(uniforms.get(uniformName),value.x,value.y,value.z,value.w);
    }
    public void setUniform(String uniformName,boolean value){
        float res=0;
        if (value) {
            res=1;
        }
        GL20.glUniform1f(uniforms.get(uniformName),res);
    }

    public void createVertexShader(String shaderCode) throws Exception {
        vertexSharedId = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderID = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
    }

    public int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderID = GL20.glCreateShader(shaderType);
        if (shaderID == 0) {
            throw new Exception("Error creating shader. Type :" + shaderType);
        }
        GL20.glShaderSource(shaderID, shaderCode);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling shader code Type :" + shaderType +
                    "Info" + GL20.glGetShaderInfoLog(shaderID, 1024));
        }
        GL20.glAttachShader(programID, shaderID);
        return shaderID;
    }

    public void link() throws Exception {
        GL20.glLinkProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking shader code Type :" +
                    "Info" + GL20.glGetProgramInfoLog(programID, 1024));
        }
        if (vertexSharedId != 0) {
            GL20.glDetachShader(programID, vertexSharedId);
        }
        if (fragmentShaderID != 0) {
            GL20.glDetachShader(programID, fragmentShaderID);
        }
        GL20.glValidateProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == 0) {
            throw new Exception("Unable to validate shader code: " + GL20.glGetProgramInfoLog(programID, 1024));
        }
    }

    public void bind() {
        GL20.glUseProgram(programID);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        unbind();
        if (programID != 0) {
            GL20.glDeleteProgram(programID);
        }
    }
}
