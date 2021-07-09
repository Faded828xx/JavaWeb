package Chapter1;

import java.io.*;


public class HelloServlet implements Servlet {

    public void init(){
        System.out.println("HelloServlet is inited!");
    }

    @Override
    public void service(byte[] requestBuffer, OutputStream out) throws Exception {
        String request = new String(requestBuffer);
        // 解析http请求
        String firstLineOfRequest = request.substring(0, request.indexOf("\r\n"));
        String[] parts = firstLineOfRequest.split(" ");
        String uri = parts[1];

        // 解析uri中的请求参数 parameters = "username=Tom" 得到username
        String username = null;
        if(uri.contains("username=")) {
            String parameter = uri.substring(uri.indexOf("?"));
            parts = parameter.split("=");
            username = parts[1];
        }

        // 将响应写入socket
        out.write("HTTP/1.1 200 OK\r\n".getBytes());
        out.write("Content-Type:text/html\r\n\r\n".getBytes());
        String content = "<html><head><title>HelloWorld" + "</title></head><body>";
        content += "<h1>Hello: " + username + "</h1></body><head>";
        out.write(content.getBytes());
    }

}
