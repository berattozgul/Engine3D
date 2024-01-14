package Launcher;

import Core.EngineManager;
import Core.Utils.Constants;
import Core.WindowManager;
import org.lwjgl.*;

public class Main {
    private static WindowManager window;
    private static TestGame game;
    public static void main(String[] args) {
        System.out.println(Version.getVersion());
        window=new WindowManager(Constants.TITLE,800,600,false);
        game=new TestGame();
        EngineManager engine=new EngineManager();
        try{
            engine.start();
        }catch (Exception e){

        }
    }
    public static WindowManager getWindow() {
        return window;
    }

    public static TestGame getGame() {
        return game;
    }
}