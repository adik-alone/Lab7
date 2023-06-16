package app.MultiThread;

import app.DataBase;
import cllient.Client;
import cllient.Request;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class ReadThread extends Thread{
    ClientData clientData;
    BlockingQueue<ClientData> output_queue;
    Socket client;

//    public ReadThread(ClientData clientData, Queue<ClientData> input_queue, Queue<ClientData> output_queue){
//        this.clientData = clientData;
//        this.input_queue = input_queue;
//        this.output_queue = output_queue;
//    }

    public ReadThread(Socket client, BlockingQueue<ClientData> output_queue){
        this.client = client;
        this.output_queue = output_queue;
    }

    @Override
    public void run() {
        clientData = new ClientData(client, null, null);
        client = clientData.getSocket();
        try{
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Request[] requests = (Request[]) in.readObject();
            clientData.setRequests(requests);
            output_queue.put(clientData);
        }catch (IOException | ClassNotFoundException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
