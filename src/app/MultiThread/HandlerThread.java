package app.MultiThread;

import app.CommandList;
import cllient.Request;
import person.Person;

import java.util.Queue;

public class HandlerThread implements Runnable{
    ClientData clientData;
    Queue<ClientData> input_queue;
    Queue<ClientData> output_queue;

    CommandList commandList;

    Request currentRequest;

    public HandlerThread(Queue<ClientData> input_queue, Queue<ClientData> output_queue, CommandList list){
        this.output_queue = output_queue;
        this.input_queue = input_queue;
        this.commandList = list;
    }

    @Override
    public void run() {
        clientData = input_queue.poll();
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
    }
    public void acceptRequest(Request r){
        String command = r.getCommand();
        GetCommand(command);
    }
    public void GetCommand(String Line){
        commandList.ExecuteCommand(Line);
    }
}
