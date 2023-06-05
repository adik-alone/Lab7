package app;

import exception.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScriptExecuter implements Reader {

    List<String> listName = new ArrayList<>();
    List<Scanner> listScanner = new ArrayList<>();
    String filename;
    Scanner scanner;
    public String WaitData(){
        String line = listScanner.get(listScanner.size() - 1).nextLine();
        System.out.println(line);
        return line;
    }

    public boolean Work() throws IndexOutOfBoundsException{
        if(!listScanner.get(listScanner.size() - 1).hasNext()){
            if(listScanner.size() == 1){
                RemoveLast();
                CloseScanner();
                return false;
            }else{
                RemoveLast();
                CloseScanner();
                return true;
            }
        }
        return true;
    }

    public void openFile(String filename) throws FileNotFoundException, ScriptRecursionException {
        this.filename = filename;
        if (listName.contains(filename)) throw new ScriptRecursionException();
        listName.add(filename);
        scanner = new Scanner(new File(filename));
        listScanner.add(scanner);
    }

    public void CloseScanner(){
        listScanner.remove(listScanner.size()-1);
    }

    public void RemoveLast(){
        listName.remove(listName.size() - 1);
    }

}
