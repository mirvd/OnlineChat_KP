package ru.netology.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static SettingsServer parserSettingServer = new SettingsServer(new File("Settings.txt"));
    private final ArrayList<ClientHandler> clients = new ArrayList<>();// список клиентов, которые будут подключаться к серверу
    private final Logger log = new Logger();

    public void start() {
        new Logger().getInstance().createFileLog();// создается логер и файл логирования
        Socket clientSocket = null;
        ServerSocket serverSocket = null; // серверный сокет
        try {
            serverSocket = new ServerSocket((int) parserSettingServer.getData("port"));  // создаём серверный сокет на определенном порту
            log.log("Сервер запущен!");
            while (true) {  // запускаем бесконечный цикл
                clientSocket = serverSocket.accept();  // таким образом ждём подключений от сервера
                // создаём обработчик клиента, который подключился к серверу
                // this - это наш сервер
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                new Thread(client).start(); // каждое подключение клиента обрабатываем в новом потоке
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                // закрываем подключение
                clientSocket.close();
                log.log("Сервер остановлен");
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // отправляем сообщение всем клиентам
    public synchronized void sendMessageToAllClients(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    // удаляем клиента из коллекции при выходе из чата
    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public synchronized ArrayList<ClientHandler> getClients() {
        return clients;
    }

}