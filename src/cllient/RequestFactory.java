package cllient;

import java.util.ArrayList;
import java.util.List;

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
        CreateServiceRequest();
        currentRequest = CreateRequest();
        currentRequest.setCommand(app.list.getCurrentNameCommand());
        app.GiveRequest(DoneRequest());
    }
    public void PersonRequest(){
        currentRequest = CreateRequest();
//        currentRequest.setCommand(nameCommand);
//        currentRequest.setCreatedPerson(creator.createPersonWithoutID());
    }
    public Request getCurrentRequest(){
        return currentRequest;
    }

    public Request[] DoneRequest(){
        Request[] r = (Request[]) listOfRequest.stream().toArray();
        return r;
    }
}
