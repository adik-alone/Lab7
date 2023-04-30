package app;

import exception.ScriptRecursionException;
import person.Person;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {
    CommandManager comMan;
    boolean work;
    String line;
    Creator creator;
    ScriptExecuter scriptExecuter;
    Consol consol;
    Reader reader;
    SingleLine singleLine;
    XmlWorker xmlworker;
    int mod = 0; // 0 --- консоль |||| 1 --- скрипт

    //блок инициализации
    public void start(CommandManager cm){
        creator = new Creator(this);
        consol = new Consol(new Scanner(System.in));
        comMan = cm;
        scriptExecuter = new ScriptExecuter();
        singleLine = new SingleLine();
        work = true;
        reader = consol;

        xmlworker = new XmlWorker("Collection.xml");
        xmlworker.parse();
        while (work){
            Work();
        }
    }
    public void Work(){
        try{
            line = reader.WaitData();
            if (line.equals("")){}
            else{
                GetCommand(line);
            }
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

    public void ChangeReader(Reader r){
        reader = r;
    }

    public Person createPerson(long id){
        return creator.createPerson(id, reader);
    }

    public void execute(){
        while(true) {
            try {
                System.out.println("Введите путь до файла:");
                String file_name = reader.WaitData();
                scriptExecuter.openFile(file_name);
                ChangeReader(scriptExecuter);
                while (scriptExecuter.Work()) {
                    String line = scriptExecuter.WaitData();
                    if (line.equals("")){}
                    else {
                        GetCommand(line);
                    }
                }
                scriptExecuter.CloseScaner();
                if (scriptExecuter.ls.size() == 0){
                    ChangeReader(consol);
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
