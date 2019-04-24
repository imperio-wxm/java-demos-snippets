package com.imperio.jetty;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpTest {

    @Test
    public void testTcpClient() throws IOException {
        String ip = "10.246.40.80";
        int port = 5240;
        try (Socket sck = new Socket(ip, port)) {
            String content = "这是一个java模拟客户端\n";
            byte[] bstream = content.getBytes("UTF-8");
            OutputStream os = sck.getOutputStream();
            os.write(bstream);
            System.out.println(content);
        }
    }

    @Test
    public void testTcpServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10003);
        try (Socket socket = serverSocket.accept() ) {
            GetMessageFromClient(new DataInputStream(socket.getInputStream()));
        }
    }

    private void GetMessageFromClient(DataInputStream dataInputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(dataInputStream, "UTF-8"));
        String str = null;
        while((str = br.readLine()) != null){
            System.out.println(str);
        }
    }
}
