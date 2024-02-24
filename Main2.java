import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i , j ;
        int sum;
        System.out.println("Please enter two numbers :");
        i = scanner.nextInt();
        j = scanner.nextInt();
        System.out.println("Please enter your choice :");
        char func = scanner.next().charAt(0);
        if (func == '+')
        {
            sum = i+j;
        }
        else if (func == '-')
        {
            sum = i-j;
        }
        else if(func == '*')
        {
            sum = i*j;
        }
        else if (func == '/')
        {
            sum = i/j;
        }



    }
}