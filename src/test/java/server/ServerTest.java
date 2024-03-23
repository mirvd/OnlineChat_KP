package server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.server.ClientHandler;
import ru.netology.server.Server;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

class ServerTest {
    Server server = mock(Server.class);
    ClientHandler clientHandler = mock(ClientHandler.class);
    ArgumentCaptor<ClientHandler> argumentCaptor = ArgumentCaptor.forClass(ClientHandler.class);
    ArrayList<ClientHandler> clients = server.getClients();


    @Test
    void testRemoveMethod() {
        ArrayList<ClientHandler> clients = server.getClients();
        clients.add(clientHandler);
        clients.add(clientHandler);
        server.removeClient(clientHandler);
        Mockito.verify(server).removeClient(clientHandler);
    }

    @Test
    void verifyQuantityClients() {
        ArrayList<ClientHandler> clients = server.getClients();
        clients.add(clientHandler);
        clients.add(clientHandler);
        Assertions.assertEquals(2, clients.size());
    }
}
