package Chapter1;

import java.io.OutputStream;

public interface Servlet {
    void init() throws Exception;

    void service(byte[] requestBuffer, OutputStream out) throws Exception;
}
