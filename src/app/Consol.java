package app;

import java.util.Scanner;

public class Consol implements Reader {

    Scanner scanner;

    String[] singleLine = new String[15];

    int mod = 0;
    int now;

    public Consol(Scanner scaner){
        this.scanner = scaner;
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
        if (now == singleLine.length){
            return false;
        }
        return true;
    }
    public String SingleWaitData(){
        now += 1;
        return singleLine[now - 1];
    }
}
