package cllient;

import com.sun.source.tree.WhileLoopTree;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args){
        try(Socket socket = new Socket("localhost", 5555);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());)
        {

            System.out.println("Создал потоки");
            System.out.println("Подключился к серверу и начинаю работу");

            while (!socket.isOutputShutdown()){
                if(br.ready()){
                    String message = br.readLine();
//                    System.out.println("Сообщение введено === " + message);
                    System.out.println("Отправляю на сервер");

                    out.writeUTF(message);
                    out.flush();
                    System.out.println("Сообщение отправленно");

                    if(message.equals("exit")){
                        System.out.println("Клиент разрывает общение");
                        if(in.available()!=0)		{
                            System.out.println("reading...");
                            String s = in.readUTF();
                            System.out.println(s);
                        }
                        break;
                    }
                    System.out.println("Сервер пытается ответить");
                    System.out.println("reading...");
                    Thread.sleep(1000);
                    while(in.available() != 0) {
                        String s = in.readUTF();
                        System.out.println(s);
                    }
                }
            }
            System.out.println("Завершение работы");
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
