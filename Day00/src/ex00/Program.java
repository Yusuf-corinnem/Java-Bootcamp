public class Program {
    public static void main(String[] args) {
        int sum = 0, temp = 0, number = 479598;
        for (int i = 0; i < 6; ++i) {
            temp = number % 10;
            sum += temp;
            number = number / 10;
        }
        System.out.println(sum);
    }
}
