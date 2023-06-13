package app.MultiThread;

import cllient.Client;
import cllient.Request;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.Queue;

public class ReadThread implements Runnable{

    ClientData clientData;
    Queue<ClientData> input_queue;
    Queue<ClientData> output_queue;
    Socket client;

    public ReadThread(ClientData clientData, Queue<ClientData> input_queue, Queue<ClientData> output_queue){
        this.clientData = clientData;
        this.input_queue = input_queue;
        this.output_queue = output_queue;
    }

    @Override
    public void run() {
        client = clientData.getSocket();
        try{
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Request[] requests = (Request[]) in.readObject();
            clientData.setRequests(requests);
            output_queue.add(clientData);
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
