package app.MultiThread;

import app.CommandList;
import app.DataBase;
import cllient.Request;
import person.Person;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class HandlerThread extends Thread{
    ClientData clientData;
    BlockingQueue<ClientData> input_queue;
    CommandList commandList;
    Request currentRequest;
    DataBase collection;

    public HandlerThread(BlockingQueue<ClientData> input_queue, CommandList list, DataBase dataBase){
        this.input_queue = input_queue;
        this.commandList = list;
        this.collection = dataBase;
    }

    @Override
    public void run() {
        try {
            clientData = input_queue.take();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        Request[] requests = clientData.getRequests();
        for (Request request : requests) {
            System.out.println(request);
            if (request.getCommand() != null) {
                currentRequest = request;
                acceptRequest(request);
            }else{
                String user_name = request.getData();
                clientData.setUser_name(user_name);
            }
        }
        System.out.println("Все запросы выполнены");
        try{
            Socket client = clientData.getSocket();
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            sleep(3000);
            out.writeUTF("Запросы выполнены\nОтключение от сервера");
            clientData.getSocket().getOutputStream().close();
//            clientData.getSocket().getInputStream().close();
            clientData.getSocket().close();
        } catch (SocketException e){
//            System.out.println("Почти всё нормально");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void acceptRequest(Request r){
        String command = r.getCommand();
        GetCommand(command);
    }
    public void GetCommand(String Line){
        commandList.ExecuteCommand(Line, this);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Help(){
        commandList.Help(clientData);
    }
    public void Info(){
        collection.Info(clientData);
    }
    public void Show(){
        collection.Show(clientData);
    }
    public void Add(){
        collection.Add(clientData, currentRequest.getCreatedPerson());
    }
    public void Remove(){
        collection.Remove(clientData, currentRequest.getData());
    }
    public void Update(){}
    public void Clear(){}

    public void Registration(){
//        System.out.println("I am here");
        String[] reg = currentRequest.getData().split(":");
        String login = reg[0];
        String passwd = reg[1];
        collection.Registration(clientData, login, passwd);
    }
    public void Login(){
        System.out.println("I am here");
        String[] data = currentRequest.getData().split(":");
        String login = data[0];
        String password = data[1];
        collection.Login(clientData, login, password);
    }
}
