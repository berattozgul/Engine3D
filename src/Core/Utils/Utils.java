package Core.Utils;

import org.lwjgl.system.MemoryUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import static java.io.FileDescriptor.in;
/**
 * The Utils class provides utility methods for handling common tasks.
 */
public class Utils {
    /**
     * Converts a float array into a FloatBuffer.
     *
     * @param data The float array to be converted.
     * @return The resulting FloatBuffer.
     */
    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }
    /**
     * Converts an int array into an IntBuffer.
     *
     * @param data The int array to be converted.
     * @return The resulting IntBuffer.
     */
    public static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();
        return buffer;
    }
    /**
     * Concatenates two float arrays using System.arraycopy.
     *
     * @param vertices The first float array.
     * @param vertices1 The second float array to concatenate.
     * @return The concatenated float array.
     */
    public static float[] concatWithArrayCopy(float[] vertices, float[] vertices1) {
        float[] result = Arrays.copyOf(vertices, vertices.length + vertices1.length);
        System.arraycopy(vertices1, 0, result, vertices.length, vertices1.length);
        return result;
    }
    /**
     * Loads the content of a resource file into a String.
     *
     * @param filename The path to the resource file.
     * @return The content of the resource file as a String.
     * @throws Exception If an error occurs during the loading process.
     */
    public static String loadResource(String filename) throws Exception {
        String result;
        try (
                InputStream in = Utils.class.getResourceAsStream(filename);
                Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }
    /**
     * Reads all lines from a resource file and returns them as a list of strings.
     *
     * @param filename The path to the resource file.
     * @return A list of strings representing the lines in the resource file.
     */
    public static List<String> readAllLines(String filename) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Class.forName(Utils.class.getName())
                .getResourceAsStream(filename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
