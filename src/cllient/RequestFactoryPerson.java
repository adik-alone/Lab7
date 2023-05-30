package cllient;

public class RequestFactoryPerson implements RequestFactory {
    ListWithCommandClient list;

    public void execute(){
        list.getApp().PersonRequest();
    }
    public RequestFactoryPerson(ListWithCommandClient l){
        this.list = l;
    }
}
