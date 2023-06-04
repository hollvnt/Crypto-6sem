public class Main {
    public static void main(String[] args) {
        String keyword = "bezpieczenstwo";
        String message = "dziewczyna sroda, z ostatniego biurka";
        String encryptedMessage = encrypt(message, keyword);
        String decryptedMessage = decrypt(encryptedMessage, keyword);

        System.out.println("Original message: " + message);
        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);
    }

    private static String encrypt(String message, String keyword) {
        StringBuilder sb = new StringBuilder();
        int keywordIndex = 0;
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                int keywordChar = Character.toUpperCase(keyword.charAt(keywordIndex % keyword.length())) - 'A';
                int messageChar = Character.toUpperCase(c) - 'A';
                int encryptedChar = (messageChar + keywordChar) % 26;
                sb.append((char) (encryptedChar + 'A'));
                keywordIndex++;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static String decrypt(String message, String keyword) {
        StringBuilder sb = new StringBuilder();
        int keywordIndex = 0;
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                int keywordChar = Character.toUpperCase(keyword.charAt(keywordIndex % keyword.length())) - 'A';
                int encryptedChar = Character.toUpperCase(c) - 'A';
                int decryptedChar = (encryptedChar - keywordChar + 26) % 26;
                sb.append((char) (decryptedChar + 'A'));
                keywordIndex++;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
