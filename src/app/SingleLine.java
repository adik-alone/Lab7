package app;

import exception.EndOfLineEcxeption;

public class SingleLine implements Reader{
    String[] line = new String[15];

    int now;

    public void NewLine(String s){
        line = s.split(" ");
//        for (String l : line) {
//            System.out.print(l + " ");
//        }
//        System.out.println("");
        now = 0;
    }

    public boolean Hangdling(){
        if (line.length > 1) return true;
        return false;
    }

    public void Work(){
        boolean a = Hangdling();
    }

    public void TrueArray(int a) throws EndOfLineEcxeption {
        if (a >= line.length) throw new EndOfLineEcxeption();
    }

    public String WaitData(){
        now += 1;
        return line[now - 1];
    }
}
