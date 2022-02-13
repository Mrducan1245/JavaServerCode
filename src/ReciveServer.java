import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static java.lang.System.in;

public class ReciveServer<fileURL> {
    private static final String fileURL = "C:\\Users\\Mrducan\\Desktop\\picture\\woaininiaiwo.png";

    public static void main(String[] args) throws IOException {
//        reciveServer.fileURL = reciveServer.getFileName();
        ServerSocket serverSocket = new ServerSocket(8009);

        System.out.println("监听中...");
        while (true){
            ioStreamSet(serverSocket);
        }
    }

//    public String getFileName(){
//
//        try {
////            byte[] bs = strFileSize.getBytes(StandardCharsets.UTF_8);
//            //发送大小信息到服务端
//            DatagramSocket ds=new DatagramSocket(8009);
//            byte[] data = new byte[1024];
//            DatagramPacket dp=new DatagramPacket(data,data.length);
//            ds.receive(dp);
//            fileURL = new String(data,0,dp.getLength());
//            System.out.println("文件名为："+fileURL);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return  fileURL;
//
//    }


    public static void ioStreamSet(ServerSocket serverSocket){
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            FileOutputStream fOS = new FileOutputStream(fileURL);
            byte[] byteBuffer = new byte[1024];
            while (in.read(byteBuffer) != -1){
                fOS.write(byteBuffer);
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
