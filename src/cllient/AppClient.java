package cllient;

import app.*;
import exception.ScriptRecursionException;
import person.Person;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AppClient {
    BufferedReader input;
    ListWithCommandClient list;
    CreatorClient creator;
    String nameCommand;
    Request[] currentRequest;

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
        while (work){
            Work();
        }
    }
    public void Work(){
        try{
            if(reader.Work()) {
                line = reader.WaitData().trim();
                if (!line.equals("")){
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
    public void GetCommand(String Line){
        try{
            list.ExecuteCommand(Line);
        }catch (NullPointerException e){
            System.out.println("Хорошая попытка, попробуйте снова. Команда help выведет информацию о всех командах");
        }
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


    public void remove(){

    }










    public void GiveRequest(Request[] r){
        this.currentRequest = r;
    }













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