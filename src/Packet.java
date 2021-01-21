import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Packet {

    public byte[] toBytes();
    public void send(OutputStream os, Packet packet) throws IOException;

}
