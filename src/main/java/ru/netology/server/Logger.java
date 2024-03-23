package ru.netology.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private int counter = 0;
    private final File file = new File("file.log");
    private Logger logger;

    public void createFileLog() {
        try {
            if (file.createNewFile()) {
                System.out.println("log file \"" + file.getName() + "\" created...");
            } else {
                System.out.println("the log file \"" + file.getName() + "\" has already been created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String msg) {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yy ' 'hh:mm:ss");
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            String msgFinal = "[ " + ++counter + " " + ft.format(date) + "] " + msg + "\n";
            byte[] bytes = msgFinal.getBytes();//перевод строки лога в массив байтов
            fos.write(bytes, 0, bytes.length);
        }//запись байтов в файл
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Logger getInstance() {
        if (this.logger == null) this.logger = new Logger();
        return logger;
    }
}