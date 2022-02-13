import sun.rmi.runtime.Log;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cilent {
    private final String IP = "192.168.10.64";
    private  Socket socket;

    private final int PORT = 8009;

    private FileInputStream fis;

    private OutputStream out ;


    public void postePic(String fileURL) {
        try {
            fis = new FileInputStream(fileURL);
            byte[] bytes = new byte[1024*1024*5];
            //得到图片大小
            int length = fis.read(bytes);
            System.out.println("图片大小为："+length);

            socket = new Socket(IP,PORT);
            out = socket.getOutputStream();
            System.out.println("socket创建成功");

            String strLength = String.valueOf(length);

            //向客户端发送图片大小
            out.write(strLength.getBytes());
            out.flush();
            System.out.println("输出流out创建成功");

            //读取服务器响应数据
            byte[] getRec = new byte[1];
            InputStream is = socket.getInputStream();
            is.read(getRec);
            String message = new String(getRec);

            //如果得到的反馈是1，说明服务端已经接收完成图片大小，可以开始发送图片了
            if (message.equals("1")){
                out.write(bytes,0,length);
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
