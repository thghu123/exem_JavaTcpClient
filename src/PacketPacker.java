import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PacketPacker {

    private int bufferSize = 1024; // 버퍼의 초기사이즈(변경가능)
    private static ByteBuffer buffer; // 한번만 생성해서 사용하고자 함.
    //바이트 타입의 원소를 저장하는 버퍼

    private int offset = 0;

    public PacketPacker() { //allocateDirect()의 경우 커널버퍼에 직접 접근가능하다.
        // TODO Auto-generated constructor stub
        buffer = ByteBuffer.allocate(bufferSize); //현재 임의로 할당
        buffer.clear();
    }

    public PacketPacker(int size){//사이즈만큼 생성해주는 생성자
        buffer = ByteBuffer.allocate(size);
        buffer.clear();
    }

    public PacketPacker(byte[] data){ //들어온 데이터값의 길이 만큼 생성하고 data 넣어줌줌
        buffer = ByteBuffer.allocate(data.length);//       buffer = ByteBuffer.allocate(data.length);
        buffer.clear();
        buffer = ByteBuffer.wrap(data); // Byte Array를 ByteBuffer로  Wrapping
    }

    public void Finish(OutputStream os) throws IOException {

        offset = buffer.position(); // 마지막 포인터 위치 offset 기억
        byte[] data = {};

        if(buffer.hasArray()){ // 따로 array 를 반환하는 함수 만들기.
            data = buffer.array();
        } // 데이터 안에 쌓인 버퍼 입력됨

        os.write(data);
        os.flush();
        buffer.flip();  //buffer의 포지션을 0으로 이동

    }

    public void SetPacketType(byte PacketType){
        buffer.put(PacketType);
    }
    //패킷 타입에 대한 헤더값 입력

//=======================입력부=====================
    public void add(int param){
        if(buffer.remaining() > Integer.SIZE / Byte.SIZE) // 남은 공간이 있을 경우 , 몇 바이트인지 환산
            buffer.putInt(param);
    }

    public void add(long param){
        if(buffer.remaining() > Long.SIZE / Byte.SIZE) // 남은 공간이 있을 경우
            buffer.putLong(param);
    }

    public void add(int iparam1, int iparam2, long lparam1, long lparam2, long lparam3, long lparam4){
        if(buffer.remaining() > (Integer.SIZE*2)+(Long.SIZE*4) / Byte.SIZE) // 남은 공간이 있을 경우 , 몇 바이트인지 환산
            buffer.putInt(iparam1);
            buffer.putInt(iparam2);
            buffer.putLong(lparam1);
            buffer.putLong(lparam2);
            buffer.putLong(lparam3);
            buffer.putLong(lparam4);
    }

    public void add(String param){
        long len = (long)param.getBytes().length; //페킷 길이 저장
        if(buffer.remaining() > len){ // 현제에서 limit까지 읽을 수 있는 갯수
            buffer.putLong(len); //int 값으로 len 값 먼저 입력
            buffer.put(param.getBytes()); //문자열 입력
        }
    }

    //====================출력부======================
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

    public ByteBuffer getBuffer(){
        return buffer;
    }

}