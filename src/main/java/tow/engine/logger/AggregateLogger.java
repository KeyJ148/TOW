package tow.engine.logger;

import java.util.ArrayList;

public class AggregateLogger {

    private ArrayList<Logger> loggers = new ArrayList<>();

    public AggregateLogger(){
        this(new Logger.Type[] {});
    }

    public AggregateLogger(Logger.Type[] typesEnable){
        this(typesEnable, typesEnable);
    }

    public AggregateLogger(Logger.Type[] consoleTypesEnable, Logger.Type[] fileTypesEnable){
        loggers.add(new ConsoleLogger(consoleTypesEnable));
        loggers.add(new FileLogger(fileTypesEnable));
    }

    public void enableType(Logger.Type type){
        for(Logger logger : loggers) logger.enableType(type);
    }

    public void disableType(Logger.Type type){
        for(Logger logger : loggers) logger.disableType(type);
    }

    public void print(String s, Logger.Type type){
        for(Logger logger : loggers) logger.print(s, type);
    }

    public void println(String s, Logger.Type type){
        for(Logger logger : loggers) logger.println(s, type);
    }

    public void print(String s, Exception e, Logger.Type type){
        for(Logger logger : loggers) logger.print(s, e, type);
    }

    public void println(String s, Exception e, Logger.Type type){
        for(Logger logger : loggers) logger.println(s, e, type);
    }

    public void close(){
        for(Logger logger : loggers) logger.close();
    }
}
