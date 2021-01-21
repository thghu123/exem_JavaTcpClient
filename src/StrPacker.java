import java.nio.ByteBuffer;

public class StrPacker implements Packet{

        private byte    packetType;
        private long    length;
        private String  str;

        public StrPacker(long length, String str){
            this.packetType = PacketType.LONG1STRING;
            this.length = length;
            this.str = str;

        } // 멤버 값만 입력


        @Override
        public byte[] toBytes() {
            //요청할 경우에 길이에 맞게 할당을 진행하고, 값을 입력한다.
            ByteBuffer buffer = ByteBuffer.allocate(((int)length)+8+1);
            buffer.put(packetType);
            buffer.put(str.getBytes());//문자열 입력

            return buffer.array();
        }



}
