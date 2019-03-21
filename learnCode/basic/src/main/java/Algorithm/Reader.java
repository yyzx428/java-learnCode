package Algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Reader {
    public static ArrayList<BigDecimal> read(File file) {
        ArrayList<BigDecimal> result = null;
        FileInputStream fileInputStream = null;
        try {
            byte[] inputs = new byte[Long.valueOf(file.length()).intValue()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(inputs);
            String[] accounts = new String(inputs, "UTF-8").split("\r\n");
            result = new ArrayList<>(accounts.length);
            for (String account : accounts) {
                result.add(new BigDecimal(account));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fileInputStream != null;
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
