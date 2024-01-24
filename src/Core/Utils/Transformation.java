package Core.Utils;

import Core.Managers.Camera;
import Core.Entities.Entity;
import org.joml.Matrix4f;
import org.joml.Vector3f;
/**
 * The Transformation class provides utility methods for creating transformation matrices.
 */
public class Transformation {
    /**
     * Creates a transformation matrix for the specified entity.
     *
     * @param entity The entity for which the transformation matrix is created.
     * @return The transformation matrix.
     */
    public static Matrix4f createTransformationMatrix(Entity entity) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(entity.getPos()).
                rotateX((float) Math.toRadians(entity.getRotation().x)).
                rotateY((float) Math.toRadians(entity.getRotation().y)).
                rotateZ((float) Math.toRadians(entity.getRotation().z)).
                scale(entity.getScale());
        return matrix;
    }
    /**
     * Creates a view matrix for the specified camera.
     *
     * @param camera The camera for which the view matrix is created.
     * @return The view matrix.
     */
    public static Matrix4f getViewMatrix(Camera camera) {
        Vector3f pos = camera.getPosition();
        Vector3f rot = camera.getRotation();

        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.rotate((float) Math.toRadians(rot.x), new Vector3f(1,0,0))
                .rotate((float) Math.toRadians(rot.y), new Vector3f(0,1,0))
                .rotate((float) Math.toRadians(rot.z), new Vector3f(0,0,1));
        matrix.translate(-pos.x, -pos.y, -pos.z);   //  Move the world opposite the camera
        return matrix;
    }

}
