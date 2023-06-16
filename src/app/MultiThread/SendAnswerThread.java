package app.MultiThread;

import app.CommandList;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class SendAnswerThread extends Thread{
    ClientData clientData;
    BlockingQueue<String> input_answer;

    public SendAnswerThread(ClientData clientData, BlockingQueue<String> input_answer){
        this.clientData = clientData;
        this.input_answer = input_answer;
    }
    @Override
    public void run() {
        try {
            System.out.println("Я родился");
            Socket client = clientData.getSocket();
            String answer = "";
//            String answer = input_answer.take();
//            System.out.println("==========================================Что-то взял======================================");
//            System.out.println(answer);
            synchronized (client.getOutputStream()){
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            sleep(100);
            while (!answer.equals("+++SUCCESS+++")) {
                answer = input_answer.take();
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++Снова взял++++++++++++++++++++++++++++++++++");
                System.out.println(answer);
                if (answer.equals("true")) {
                    System.out.println("I am hhhhhheeeeeere");
                    out.writeBoolean(true);
                    out.flush();
                    System.out.println("write done");
                } else if (answer.equals("false")) {
                    out.writeBoolean(false);
                    out.flush();
                } else {
                    out.writeUTF(answer);
                    out.flush();
                }
            }
//                out.close();
            }
            sleep(1000);
            System.out.println("Здесь");
//            out.writeUTF("Завершение работы");
//            out.close();
//            client.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SocketException e){
//            System.out.println("Что-то пошло не так");
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Something goes wrong");
        }


    }
}
