import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileEncryptor {

    private static final String KEY = "0123456789abcdef"; // 16-byte encryption key

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of the folder: ");
        String path = scanner.nextLine();
        System.out.print("Enter the operation to perform (encrypt/decrypt): ");
        String operation = scanner.nextLine();
        Path root = Paths.get(path);
        if (operation.equals("encrypt")) {
            encryptFolder(root);
        } else if (operation.equals("decrypt")) {
            decryptFolder(root);
        } else {
            System.out.println("Invalid operation specified.");
        }
        scanner.close();
    }

    public static void encryptFolder(Path folder) {
        try {
            Files.walk(folder)
                    .filter(Files::isRegularFile)
                    .forEach(FileEncryptor::encryptFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void encryptFile(Path file) {
        try {
            byte[] data = Files.readAllBytes(file);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedData = cipher.doFinal(data);
            Files.write(file, encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decryptFolder(Path folder) {
        try {
            Files.walk(folder)
                    .filter(Files::isRegularFile)
                    .forEach(FileEncryptor::decryptFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decryptFile(Path file) {
        try {
            byte[] data = Files.readAllBytes(file);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedData = cipher.doFinal(data);
            Files.write(file, decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
