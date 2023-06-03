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
    ObjectInputStream in;
    DataOutputStream out;

    int amountRequest;

    Request currentRequest;

    public void setInputStream(ObjectInputStream in){
        this.in = in;
    }
    public void setOutputStream(DataOutputStream out){
        this.out = out;
    }

    //блок инициализации
    public void start(CommandList l){
        creator = new CreatorServer(this);
        work = true;
        String filename = "Collection.xml";
//        String filename = System.getenv("PATH_COLLECTION");
        xmlworker = new XmlWorker(filename);
        xmlworker.parse();
        reader = xmlworker;
        this.list = l;
        list.col_manager.createCollection();
    }
    public void Write(String s){
        try{
            out.writeUTF(s);
            out.flush();
        }catch (IOException e){
            System.out.println("Клиент отключился");
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
//        amountRequest();
        System.out.println("Хочу прочитать массив");
        while(in.available() > 0) {
            System.out.println("Окей, мы здесь");
            Request[] requests = (Request[]) in.readObject();
            for( Request request: requests){
                System.out.println(request);
            }
        }
        System.out.println("Вот тут ввод должен завершиться");

//        for (int i = 0; i < amountRequest; i++){
//            readRequest();
//        }
    }
    public void amountRequest()throws IOException, ClassNotFoundException{
        System.out.println("Ожидаю служебный запрос");
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
        try{
            list.ExecuteCommand(Line);
        }catch (NullPointerException e){
            Write("Хорошая попытка, попробуйте снова. Команда help выведет информацию о всех командах");
        }
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

//    public void executeScript(){
//        try {
//            Write("Введите путь до файла");
//            String file_name = reader.WaitData();
//            scriptExecuter.openFile(file_name);
//            ChangeReader(scriptExecuter);
//        } catch (FileNotFoundException e) {
//            Write("Такого файла не существует попробуйте снова");
//        }catch (ScriptRecursionException e){
//            Write("Вы создали рекурсию скриптов. Сейчас я смог с этим справиться, но впредь не стоит так делать!");
//        }
//    }
}
