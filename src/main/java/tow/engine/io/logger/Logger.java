package tow.engine.io.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

public abstract class Logger {

    public enum Type {INFO, ERROR, DEBUG, DEBUG_OBJECT, DEBUG_IMAGE, DEBUG_MASK, DEBUG_AUDIO, CONSOLE_FPS,
        SERVER_INFO, SERVER_DEBUG, SERVER_ERROR, MPS};

    private Set<Type> typeEnabled = new HashSet<>();

    public Logger(){
        this(new Type[] {});
    }

    public Logger(Type[] typesEnable){
        for(Type type : typesEnable) enableType(type);
    }

    public void enableType(Type type){
        typeEnabled.add(type);
    }

    public void disableType(Type type){
        typeEnabled.remove(type);
    }

    //Вывод сообщения (Консоль/файл/БД)
    protected abstract void print(String s);

    public abstract void close();

    //Присвоение префикса и передача сообщения для вывода
    public void print(String s, Type type){
        if (!typeEnabled.contains(type)) return;

        if (type.equals(Type.ERROR)) s = "[ERROR] " + s;
        if (type.equals(Type.INFO)) s = "[INFO] " + s;
        if (    type.equals(Type.DEBUG) ||
                type.equals(Type.DEBUG_OBJECT) ||
                type.equals(Type.DEBUG_IMAGE) ||
                type.equals(Type.DEBUG_MASK) ||
                type.equals(Type.DEBUG_AUDIO) ||
                type.equals(Type.CONSOLE_FPS)) s = "[DEBUG] " + s;

        if (type.equals(Type.SERVER_ERROR)) s = "[SERVER ERROR] " + s;
        if (type.equals(Type.SERVER_INFO)) s = "[SERVER INFO] " + s;
        if (type.equals(Type.SERVER_DEBUG)) s = "[SERVER DEBUG] " + s;

        print(s);
    }

    public void println(String s, Type type){
        print(s + "\n", type);
    }

    public void print(String s, Exception e, Type type){
        print(s + getStackTraceAsString(e).replaceAll("\n", ""), type);
    }

    public void println(String s, Exception e, Type type){
        //Не println, потому что stackTrace и так содержит в конце перенос строки
        print(s + getStackTraceAsString(e), type);
    }

    private String getStackTraceAsString(Exception e){
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}
