package Core.Utils;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.io.InputStream;
import java.util.Scanner;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import static java.io.FileDescriptor.in;

public class Utils {
    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static String loadResurce(String filename) throws Exception {
        String result;
        try (InputStream in = Utils.class.getResourceAsStream(filename);
             Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
            System.out.println("Loaded Resource Content:\n" + result);
        }
        return result;
    }
}
