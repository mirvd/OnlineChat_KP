package ru.netology.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Server server;
    public PrintWriter outMessage;
    public Scanner inMessage;
    private final Socket clientSocket;
    private int clients_count = 0;
    private final Logger log = new Logger();

    public ClientHandler(Socket socket, Server server) {// конструктор, который принимает клиентский сокет и сервер
        clients_count++;
        this.server = server;
        this.clientSocket = socket;
        this.outMessage = getOutMessage();
        this.inMessage = getInMessage();
    }

    @Override
    public void run() {
        try {
            server.sendMessageToAllClients("Новый участник вошёл в чат! Клиентов в чате = " + clients_count);
            while (inMessage.hasNextLine()) {
                String clientMessage = inMessage.nextLine();
                if (clientMessage.contains("/exit")) {
                    handleClientExit(clientMessage);
                    break;
                }
                handleClientMessage(clientMessage);
            }
        } finally {
            this.close();
        }
    }

    private void handleClientExit(String clientMessage) {
        String nameClient = parserNameClient(clientMessage);
        String exitMessage = nameClient + " вышел из чата";
        System.out.println(exitMessage);
        server.sendMessageToAllClients(exitMessage);
        log.log(exitMessage);
    }

    private void handleClientMessage(String clientMessage) {
        System.out.println(clientMessage);
        log.log(clientMessage);
        server.sendMessageToAllClients(clientMessage);
    }

    public PrintWriter getOutMessage() {
        try {
            outMessage = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outMessage;
    }

    public Scanner getInMessage() {
        try {
            inMessage = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inMessage;
    }

    // отправляем сообщение
    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // клиент выходит из чата
    public void close() {
        clients_count--;   // удаляем клиента из списка
        server.sendMessageToAllClients("Клиентов в чате = " + clients_count);
        server.removeClient(this);
    }

    public String parserNameClient(String str) {
        int value = str.indexOf(": ");
        return str.substring(0, value);
    }
}