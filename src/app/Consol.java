package app;

import cllient.Request;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.Scanner;

public class Consol implements Reader {
//    Scanner scanner;

    ObjectInputStream in;
    String[] singleLine = new String[15];
    int mod = 0;
    int now;

    public Consol(ObjectInputStream in){
        this.in = in;
    }

    public String WaitData(){
        String line = null;
        try {
            Request request = (Request) in.readObject();
            line = request.getCommand();
//            line = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return line;
    }

    public boolean Work(){
        return true;
    }

    public void CloseStream(){
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
