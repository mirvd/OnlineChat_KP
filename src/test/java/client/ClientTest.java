package client;

import org.junit.Test;
import ru.netology.client.Client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

    @Test
    void testGetValueString() {
        String input = "Test Input";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        assertEquals(input, Client.getValueString());
    }
}