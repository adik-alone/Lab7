package app;

import cllient.Request;
import exception.ScriptRecursionException;
import person.Person;

import java.io.*;
import java.net.SocketException;
import java.util.NoSuchElementException;

public class App {
    CommandList list;
    boolean work;
    CreatorServer creator;
    Reader reader;
    XmlWorker xmlworker;
    ObjectInputStream in = null;
    DataOutputStream out = null;
    int amountRequest;
    Request currentRequest;

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
        work = true;
        String filename = "Collection.xml";
        xmlworker = new XmlWorker(filename);
        xmlworker.parse();
        reader = xmlworker;
        this.list = l;
        list.col_manager.createCollection();
    }
    public void Write(String s){
        try{
            System.out.println(out);
            out.writeUTF(s);
            out.flush();
        }catch (IOException e){
            System.out.println("Клиент отключился");
            e.printStackTrace();
        }
    }
    public void Write(Person p){
        try{
            out.writeUTF(p.toString());
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

//    ---------------
//    блок работы
//    ---------------
    public void HandlerRequests() throws IOException, ClassNotFoundException{
        System.out.println("Пытаюсь прочитать");
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try{
            System.out.println(in.available());
//            while (in.available() > 0) {
                System.out.println("В потоке есть данные");
                System.out.println("Начинаю читать");
                Request[] requests = (Request[]) in.readObject();
                System.out.println("Прочитал");
                for (Request request : requests) {
                    System.out.println(request);
                    if (request.getCommand() != null) {
                        currentRequest = request;
                        acceptRequest(request);
                    }
                }
//            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    //если вдруг начнуться проблемы с размером передаваемых данных
    public void amountRequest()throws IOException, ClassNotFoundException{
        System.out.println("Ожидаю службный запрос");
        if (in.available() > 0) {
            Request r = (Request) in.readObject();
            amountRequest = Integer.parseInt(r.getData());
        }
        System.out.println("Служебный запрос обработан");
    }
    public void readRequest() throws IOException, ClassNotFoundException {
        System.out.println("Жду запроса");
        System.out.println("===========");
        Request r = (Request) in.readObject();
        currentRequest = r;
        System.out.println("Запрос выглядит так:");
        System.out.println(r);
        acceptRequest(r);
    }
    public String getRequestData(){
        return currentRequest.getData();
    }
    public Person getRequestPerson(){
        return currentRequest.getCreatedPerson();
    }

    public void acceptRequest(Request r){
        String command = r.getCommand();
        Person p = r.getCreatedPerson();
        String data = r.getData();
        GetCommand(command);
    }


    public void GetCommand(String Line){
//        try{
            list.ExecuteCommand(Line);
//        }catch (NullPointerException e){
//            Write("Хорошая попытка, попробуйте снова. Команда help выведет информацию о всех командах");
//        }
    }

    public Person creationStartPerson() {
        return creator.createPerson(reader);
    }

//    ------------------
//    исполнители команд
//    ------------------
    public Person createPerson(long id){
        return creator.createPerson(id, reader);
    }



    public void HandlerServerCommand(String s){
        s = s.trim();
        if (s.equals("save")) list.ExecuteCommand(s);
    }

}
