import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileEncryptor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of the folder: ");
        String path = scanner.nextLine();
        System.out.print("Enter the operation to perform (encrypt/decrypt): ");
        String operation = scanner.nextLine();
        System.out.print("Enter the encryption key (16 characters): ");
        String key = scanner.nextLine();
        Path root = Paths.get(path);
        if (operation.equals("encrypt")) {
            encryptFolder(root, key);
        } else if (operation.equals("decrypt")) {
            decryptFolder(root, key);
        } else {
            System.out.println("Invalid operation specified.");
        }
        scanner.close();
    }

    public static void encryptFolder(Path folder, String key) {
        try {
            Files.walk(folder)
                    .filter(Files::isRegularFile)
                    .forEach(file -> encryptFile(file, key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void encryptFile(Path file, String key) {
        try {
            byte[] data = Files.readAllBytes(file);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedData = cipher.doFinal(data);
            Files.write(file, encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decryptFolder(Path folder, String key) {
        try {
            Files.walk(folder)
                    .filter(Files::isRegularFile)
                    .forEach(file -> decryptFile(file, key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decryptFile(Path file, String key) {
        try {
            byte[] data = Files.readAllBytes(file);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedData = cipher.doFinal(data);
            Files.write(file, decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
