package Core.Managers;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Camera {
    private Vector3f position, rotation;

    public Camera() {
        position = new Vector3f(0,0,0);
        rotation = new Vector3f(0,0,0);
    }

    public Camera(Vector3f pos, Vector3f rotation) {
        this.position = pos;
        this.rotation = rotation;
    }

    public void movePosition(float x, float y, float z){
        if(z != 0) {
            position.x += (float) java.lang.Math.sin(java.lang.Math.toRadians(rotation.y)) * -1.0f * z;
            position.z += (float) java.lang.Math.cos(java.lang.Math.toRadians(rotation.y)) * z;
        }
        if(x != 0) {
            position.x += (float) java.lang.Math.sin(java.lang.Math.toRadians(rotation.y - 90)) * -1.0f * x;
            position.z += (float) java.lang.Math.cos(Math.toRadians(rotation.y - 90)) * x;
        }
        position.y += y;
    }

    public void setPosition(float x, float y, float z){
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;

    }

    public void setRotation(float x, float y, float z){
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void moveRotation(float x, float y, float z){
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
