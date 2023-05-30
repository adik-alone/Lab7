package cllient;

public class RequestFactoryAll implements RequestFactory {

    ListWithCommandClient list;

    public void execute(){
        list.getApp().AllRequest();
    }

    public RequestFactoryAll(ListWithCommandClient l){
        this.list = l;
    }

}
