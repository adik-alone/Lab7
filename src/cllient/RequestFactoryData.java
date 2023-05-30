package cllient;

public class RequestFactoryData implements RequestFactory {
    ListWithCommandClient list;

    public void execute(){
        list.getApp().OneRequest();
    }

    public RequestFactoryData(ListWithCommandClient l){
        this.list = l;
    }
}
