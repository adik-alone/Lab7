package app;

import app.MultiThread.ClientData;
import app.MultiThread.HandlerThread;
import app.MultiThread.ReadThread;
import cllient.Client;
import cllient.Request;
import person.ColorEye;
import person.ColorHair;
import person.CreatorServer;
import person.Person;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    CommandList list;
    CreatorServer creator;
    Reader reader;
    XmlWorker xmlworker;
    ObjectInputStream in = null;
    DataOutputStream out = null;
    int amountRequest;
    Request currentRequest;
    DataBase dataBase;
    BlockingQueue<ClientData> queue;

    public void setInputStream(ObjectInputStream inS){
        this.in = inS;
        System.out.println(in);
    }
    public void setOutputStream(DataOutputStream outS){
        this.out = outS;
        System.out.println(out);
    }

    //блок инициализации
    public void start(CommandList l){
        creator = new CreatorServer(this);
//        String filename = "Collection.xml";
//        xmlworker = new XmlWorker(filename);
//        xmlworker.parse();
//        reader = xmlworker;
        this.list = l;
//        list.col_manager.createCollection();
        dataBase = new DataBase();
        queue = new LinkedBlockingQueue<>();
        try {
            dataBase.Connect();
            dataBase.Start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void Work(int port){
        ForkJoinPool pool = ForkJoinPool.commonPool();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                System.out.println("\nОжидание клиента:");
                System.out.println("----------------");
                Socket client = serverSocket.accept();
//                forkJoinPool.invoke(new Thread());
                new ReadThread(client, queue).start();
                System.out.println("Клиент подключен");
                new HandlerThread(queue, list, dataBase).start();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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






































//    public void Write(String s){
//        try{
////            System.out.println(out);
//            out.writeUTF(s);
//            out.flush();
//        }catch (IOException e){
//            System.out.println("Клиент отключился");
//            e.printStackTrace();
//        }
//    }
//    public void Write(Person p){
//        try{
//            out.writeUTF(p.toString());
//            out.flush();
//    }catch (IOException e){
//            e.printStackTrace();
//        }
//    }

//    ---------------
//    блок работы
//    ---------------
//    public void HandlerRequests() throws IOException, ClassNotFoundException{
//        System.out.println("Чтение запросов");
//        try {
//            Thread.sleep(1000);
//        }catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        try{
//            System.out.println(in.available());
//            System.out.println("Начинаю читать");
//            Request[] requests = (Request[]) in.readObject();
//            System.out.println("Прочитал");
//            for (Request request : requests) {
//                System.out.println(request);
//                if (request.getCommand() != null) {
//                    currentRequest = request;
//                    acceptRequest(request);
//                }
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }

    //если вдруг начнуться проблемы с размером передаваемых данных
//    public void amountRequest()throws IOException, ClassNotFoundException{
//        System.out.println("Ожидаю служебный запрос");
//        if (in.available() > 0) {
//            Request r = (Request) in.readObject();
//            amountRequest = Integer.parseInt(r.getData());
//        }
//        System.out.println("Служебный запрос обработан");
//    }
//    public void readRequest() throws IOException, ClassNotFoundException {
//        System.out.println("Жду запроса");
//        System.out.println("===========");
//        Request r = (Request) in.readObject();
//        currentRequest = r;
//        System.out.println("Запрос выглядит так:");
//        System.out.println(r);
//        acceptRequest(r);
//    }
    public String getRequestData(){
        return currentRequest.getData();
    }
    public Person getRequestPerson(){
        return currentRequest.getCreatedPerson();
    }

//    public void acceptRequest(Request r){
//        String command = r.getCommand();
//        Person p = r.getCreatedPerson();
//        String data = r.getData();
//        GetCommand(command);
//    }
//    public void GetCommand(String Line){
//        System.out.println("I want to get command");
//            list.ExecuteCommand(Line);
//    }

//    public Person AddPersonToCollection(int id, String name, Long cor_x, Double cor_y, double height, LocalDateTime birthday, ColorEye eye, ColorHair hair, LocalDateTime createTime, Integer loc_x, Integer loc_y, Double loc_z){
//        Person p = creator.createPerson(id, name, cor_x, cor_y, height, birthday, eye, hair, createTime, loc_x, loc_y, loc_z);
//        return p;
//    }


    public Person creationStartPerson() {
        return creator.createPerson(reader);
    }

    public Person createPerson(long id){
        return creator.createPerson(id, reader);
    }
    
//    public void HandlerServerCommand(String s){
//        s = s.trim();
//        if (s.equals("save")) list.ExecuteCommand(s);
//    }

//    public void Registration(){
////        System.out.println("I am here");
//        String[] reg = currentRequest.getData().split(":");
//        String login = reg[0];
//        String passwd = reg[1];
//        boolean a = dataBase.Registration(login, passwd);
//        try {
//            out.writeBoolean(a);
//            Write("Завершение регистрации");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public void Login(){
//        System.out.println("I am here");
//        String[] data = currentRequest.getData().split(":");
//        String login = data[0];
//        String password = data[1];
//        boolean a = dataBase.Login(login, password);
//        try{
//            out.writeBoolean(a);
//            Write("Завершение входа");
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }

}
