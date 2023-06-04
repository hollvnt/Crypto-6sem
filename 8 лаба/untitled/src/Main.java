import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] seq = new int[BBS.length];

        System.out.println("n = " + BBS.n);
        System.out.println("x = " + BBS.x + "\n");
        int buf = BBS.x;

        long oldTicks = System.currentTimeMillis();
        for (int i = 0; i < BBS.length; i++) {
            buf = BBS.BBSnext(buf, i);
            seq[i] = buf;
        }
        System.out.print("\nPSP = ");
        for (int item : seq) {
            System.out.print(item + "; ");
        }

        System.out.println("\n\n\n ----------- R C 4 ---------------\n");


        int n = 256;
        int[] key = {1, 11, 21, 31, 41, 51};

        RC4Algorithm rc4 = new RC4Algorithm(n, key);
        rc4.initialize();

        String message = "Hello, world!";
        byte[] encryptedMessage = rc4.encrypt(message.getBytes());
        System.out.println("ecncrypyed: " + Arrays.toString(encryptedMessage));

        byte[] decryptedMessage = rc4.decrypt(encryptedMessage);
        System.out.println("decrypted: " + new String(decryptedMessage));
    }
}
