import java.util.Scanner;

public class Main {

    public static boolean SipmleNumber(int n){
        boolean result = true;
        if(n > 1) {
            for (int i = 2; i <= Math.sqrt(n); i++){
                if(n % i == 0){
                    result = false;
                    break;
                }

            }

        }else {
            result = false;
        }
        return result;
    }

    public static int NOD(int a, int b){
        while (b !=0 ){
            int tmp = b;
            b = a % b;
            a = tmp;
        }
        return a;
    }

    public static int NOD(int a, int b, int c){
        return NOD(a, NOD(b, c));
    }
    public static void main(String[] args) {
        System.out.println("Поиск простых чисел на интервале: ");
        Scanner sc = new Scanner(System.in);
        for( int j = 0; j < 2; j++){
            int count = 0;
            System.out.println("Введите начало интервала ");
            int start = sc.nextInt();
            int end = sc.nextInt();
            System.out.println("Простые числа на интервале " + start + " - " + end);
            for(int i = start; i < end; i++){
                if(SipmleNumber(i)){
                    count ++;
                    System.out.println(i);
                }
            }
            System.out.println("Количество простых чисел: " + count);
            System.out.println("");
        }
        System.out.println("НОД скольких чисел хотите найти? 1 - 2, 2 - 3, 3 - выход");
        int countGCD = sc.nextInt();
        do{
            switch (countGCD) {
                case 1:
                    System.out.println("Ввидите 2 числа");
                    int a = sc.nextInt(), b = sc.nextInt();
                    System.out.println("НОД 2 чисел равен: " + NOD(a, b));
                    break;
                case 2:
                    System.out.println("Введите 3 числа");
                    int c = sc.nextInt(), d = sc.nextInt(), f = sc.nextInt();
                    System.out.println("НОД 3 чисел равен: " + NOD(c, d, f));
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Некоректное количество чисел");
            }
        }while (countGCD != 0);
    }
}
