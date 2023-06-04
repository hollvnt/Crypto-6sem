import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class task2 {
    static char[] characters = new char[]{'a', 'ą', 'b', 'c', 'ć', 'd', 'e', 'ę', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'ł', 'm', 'n', 'ń', 'o', 'ó', 'p', 'r', 's', 'ś', 't', 'u', 'w',
            'y', 'z', 'ź', 'ż'};

    public static String Encrypt(String msg, int[] key1, int[] key2) {
        String result = "";
        String[] msgInArray = new String[(msg.length() / key1.length) + 1];

        for (int i = 0; i < (msg.length() / key1.length) + 1; i++) {
            if (msg.length() - i * key1.length <= key1.length) {
                msgInArray[i] = msg.substring(i * key1.length);
                System.out.println("msgInArray[" + i + "] = " + msgInArray[i]);
                break;
            } else {
                msgInArray[i] = msg.substring(i * key1.length, i * key1.length + key1.length);
                System.out.println("msgInArray[" + i + "] = " + msgInArray[i]);
            }
        }
        char[][] res = new char[key1.length][key2.length];

        for (int i = 0; i < key1.length; i++) {
            for (int k = 0; k < key2.length; k++) {
                if (msgInArray.length > k) {
                    res[i][key2[k]] = msgInArray[k].charAt(i);
                }
            }
        }

        for (int i = 0; i < key1.length; i++) {
            for (int k = 0; k < key2.length; k++) {
                result += res[i][k];
            }
        }
        result = result.replace("\0", "");
        return result;
    }

    public static String Decrypt(String msg, int[] key1, int[] key2) {
        String result = "";
        String[] msgInArray = new String[(msg.length() / key1.length) + 1];

        for (int i = 0; i < (msg.length() / key1.length) + 1; i++) {
            if (msg.length() - i * key1.length <= key1.length) {
                msgInArray[i] = msg.substring(i * key1.length);
                break;
            } else {
                msgInArray[i] = msg.substring(i * key1.length, i * key1.length + key1.length);
            }
        }
        char[][] res = new char[key1.length][key2.length];

        for (int i = 0; i < key1.length; i++) {
            for (int k = 0; k < key2.length; k++) {
                if (msgInArray.length > key2[k]) {
                    res[i][k] = msgInArray[key2[k]].charAt(key1[i]);
                }
            }
        }

        for (int i = 0; i < key1.length; i++) {
            for (int k = 0; k < key2.length; k++) {
                result += res[i][k];
            }
        }
        result = result.replace("\0", "");
        return result;
    }

    public static void main(String[] args) throws IOException {
        task2 mnozh = new task2();
        int[] key1 = {2, 1, 0, 3};
        int[] key2 = {7, 2, 5, 6, 8, 3, 4, 1, 0};
        String msg = "";
        BufferedReader reader = new BufferedReader(new FileReader("in.txt"));
        String line = reader.readLine();

        while (line != null) {
            msg += line;
            line = reader.readLine();
        }

        msg = msg.replaceAll("\\s+", ""); // убираем все пробелы из строки


        String result_mnozh = mnozh.Encrypt(msg, key1, key2);

        System.out.println("oroginal text:  " + msg);
        System.out.println("encrypted task 2:   " + result_mnozh);
        System.out.println("decrypted task 2: " + mnozh.Decrypt(result_mnozh, key2, key1));
    }
}