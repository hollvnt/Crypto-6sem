public class BBS {
    public static final int n = 253; // 11 23
    public static final int x = 5;
    public static final int length = 13;

    public static int BBSnext(int prev, int index) {
        int res = (prev * prev) % n;
        System.out.printf("x%d = (%d*%d)mod %d = %d\n", index, prev, prev, n, res);
        return res;
    }
}
