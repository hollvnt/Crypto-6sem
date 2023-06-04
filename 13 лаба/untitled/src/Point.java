class Point {
    static int p = 751;
    public static int a = -1;
    public static int b = 1;
    public long x;
    public long y;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    static public void drawFunction() {
        boolean flag = true;
        for (int i = 4; i < 10; i++) {
            int y = (int) Math.sqrt((Math.pow(i, 3) + (a * i) + b) % p);
            System.out.print("*");
            flag = !flag;
        }
    }

    public static long getLambda(Point p1, Point p2) {
        long lambda;

        if (p1 == p2) {
            long a = 0;
            long b = 1;
            long g = gcd(2 * p1.y, Point.p, a, b);

            lambda = ((3 * p1.x * p1.x + Point.a) * a);
        } else {
            long a = 0;
            long b = 1;
            long dx = p2.x - p1.x;
            dx = (dx % Point.p + Point.p) % Point.p;
            long dy = p2.y - p1.y;
            dy = (dy % Point.p + Point.p) % Point.p;
            long g = gcd(dx, Point.p, a, b);

            lambda = (dy * a);
        }
        lambda = (lambda % Point.p + Point.p) % Point.p;

        return lambda;
    }

    static long gcd(long a, long b, long x, long y) {
        if (a == 0) {
            x = 0;
            y = 1;
            return b;
        }
        long x1 = 0, y1 = 0;
        long d = gcd(b % a, a, x1, y1);
        x = y1 - (b / a) * x1;
        y = x1;
        return d;
    }

    public static Point add(Point p1, Point p2) {
        long x;
        long y;
        long lambda = Point.getLambda(p1, p2);

        if (p1.x == p2.x && p2.y == -(p1.y)) {
            return new Point(0, 0);
        }

        x = lambda * lambda - p1.x - p2.x;
        x = (x % Point.p + Point.p) % Point.p;
        y = lambda * (p1.x - x) - p1.y;
        y = (y % Point.p + Point.p) % Point.p;

        return new Point(x, y);
    }

    public static Point multiply(Point p1, int multiplier) {
        Point tmp = p1;

        for (int i = 1; i < multiplier; ++i) {
            tmp = add(tmp, p1);
        }

        return new Point(tmp.x, tmp.y);
    }

    public static Point negate(Point a) {
        return multiply(a, Point.p - 1);
    }

    public static boolean equals(Point p1, Point p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }

    public static boolean notEquals(Point p1, Point p2) {
        return p1.x != p2.x && p1.y != p2.y;
    }

    public static int findOrder(Point G) {
        Point tmp;

        for (int i = 1; i < 70000; i++) {
            tmp = G;
            tmp = multiply(tmp, i);

            if (equals(tmp, new Point(0, 0)))
                return i;
        }

        return -1;
    }
}
