package cllient;

public class RequestFactorySingle implements RequestFactory {
    ListWithCommandClient list;

    public void execute(){
        list.getApp().SingleRequest();
    }
    public RequestFactorySingle(ListWithCommandClient l){
        this.list = l;
    }
}