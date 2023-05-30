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
    String line;
    CreatorServer creator;
    ScriptExecuter scriptExecuter;
    Consol consol;
    Reader reader;
    SingleLine singleLine;
    XmlWorker xmlworker;
//    DataInputStream in;
    ObjectInputStream in;
    DataOutputStream out;


    public void setInputStream(ObjectInputStream in){
        this.in = in;
    }
    public void setOutputStream(DataOutputStream out){
        this.out = out;
    }

    //блок инициализации
    public void start(CommandList l){
        creator = new CreatorServer(this);
        consol = new Consol(in);
        scriptExecuter = new ScriptExecuter();
        singleLine = new SingleLine();
        work = true;
        String filename = "Collection.xml";
//        String filename = System.getenv("PATH_COLLECTION");
        xmlworker = new XmlWorker(filename);
        xmlworker.parse();
        reader = xmlworker;
        this.list = l;
        list.col_manager.createCollection();
        reader = consol;
    }
    protected void Write(String s){
        try{
            out.writeUTF(s);
            out.flush();
        }catch (IOException e){
            System.out.println("Клиент отключился");
        }
    }

    public void Work() throws SocketException {
        try{
            if(reader.Work()) {
                line = reader.WaitData();
                if (line.equals("")) {
                } else {
                    if (singleLine.Check(line)){
                        ChangeReader(singleLine);
                        line = singleLine.WaitData();
                    }
                    GetCommand(line);
                }
            }else{
                ChangeReader(consol);
            }
        }catch (NoSuchElementException e){
            System.out.println("Экстренный выход");
            finish();
        }
    }

    public void acceptRequest(Request r){
        String command = r.getCommand();
        System.out.println(command);
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
    public void finish(){
        work = false;
        consol.CloseStream();
    }
    public void ChangeReader(Reader r){
        reader = r;
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

    public void executeScript(){
        try {
            Write("Введите путь до файла");
            String file_name = reader.WaitData();
            scriptExecuter.openFile(file_name);
            ChangeReader(scriptExecuter);
        } catch (FileNotFoundException e) {
            Write("Такого файла не существует попробуйте снова");
        }catch (ScriptRecursionException e){
            Write("Вы создали рекурсию скриптов. Сейчас я смог с этим справиться, но впредь не стоит так делать!");
        }
    }

    public DataOutputStream getOut(){
        return out;
    }
//

}
