package app;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Server {

    static ExecutorService executeIt = Executors.newFixedThreadPool(2);
    static ForkJoinPool forkJoinPool = new ForkJoinPool(2);
    public static void main (String[] args){
        int port = 5001;

        ObjectInputStream in;
        DataOutputStream out;

        App app = new App();


        CollectionManager collection = new CollectionManagerServer(app);
        CommandList list = new CommandList(collection, app);
        list.CreateList();
        app.start(list);

        System.out.println("Сервер начинает работу на порте " + port);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                System.out.println("\nОжидание клиента:");
                System.out.println("----------------");
                Socket client = serverSocket.accept();
//                forkJoinPool.invoke(new Thread());
                System.out.println("Клиент подключен");

                in = new ObjectInputStream(client.getInputStream());
                //Stream to user
                out = new DataOutputStream(client.getOutputStream());

                System.out.println("Потоки созданы");

                app.setInputStream(in);
                app.setOutputStream(out);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                System.out.println("Потоки подключены к приложению");

                System.out.println("Начинаем общение с клиентом...");
                    try {
                        app.HandlerRequests();
                    } catch (SocketException e) {
                        System.out.println("Пользователь принудительно разорвал соединение");
                        e.printStackTrace();
                        break;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
//                }
                app.Write("Запрос выполнен. Выполняем отключение клиента от сервера");
                in.close();
                out.close();
                client.close();
                System.out.println("Клиент отключен...");

                //Можно доделать команды сервера...Нужно что-то не блокирующее. Или можно сделать во время тех перервывов, когда клиентов нет. Можно что-то придумать...
//                if (scanner.hasNext()){
//                    System.out.println("Запрос на серверную команду");
//                    String command = scanner.nextLine();
//                    app.HandlerServerCommand(command);
//                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
