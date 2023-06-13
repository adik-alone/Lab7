package app.MultiThread;

import cllient.Request;

import java.net.Socket;

public class ClientData {
    Socket socket;
    Request[] requests;
    String user_name;

    public ClientData(Socket socket, Request[] requests, String user_name){
        this.socket = socket;
        this.requests = requests;
        this.user_name = user_name;
    }

    public Socket getSocket() {
        return socket;
    }
    public Request[] getRequests() {
        return requests;
    }
    public String getUser_name() {
        return user_name;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public void setRequests(Request[] requests) {
        this.requests = requests;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
