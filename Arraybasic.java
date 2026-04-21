import java.util.*;
public class Arraybasic {
    static int sum(int arr[],int n){
        int sum=0;
        for(int i=0;i<n;i++){
            sum=sum+arr[i];
        }
        return sum;
    }
    static int max(int arr[],int n){
        int max=arr[0];
        for(int i=0;i<n;i++){
            if(arr[i]>max){
                max=arr[i];
            }
        }
        return max;
    }
    static int min(int arr[],int n){
        int min=arr[0];
        for(int i=0;i<n;i++){
            if(arr[i]<min){
                min=arr[i];
            }
        }
        return min;
    }
    static void count(int arr[],int n){
        int even=0;
        int odd=0;
        for(int i=0;i<n;i++){
            if(arr[i]%2==0){
                even++;
            }
            else{
                odd++;
            }
        }
        System.out.println("even count:"+even);
        System.out.println("odd count:"+odd);
    }
    static void countdup(int arr[],int n){
        int count=0;
        for(int i=0;i<n-1;i++){
            if(arr[i]== arr[i+1]){
                count++;
                System.out.println(" dup num:"+arr[i]);
            }
        }
        System.out.println("duplicate count is:"+count);
    }
    static void secmax(int arr[],int n){
        int max=Integer.MIN_VALUE;
        int secmax=Integer.MIN_VALUE;
        for(int i=0;i<n;i++){
            if(arr[i]>max){
               secmax=max;
               max=arr[i];
            }
        }
        System.out.println("secmax:"+secmax);
    }
    static void reversearray(int arr[],int n){
        System.out.println("reversed array:");
        for(int i=n-1;i>=0;i--){
            
            System.out.print(arr[i]+" ");
        }
    }
    static void movezeroend(int arr[],int n){
        System.out.println("\nMoved zeroes to end:");
        int k=0;
        for(int i=0;i<n;i++){
            if(arr[i] !=0){
                arr[k]=arr[i];
                k++;
            }
        }
        while(k<n){
            arr[k]=0;
            k++;
        }
        for(int i=0;i<n;i++){
            System.out.print(arr[i]+" ");
        }
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int arr[]=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        System.out.println("sum is:"+sum(arr,n));
        System.out.println("max is:"+max(arr,n));
        System.out.println("min is:"+min(arr,n));
        count(arr,n);
        countdup(arr,n);
        secmax(arr,n);
        reversearray(arr,n);
        movezeroend(arr,n);
    }
}

