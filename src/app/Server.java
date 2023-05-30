package app;

import cllient.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    public static void main (String[] args){

        App app = new App();

        CollectionManager collection = new CollectionManagerServer(app);
        CommandList list = new CommandList(collection);
        list.CreateList();
        app.start(list);

        try(ServerSocket serverSocket = new ServerSocket(5555)){
            while(true) {
                Socket client = serverSocket.accept();;
                System.out.println("Клиент в конекте");

                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                //Stream to user
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                System.out.println("Потоки созданы");

                System.out.println("Начинаем общение с клиентом");

                app.setInputStream(in);
                app.setOutputStream(out);


                while (!client.isClosed()) {
                    try {


                        Request r = (Request) in.readObject();
                        System.out.println(r);

                        app.acceptRequest(r);

                        //Модуль обработки запроса
                        System.out.println("Ответ от сервера");



                        //Модуль оправки ответа
//                        out.writeUTF("Отвечаю --- " + entery);
//                        out.flush();
                    }catch (SocketException e){
                        System.out.println("Пользователь принудительно разорвал соединение");
                        break;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                in.close();
                out.close();
                client.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
