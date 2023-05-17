package app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    public static void main (String[] args){

        App app = new App();

        CollectionManager collection = new CollectionManager(app);
        CommandList list = new CommandList(collection);
        CommandManager manager = new CommandManager(list);
        list.CreateList();


        try(ServerSocket serverSocket = new ServerSocket(5555)){
            while(true) {
                Socket client = serverSocket.accept();;
                System.out.println("Клиент в конекте");

                //Stream from user
                DataInputStream in = new DataInputStream(client.getInputStream());
                //Stream to user
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                System.out.println("Потоки созданы");

                System.out.println("Начинаем общение с клиентом");

                app.setInputStream(in);
                app.setOutputStream(out);

                while (!client.isClosed()) {
                    try {
                        //Модуль получения запроса
//                        String entery = in.readUTF();
//                        System.out.println("Строка полученна --- " + entery);

                        app.start(manager);

                        //Модуль обработки запроса
                        System.out.println("Ответ от сервера");




                        //Модуль оправки ответа
//                        out.writeUTF("Отвечаю --- " + entery);
//                        out.flush();
                    }catch (SocketException e){
                        System.out.println("Пользователь принудительно разорвал соединение");
                        break;
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
