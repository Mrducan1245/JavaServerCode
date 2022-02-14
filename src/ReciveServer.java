import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static java.lang.System.in;

public class ReciveServer<fileURL> {
    private static final String filePath = "C:\\Users\\Mrducan\\Desktop\\picture\\";
    private static String fileURL;

    public static void main(String[] args) throws IOException {
//        reciveServer.fileURL = reciveServer.getFileName();
        ServerSocket serverSocket = new ServerSocket(8009);
        System.out.println("监听中...");
        System.out.println("文件地址及名字："+fileURL);
        while (true){
            fileURL = filePath + getFileName();
            ioStreamSet(serverSocket);
        }
    }

    public static String getFileName(){
        DatagramSocket ds = null;
        String fileName = null;
        try {
            ds=new DatagramSocket(9000);
            byte[] data = new byte[1024];
            DatagramPacket dp=new DatagramPacket(data,data.length);
            ds.receive(dp);
            fileName = new String(data, 0, dp.getLength());
            System.out.println("文件名为："+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            ds.close();
        }
        return  fileName;
    }


    public static void ioStreamSet(ServerSocket serverSocket){
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            FileOutputStream fOS = new FileOutputStream(fileURL);
            byte[] byteBuffer = new byte[1024];
            int length = 0;
            while ((length =dis.read(byteBuffer,0,byteBuffer.length))>0){
                fOS.write(byteBuffer,0,length);
                fOS.flush();
            }
            fOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
