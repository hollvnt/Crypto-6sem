public class Main {
    public static final int z = 8;
    public static final int a = 31;
    public static final int n = 420;
    public static final int[] d = {2, 3, 6, 13, 27, 52, 105, 210};
    public static String M = "Mark";

    public static void main(String[] args) {

        Ranec r = new Ranec();
        int[] d2 = r.generate(8);
        System.out.println("Закрытый ключ d: " + r.str(d));


        int[] e = r.getNorm(d, a, n, z);
        System.out.println("Открытый ключ e: " + r.str(e) + "\n");


        long oldTicks = System.currentTimeMillis();
        int[] C = r.getCipher(e, M, z);
        System.out.println("\nЗашифров сооб C: " + r.str(C));
        System.out.println("Время: " + (System.currentTimeMillis() - oldTicks) + " мс\n");


        int a_1 = r.a_1(a, n);

        int[] S = new int[C.length];
        String M2 = "";

        for (int i = 0; i < C.length; i++) {
            S[i] = (C[i] * a_1) % n;
        }
        System.out.println("Вектор весов S: " + r.str(S) + "      a^(-1) = " + a_1);

        oldTicks = System.currentTimeMillis();
        for (int Si : S) {
            String M2i = r.decipher(d, Si, z); // 110000
            M2 += M2i + " ";
        }
        System.out.println("Расшифр сообщ  : " + M2);
        System.out.println("Время: " + (System.currentTimeMillis() - oldTicks) + " мс\n");

        M2 = M2.replace(" ", "");
        byte[] stringArray = new byte[M2.length() / 8];
        for (int i = 0; i < M2.length(); i += 8) {
            String byteString = M2.substring(i, i + 8);
            stringArray[i / 8] = Byte.parseByte(byteString, 2);
        }
        String str = new String(stringArray);
        System.out.println(str);
    }
}