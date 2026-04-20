import java.util.Scanner;
public class pyramids {
    static void starpyra(int n){
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n-i;j++){
                System.out.print(" ");
            }
            for(int k=1;k<=2*i-1;k++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
    static void revstarpyra(int n){
        for(int i=n;i>=1;i--){
            for(int j=1;j<=n-i;j++){
                System.out.print(" ");
            }
            for(int k=1;k<=2*i-1;k++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
    static void numberpyra(int n){
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n-i;j++){
                System.out.print(" ");
            }
            for(int k=1;k<=i;k++){
                System.out.print(k);
            }
            for(int p=i-1;p>=1;p--){
                System.out.print(p);
            }
            System.out.println();
        }
    }
    static void revnumberpyra(int n){
        for(int i=n;i>=1;i--){
            for(int j=1;j<=n-i;j++){
                System.out.print(" ");
            }
            for(int k=1;k<=i;k++){
                System.out.print(k);
            }
            for(int p=i-1;p>=1;p--){
                System.out.print(p);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
           System.out.println("enter  num:");
           int n=sc.nextInt();
           while (true) { 
               System.out.println("\n 1.star pyramid");
               System.out.println("2. reverse star pyramid");
               System.out.println("3. Number pyramid");
               System.out.println("4. Reverse Number pyramid");
               System.out.println("5.Exit");

               int choice=sc.nextInt();
               if(choice==6)
                break;
            switch(choice){
                case 1:
                starpyra(n);
                break;
                case 2:
                revstarpyra(n);
                break;
                case 3:
                numberpyra(n);
                break;
                case 4:
                revnumberpyra(n);
                break;
                default:
                System.out.println("Invalid choice");
            }
           }
           sc.close();
        }
    }


