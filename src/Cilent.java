import sun.rmi.runtime.Log;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cilent {
    private  Socket socket;

    private final int PORT = 8009;

    private FileInputStream fis;

    private OutputStream out ;


    public void postePic(String fileURL) {

        try {
            File file = new File(fileURL);
            final String IP = "192.168.10.64";
            fis = new FileInputStream(fileURL);
            byte[] bytes = new byte[1024];
            //得到图片大小
            int length = (int) file.length();
            System.out.println("图片大小为："+length);

            socket = new Socket(IP,PORT);
            out = socket.getOutputStream();
            System.out.println("socket创建成功");

            //读取服务器响应数据
//            byte[] getRec = new byte[1];
//            InputStream is = socket.getInputStream();
//            is.read(getRec);
//            String message = new String(getRec);
//
//            //如果得到的反馈是1，说明服务端已经接收完成图片大小，可以开始发送图片了
//            if (message.equals("1")){
//                out.write(bytes,0,length);
//                out.flush();
//            }
            while (fis.read(bytes) != -1){
                out.write(bytes);
                out.flush();
            }
            //关闭流和套接字
            socket.shutdownOutput();
            fis.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
