public class Cesar {
    public static void main(String[] args) {
        String message = "dziewczyna";
        int k = 20; // ключ
        int N = 26; // модуль
        String encryptedMessage = encrypt(message, k, N);
        System.out.println("encrypted message: " + encryptedMessage);
        String decryptedMessage = decrypt(encryptedMessage, k, N);
        System.out.println("decrypted message: " + decryptedMessage);
    }

    public static String encrypt(String message, int k, int N) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (Character.isLetter(c)) {
                int x = c - (Character.isUpperCase(c) ? 'A' : 'a');
                int y = (x + k) % N; // зашифрованное значение
                char encryptedChar = (char) (y + (Character.isUpperCase(c) ? 'A' : 'a')); // преобразование обратно в букву
                sb.append(encryptedChar);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String decrypt(String encryptedMessage, int k, int N) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encryptedMessage.length(); i++) {
            char c = encryptedMessage.charAt(i);
            if (Character.isLetter(c)) {
                int y = c - (Character.isUpperCase(c) ? 'A' : 'a');
                int x = (y - k + N) % N; // расшифрованное значение
                char decryptedChar = (char) (x + (Character.isUpperCase(c) ? 'A' : 'a'));
                sb.append(decryptedChar);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
