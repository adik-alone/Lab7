package cllient;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;

public class AppClient {
    BufferedReader input;
    ListWithCommandClient list;
    CreatorClient creator;
    String nameCommand;
    Request currentRequest;

    public void start(BufferedReader br) {
        this.input = br;
        list = new ListWithCommandClient();
        list.setApp(this);
        list.Create();
        creator = new CreatorClient(this);
    }

    public void Work(){
        while (true) {
            try {
                nameCommand = Read();
                if (!nameCommand.equals("")) {
                    list.CreateRequest(nameCommand);
                    break;
                    //Вроде можно и не выходить из цикла, пусть работает;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Экстренный выход");
            }catch (NullPointerException e){
                System.out.println("Данной команды нет в списке." + "\n" + "Команда help выведет доступные команды");
            }
        }
    }
     public String Read() {
        try {
            String s = input.readLine().trim();
            System.out.println("Введёная строка --- " + s);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public Request CreateRequest(){
        return new Request();
    }

    public void SingleRequest(){
        currentRequest = CreateRequest();
        currentRequest.setCommand(nameCommand);
    }
    public void PersonRequest(){
        currentRequest = CreateRequest();
        currentRequest.setCommand(nameCommand);
        currentRequest.setCreatedPerson(creator.createPersonWithoutID());
    }
    public Request getCurrentRequest(){
        return currentRequest;
    }
}