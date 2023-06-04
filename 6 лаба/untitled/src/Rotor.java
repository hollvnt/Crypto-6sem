public class Rotor {
    private String name;
    private int position;
    private int notch;
    private String wiring;
    private int ringSetting;

    public Rotor(String name, int notch, String wiring, int ringSetting) {
        this.name = name;
        this.position = 0;
        this.wiring = wiring;
        this.notch = notch;
        this.ringSetting = ringSetting;
    }

    // Геттер для поля notch
    public int getNotch() {
        return notch;
    }
    public void turnover() {
        // Сдвиг позиции ротора
        position = (position + 1) % 26;
    }


    // Передача символа через ротор в прямом направлении
    public int forward(int input) {
        // Сдвиг входного символа на позицию кольца ротора
        input = (input + ringSetting) % 26;

        // Сдвиг на текущую позицию ротора
        input = (input + position) % 26;

        // Получение символа на выходе ротора
        char output = wiring.charAt(input);

        // Сдвиг обратно на текущую позицию ротора
        input = (wiring.indexOf(output) - position + 26) % 26;

        // Сдвиг обратно на позицию кольца ротора
        input = (input - ringSetting + 26) % 26;

        // Сдвиг позиции ротора
        position = (position + 1) % 26;

        // Проверка, произошло ли срабатывание шлица
        if (position == notch) {
            return -1;
        }

        return input;
    }

    public int backward(int output) {
        // Сдвиг символа на текущую позицию ротора
        output = (output + position) % 26;

        // Получение символа на входе ротора
        char input = wiring.charAt(output);

        // Сдвиг обратно на текущую позицию ротора
        output = (wiring.indexOf(input) - position + 26) % 26;

        // Сдвиг обратно на позицию кольца ротора
        output = (output - ringSetting + 26) % 26;

        // Сдвиг позиции ротора
        position = (position + 1) % 26;

        // Проверка, произошло ли срабатывание шлица
        if (position == notch) {
            return -1;
        }

        return output;
    }


    // Передача символа через ротор в обратном направлении
    public int reverse(int input) {
        // Сдвиг входного символа на позицию кольца ротора
        input = (input + ringSetting) % 26;

        // Сдвиг на текущую позицию ротора
        input = (input + position) % 26;

        // Получение символа на выходе ротора
        char output = wiring.charAt(input);

        // Сдвиг обратно на текущую позицию ротора
        input = (wiring.indexOf(output) - position + 26) % 26;

        // Сдвиг обратно на позицию кольца ротора
        input = (input - ringSetting + 26) % 26;

        // Сдвиг позиции ротора
        position = (position + 1) % 26;

        // Проверка, произошло ли срабатывание шлица
        if (position == notch) {
            return -1;
        }

        return input;
    }

    // Сброс позиции ротора в начальное значение
    public void reset() {
        position = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}