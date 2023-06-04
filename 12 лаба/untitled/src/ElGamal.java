import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ElGamal {

    public static final char[] CHARACTERS = {'#', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-'};

    public boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public int generatePrimeNumber() {
        Random random = new Random();
        int p;

        do {
            p = random.nextInt(2500 - 2000) + 2000;
        } while (!isPrime(p));

        return p;
    }

    public int calculatePrimitiveRoot(int p) {
        Random random = new Random();
        int g;

        do {
            g = random.nextInt(p - 2) + 2;
        } while (BigInteger.valueOf(g).modPow(BigInteger.valueOf(p - 1), BigInteger.valueOf(p)).intValue() != 1);

        return g;
    }

    public BigInteger calculateY(int g, int x, int p) {
        return BigInteger.valueOf(g).modPow(BigInteger.valueOf(x), BigInteger.valueOf(p));
    }

    public List<BigInteger> encrypt(String message, int g, BigInteger y, int p) {
        List<BigInteger> ciphertext = new ArrayList<>();
        Random random = new Random();
        int k = random.nextInt(p - 2) + 2;

        for (int i = 0; i < message.length(); i++) {
            BigInteger a = BigInteger.valueOf(g).modPow(BigInteger.valueOf(k), BigInteger.valueOf(p));
            BigInteger b = y.modPow(BigInteger.valueOf(k), BigInteger.valueOf(p)).multiply(BigInteger.valueOf((int) message.charAt(i))).mod(BigInteger.valueOf(p));
            ciphertext.add(a);
            ciphertext.add(b);
        }

        return ciphertext;
    }

    public String decrypt(List<BigInteger> ciphertext, int x, int p) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.size(); i += 2) {
            BigInteger a = ciphertext.get(i);
            BigInteger b = ciphertext.get(i + 1);
            BigInteger inverseA = a.modPow(BigInteger.valueOf(p - 1 - x), BigInteger.valueOf(p));
            BigInteger decryptedChar = inverseA.multiply(b).mod(BigInteger.valueOf(p));
            int charIndex = decryptedChar.intValue();
            if (charIndex >= 0 && charIndex < CHARACTERS.length) {
                plaintext.append(CHARACTERS[charIndex]);
            }
        }

        return plaintext.toString();
    }

    public static void main(String[] args) {
        System.out.println("\nЭЦП на основе Гамалы\n");

        ElGamal elGamal = new ElGamal();
        String message = "Yaskovich Mark";

        int p = 2441;
        int g = 313;

        Random random = new Random();
        int x = random.nextInt(p - 2) + 2;
        BigInteger y = elGamal.calculateY(g, x, p);

        System.out.println("p = " + p);
        System.out.println("g = " + g);
        System.out.println("Открытый ключ - это <p, g, y>");
        System.out.println("Закрытый ключ - это <x>");
        System.out.println("Сообщение: " + message);

        List<BigInteger> ciphertext = elGamal.encrypt(message, g, y, p);
        System.out.println("Зашифрованное сообщение: " + ciphertext);

        String decryptedMessage = elGamal.decrypt(ciphertext, x, p);
        System.out.println("Расшифрованное сообщение: " + decryptedMessage);
    }
}
