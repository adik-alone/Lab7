package app;

import commands.Reader;
import exception.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScriptExecuter implements Reader {

    List<String> list = new ArrayList<>();
    List<Scanner> ls = new ArrayList<>();

    String filename;
    Scanner scanner;

    public String WaitData(){
        String line = ls.get(ls.size() - 1).nextLine();
        System.out.println(line);
        return line;
    }

    public String execute(){
        String line = ls.get(ls.size() - 1).nextLine();
        System.out.println(line);
        return line;
    }

    public Boolean Work() throws IndexOutOfBoundsException{
        return ls.get(ls.size() - 1).hasNext();
    }

    public void openFile(String filename) throws FileNotFoundException, ScriptRecursionException {
        this.filename = filename;
        if (list.contains(filename)) throw new ScriptRecursionException();
        list.add(filename);
        scanner = new Scanner(new File(filename));
        ls.add(scanner);
    }

    public void CloseScaner(){
        ls.remove(ls.size()-1);
    }

    public void RemoveLast(){
        list.remove(list.size() - 1);
    }

}
