package app;

import java.util.Scanner;

public class Console implements Reader {

    Scanner scanner;
    String[] singleLine = new String[15];

    int now;

    public Console(Scanner scanner){
        this.scanner = scanner;
    }

    public String WaitData(){
        String line = null;
        line = scanner.nextLine();
        return line;
    }

    public boolean Work(){
        return true;
    }

    public void CloseStream(){
        scanner.close();
    }

    public boolean singleLineCheck(String line){
        singleLine = line.split(" ");
        if (singleLine.length > 1){
            now = 0;
            return true;
        }
        return false;
    }

    public boolean LenCheck(){
        return now != singleLine.length;
    }
    public String SingleWaitData(){
        now += 1;
        return singleLine[now - 1];
    }
}
