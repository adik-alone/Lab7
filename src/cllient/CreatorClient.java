package cllient;

import person.Creator;
import person.Person;

public class CreatorClient extends Creator {
    AppClient app;
    public CreatorClient(AppClient app){
        this.app = app;
    }

    public String WaitData(){
        return app.reader.WaitData().trim();
    }
    public Person createPersonWithoutID(){
        p = new Person(0);
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
