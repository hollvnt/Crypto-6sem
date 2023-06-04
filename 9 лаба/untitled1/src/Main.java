import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {

    private JTextField textFieldP;
    private JTextField textFieldQ;
    private JTextArea textAreaCipher;
    private JTextField textFieldE;
    private JTextField textFieldN;
    private JTextField textFieldD;
    private JTextArea textAreaDecrypted;

    private static final char[] characters = {'#', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н',
            'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь', 'Ы', 'Ъ', 'Э', 'Ю', 'Я', ' ', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '0'};

    public Main() {
        setTitle("RSA Encryption");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        JLabel labelPrimes = new JLabel("Простые числа:");
        JLabel labelCipher = new JLabel("Шифр:");
        JLabel labelPublicKey = new JLabel("Публичный ключ:");
        JLabel labelPrivateKey = new JLabel("Тайный ключ:");

        textFieldP = new JTextField();
        textFieldQ = new JTextField();
        textAreaCipher = new JTextArea();
        JScrollPane scrollPaneCipher = new JScrollPane(textAreaCipher);
        textFieldE = new JTextField();
        textFieldN = new JTextField();
        textFieldD = new JTextField();
        textAreaDecrypted = new JTextArea();
        JScrollPane scrollPaneDecrypted = new JScrollPane(textAreaDecrypted);

        JButton buttonEncrypt = new JButton("Зашифровать");
        buttonEncrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                encrypt();
            }
        });

        JButton buttonDecrypt = new JButton("Расшифровать");
        buttonDecrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                decrypt();
            }
        });

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(labelPrimes)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(textFieldP)
                        .addComponent(textFieldQ)
                        .addComponent(buttonEncrypt))
                .addComponent(labelCipher)
                .addComponent(scrollPaneCipher)
                .addComponent(labelPublicKey)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(textFieldE)
                        .addComponent(textFieldN))
                .addComponent(labelPrivateKey)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(textFieldD)
                        .addComponent(buttonDecrypt))
                .addComponent(scrollPaneDecrypted)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(labelPrimes)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(textFieldP)
                        .addComponent(textFieldQ)
                        .addComponent(buttonEncrypt))
                .addGap(10)
                .addComponent(labelCipher)
                .addComponent(scrollPaneCipher, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                .addGap(10)
                .addComponent(labelPublicKey)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(textFieldE)
                        .addComponent(textFieldN))
                .addGap(10)
                .addComponent(labelPrivateKey)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(textFieldD)
                        .addComponent(buttonDecrypt))
                .addGap(10)
                .addComponent(scrollPaneDecrypted, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
        );

        getContentPane().add(panel);
    }

    private void encrypt() {
        String pText = textFieldP.getText();
        String qText = textFieldQ.getText();

        if (!pText.isEmpty() && !qText.isEmpty()) {
            long p = Long.parseLong(pText);
            long q = Long.parseLong(qText);

            if (isNumberSimple(p) && isNumberSimple(q)) {
                String inputText = textAreaCipher.getText().toUpperCase();
                long n = p * q;
                long m = (p - 1) * (q - 1);
                long e = calculateE(m);
                long d = calculateD(e, m);

                List<String> result = rsaEncode(inputText, e, n);
                StringBuilder cipherText = new StringBuilder();

                for (String item : result) {
                    cipherText.append(item).append("\n");
                }

                textFieldE.setText(String.valueOf(e));
                textFieldN.setText(String.valueOf(n));
                textFieldD.setText(String.valueOf(d));
                textAreaCipher.setText(cipherText.toString());
            } else {
                JOptionPane.showMessageDialog(this, "p или q - не простые числа!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Введите p и q!");
        }
    }

    private void decrypt() {
        String dText = textFieldD.getText();
        String nText = textFieldN.getText();

        if (!dText.isEmpty() && !nText.isEmpty()) {
            long d = Long.parseLong(dText);
            long n = Long.parseLong(nText);

            List<String> input = new ArrayList<>();
            String[] lines = textAreaCipher.getText().split("\n");
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    input.add(line.trim());
                }
            }

            String result = rsaDecode(input, d, n);
            textAreaDecrypted.setText(result);
        } else {
            JOptionPane.showMessageDialog(this, "Введите секретный ключ!");
        }
    }

    private boolean isNumberSimple(long n) {
        if (n < 2) {
            return false;
        }

        if (n == 2) {
            return true;
        }

        for (long i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    private List<String> rsaEncode(String s, long e, long n) {
        List<String> result = new ArrayList<>();
        BigInteger bi;

        for (int i = 0; i < s.length(); i++) {
            int index = indexOf(characters, s.charAt(i));

            bi = BigInteger.valueOf(index);
            bi = bi.pow((int) e);

            BigInteger n_ = BigInteger.valueOf(n);

            bi = bi.mod(n_);

            result.add(bi.toString());
        }

        return result;
    }

    private String rsaDecode(List<String> input, long d, long n) {
        StringBuilder result = new StringBuilder();
        BigInteger bi;

        for (String item : input) {
            bi = new BigInteger(item);
            bi = bi.pow((int) d);

            BigInteger n_ = BigInteger.valueOf(n);

            bi = bi.mod(n_);

            int index = bi.intValue();

            result.append(characters[index]);
        }

        return result.toString();
    }

    private long calculateE(long m) {
        long e = m - 1;

        for (long i = 2; i <= m; i++) {
            if (m % i == 0 && e % i == 0) {
                e--;
                i = 1;
            }
        }

        return e;
    }

    private long calculateD(long e, long m) {
        long d = 1000;

        while (true) {
            if ((e * d) % m == 1) {
                break;
            } else {
                d++;
            }
        }

        return d;
    }

    private int indexOf(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
