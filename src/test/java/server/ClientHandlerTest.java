package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.server.ClientHandler;
import ru.netology.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientHandlerTest extends Server {
    static ClientHandler clientHandler = Mockito.mock(ClientHandler.class);
    static Socket mockSocket = Mockito.mock(Socket.class);


    @BeforeEach
    void setUp() {
        clientHandler = new ClientHandler(mockSocket, this);
    }

    @Test
    void testGetOutMessage() throws IOException {
        // Мы можем использовать ByteArrayOutputStream для перехвата вывода в PrintWriter
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(mockSocket.getOutputStream()).thenReturn(outputStream);

        PrintWriter outMessage = clientHandler.getOutMessage();

        // Проверяем, что созданный PrintWriter отсылает данные в outputStream
        outMessage.print("Test Message");
        outMessage.flush();
        assertEquals("Test Message", outputStream.toString());
    }

    @Test
    void testGetInMessage() throws IOException {
        // Мы можем использовать ByteArrayInputStream для передачи входных данных в Scanner
        InputStream inputStream = new ByteArrayInputStream("Test Input".getBytes());
        when(mockSocket.getInputStream()).thenReturn(inputStream);

        Scanner inMessage = clientHandler.getInMessage();

        // Проверяем, что созданный Scanner считывает данные из inputStream
        assertEquals("Test Input", inMessage.nextLine());
    }

    @Test
    void parserNameClient() {
        Mockito.when(clientHandler.parserNameClient("Гриша: привет!")).
                thenReturn("Гриша");
        String actual = clientHandler.parserNameClient("Гриша: привет!");
        assertEquals("Гриша", actual);

    }

    @Test
    void parserDoesNotMatchNameClient() {
        Mockito.when(clientHandler.parserNameClient("Гриша: привет !")).thenReturn("Николай");
        String actual = clientHandler.parserNameClient("Гриша: привет !");
        assertNotEquals("Степа", actual);
    }
}
