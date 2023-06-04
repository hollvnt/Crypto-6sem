class Elliptic {
    public static int a_1(int a, int n) {
        int res = 0;
        for (int i = 0; i < 10000; i++) {
            if (((a * i) % n) == 1) return (i);
        }
        return (res);
    }

    public static void main(String[] args) {
        Point.drawFunction();
        System.out.println();

        int xx;
        double yy;
        for (xx = 716; xx < 750; xx++) {
            yy = Math.pow(xx, 3) - xx + 1;
            yy = yy % 751;
            System.out.print("(" + xx + "," + yy + ");\n     ");
        }

        Point P = new Point(67, 84);
        Point Q = new Point(69, 241);
        Point R = new Point(66, 199);
        int k = 11, l = 5;

        Point p1 = Point.multiply(P, k);
        Point p2 = Point.add(P, Q);
        Point p3 = Point.add(Point.add(p1, Point.multiply(Q, l)), Point.negate(R));
        Point p4 = Point.add(Point.add(P, Point.negate(Q)), R);

        System.out.println("\n\nЗадание 1:");
        System.out.println("kP          (" + p1.x + ", " + p1.y + ")");
        System.out.println("P+Q         (" + p2.x + ", " + p2.y + ")");
        System.out.println("kP+lQ-R     (" + p3.x + ", " + p3.y + ")");
        System.out.println("P-Q+R       (" + p4.x + ", " + p4.y + ")\n\n\n");

        Point m1 = new Point(227, 299); //Ю
        Point m2 = new Point(200, 721); //Л
        Point m3 = new Point(227, 452); //Я
        Point[] M = { m1, m2, m3 };
        Point[] C = new Point[6];
        Point[] M2 = new Point[3];

        Point G = new Point(0, 1);
        int d = 50;
        int j = 0;
        Point Q_ = Point.multiply(G, d);

        System.out.println("\nЗадание 2:");
        System.out.println("Тайный ключ: " + d);
        System.out.println("Открытый ключ: (" + Q_.x + ", " + Q_.y + ")");

        //Зашифрование
        for (int i = 0; i < 3; i++) {
            C[j] = Point.multiply(G, k);
            j++;
            C[j] = Point.add(M[i], Point.multiply(Q_, k));
            j++;
        }

        System.out.println("Открытый текст: (" + M[0].x + ", " + M[0].y + "), (" + M[1].x + ", " + M[1].y + "), (" + M[2].x + ", " + M[2].y + ")");
        System.out.println("Шифротекст:     (" + C[0].x + ", " + C[0].y + "), (" + C[1].x + ", " + C[1].y + "), (" + C[2].x + ", " + C[2].y + "), \n" +
                "(" + C[3].x + ", " + C[3].y + "), (" + C[4].x + ", " + C[4].y + "), (" + C[5].x + ", " + C[5].y + ")");

        //Расшифрование
        M2[0] = Point.add(C[1], Point.multiply(Point.negate(C[0]), d));
        M2[1] = Point.add(C[3], Point.multiply(Point.negate(C[2]), d));
        M2[2] = Point.add(C[5], Point.multiply(Point.negate(C[4]), d));

        System.out.println("Расшифр текст: (" + M2[0].x + ", " + M2[0].y + "), (" + M2[1].x + ", " + M2[1].y + "), (" + M2[2].x + ", " + M2[2].y + ")\n\n\n");

        Point G_ = new Point(416, 55);
        int q = 13;
        Point Q_sign = Point.multiply(G_, d);
        int H = 215 % 13; //Ч
        Point kG = Point.multiply(G_, k);

        //Генерация ЭЦП
        int r = (int) kG.x % q;
        if (r == 0) System.out.println("Замените параметр k");

        int t = a_1(k, q);

        int s = (H * t + d * r) % q;
        if (s == 0) System.out.println("Замените параметр k");

        System.out.println("Задание 3:");
        System.out.println("Открытый ключ Q: (" + Q_sign.x + ", " + Q_sign.y + ")");
        System.out.println("Точка kG: (" + kG.x + ", " + kG.y + ")");
        System.out.println("Хеш: " + H);
        System.out.println("Отсылаем стороне B (r,s) = (" + r + "," + s + ")");

        //Верификация ЭЦП
        if (r < 1 || s > q) System.out.println("Легитимность не подтверждена!");
        int w = a_1(s, q);
        int u1 = (w * H) % q;
        int u2 = (w * r) % q;

        Point ver = Point.add(Point.multiply(G_, u1), Point.multiply(Q_sign, u2));
        int v = (int) ver.x % q;
        if (v != r) System.out.println("Легитимность подтверждена!");
    }
}