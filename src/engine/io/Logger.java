package engine.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class Logger {

    public enum Type {INFO, ERROR, DEBUG, DEBUG_OBJECT, DEBUG_IMAGE, DEBUG_MASK, CONSOLE_FPS, SERVER_INFO, SERVER_ERROR, MPS};

    private static Set<Type> enable = new HashSet<>();
    private static boolean logInFile = false;
    private static PrintWriter out;

    //Вывод сообщения
    public static void print(String s, Type type){
        if (type.equals(Type.ERROR)) s = "[ERROR] " + s;
        if (type.equals(Type.INFO)) s = "[INFO] " + s;
        if (type.equals(Type.SERVER_ERROR)) s = "[SERVER ERROR] " + s;
        if (type.equals(Type.SERVER_INFO)) s = "[SERVER INFO] " + s;


        if (enable.contains(type)){
            System.out.print(s);
            if (Logger.logInFile) out.print(s);
        }
    }

    public static void println(String s, Type type){
        Logger.print(s + "\n", type);
    }

    public static void enable(Type type){
        enable.add(type);
    }

    public static void disable(Type type){
        enable.remove(type);
    }

    public static void logInFileEnable(){
        if (!logInFile){
            try {
                out = new PrintWriter(new FileWriter("res/log.txt"));
                logInFile = true;
            } catch (IOException e) {
                out.close();
                logInFile = false;
                Logger.println("Write log file", Type.ERROR);
            }
        }
    }

    public static void logInFileDisable(){
        if (logInFile){
            out.close();
            logInFile = false;
        }
    }
}
