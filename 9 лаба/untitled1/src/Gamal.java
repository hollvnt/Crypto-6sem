import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gamal {

    private static int g_main;
    private static BigInteger a;

    /* Definition number <g> */
    public static boolean search_g(int p, int g) {
        boolean bool = false;
        List<BigInteger> array_mod_number = new ArrayList<>();

        BigInteger integer = BigInteger.valueOf(g).pow(1).mod(BigInteger.valueOf(p));
        array_mod_number.add(integer);

        for (int i = 2; i != p; i++) {
            integer = BigInteger.valueOf(g).pow(i).mod(BigInteger.valueOf(p));
            for (int j = 0; j != i - 1; j++) {
                if (array_mod_number.get(j).equals(integer)) {
                    g--;
                    array_mod_number.clear();
                    i = 1;
                    integer = BigInteger.valueOf(g).pow(1).mod(BigInteger.valueOf(p));
                    array_mod_number.add(integer);
                    break;
                }

                if ((j == i - 2) && (!array_mod_number.get(j).equals(integer))) {
                    array_mod_number.add(integer);
                }
            }
        }
        g_main = g;
        bool = true;
        return bool;
    }


    /* Definition simple number <p> */
    public static int search_p() {
        Random random = new Random();
        int p = 0;
        boolean bool = false;
        do {
            p = random.nextInt(2500 - 2000) + 2000;

            for (int i = 2; i != p; i++) {
                if (i == p - 1) {
                    bool = search_g(p, p - 1);
                    break;
                }
                if (p % i == 0) break;
            }
        } while (!bool);
        return p;
    }


    /* Cipher */
    public static List<BigInteger> cipher(String text, int p, BigInteger y) {
        List<BigInteger> array = new ArrayList<>();
        Random random = new Random();
        int k = random.nextInt(p - 1) + 1;

        for (int i = 0; i != text.length(); i++) {
            a = BigInteger.valueOf(g_main).pow(k).mod(BigInteger.valueOf(p));
            array.add((y.pow(k).multiply(BigInteger.valueOf((int) text.charAt(i)))).mod(BigInteger.valueOf(p)));
        }
        return array;
    }


    /* Decryption */
    public static String cipher_raz(int length_text, List<BigInteger> array_number, int x, int p) {
        StringBuilder save_text = new StringBuilder();
        BigInteger integer;

        for (int i = 0; i != length_text; i++) {
            integer = (array_number.get(i).multiply(a.pow(p - 1 - x))).mod(BigInteger.valueOf(p));
            save_text.append((char) integer.intValue());
        }
        return save_text.toString();
    }


    public static void main(String[] args) {
        int p = 0;
        Random random = new Random();

        p = search_p();

        int x = random.nextInt(p - 1) + 1; // Generate private key
        BigInteger y = BigInteger.valueOf(g_main).pow(x).mod(BigInteger.valueOf(p)); // Find public key

        System.out.printf("Числа [%d, %d, %d] ---> [p, g, y]%n", p, g_main, y);
        System.out.println("Открытый ключ - это <y>");
        System.out.printf("Закрытый ключ: %d%n", x);

        while (true) {
            System.out.print("Введите текст: ");
            String text = System.console().readLine();
            System.out.println();

            // Encryption
            long oldTicks = System.currentTimeMillis();
            List<BigInteger> array_cipher_text = cipher(text, p, y);
            System.out.println("Зашифрованное сообщение: ");
            for (int i = 0; i != text.length(); i++) {
                System.out.printf("%d:[%d, %d]%n", i, a, array_cipher_text.get(i));
            }
            System.out.printf("Время: %d мс%n%n", System.currentTimeMillis() - oldTicks);

            // Decryption
            oldTicks = System.currentTimeMillis();
            System.out.printf("Расшифрованное сообщение: %s%n", cipher_raz(text.length(), array_cipher_text, x, p));
            System.out.printf("Время: %d мс%n%n", System.currentTimeMillis() - oldTicks);
        }
    }
}
