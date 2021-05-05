package cc.abro.orchengine.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger extends Logger {

    private static final String LOG_DIRECTORY = "logs/";
    private FileWriter writer;

    public FileLogger() {
        this(new Type[]{}, getDefaultFilename());
    }

    public FileLogger(Type[] typesEnable) {
        this(typesEnable, getDefaultFilename());
    }

    public FileLogger(String filename) {
        this(new Type[]{}, filename);
    }

    public FileLogger(Type[] typesEnable, String filename) {
        super(typesEnable);
        try {
            createDirectory(LOG_DIRECTORY);
            writer = new FileWriter(LOG_DIRECTORY + filename);
        } catch (IOException e) {
            System.out.println("FileLogger was not created");
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("FileLogger was not closed");
            e.printStackTrace();
        }
    }

    @Override
    protected void print(String s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            System.out.println("FileLogger did not print message to log");
            e.printStackTrace();
        }
    }

    private void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) directory.mkdir();
        if (!directory.isDirectory()) System.out.println("FileLogger can not create folder \"" + path + "\"");
    }

    private static String getDefaultFilename() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss-SSS");
        Date date = new Date();
        return dateFormat.format(date) + ".log";
    }
}
