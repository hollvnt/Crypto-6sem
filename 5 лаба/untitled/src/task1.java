import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class task1 {
    private static final char[] characters = {'a','ą','b','c','ć','d','e','ę','f','g','h','i','j','k',
            'l','ł','m','n','ń','o','ó','p','r','s','ś','t','u','w',
            'y','z','ź','ż'};
    private static final int N = characters.length;
    private final int[] colFx1 = new int[N];
    private final int[] colFx2 = new int[N];

    public String encrypt(String msg, int key) {
        for (int i = 0; i < N; i++) {
            final char c = characters[i];
            colFx1[i] = (int) msg.chars().filter(el -> el == c).count();
        }

        StringBuilder result = new StringBuilder();
        String[] msgInArray = new String[(msg.length() / key) + 1];

        for (int i = 0; i < (msg.length() / key) + 1; i++) {
            if (msg.length() - i * key <= key) {
                msgInArray[i] = msg.substring(i * key);
                System.out.println("msgInArray[" + i + "] = " + msgInArray[i]);
                break;
            } else {
                msgInArray[i] = msg.substring(i * key, i * key + key);
                System.out.println("msgInArray[" + i + "] = " + msgInArray[i]);
            }
        }

        for (int i = 0; i < key; i++) {
            for (int k = 0; k < msgInArray.length - 1; k++) {
                if (msgInArray[k].length() <= i) continue;
                result.append(msgInArray[k].charAt(i));
            }
        }
        return result.toString();
    }

    public String decrypt(String msg, int key) {
        StringBuilder result = new StringBuilder();
        String[] msgInArray = new String[(msg.length() / key) + 1];

        for (int i = 0; i < (msg.length() / key) + 1; i++) {
            if (msg.length() - i * key <= key) {
                msgInArray[i] = msg.substring(i * key);
                break;
            } else {
                msgInArray[i] = msg.substring(i * key, i * key + key);
            }
        }

        for (int i = 0; i < key; i++) {
            for (int k = 0; k < msgInArray.length - 1; k++) {
                if (msgInArray[k].length() <= i) continue;
                result.append(msgInArray[k].charAt(i));
            }
        }

        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        task1 marsh = new task1();
        System.setProperty("console.encoding", "UTF-8");
        // encrypt
        int key = 6;
        String msg = "";
        long OldTime = System.nanoTime();
        BufferedReader reader = new BufferedReader(new FileReader("in.txt"));
        String line = reader.readLine();

        while (line != null) {
            msg += line;
            line = reader.readLine();
        }

        msg = msg.replaceAll("\\s+", ""); // убираем все пробелы из строки

        reader.close();

        String resultMarsh = marsh.encrypt(msg, key);

        System.out.println("\noriginal text:  " + msg);
        System.out.println("ecncrypted  task 1: " + resultMarsh);
        System.out.println("decrypyed task 1: " + marsh.decrypt(resultMarsh, key));
        
    }
}


