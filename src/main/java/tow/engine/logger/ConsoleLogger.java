package tow.engine.logger;

public class ConsoleLogger extends Logger {

    public ConsoleLogger(){
        this(new Type[] {});
    }

    public ConsoleLogger(Type[] typesEnable){
        super(typesEnable);
    }

    @Override
    protected void print(String s) {
        System.out.print(s);
    }

    @Override
    public void close(){}
}
