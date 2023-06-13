package cllient;

import person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestFactory {

    List<Request> listOfRequest = new ArrayList<>();
    Request currentRequest;

    Request currentServiceRequest;
    
    Request currentLogInRequest;

    AppClient app;
//    private Request CreateRequest(){
//        return new Request();
//    }
    public void setApp(AppClient app) {
        this.app = app;
    }
    public void CreateServiceRequest(){
        currentServiceRequest =new Request();
        listOfRequest.add(currentServiceRequest);
    }

    public void CreateLoginRequest(){
//        currentLogInRequest = CreateRequest();
//        currentLogInRequest.setCommand();
    }
//    public void SingleRequest(){
////        currentRequest = CreateRequest();
//        currentRequest.setCommand(app.list.getCurrentNameCommand());
//        listOfRequest.add(currentRequest);
//    }
//    public void PersonRequest(){
////        currentRequest = CreateRequest();
//        currentRequest.setCommand(app.list.getCurrentNameCommand());
//        currentRequest.setCreatedPerson(app.creator.createPersonWithoutID());
//        listOfRequest.add(currentRequest);
//    }
//    public void OneRequest(){
////        currentRequest = CreateRequest();
//        currentRequest.setCommand(app.list.getCurrentNameCommand());
//        //Надо добавить исключение на невалидные данные....
//        System.out.println("Введидет доп данные. Возможно сейчас это id");
//        currentRequest.setData(app.reader.WaitData());
//        listOfRequest.add(currentRequest);
//    }
    public Request getCurrentRequest(){
        return currentRequest;
    }

    public void CreateRequest(String command){
        currentRequest = new Request();
        currentRequest.setCommand(command);
        listOfRequest.add(currentRequest);
    }
    public void CreateRequest(String command, String data){
        currentRequest = new Request();
        currentRequest.setCommand(command);
        currentRequest.setData(data);
        listOfRequest.add(currentRequest);
    }
    public void CreateRequest(String command, Person person){
        currentRequest = new Request();
        currentRequest.setCommand(command);
        currentRequest.setCreatedPerson(person);
        listOfRequest.add(currentRequest);
    }
    public void CreateRequest(String command, String data, Person person){
        currentRequest = new Request();
        currentRequest.setCommand(command);
        currentRequest.setCreatedPerson(person);
        currentRequest.setData(data);
        listOfRequest.add(currentRequest);
    }







//
//    Машинные методы
//
//
//
//
    public Request[] DoneRequest(){
        Request[] requests = listOfRequest.toArray(new Request[0]);
        return requests;
    }
    public void EndOfService(){
//        currentServiceRequest.setData(String.valueOf(listOfRequest.size()));
        currentServiceRequest.setData(app.login);
        listOfRequest.set(0, currentServiceRequest);
    }
    public void ClearFactory(){
        listOfRequest.removeAll(listOfRequest);
    }
}
