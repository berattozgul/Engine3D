package Core.Managers;

import Core.Entities.Model;
import Core.Utils.Utils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.opengl.GL46;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import java.util.List;
import java.util.ArrayList;

/**
 * The ObjectLoader class is responsible for loading 3D models in the Wavefront OBJ format and managing associated resources.
 */
public class ObjectLoader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();
    /**
     * Loads a 3D model from an OBJ file.
     *
     * @param filename The path to the OBJ file.
     * @return A Model object representing the loaded 3D model.
     */
    public Model loadOBJModel(String filename){
        List<String> lines=Utils.readAllLines(filename);

        List<Vector3f>vertices=new ArrayList<>();
        List<Vector3f>normals=new ArrayList<>();
        List<Vector2f>texts=new ArrayList<>();
        List<Vector3i>faces=new ArrayList<>();


        for(String line:lines){
            String[]tokens=line.split("\\s+");
            switch(tokens[0]){
                case "v":
                    Vector3f verticesVec=new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    );
                    vertices.add(verticesVec);
                    break;
                case "vt":
                    Vector2f textureVec=new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2])
                    );
                    texts.add(textureVec);
                    break;
                case "vn":
                    Vector3f normalsVec=new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    );
                    normals.add(normalsVec);
                    break;
                case "f":
                    processFace(tokens[1],faces);
                    processFace(tokens[2],faces);
                    processFace(tokens[3],faces);
                    break;
                default:
                    break;
            }
        }
        List<Integer>indices=new ArrayList<>();
        float[]verticesArr=new float[vertices.size()*3];
        int i=0;
        for(Vector3f pos:vertices){
            verticesArr[i*3]=pos.x;
            verticesArr[i*3+1]=pos.y;
            verticesArr[i*3+2]=pos.z;
            i++;
        }
        float[]textCoordArr=new float[vertices.size()*2];
        float[]normalArr=new float[vertices.size()*3];
        for(Vector3i face:faces){
            processVertex(face.x,face.y,face.z,texts,normals,indices,textCoordArr,normalArr);
        }
        int[] indicesArr=indices.stream().mapToInt((Integer v)->v).toArray();

        return loadModel(verticesArr,textCoordArr,normalArr,indicesArr);
    }
    /**
     * Processes a vertex in the OBJ file and updates the corresponding lists.
     *
     * @param pos         The position index of the vertex.
     * @param textCoord   The texture coordinate index of the vertex.
     * @param normal      The normal vector index of the vertex.
     * @param textCoordList The list of texture coordinates.
     * @param normalList    The list of normal vectors.
     * @param indicesList   The list of vertex indices.
     * @param textCoordArr  The array to store texture coordinates.
     * @param normalArr     The array to store normal vectors.
     */
    public static void processVertex(int pos,int textCoord,int normal
            ,List<Vector2f> textCoordList,List<Vector3f>normalList,
                                     List<Integer>indicesList,
                                     float[]textCoordArr,float[]normalArr){
        indicesList.add(pos);
        if(textCoord>=0){
            Vector2f textCoordVec=textCoordList.get(textCoord);
            textCoordArr[pos*2]=textCoordVec.x;
            textCoordArr[pos*2+1]=1-textCoordVec.y;
        }
        if(normal>=0){
            Vector3f normalVec=normalList.get(normal);
            normalArr[pos*3]=normalVec.x;
            normalArr[pos*3+1]=normalVec.y;
            normalArr[pos*3+2]=normalVec.z;
        }
    }
    /**
     * Processes a face in the OBJ file and updates the list of faces.
     *
     * @param token The face token from the OBJ file.
     * @param faces The list of faces.
     */
    private static void processFace(String token, List<Vector3i>faces){
        String[] lineToken=token.split("/");
        int length= lineToken.length;
        int pos=-1,coords=-1,normal=-1;
        pos=Integer.parseInt(lineToken[0])-1;
        if(length>1){
            String textCoord=lineToken[1];
            coords=textCoord.length()>0?Integer.parseInt(textCoord)-1 :-1;
            if(length>2){
                normal=Integer.parseInt(lineToken[2])-1;

            }
        }
        Vector3i facesVec=new Vector3i(pos,coords,normal);
        faces.add(facesVec);
    }
    /**
     * Loads a 3D model with the provided vertex, texture coordinate, normal, and index data.
     *
     * @param vertices      The array of vertex coordinates.
     * @param textureCoords The array of texture coordinates.
     * @param normals       The array of normal vectors.
     * @param indices       The array of vertex indices.
     * @return A Model object representing the loaded 3D model.
     */
    public Model loadModel(float[] vertices, float[] textureCoords,
                           float[]normals,int[] indices) {
        int id = createVAO();
        storeIndicesBuffer(indices);
        storeDataInAttribList(0, 3, vertices);
        storeDataInAttribList(1, 2, textureCoords);
        storeDataInAttribList(2, 3, normals);
        unbind();
        return new Model(id, indices.length);
    }
    /**
     * Loads a texture from an image file.
     *
     * @param filename The path to the image file.
     * @return The OpenGL texture ID for the loaded texture.
     * @throws Exception If an error occurs during texture loading.
     */
    public int loadTexture(String filename) throws Exception {
        int width, height;
        ByteBuffer buffer;
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);

            buffer = STBImage.stbi_load(filename, w, h, c, 4);
            if(buffer == null)
                throw new Exception("Image File " + filename + " not loaded " + STBImage.stbi_failure_reason());

            width = w.get();
            height = h.get();
        }

        int id = GL11.glGenTextures();
        textures.add(id);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_LINEAR);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        STBImage.stbi_image_free(buffer);
        return id;
    }
    /**
     * Creates a new Vertex Array Object (VAO) in OpenGL.
     *
     * @return The OpenGL ID of the created VAO.
     */
    private int createVAO() {
        int id = GL30.glGenVertexArrays();
        vaos.add(id);
        GL30.glBindVertexArray(id);
        return id;
    }
    /**
     * Stores vertex data in a Vertex Buffer Object (VBO) for a specific attribute.
     *
     * @param attribNo    The attribute number for the VBO.
     * @param vertexCount The number of components per vertex attribute.
     * @param data        The array of vertex data.
     */
    private void storeDataInAttribList(int attribNo, int vertexCount, float[] data){
        int vbo = GL15.glGenBuffers();
        vbos.add(vbo);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = Utils.storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attribNo, vertexCount, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
    /**
     * Stores index data in an Element Array Buffer (EBO).
     *
     * @param indices The array of vertex indices.
     */
    private void storeIndicesBuffer(int[] indices) {
        int vbo = GL15.glGenBuffers();
        vbos.add(vbo);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
        IntBuffer buffer = Utils.storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }
    /**
     * Unbinds the current VAO in OpenGL.
     */
    private void unbind() {
        GL30.glBindVertexArray(0);
    }
    /**
     * Cleans up resources by deleting VAOs, VBOs, and textures.
     */
    public void cleanup(){
        for(int vao : vaos)
            GL30.glDeleteVertexArrays(vao);
        for(int vbo : vbos)
            GL30.glDeleteBuffers(vbo);
        for(int texture: textures)
            GL11.glDeleteTextures(texture);
    }
}