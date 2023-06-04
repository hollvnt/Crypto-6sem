public class Reflector {

    private String name;
    private String wiring;

    public Reflector(String name, String wiring) {
        this.name = name;
        this.wiring = wiring;
    }

    public char reflect(char c) {
        int index = wiring.indexOf(c);
        if (index % 2 == 0) {
            return wiring.charAt(index + 1);
        } else {
            return wiring.charAt(index - 1);
        }
    }
}


