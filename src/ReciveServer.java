import java.io.*;
import java.net.*;

public class ReciveServer<fileURL> {
    private static final String filePath = "C:\\Users\\Mrducan\\Desktop\\picture\\";
    private static String fileURL;

    public static void main(String[] args) throws IOException {
//        reciveServer.fileURL = reciveServer.getFileName();
        ServerSocket serverSocket = new ServerSocket(8009);
        System.out.println("监听中...");

        while (true){
            try {
                Socket socket1 =serverSocket.accept() ;
                String[] mFileNameHostIP = getFileNameHostIP(socket1);
                socket1.close();

                fileURL = filePath + mFileNameHostIP[1]+"\\"+mFileNameHostIP[0];
                File dir = new File(filePath + mFileNameHostIP[1]);
                if (!dir.exists()) {// 判断目录是否存在
                    dir.mkdir();
                }
                System.out.println("文件地址是："+fileURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Socket socket2 = serverSocket.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                        ioStreamSet(socket2);
                    try {
                        socket2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }



    public static String[] getFileNameHostIP(Socket socket){

        String[] fileNameHostIP = null;
        String fileName = null;
        String cilentHostIP = null;
        try {

            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            cilentHostIP = bufferedReader.readLine();
            fileName = bufferedReader.readLine();

            fileNameHostIP = new String[2];
            fileNameHostIP[0] = fileName;
            fileNameHostIP[1] = cilentHostIP;
            System.out.println("文件名为："+fileName);
            System.out.println("客户端地址是："+cilentHostIP);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileNameHostIP;

    }



    public static void ioStreamSet(Socket socket){
        try {
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
//        }finally {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
