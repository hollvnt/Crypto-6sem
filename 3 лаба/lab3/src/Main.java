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
        System.out.println("����� ������� ����� �� ���������: ");
        Scanner sc = new Scanner(System.in);
        for( int j = 0; j < 2; j++){
            int count = 0;
            System.out.println("������� ������ ��������� ");
            int start = sc.nextInt();
            int end = sc.nextInt();
            System.out.println("������� ����� �� ��������� " + start + " - " + end);
            for(int i = start; i < end; i++){
                if(SipmleNumber(i)){
                    count ++;
                    System.out.println(i);
                }
            }
            System.out.println("���������� ������� �����: " + count);
            System.out.println("");
        }
        System.out.println("��� �������� ����� ������ �����? 1 - 2, 2 - 3, 3 - �����");
        int countGCD = sc.nextInt();
        do{
            switch (countGCD) {
                case 1:
                    System.out.println("������� 2 �����");
                    int a = sc.nextInt(), b = sc.nextInt();
                    System.out.println("��� 2 ����� �����: " + NOD(a, b));
                    break;
                case 2:
                    System.out.println("������� 3 �����");
                    int c = sc.nextInt(), d = sc.nextInt(), f = sc.nextInt();
                    System.out.println("��� 3 ����� �����: " + NOD(c, d, f));
                    break;
                case 3:
                    return;
                default:
                    System.out.println("����������� ���������� �����");
            }
        }while (countGCD != 0);
    }
}
