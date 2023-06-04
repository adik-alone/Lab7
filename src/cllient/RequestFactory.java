package cllient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestFactory {

    List<Request> listOfRequest = new ArrayList<>();
    Request currentRequest;

    Request currentServiceRequest;

    AppClient app;
    public Request CreateRequest(){
        return new Request();
    }
    public void CreateServiceRequest(){
        currentServiceRequest = CreateRequest();
        listOfRequest.add(currentServiceRequest);
    }
    public void SingleRequest(){
        currentRequest = CreateRequest();
        currentRequest.setCommand(app.list.getCurrentNameCommand());
        listOfRequest.add(currentRequest);
    }
    public void PersonRequest(){
        currentRequest = CreateRequest();
        currentRequest.setCommand(app.list.getCurrentNameCommand());
        currentRequest.setCreatedPerson(app.creator.createPersonWithoutID());
        listOfRequest.add(currentRequest);
    }
    public Request getCurrentRequest(){
        return currentRequest;
    }

    public Request[] DoneRequest(){
        Request[] requests = listOfRequest.toArray(new Request[0]);
        return requests;
    }

    public void EndOfService(){
        currentServiceRequest.setData(String.valueOf(listOfRequest.size()));
        listOfRequest.set(0, currentServiceRequest);
    }

    public void setApp(AppClient app) {
        this.app = app;
    }

    public void Clear(){
        listOfRequest.removeAll(listOfRequest);
    }
}
