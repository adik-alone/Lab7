package app;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    public static void main (String[] args){

        App app = new App();
        int port = 5555;

        CollectionManager collection = new CollectionManagerServer(app);
        CommandList list = new CommandList(collection);
        list.CreateList();
        app.start(list);

        System.out.println("Сервер начинает работу на порте " + port);

        try(ServerSocket serverSocket = new ServerSocket(port)){
            while(true) {
                System.out.println("");
                System.out.println("Ожидание клиента:");
                System.out.println("----------------");
                Socket client = serverSocket.accept();
                System.out.println("Клиент подключен");

                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                //Stream to user
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                System.out.println("Потоки созданы");
                app.setInputStream(in);
                app.setOutputStream(out);
                System.out.println("Потоки подключены к приложению");

                System.out.println("Начинаем общение с клиентом...");

                    try {
                        app.HandlerRequests();
                    }catch (SocketException e){
                        System.out.println("Пользователь принудительно разорвал соединение");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                app.Write("Зарос выполнен выполняем отключение от сервера");
                in.close();
                out.close();
                client.close();
                System.out.println("Клиент отключен...");
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
