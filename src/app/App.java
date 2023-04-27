package app;

import exception.ScriptRecursionException;
import person.Person;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {
    CommandManager comMan;
    boolean work;
    String new_line;
    Creator creator;
    ScriptExecuter scriptExecuter;
    Consol consol;
    Reader reader;
    SingleLine singleLine;
    int mod = 0; // 0 --- консоль |||| 1 --- скрипт
    public void start(CommandManager cm){
        creator = new Creator(this);
        consol = new Consol(new Scanner(System.in));
        comMan = cm;
        scriptExecuter = new ScriptExecuter();
        singleLine = new SingleLine();
        work = true;
        reader = consol;
        while (work){
            waitCommand();
        }
    }
    public void waitCommand(){
        try {
            new_line = reader.WaitData();
            if (new_line.equals("")){}
            else{
                singleLine.NewLine(new_line);
                if(singleLine.Hangdling()){
                    Reader reader1 = reader;
                    ChangeToSingleLine();
                    GetCommand(reader.WaitData());
                    ChangeReader(reader1);
                }else {
                    GetCommand(new_line);
                }
            }
            if (new_line.equals("exit")) finish();
        }catch (NoSuchElementException e){
            System.out.println("Экстренный выход");
            finish();
        }

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
        consol.CloseStream();
    }
    public void ChangeToScripte(){
        reader = scriptExecuter;
    }
    public void ChangeToConsol(){
        reader = consol;
    }

    public void ChangeReader(Reader r){
        reader = r;
    }

    public void ChangeToSingleLine(){
        reader = singleLine;
    }

    public Person createPerson(long id){
        return creator.createPerson(id, reader);
    }

    public void execute(){
//        Reader reader;
//        if (mod == 1){
//            reader = scriptExecuter;
//        }else{
//            reader = consol;
//        }
        while(true) {
            try {
                System.out.println("Введите путь до файла:");
                String file_name = reader.WaitData();
                scriptExecuter.openFile(file_name);
                ChangeToScripte();
                while (scriptExecuter.Work()) {
                    String line = scriptExecuter.WaitData();
                    if (line.equals("")){}
                    else {
                        GetCommand(line);
                    }
                }
                scriptExecuter.CloseScaner();
                if (scriptExecuter.ls.size() == 0){
                    ChangeToConsol();
                }
                scriptExecuter.RemoveLast();
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Такого файла не существует попробуйте снова");
            }catch (ScriptRecursionException e){
                System.out.println("Вы нарушили очень важный закон, который гласит: \"Не стоит делть рекурсию, иначе кара будет ужасна\"");
                System.out.println("Ладно, можете вводить команды, я не настолько жесток");
                break;
            }
        }
    }
}
