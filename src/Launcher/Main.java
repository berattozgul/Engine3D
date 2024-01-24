package Launcher;

import Core.Managers.EngineManager;
import Core.Utils.Constants;
import Core.Managers.WindowManager;
import org.lwjgl.*;
/**
 * The Main class serves as the entry point for the application.
 * It initializes the window, game, and engine components, and starts the game loop.
 */
public class Main {

    private static WindowManager window;
    private static TestGame game;

    /**
     * The main entry point for the application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        System.out.println(Version.getVersion());
        window = new WindowManager(Constants.TITLE, 800, 600, false);
        game = new TestGame();
        EngineManager engine = new EngineManager();
        try {
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the WindowManager instance associated with the application.
     *
     * @return The WindowManager instance.
     */
    public static WindowManager getWindow() {
        return window;
    }

    /**
     * Gets the TestGame instance associated with the application.
     *
     * @return The TestGame instance.
     */
    public static TestGame getGame() {
        return game;
    }
}
