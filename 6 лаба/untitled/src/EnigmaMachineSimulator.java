import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EnigmaMachineSimulator extends JFrame implements ActionListener {

    private JTextField inputField;
    private JTextField outputField;
    private JButton encryptButton;
    private JButton resetButton;

    private Rotor leftRotor;
    private Rotor middleRotor;
    private Rotor rightRotor;
    private Reflector reflector;

    public EnigmaMachineSimulator() {
        // Создание роторов и отражателя
        leftRotor = new Rotor("L VII", 1, "ESOVPZJAYQUIRHXLNFTGKDCMWB", 0);
        middleRotor = new Rotor("M Gamma", 2, "AJDKSIRUXBLHWTMCQGZNPYFVOE", 0);
        rightRotor = new Rotor("R II", 2, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 0);
        reflector = new Reflector("B Dunn", "YRUHQSLDPXNGOKMIEBFZCWVJAT");

        // Создание компонентов пользовательского интерфейса
        inputField = new JTextField(20);
        outputField = new JTextField(20);
        outputField.setEditable(false);
        encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(this);
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);

        // Создание панели для размещения компонентов
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("Input:"), c);
        c.gridx = 1;
        panel.add(inputField, c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Output:"), c);
        c.gridx = 1;
        panel.add(outputField, c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(encryptButton, c);
        c.gridy = 3;
        panel.add(resetButton, c);

        // Размещение панели на главной
        getContentPane().add(panel, BorderLayout.CENTER);

        // Настройка параметров главного окна
        setTitle("Enigma Machine Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == encryptButton) {
            String input = inputField.getText();
            String output = encrypt(input);
            outputField.setText(output);
        } else if (e.getSource() == resetButton) {
            inputField.setText("");
            outputField.setText("");
        }
    }

    private String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            if (Character.isLetter(c)) {
                // Вращение роторов перед шифрованием
                if (rightRotor.getStep() == rightRotor.getNotch() ||
                        middleRotor.getStep() == middleRotor.getNotch()) {
                    middleRotor.rotate();
                    leftRotor.rotate();
                }
                rightRotor.rotate();

                int input = c - 'A';
                int output = rightRotor.encode(input);
                output = middleRotor.encode(output);
                output = leftRotor.encode(output);
                output = reflector.reflect(output);
                output = leftRotor.encode(output, true);
                output = middleRotor.encode(output, true);
                output = rightRotor.encode(output, true);

                result.append((char) (output + 'A'));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private static class Rotor {
        private String name;
        private int notch;
        private String wiring;
        private int step;
        private int ring;

        public Rotor(String name, int notch, String wiring, int ring) {
            this.name = name;
            this.notch = notch;
            this.wiring = wiring.toUpperCase();
            this.step = 0;
            this.ring = ring;
        }

        public int getNotch() {
            return notch;
        }

        public int getStep() {
            return step;
        }

        public void rotate() {
            step = (step + 1) % 26;
        }

        public int encode(int input) {
            int offset = (step - ring + 26) % 26;
            int output = (input + offset) % 26;
            output = wiring.charAt(output) - 'A';
            output = (output - offset + 26) % 26;
            return output;
        }

        public int encode(int input, boolean reverse) {
            int offset = (step - ring + 26) % 26;
            int output = (input + offset) % 26;
            int index = wiring.indexOf((char) (output + 'A'));
            output = (index - offset + 26) % 26;
            return output;
        }
    }

    private static class Reflector {
        private String name;
        private String wiring;

        public Reflector(String name, String wiring) {
            this.name = name;
            this.wiring = wiring.toUpperCase();
        }

        public int reflect(int input) {
            return wiring.charAt(input) - 'A';
        }
    }

    public static void main(String[] args) {
        new EnigmaMachineSimulator();
    }
}