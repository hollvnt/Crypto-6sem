import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SHA256 {
    public static void main(String[] args) {
        System.out.println("\nХеширование SHA256\n");
        long OldTicks = System.currentTimeMillis();
        String text = "Yaskobvich Mark Eduardovuich";
        String salt = createSalt(15);
        String hash = generateSHA256(text, salt);

        System.out.println("M:  " + text + "\nСоль: " + salt + "\nХэш:  " + hash);
        System.out.println("Время: " + (System.currentTimeMillis() - OldTicks) + " мс\n\n");
    }

    public static String createSalt(int size) {
        SecureRandom rng = new SecureRandom();
        byte[] buff = new byte[size];
        rng.nextBytes(buff);
        return Base64.getEncoder().encodeToString(buff);
    }

    public static String generateSHA256(String input, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = (input + salt).getBytes();
            byte[] hash = digest.digest(bytes);
            return toHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toHex(byte[] ba) {
        StringBuilder hex = new StringBuilder(ba.length * 2);
        for (byte b : ba) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}
