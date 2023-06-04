import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
public class RC4Algorithm {
    private int[] S;
    private int[] key;
    private int n;
    private int keyLength;

    public RC4Algorithm(int n, int[] key) {
        this.n = n;
        this.key = key;
        this.keyLength = key.length;
        this.S = new int[n];
    }

    public void initialize() {
        for (int i = 0; i < n; i++) {
            S[i] = i;
        }

        int j = 0;
        for (int i = 0; i < n; i++) {
            j = (j + S[i] + key[i % keyLength]) % n;
            swap(i, j);
        }
    }

    public int[] generatePseudoRandomSequence(int length) {
        int[] sequence = new int[length];
        int i = 0;
        int j = 0;

        for (int k = 0; k < length; k++) {
            i = (i + 1) % n;
            j = (j + S[i]) % n;
            swap(i, j);
            sequence[k] = S[(S[i] + S[j]) % n];
        }

        return sequence;
    }

    public byte[] encrypt(byte[] message) {
        int[] pseudoRandomSequence = generatePseudoRandomSequence(message.length);
        byte[] encryptedMessage = new byte[message.length];

        for (int i = 0; i < message.length; i++) {
            encryptedMessage[i] = (byte) (message[i] ^ pseudoRandomSequence[i]);
        }

        return encryptedMessage;
    }

    public byte[] decrypt(byte[] encryptedMessage) {
        return encrypt(encryptedMessage);
    }

    private void swap(int i, int j) {
        int temp = S[i];
        S[i] = S[j];
        S[j] = temp;
    }

    public String encrypt(String message) {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        int[] pseudoRandomSequence = generatePseudoRandomSequence(messageBytes.length);
        byte[] encryptedMessage = new byte[messageBytes.length];

        for (int i = 0; i < messageBytes.length; i++) {
            encryptedMessage[i] = (byte) (messageBytes[i] ^ pseudoRandomSequence[i]);
        }

        return Hex.encodeHexString(encryptedMessage);
    }

    public String decrypt(String encryptedMessageHex) throws DecoderException {
        byte[] encryptedMessage = Hex.decodeHex(encryptedMessageHex.toCharArray());
        byte[] decryptedMessage = encrypt(encryptedMessage); // RC4 is a symmetric cipher, so decryption is the same as encryption

        return new String(decryptedMessage, StandardCharsets.UTF_8);
    }
}