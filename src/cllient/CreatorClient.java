package cllient;

import app.Creator;
import app.CreatorServer;
import person.Person;

public class CreatorClient extends Creator {
    AppClient app;
    public CreatorClient(AppClient app){
        this.app = app;
    }

    public String WaitData(){
        return app.Read();
    }
    public Person createPersonWithoutID(){
        p = new Person();
        NameAsk();
        CoordinatesAsker();
        HeightAsker();
        BirthdayAsker();
        EyeColorAsker();
        HairColorAsker();
        LocationAsker();
        return p;
    }
}