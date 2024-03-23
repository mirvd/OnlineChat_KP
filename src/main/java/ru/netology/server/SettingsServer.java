package ru.netology.server;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class SettingsServer {
    private int serverPort;
    private final File filename;
    List<String> stringList = new ArrayList<>();

    public SettingsServer(File filename) {
        this.filename = filename;
    }

    public Object getData(String flagData) {
        int port = 0;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filename), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringList.add(line);
                if (line.startsWith("server-port") && flagData.equals("port")) {
                    int value = line.indexOf("=");
                    return Integer.parseInt(line.substring(value + 1));
                }
                if (line.startsWith("server-host") && flagData.equals("host")) {
                    int value = line.indexOf("=");
                    return line.substring(value + 1);

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}