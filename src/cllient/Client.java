package cllient;

import com.sun.source.tree.WhileLoopTree;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){


//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Введите порт сервера");
//        int port = scanner.nextInt();

        System.out.println("Создаю приложение");
        AppClient app = new AppClient();

        System.out.println("Запускаем приложение");

        app.start();
//        app.setPort(port);
        app.Work();





//
//        try(Socket socket = new Socket("localhost", 5555);
//            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//            DataInputStream in = new DataInputStream(socket.getInputStream());)
//        {
//            System.out.println("Создал потоки");
//            System.out.println("Подключился к серверу и начинаю работу");
//
//
////            app.start(br);
//
//            while (!socket.isOutputShutdown()){
//                System.out.println("Отправляю на сервер");
//                app.Work();
////                    System.out.println("текущий запрос: " + app.getCurrentRequest());
//////
////                    out.writeObject(app.getCurrentRequest());
//
////                    out.writeUTF(message);
//                out.flush();
//                System.out.println("Сообщение отправленно");
//
////                    if(message.equals("exit")){
////                        System.out.println("Клиент разрывает общение");
////                        if(in.available()!=0)		{
////                            System.out.println("reading...");
////                            String s = in.readUTF();
////                            System.out.println(s);
////                        }
////                        break;
////                    }
//                System.out.println("Сервер пытается ответить");
//                System.out.println("reading...");
//                Thread.sleep(1000);
//                while(in.available() != 0) {
//                    String s = in.readUTF();
//                    System.out.println(s);
//                }
//
//            }
//            System.out.println("Завершение работы");
//        }catch (IOException e){
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
