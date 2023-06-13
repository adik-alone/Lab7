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


//        app.setPort(port);

        app.start();
        app.StartApi();
        app.Work();
    }
}
