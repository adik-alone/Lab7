package cllient;

import app.*;
import app.Reader;
import exception.ScriptRecursionException;
import person.Person;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AppClient {
    ListWithCommandClient list;
    CreatorClient creator;
    Request currentRequest;
    Request serviceRequest;
    Request[] requests;

    ObjectOutputStream out;
    DataInputStream in;

//    ТОТ

    RequestFactory requestFactory;
    //////////////////////////////////////////////////////////////////////////////////
    Reader reader;
    String line;
    ScriptExecuter scriptExecuter;
    Consol consol;
    SingleLine singleLine;
    boolean work = true;
    //блок инициализации
    public void start(){
        consol = new Consol(new Scanner(System.in));
        scriptExecuter = new ScriptExecuter();
        singleLine = new SingleLine();
        reader = consol;
        requestFactory = new RequestFactory();
        requestFactory.setApp(this);
        list = new ListWithCommandClient();
        list.setApp(this);
        list.Create();
        creator = new CreatorClient(this);
        list.setRequestFactory(requestFactory);
    }
    public void WaitCommand(){
            try {
                while (true) {
                    if (reader.Work()) {
                        line = reader.WaitData().trim();
                        if (!line.equals("")) {
                            if (singleLine.Check(line)) {
                                ChangeReader(singleLine);
                                line = singleLine.WaitData();
                            }
                            try {
                                GetCommand(line);
                                break;
                            }catch (NullPointerException e){
                                System.out.println("Хорошая попытка, попробуйте снова. Команда help выведет информацию о всех командах");
                            }
                        }
                    } else {
                        ChangeReader(consol);
                    }
                }
            } catch (NoSuchElementException e) {
                System.out.println("Экстренный выход");
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
                requestFactory.EndOfService();
                requests = requestFactory.DoneRequest();


                for (int i = 0; i < requests.length; i++) {
                    System.out.println(requests[i]);
                }
                System.out.println("Должны соеденится с сервером");
                ConnectingToServer(requests);
//                System.out.println("Должны отправить запрос");
////                SendRequest(requests);
//                System.out.println("");
//                requestFactory.Clear();
//                System.out.println("Очистили фабрику");
//                System.out.println("Конец выполнения запроса");
//                System.out.println("===============================");
//
//                System.out.println("Сервер пытается ответить");
//                System.out.println("reading...");
//                try {
//                    while (in.available() != 0) {
//                        String s = in.readUTF();
//                        System.out.println(s);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }catch (SocketException e){
                e.printStackTrace();
            }
        }
    }


    public void ConnectingToServer(Request[] requests) throws SocketException{
        try(Socket socket = new Socket("localhost", 7777);
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
            requestFactory.Clear();
            System.out.println("Очистили фабрику");
            System.out.println("Конец выполнения запроса");
            System.out.println("===============================");
            try {
                Thread.sleep(3000);
                System.out.println("Сервер пытается ответить");
                System.out.println("reading...");
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
    public void GetCommand(String Line) throws NullPointerException{
        list.ExecuteCommand(Line);
    }
    public void finish(){
        work = false;
        consol.CloseStream();
    }
    public void ChangeReader(Reader r){
        reader = r;
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
        } catch (FileNotFoundException e) {
            System.out.println("Такого файла не существует попробуйте снова");
        }catch (ScriptRecursionException e){
            System.out.println("Вы создали рекурсию скриптов. Сейчас я смог с этим справиться, но впредь не стоит так делать!");
        }
    }











//    public void GiveRequest(Request[] r){
//        this.currentRequest = r;
//    }













//    public void start(BufferedReader br) {
//        this.input = br;
//        list = new ListWithCommandClient();
//        list.setApp(this);
//        list.Create();
//        creator = new CreatorClient(this);
//    }
//
//    public void Work(){
//        while (true) {
//            try {
//                nameCommand = Read();
//                if (!nameCommand.equals("")) {
//                    list.CreateRequest(nameCommand);
//                    break;
//                    //Вроде можно и не выходить из цикла, пусть работает;
//                }
//            } catch (NoSuchElementException e) {
//                System.out.println("Экстренный выход");
//            }catch (NullPointerException e){
//                System.out.println("Данной команды нет в списке." + "\n" + "Команда help выведет доступные команды");
//            }
//        }
//    }
//     public String Read() {
//        try {
//            String s = input.readLine().trim();
//            System.out.println("Введёная строка --- " + s);
//            return s;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }







































//    public Request CreateRequest(){
//        return new Request();
//    }
//
//    public void SingleRequest(){
//        currentRequest = CreateRequest();
//        currentRequest.setCommand(nameCommand);
//    }
//    public void PersonRequest(){
//        currentRequest = CreateRequest();
//        currentRequest.setCommand(nameCommand);
//        currentRequest.setCreatedPerson(creator.createPersonWithoutID());
//    }
//    public Request getCurrentRequest(){
//        return currentRequest;
//    }
}