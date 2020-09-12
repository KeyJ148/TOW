package tech.abro.orchengine.net;

public class NetTools {

    //Обрезаем пустые байты
    public static byte[] clearByteData(byte[] data){
        int pos = 0;
        while (pos < data.length && data[pos] != 0) pos++;

        byte[] result = new byte[pos];
        System.arraycopy(data, 0, result, 0, pos);
        return result;
    }

}
