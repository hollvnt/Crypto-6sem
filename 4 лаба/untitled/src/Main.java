import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    private static char[][] generateAlphabetSequences(String keyword, int alphabetSize){
        char[][] sequence = new char[keyword.length()][alphabetSize];
        for (int i = 0; i < keyword.length(); i++){
            char c = keyword.charAt(i);
            for (int j = 0; j < alphabetSize; j++){
                sequence[i][j] = (char) ((c + j) % alphabetSize);
            }
        }
        return sequence;
    }

    private static String encrypt(String message, String keyword){
        char[][] sequences = generateAlphabetSequences(keyword, 32);
        StringBuilder sb = new StringBuilder();
        int sequenceIndex = 0;
        for(char c : message.toCharArray()){
            if(Character.isLetter(c)){
                int alphabetIndex = Character.toUpperCase(c) - 'A';
                char encryptedChar = sequences[sequenceIndex][alphabetIndex];
                sb.append(Character.isLowerCase(c) ? Character.toLowerCase(encryptedChar) : encryptedChar);
                sequenceIndex = (sequenceIndex + 1) % sequences.length;
            }
            else sb.append(c);
        }
        return sb.toString();
    }

    private static String decrypt(String message, String keyword){
        char[][] sequences = generateAlphabetSequences(keyword, 32);
        StringBuilder sb = new StringBuilder();
        int sequenceIndex = 0;
        for (char c: message.toCharArray()){
            if(Character.isLetter(c)){
                int alphabetIndex = -1;
                for(int i = 0; i < sequences[sequenceIndex].length; i++){
                    if(sequences[sequenceIndex][i] == Character.toUpperCase(c)){
                        alphabetIndex = i;
                        break;
                    }
                }
                char decryptedChar = (char)('A' + alphabetIndex);
                sb.append(Character.isLowerCase(c) ? Character.toLowerCase(decryptedChar) : decryptedChar);
                sequenceIndex = (sequenceIndex + 1) % sequences.length;
            }else
                sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String line = "Wednesday girl, from the last desk";
        String encryptedLine = encrypt(line, "security ");
        System.out.println("Зашифрованная строка: " + encryptedLine);
        String decryptedLine = decrypt(encryptedLine, "security ");
        System.out.println("Расшифрованная строка: " + decryptedLine);
    }



}
