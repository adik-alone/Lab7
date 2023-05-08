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
        String filename = System.getenv("PATH_COLLECTION");
        xmlworker = new XmlWorker(filename);
        xmlworker.parse();
        reader = xmlworker;
        comMan.commandList.col_manager.createCollection();
        reader = consol;
        while (work){
            Work();
        }
    }

    public void Work(){
        try{
            if(reader.Work()) {
                line = reader.WaitData().trim();
                if (line.equals("")) {
                } else {
                    if (singleLine.Check(line)){
                        ChangeReader(singleLine);
                        line = singleLine.WaitData();
                    }
                    GetCommand(line);
                }
            }else{
                ChangeReader(consol);
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

    public Person creationStartPerson() {
        return creator.createPerson(reader);
    }

//    ------------------
//    исполнители команд
//    ------------------
    public Person createPerson(long id){
        return creator.createPerson(id, reader);
    }

    public void executeScript(){
        try {
            System.out.println("Введите путь до файла");
            String file_name = reader.WaitData();
            scriptExecuter.openFile(file_name);
            ChangeReader(scriptExecuter);
        } catch (FileNotFoundException e) {
            System.out.println("Такого файла не существует попробуйте снова");
        }catch (ScriptRecursionException e){
            System.out.println("Вы создали рекурсию скриптов. Сейчас я смог с этим справиться, но впредь не стоит так делать!");
        }
    }


    public void remove(){

    }
//

}
