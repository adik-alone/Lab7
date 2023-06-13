package cllient;

import app.*;
import app.Console;
import app.Reader;
import exception.ScriptRecursionException;
import person.Person;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AppClient {
    ListWithCommandClient list;
    CreatorClient creator;
    Request[] requests;
    ObjectOutputStream out;
    DataInputStream in;
    RequestFactory requestFactory;
    Reader reader;
    String line;
    ScriptExecuter scriptExecuter;
    Console console;
    BufferedReader br;
//    SingleLine singleLine;
//
//    Внутренние перменные
//
//
//
//
//
    String command;
    String login;
    String password;
    boolean enter = false;



    int port = 5001;

    boolean ScriptMode = false;
    boolean work = true;
    //блок инициализации
    public void start(){
        console = new Console(new Scanner(System.in));
        scriptExecuter = new ScriptExecuter();
//        singleLine = new SingleLine();
        reader = console;
        requestFactory = new RequestFactory();
        requestFactory.setApp(this);
        list = new ListWithCommandClient();
        list.setApp(this);
        list.Create();
        creator = new CreatorClient(this);
        list.setRequestFactory(requestFactory);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void WaitCommand(){
            try {
                while (true) {
                    if (reader.Work()) {
                        line = reader.WaitData().trim();
                        if (!line.equals("")) {
//                            Одна строка не работает в скриптах
//                            if (singleLine.Check(line)) {
////                                Reader r = reader;
//                                ChangeReader(singleLine);
//                                line = singleLine.WaitData();
////                                ChangeReader(r);
//                            }
                            try {
                                GetCommand(line);
                                if (!reader.Work()) ChangeReader(console);
                                break;
                            }catch (NullPointerException e){
                                System.out.println("Хорошая попытка, попробуйте снова. Команда help выведет информацию о всех командах");
                            }
                        }
                    } else {
                        if (reader.equals(scriptExecuter)){
                            ScriptMode = false;
                        }
                        ChangeReader(console);
                    }
                }
            } catch (NoSuchElementException e) {
                System.out.println("Экстренный выход");
            }
    }
    
    public void StartApi(){
        try{
            br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Добро пожаловать в замечательное приложение");
            System.out.println("===========================================");
            System.out.println("У вас уже есть учётная запись? N/Y");
            while (true) {
                String ans = br.readLine().toUpperCase();
                if (ans.equals("N")) {
                    Registration();
                    if(enter){
                        break;
                    }
                } else if (ans.equals("Y")) {
                    Login();
                    if(enter){
                        break;
                    }
                }else{
                    System.out.println("Не могу распознать символов, попробуйте ещё раз");
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Work(){
        while(true){
            try {
                PrepareRequest();
                WaitCommand();
                while (reader.equals(scriptExecuter)) {
                    WaitCommand();
                }
//                while (ScriptMode){
//                    WaitCommand();
//                }
                requestFactory.EndOfService();
                requests = requestFactory.DoneRequest();


                for (int i = 0; i < requests.length; i++) {
                    System.out.println(requests[i]);
                }
                System.out.println("Должны соединиться с сервером");
                ConnectingToServer(requests);
            }catch (SocketException e){
                e.printStackTrace();
            }
        }
    }


    public void ConnectingToServer(Request[] requests) throws SocketException{
        try(Socket socket = new Socket("localhost", port);
            ObjectOutputStream outS = new ObjectOutputStream(socket.getOutputStream());
            DataInputStream inS = new DataInputStream(socket.getInputStream());) {
            System.out.println("Создал потоки");
            in = inS;
            out = outS;
            System.out.println("Подключился к серверу и начинаю работу...");
            System.out.println("+++++++++++++++++++++++++++++++++++++++++");


            System.out.println("Должны отправить запрос");
            SendRequest(requests);
            System.out.println("");
            requestFactory.ClearFactory();
            System.out.println("Очистили фабрику");
            System.out.println("Конец выполнения запроса");
            System.out.println("===============================");
            try {
                Thread.sleep(1000);
                System.out.println("Сервер пытается ответить");
                System.out.println("reading...");
                System.out.println("=========================");
                while (in.available() > 0) {
                    String s = in.readUTF();
                    System.out.println(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        } catch (ConnectException e){
            System.out.println("В данный момент сервер не работает, ведутся технические работы. Повотрите попытку через несколько минут");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void SendRequest(Request[] requests){
//        for(Request request: requests){
//            try {
//                out.writeObject(request);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        try {
            out.writeObject(requests);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Запрос отправлен");
        System.out.println("-------------------------------------------");
    }


    public void PrepareRequest(){
        requestFactory.CreateServiceRequest();
    }
//    public void LogInRequest(){
//        requestFactory.CreateRequest();
//    }
    public void GetCommand(String Line) throws NullPointerException{
        command = Line;
        list.ExecuteCommand(Line);
    }
    public void finish(){
        work = false;
        console.CloseStream();
    }
    public void ChangeReader(Reader r){
        reader = r;
    }



//
//
//    Регистрация или вход
//
//
//

    public void Registration() throws IOException{
        System.out.println("Предлагаем вам завести её прямо сейчас");
        while (true) {
            System.out.println("Введите логин");
            login = br.readLine();
            System.out.println("Теперь введите пароль");
            password = br.readLine();
            System.out.println("Готово. Идёт регистрация, подождите пожалуйста");
            command = "registration";
            LoginRequest();
            requests = requestFactory.DoneRequest();
            ConnectingWithLogin(requests);
            if (!enter) {
                System.out.println("Имя пользовотелья уже существует, попробуйте снова");
            } else {
                System.out.println("Вы успешно зарегестрированы");
                System.out.println("Можете вводить команды");
                break;
            }
        }
    }
    public void Login() throws IOException{
        while(true) {
            System.out.println("Введие логин");
            login = br.readLine();
            System.out.println("Введите пароль");
            password = br.readLine();
            System.out.println("Авотризация, подождите пожалуйста");
            command = "login";
            LoginRequest();
            requests = requestFactory.DoneRequest();
            ConnectingWithLogin(requests);
            if(enter){
                System.out.println("Вход успешно выполнен");
                System.out.println("Можете вводить команды");
                break;
            }
            System.out.println("Логин или пароль введены неверно, попобуйте снова");
        }
    }
    public void ConnectingWithLogin(Request[] requests)throws SocketException{
        try(Socket socket = new Socket("localhost", port);
            ObjectOutputStream outS = new ObjectOutputStream(socket.getOutputStream());
            DataInputStream inS = new DataInputStream(socket.getInputStream());) {

            in = inS;
            out = outS;

            SendRequest(requests);

            requestFactory.ClearFactory();

            Thread.sleep(1000);

            enter = in.readBoolean();
            while (in.available() > 0){
                String s = in.readUTF();
                System.out.println(s);
            }
        } catch (ConnectException e){
            System.out.println("Сервер недоступен попробуйте позже");
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }






















//    ------------------
//    исполнители команд
//    ------------------
    public void executeScript(){
        try {
            System.out.println("Введите путь до файла");
            String file_name = reader.WaitData();
            scriptExecuter.openFile(file_name);
            ChangeReader(scriptExecuter);
            ScriptMode = true;
        } catch (FileNotFoundException e) {
            System.out.println("Такого файла не существует попробуйте снова");
        }catch (ScriptRecursionException e){
            System.out.println("Вы создали рекурсию скриптов. Сейчас я смог с этим справиться, но впредь не стоит так делать!");
        }
    }

    public void OnlyCommandRequest(){
        requestFactory.CreateRequest(command);
    }
    public void OneLineRequest(){
        requestFactory.CreateRequest(command, reader.WaitData());
    }
    public void PersonRequest(){
        Person person = creator.createPersonWithoutID();
        requestFactory.CreateRequest(command, person);
    }
    public void AllRequest(){
        String data = reader.WaitData();
        Person person = creator.createPersonWithoutID();
        requestFactory.CreateRequest(command, data, person);
    }
    public void LoginRequest(){
        requestFactory.CreateRequest(command, login + ":" + password);
    }
}