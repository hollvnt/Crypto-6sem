import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSA {
    public static final char[] CHARACTERS = {'#', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-'};

    // Проверка: простое ли число?
    public boolean isTheNumberSimple(long n) {
        if (n < 2) return false;
        if (n == 2) return true;

        for (long i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // Вычисление параметра e
    public int calculate_e(int d, int m) {
        int e = 10;
        while (true) {
            if ((e * d) % m == 1) break;
            else e++;
        }
        return e;
    }

    // Вычисление параметра d. d должно быть взаимно простым с m
    public int calculate_d(int m) {
        int d = m - 1;
        for (int i = 2; i <= m; i++) {
            if ((m % i == 0) && (d % i == 0)) {
                d--;
                i = 1;
            }
        }
        return d;
    }

    // Зашифрование
    public List<String> RSA_Encode(String hash, int e, int n) {
        List<String> result = new ArrayList<>();
        BigInteger bi;

        for (int i = 0; i < hash.length(); i++) {
            int index = new String(CHARACTERS).indexOf(hash.charAt(i));
            bi = new BigInteger(String.valueOf(index));
            bi = bi.pow(e);

            BigInteger n_ = BigInteger.valueOf(n);
            bi = bi.mod(n_);
            result.add(bi.toString());
        }
        return result;
    }

    // Расшифровать
    public String RSA_Decode(List<String> input, int d, int n) {
        try {
            StringBuilder result = new StringBuilder();
            BigInteger bi;

            for (String item : input) {
                bi = new BigInteger(item);
                bi = bi.pow(d);

                BigInteger n_ = BigInteger.valueOf(n);
                bi = bi.mod(n_);

                int index = bi.intValue();
                result.append(CHARACTERS[index]);
            }

            return result.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println("\nЭЦП на основе RSA\n");

        RSA rsa = new RSA();
        String M = "Mark Yaskovich";

        int p = 101;
        int q = 103;

        String hash = String.valueOf(M.hashCode());
        int n = p * q;
        int m = (p - 1) * (q - 1);

        int d = rsa.calculate_d(m);
        int e_ = rsa.calculate_e(d, m);

        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("n = " + n);
        System.out.println("ф(n) = " + m);
        System.out.println("d = " + d);
        System.out.println("e = " + e_);
        System.out.println("M = " + M);
        System.out.println("Хеш = " + hash);

        List<String> sign = rsa.RSA_Encode(hash, e_, n);

        while (true) {
            System.out.println("\nPress Enter to continue...");
            try {
                System.in.read();
                String hash2 = String.valueOf(M.hashCode());

                String result = rsa.RSA_Decode(sign, d, n);
                System.out.println("Хэш эл. подписи = " + result);
                System.out.println("Хэш файла = " + hash2);

                if (result.equals(hash2)) {
                    System.out.println("Файл подлинный. Подпись верна.");
                } else {
                    System.out.println("Внимание! Файл НЕ подлинный!!!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
