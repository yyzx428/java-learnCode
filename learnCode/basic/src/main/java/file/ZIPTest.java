package file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZIPTest {
    public static void main(String[] args) {
        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(new File("C:\\Users\\29410\\Desktop\\Nginx.zip")), Charset.forName("GBK"))) {
            ZipEntry zp;
            while ((zp = zip.getNextEntry()) != null) {
                if (zp.isDirectory()) {
                    continue;
                }
                byte[] buf = new byte[1024];
                Integer num = -1;
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    while ((num = zip.read(buf, 0, buf.length)) != -1) {
                        baos.write(buf, 0, num);
                    }
                }
                buf = null;
                System.out.println(zp.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
