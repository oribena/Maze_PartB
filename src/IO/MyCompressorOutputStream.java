package IO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {

    }

    public void write(byte[] bytes) throws IOException {
        int left = (bytes.length-25)%32; //what's left in the end and we can't compress
        byte[] finalBytes = new byte[25+left+((bytes.length-25-left)/8)]; //TODO: check
        //add the first 24 bytes as is
        for (int i = 0; i <24 ; i++) {
            finalBytes[i] = bytes[i];
        }
        finalBytes[24]=(byte)0; //add left=0 in loc 24
        int loc = 25;
        int count = 0;
        String test = "";
        //less then 32 in mat
        if (bytes.length-25<32){
            for (int k = loc; k < bytes.length; k++) {
                finalBytes[loc] = bytes[k];
                loc++;
            }
            finalBytes[24]=(byte)1;
        }
        else {
            //add 32 each time
            for (int i = 25; i < bytes.length; i++) {
                if (count < 32) {
                    test += Byte.toString(bytes[i]);
                    count++;
                }
                //after 32 bytes, add and start over
                if (count == 32) {

                    int dec = (int) Long.parseLong(test, 2);
                    byte[] currFinalBytes = convertIntToByte(dec);

                    int j = 0;
                    //add to final bytes array the compressed bytes
                    for (int k = loc; k < loc + 4; k++) {
                        finalBytes[k] = currFinalBytes[j];
                        j++;
                    }
                    loc = loc + 4;
                    count = 0;
                    test = "";
                    //if there are less then 32 left
                    if (left == finalBytes.length - loc && left != 0) {
                        finalBytes[24] = (byte) left;
                        for (int k = i + 1; k < bytes.length; k++) {
                            finalBytes[loc] = bytes[k];
                            loc++;
                        }
                        break;
                    }
                }
            }
        }
        if( out instanceof ObjectOutputStream){
            ((ObjectOutputStream) out).writeObject((finalBytes));
        }
        else
            out.write(finalBytes);
        out.flush();
        out.close();
    }


    private  byte[] convertIntToByte(int i){
        ByteBuffer allBytes = ByteBuffer.allocate(4);
        allBytes.putInt(i);
        return allBytes.array();
    }
}

