package server;

import org.junit.jupiter.api.Test;
import ru.netology.server.SettingsServer;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SettingsServerTest {
    File file = new File("settings.txt");
    SettingsServer parserSettingServer = new SettingsServer(file);

    @Test
    void notGetPort() throws IOException {
        int result = (int) parserSettingServer.getData("port");
        assertNotEquals(486837, result);
    }

    @Test
    void getPort() throws IOException {
        int result = (int) parserSettingServer.getData("port");
        assertEquals(8081, result);
    }
}