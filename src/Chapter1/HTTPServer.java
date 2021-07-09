// 客户端懒得搬了 用浏览器发送请求 但是一次性发送了两个请求：HttpServerHtml.ico
// 所以第一次响应正常 而第二次抛出异常 没搞懂76行的寻找服务器上资源的路径是咋整的
package Chapter1;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class HTTPServer {

    private static Map<String, Servlet> servletCache = new HashMap<>(); // servlet缓存

    public static void main(String[] args) {
        int port;
        ServerSocket serverSocket;

        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) { // 若启动时传参失败，则使用默认端口
            System.out.println("port = 8080 (Default)");
            port = 8080;
        }

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务器正在监听端口： " + serverSocket.getLocalPort());
            while (true) {
                try {
                    final Socket socket = serverSocket.accept();
                    System.out.println("建立客户端连接：" + socket.getInetAddress() + ":" + socket.getPort());
                    // 很重要！！！
                    service(socket);    // 响应用户请求 资源不存在时抛出异常

                } catch (Exception e) {
                    System.out.println("客户请求资源不存在");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();    // 监听端口套接字创建失败
        }
    }

    // 从服务器端套接字接收到http请求信息， 解析并将静态资源写入该套接字
    public static void service(Socket socket) throws Exception {
        // 接受http请求信息
        InputStream socketIn = socket.getInputStream(); //  获得输入流
        Thread.sleep(500);
        int size = socketIn.available();
        byte[] buffer = new byte[size];
        socketIn.read(buffer);
        String request = new String(buffer);
        System.out.println(request);    // 打印请求信息

        // 解析http请求信息

        //请求头首行
        int endIndex = request.indexOf("\r\n");
        if (endIndex == -1)
            endIndex = request.length();
        String firstLineOfRequest = request.substring(0, endIndex);
        // 解析该首行
        String[] parts = firstLineOfRequest.split(" ");
        String uri = "";    // 相对路径
        if (parts.length >= 2) { // 请求方法 uri http协议
            uri = parts[1];
        }

        // 动态资源
        if(uri.contains("servlet")) {
            String servletName;
            if(uri.contains("?")) {
                servletName = uri.substring(uri.indexOf("servlet/") + 8, uri.indexOf("?"));
            } else {
                servletName = uri.substring(uri.indexOf("servlet/") + 8);
            }
            Servlet servlet = servletCache.get(servletName);
            if(servlet == null) {
//                servlet = new HelloServlet();
                // 反射 通过servletName拿到HelloServlet实现类
                servlet = (Servlet)Class.forName("Chapter1." + servletName).getDeclaredConstructor().newInstance();
                servlet.init();
                servletCache.put(servletName, servlet);
            }
            servlet.service(buffer, socket.getOutputStream());
            Thread.sleep(1000);
            socket.close();
            return ;
        }

        // 静态资源
        // http响应正文的类型
        String contentType;
        if (uri.contains("html") || uri.contains("htm"))
            contentType = "text/html";
        else if (uri.contains("jpg") || uri.contains("jpeg"))
            contentType = "image/jpeg";
        else if (uri.contains("gif"))
            contentType = "image/gif";
        else contentType = "application/octet-stream";

        // 创建http响应报文
        String responseFirstLine = "HTTP/1.1 200 OK\r\n";
        String responseHeader = "Content-Type: " + contentType + "\r\n\r\n";    // 响应头与响应体间有空行
        InputStream in = HTTPServer.class.getResourceAsStream(uri);   // 读取服务器上资源作为响应体
        OutputStream socketOut = socket.getOutputStream();  // 从服务器套接字获得输出流，即将向其发送响应报文
        socketOut.write(responseFirstLine.getBytes());
        socketOut.write(responseHeader.getBytes());
        int len;
        buffer = new byte[128];
        while ((len = in.read(buffer)) != -1) {
            socketOut.write(buffer, 0, len);
        }
        Thread.sleep(1000);
        socket.close(); // 服务器关闭连接
    }
}
