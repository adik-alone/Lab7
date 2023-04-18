package app;

import commands.Reader;
import exception.ScriptRecursionException;
import person.Person;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    Scanner scan;
    CommandManager comMan;
    boolean work;
    String new_line;

    Creator creator;

    ScriptExecuter scriptExecuter;

    Consol consol;
    int mod = 0; // 0 --- консоль |||| 1 --- скрипт
    public App(){}
    public void start(CommandManager cm){
        scan = new Scanner(System.in);
        creator = new Creator(this);
        consol = new Consol(scan);
        comMan = cm;
        scriptExecuter = new ScriptExecuter();
        work = true;
        while (work){
            waitCommand();
        }
    }
    public void waitCommand(){
        new_line = scan.nextLine().trim();
        GetCommand(new_line);
        if (new_line.equals("exit")) this.finish();
    }
    public void GetCommand(String Line){
        try{
            comMan.getCommand(Line);
        }catch (NullPointerException e){
            System.out.println("Хорошая попытка, попробуйте снова. Команда help выведет информацию о всех командах");
        }
    }
    public void finish(){
        work = false;
        scan.close();
    }

    public Person createPerson(long id){
        if (mod == 0){
            return creator.createPerson(id, consol);
        }else{
            return creator.createPerson(id, scriptExecuter);
        }

    }



    public void execute(){
        Reader reader;
        if (mod == 1){
            reader = scriptExecuter;
        }else{
            reader = consol;
        }
        while(true) {
            try {
                System.out.println("Введите путь до файла:");
                String file_name = reader.WaitData();
                scriptExecuter.openFile(file_name);
                mod = 1;
                while (scriptExecuter.Work()) {
                    String line = scriptExecuter.execute();
                    GetCommand(line);
                }
                scriptExecuter.CloseScaner();
                if (scriptExecuter.ls.size() == 0){
                    mod = 0;
                }
                scriptExecuter.RemoveLast();
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Такого файла не существует попробуйте снова");
            }catch (ScriptRecursionException e){
                System.out.println("Вы нарушили очень важный закон, который гласит: \"Не стоит делть рекурсию, иначе кара будет ужасна\". Ну и собственно, кара ужасна.");
                System.out.println("Ладно, можете вводить команды, я не настолько жесток");
                break;
            }
        }
    }
}
