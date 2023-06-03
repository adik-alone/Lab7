package app;

public class SingleLine implements Reader{
    String[] line = new String[15];

    int now;

    public void NewLine(String s){
        line = s.split(" ");
        now = 0;
    }

    public boolean Check(String s){
        NewLine(s);
        return line.length > 1;
    }

    public boolean Work(){
        return (line.length - now > 1);
    }

    public String WaitData(){
        now += 1;
        return line[now - 1];
    }
}
