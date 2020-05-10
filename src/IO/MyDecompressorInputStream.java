package IO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    public int read(byte[] finalBytes) throws IOException {
        //The size of the compress array
        int left = (finalBytes.length-25)%32; //what's left in the end and we can't compress
        byte[] bytes = new byte[25+left+((finalBytes.length-25-left)/8)];
        in.read(bytes);
        in.close();

        //add the first 24 bytes as is
        for (int i = 0; i <24 ; i++) {
            finalBytes[i] = bytes[i];
        }
        finalBytes[24] = (byte) (0); //empty for left
        int loc = 25;
        byte[] curr4Bytes = new byte[4];
        int count = 0;
        //add 4 each time
        for (int i = 25; i < bytes.length; i++) {
            if (count<4) {
                curr4Bytes[count] = bytes[i];
                count++;
            }
            //after 4 bytes, add and start over
            if (count==4){
                int currDecNum = convertByteToInt(curr4Bytes);
                String binaryNum = Integer.toBinaryString(currDecNum);
                String currBinaryNum = "";
                int s=0;
                int curr = binaryNum.length();
                //adding zero to the beginning of the binary num
                if(binaryNum.length() <32) {
                    for (s=0; s < 32-binaryNum.length(); s++) {
                        currBinaryNum += "0";
                    }
                }
                currBinaryNum +=binaryNum;

                int j=loc;
                String[] temp = currBinaryNum.split("");
                //add to final bytes array the compressed bytes
                for (String item: temp) {
                    finalBytes[j] = Byte.parseByte(item);
                    j++;
                }
                loc = loc+32;
                count=0;
                currBinaryNum="";
                //if there are less then 32 left
                if (left==finalBytes.length-loc && left!=0){ /// wrong if!!!!!
                    for (int k = i+1; k < bytes.length; k++) {
                        finalBytes[loc] = bytes[k];
                        loc++;
                    }
                    return 0;
                }
            }
        }
        return 0;
    }


    private int convertByteToInt(byte[] bytes){
        ByteBuffer allBytes = ByteBuffer.wrap(bytes);
        int i = allBytes.getInt();
        return i;
    }
}

