package mavenPlugins;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class Test {
    static String path = System.getProperty("user.dir") + "\\basic\\src\\main\\resources\\td-query-service-core-1.0-SNAPSHOT.jar";
    static File source = new File(path);

    public static void main(String[] args) {

        File workingSource = getBackupFile();
        renameFile(source, workingSource);

        try (JarFile jarFileSource = new JarFile(workingSource)) {
            System.out.println(jarFileSource.getManifest().getMainAttributes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static File getBackupFile() {
        return new File(source.getParentFile(), source.getName() + ".original");
    }

    private static void renameFile(File file, File dest) {
        if (!file.renameTo(dest)) {
            throw new IllegalStateException(
                    "Unable to rename '" + file + "' to '" + dest + "'");
        }
    }
}
