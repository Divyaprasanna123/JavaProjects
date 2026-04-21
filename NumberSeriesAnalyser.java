package hello;

import java.util.Scanner;

public class NumberSeriesAnalyser{

 public static void main(String[] args) {

  Scanner sc = new Scanner(System.in);

  // from here we will build the code in order to display the array

  System.out.println("Enter size:");
  int n = sc.nextInt();

  System.out.println("Enter array:");
  int arr[] = new int[n];

  for(int i=0;i<n;i++){
   arr[i] = sc.nextInt();
  }

  System.out.println("Series is:");

  for(int i=0;i<n;i++){
   System.out.print(arr[i] + " ");
  }

  // from here we will develop the logic to find the difference between the series

  int diff[] = new int[n-1];

  System.out.println("\nDifferences are:");

  for(int i=0;i<n-1;i++){
   diff[i] = arr[i+1] - arr[i];
   System.out.print(diff[i] + " ");
  }

  // from here the logic is about identifying the arithmetic pattern

  boolean arithmetic = true;

  for(int i=0;i<diff.length-1;i++){
   if(diff[i+1] != diff[i]){
    arithmetic = false;
    break;
   }
  }

  // from here the logic is about whether the series came from multiplication

  boolean multiplied = true;
  int ratio = arr[1] / arr[0];

  for(int i=0;i<n-1;i++){
   if(arr[i+1] / arr[i] != ratio){
    multiplied = false;
    break;
   }
  }

  // from here the logic is about increasing difference

  boolean incrediff = true;

  for(int i=0;i<diff.length-1;i++){
   if(diff[i+1] != diff[i] + 1){
    incrediff = false;
    break;
   }
  }

  // from here the logic is about square pattern

  boolean square = true;

  for(int i=0;i<n;i++){

   int root = (int)Math.sqrt(arr[i]);

   if(root * root != arr[i]){
    square = false;
    break;
   }
  }

  // from here the logic is about fibonacci pattern

  boolean fibonacci = true;

  for(int i=2;i<n;i++){

   if(arr[i] != arr[i-1] + arr[i-2]){
    fibonacci = false;
    break;
   }
  }

  // from here we identify which pattern is correct

  int next = 0;

  if(fibonacci){

   System.out.println("\nPattern: Fibonacci Series");
   next = arr[n-1] + arr[n-2];

  }

  else if(square){

   System.out.println("\nPattern: Square Series");
   int root = (int)Math.sqrt(arr[n-1]);
   next = (root+1)*(root+1);

  }

  else if(multiplied){

   System.out.println("\nPattern: Multiplication Series");
   next = arr[n-1] * ratio;

  }

  else if(arithmetic){

   System.out.println("\nPattern: Arithmetic Series");
   next = arr[n-1] + diff[0];

  }

  else if(incrediff){

   System.out.println("\nPattern: Increasing Difference Series");
   int nextdiff = diff[diff.length-1] + 1;
   next = arr[n-1] + nextdiff;

  }

  else{

   System.out.println("\nPattern not detected");
  }

  // from here we predict the next number

  System.out.println("Next number is: " + next);

  // from here we predict next 3 numbers

  System.out.println("Next 3 numbers are:");

  if(square){

   int root = (int)Math.sqrt(arr[n-1]);

   for(int i=1;i<=3;i++){

    int value = (root+i)*(root+i);

    System.out.print(value + " ");
   }

  }

  else if(fibonacci){

   int a = arr[n-2];
   int b = arr[n-1];

   for(int i=0;i<3;i++){

    int c = a + b;
    System.out.print(c + " ");

    a = b;
    b = c;
   }

  }

  else{

   int temp = next;

   for(int i=0;i<3;i++){

    System.out.print(temp + " ");

    if(arithmetic){
     temp = temp + diff[0];
    }

    else if(multiplied){
     temp = temp * ratio;
    }

    else if(incrediff){
     int d = diff[diff.length-1] + i + 1;
     temp = arr[n-1] + d;
    }

   }

  }

  // from here we create visualization

  System.out.println("\n\nSeries Visualization:");

  for(int i=0;i<n;i++){

   System.out.print(arr[i] + " ");

   for(int j=0;j<arr[i];j++){
    System.out.print("*");
   }

   System.out.println();
  }

 }

}