package app;

import java.util.Scanner;

public class Consol implements Reader {

    Scanner scanner;

    public Consol(Scanner scaner){
        this.scanner = scaner;
    }

    public String WaitData(){
        String line;
        line = scanner.nextLine().trim();
        return line;
    }

    public void CloseStream(){
        scanner.close();
    }
}
