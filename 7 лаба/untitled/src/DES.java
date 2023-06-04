import javax.crypto.*;
import javax.crypto.spec.*;

public class DES {

    public static void main(String[] args) {
        try {

            String yaskmark = "yaskovichmarkedu";
            byte[] yaskmarkBytes = hexStringToByteArray(yaskmark);
            SecretKey key = new SecretKeySpec(yaskmarkBytes, "DES");

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            Cipher decipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            decipher.init(Cipher.DECRYPT_MODE, key);


            String message = "Mark";
            byte[] messageBytes = message.getBytes("UTF-8");
            byte[] encryptedBytes = cipher.doFinal(messageBytes);

            byte[] decryptedBytes = decipher.doFinal(encryptedBytes);
            String decryptedMessage = new String(decryptedBytes, "UTF-8");

            System.out.println("Encrypt: " + byteArrayToHexString(encryptedBytes));
            System.out.println("Decrypt: " + decryptedMessage);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] result = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            result[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return result;
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
