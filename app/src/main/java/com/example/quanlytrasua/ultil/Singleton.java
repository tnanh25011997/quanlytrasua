//package com.example.quanlytrasua.ultil;
//
//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;
//
//public class Singleton {
//    private static Singleton instance;
//    Socket mSocket;
//    Emitter.Listener onResult;
//    Emitter.Listener listTable;
//
//    private Singleton() {}
//
//    public static Singleton Instance()
//    {
//        if (instance == null)
//        {
//            instance = new Singleton();
//        }
//        return instance;
//    }
//    public Socket getmSocket()
//    {
//        return this.mSocket;
//    }
//
//    public Emitter.Listener getOnResult()
//    {
//        return this.onResult;
//    }
//
//    public void setmSocket(Socket socket)
//    {
//        this.mSocket = socket;
//    }
//
//    public void setOnResult(Emitter.Listener listener)
//    {
//        this.onResult = listener;
//    }
//
//    public Emitter.Listener getListTable() {
//        return listTable;
//    }
//}