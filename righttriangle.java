import java.util.Scanner;
public class righttriangle{
  static void numberrighttri(int n){
    for(int i=1;i<=n;i++){
        for(int j=1;j<=i;j++){
            System.out.print(j+" ");
        }
        System.out.println();
    }
  }
  static void reversenumbertri(int n){
    for(int i=n;i>=1;i--){
        for(int j=1;j<=i;j++){
            System.out.print(j+" ");
        }
        System.out.println();
    }
  }
  static void starrighttri(int n){
    for(int i=1;i<=n;i++){
        for(int j=1;j<=i;j++){
            System.out.print("*");
        }
        System.out.println();
    }
  }
  static void revstarrighttri(int n){
    for(int i=n;i>=1;i--){
        for(int j=1;j<=i;j++){
            System.out.print("*");
        }
        System.out.println();
    }
  }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enetr the n value:");
        int n=sc.nextInt();
        while (true) { 
            System.out.println("\n 1.Right Triangle with Number");
            System.out.println("2.Rev Right Triangle with Number");
             System.out.println("3.Right Triangle with Star");
              System.out.println("4.Rev Right Triangle with Star");
               System.out.println("5.Exit");

               int choice=sc.nextInt();
               if(choice==5) 
                break;
            switch(choice){
                case 1:
                numberrighttri(n);
                break;
                case 2:
                reversenumbertri(n);
                break;
                case 3:
                starrighttri(n);
                break;
                case 4:
                revstarrighttri(n);
                break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        sc.close();
    }
    }

