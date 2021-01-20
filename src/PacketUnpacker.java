import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PacketUnpacker {

    private int bufferSize = 1024; // 버퍼의 초기사이즈(변경가능)
    private static ByteBuffer buffer; // 한번만 생성해서 사용하고자 함.
    private int offset = 0;

    public PacketUnpacker() {
        // TODO Auto-generated constructor stub
        buffer = ByteBuffer.allocate(bufferSize);
        buffer.clear();
    }

    public PacketUnpacker(int size){
        buffer = ByteBuffer.allocate(size);
        buffer.clear();
    }

    public PacketUnpacker(byte[] data){
        buffer = ByteBuffer.allocate(data.length);
        buffer.clear();
        buffer = ByteBuffer.wrap(data); // Byte Array를 ByteBuffer로  Wrapping
    }

    public byte[] Finish(){

        offset = buffer.position(); // 마지막 포인터 위치 기억
        byte[] data = {};

        if(buffer.hasArray()){ // Array가 존재하는 경우에만
            data = buffer.array();
        }

        byte[] result = new byte[offset];
        System.arraycopy(data, 0, result, 0, offset); // offset만큼 복사한다

        buffer.flip();
        return result;
    }

    public void add(int param){
        if(buffer.remaining() > Integer.SIZE / Byte.SIZE) // 남은 공간이 있을 경우
            buffer.putInt(param);
    }

    public void add(long param){
        if(buffer.remaining() > Long.SIZE / Byte.SIZE) // 남은 공간이 있을 경우
            buffer.putLong(param);
    }

    public void add(String param){
        int len = param.getBytes().length;
        if(buffer.remaining() > len){ // 남은 공간이 있을 경우
            buffer.putInt(len);
            buffer.put(param.getBytes());
        }
    }

    public ByteBuffer getBuffer(){
        return buffer;
    }

    public byte getPacketType(){
        return buffer.get();
    }

    public int getInt(){
        return buffer.getInt();
    }

    public long getLong(){
        return buffer.getLong();
    }


    public String getString(){
        long len = buffer.getLong();
        System.out.println("문자열 길이 : "+(int)len);
        byte[] temp = new byte[(int)len];

        buffer.get(temp);
        String result = new String(temp);
        return result;
    }

    public void printINT2LONG4(){
        System.out.println("int 1 : "+buffer.getInt());
        System.out.println("int 2 : "+buffer.getInt());
        System.out.println("long 1 : "+buffer.getLong());
        System.out.println("long 2 : "+buffer.getLong());
        System.out.println("long 3 : "+buffer.getLong());
        System.out.println("long 4 : "+buffer.getLong());
    }
    public void printLONG1STRING() {

        //System.out.println("롱형 1개: " + buffer.getLong());
        System.out.println("스트링 : " + getString());
    }

}