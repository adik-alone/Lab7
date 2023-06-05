package app;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Server {
    public static void main (String[] args){
        int port = 7777;

//        System.out.println("Введите порт на котором будет базироваться сервер");
//        Scanner scanner = new Scanner(System.in);
//        port = scanner.nextInt();

        ObjectInputStream in;
        DataOutputStream out;

        App app = new App();


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
//                while (!client.isClosed()){
                    try {
//                        Request[] requests = (Request[]) in.readObject();
//                        for (Request request: requests){
//                            System.out.println(request);
//                        }
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
