//Anthony Mace CSC205AB 9/6/2013
//This program is designed to retrieve data
//from a data file and display data from that file
//according to the choice the user chooses from the
//program menu

import java.util.Scanner;
import java.io.*;

public class program3 {
   
   public static Scanner console = new Scanner(System.in);

   //This is the main method that will declare any
   //exceptions and call the other methods necessary
   //for the program to run. It will also declare
   //Scanner/File objects.
   public static void main(String[] args) {
      int b = 0;
      printIntro();
      Elements[] periodicTable = new Elements[240];
      int recs = createArray(periodicTable);
      do {
          b = menu();
          if (b == 1) {
              displayFile(periodicTable, recs);
          } else if (b == 2) {
              displaySelectRecord(periodicTable, recs);
          } else if (b == 3) {
              displayCumResult(periodicTable, recs);
          } else if (b == 4) {
              displayHistogram(periodicTable, recs);
          }
      } while (b != 5);
      quitProg();
   }
   
   //This method will simply print an intro to the user
   //of what the program does.
   public static void printIntro() {
      System.out.println("This program allows you to search through the data");
      System.out.println("of the specified file and view certain aspects");
      System.out.println("of the data the file contains.");
      System.out.println();
   }
   
   //This method will print the menu options of the program
   //and allow the user to pick which option they would like to
   //run.
   public static int menu() {
      System.out.println("Choose a task number from the following:");
      System.out.println("       1 - Display entire data file");
      System.out.println("       2 - Display specific record in the file");
      System.out.println("       3 - Display cumulative value from select " +
                                 "records in data file");
      System.out.println("       4 - View a chart that displays number of " +
                                 "times data in a selected\n" + 
                                 "           field occurs in each of ten ranges");
      System.out.println("       5 - Quit the program");
      System.out.print("Which would you like to perform: ");
      int a = console.nextInt();
      System.out.println();
      while (a < 1 || a > 5) {
         System.out.print("Enter a number between 1 and 5: ");
         a = console.nextInt();
         System.out.println();
      }
      return a;
   }
   
   //This method goes through the file, fills an array with the records
   //from the file and counts the records in the file. The method then returns
   //ONLY the count of the records
   public static int createArray(Elements[] periodicTable) {
      int elems = 0;
      try {
         Scanner fromFile = new Scanner(new File("periodictable.txt"));
         do {
            periodicTable[elems] = new Elements();
            periodicTable[elems].elementName = fromFile.next();
            periodicTable[elems].elementSym = fromFile.next();
            periodicTable[elems].elementNum = fromFile.nextInt();
            periodicTable[elems].elementMass = fromFile.nextDouble();
            elems++;
         } while (!(periodicTable[elems - 1].elementName.equals("EOF")));
         elems--;
      } catch (IOException ioe) {
         System.out.println("File access error!");
         elems = 0;
      }
      return elems;
   }

   //This method displays all the records in the array (loaded from
   //the file) 15 lines at a time
   public static void displayFile(Elements[] periodicTable, int recs) {
      char cont = 'y';
      int count = 0;
      String elem = "Element";
      String sym = "Symbol";
      String num = "Number";
      String mass = "Mass";
      System.out.printf("%-15s", elem);
      System.out.printf("%-15s", sym);
      System.out.printf("%-15s", num);
      System.out.printf("%-15s\n", mass);
      do {
         System.out.printf("%-15s", periodicTable[count].elementName);
         System.out.printf("%-15s", periodicTable[count].elementSym);
         System.out.printf("%-15s", periodicTable[count].elementNum);
         System.out.printf("%-15s\n", periodicTable[count].elementMass);
         count++;
         if (count % 15 == 0) {
            System.out.println();
            System.out.print("Would you like to continue displaying the file" +
                              ", Y or N: ");
            cont = console.next().charAt(0);
            System.out.println();
            if (cont == 'Y' || cont == 'y') {
               System.out.printf("%-15s", elem);
               System.out.printf("%-15s", sym);
               System.out.printf("%-15s", num);
               System.out.printf("%-15s\n", mass);
            }
            if (cont == 'N' || cont == 'n') {
               System.out.println("You have quit displaying the file.");
            }
            while (cont != 'Y' && cont != 'y' &&
                   cont != 'N' && cont != 'n') {
               System.out.println("You did not enter Y or N, try again.");
               System.out.print("Would you like to continue displaying the file" +
                              ", Y or N: ");
               cont = console.next().charAt(0);
               System.out.println();
           }
         }
      } while ((cont == 'y' || cont == 'Y') && count < recs);
      System.out.println();
   }
   
   //This method displays all the information of a record selected
   //(input by) the user. If there is no such record, the method
   //lets the user know.
   public static void displaySelectRecord(Elements[] periodicTable, int recs) {
      boolean foundIt = false;
      String elem = "Element";
      String sym = "Symbol";
      String num = "Number";
      String mass = "Mass";
      String choice = "";
      System.out.print("Enter the element whose information you would" +
                        " like to display: ");
      choice = console.next();
      for (int i = 0; i < recs; i++) {
         if (periodicTable[i].elementName.equals(choice)) {
            foundIt = true;
            System.out.println();
            System.out.printf("%-15s", elem);
            System.out.printf("%-15s", sym);
            System.out.printf("%-15s", num);
            System.out.printf("%-15s\n", mass);
            System.out.printf("%-15s", periodicTable[i].elementName);
            System.out.printf("%-15s", periodicTable[i].elementSym);
            System.out.printf("%-15s", periodicTable[i].elementNum);
            System.out.printf("%-15s\n", periodicTable[i].elementMass);
            System.out.println();
         }
      }
      if (!(foundIt)) {
          System.out.println("There is no matching record in the file.");
      }
      System.out.println();
   }
   
   //This method will display the average molecular weight of elements
   //within the range of atomic numbers that the user specifies (inputs)
   public static void displayCumResult(Elements[] periodicTable, int recs) {
      int lower = 0;
      int upper = 0;
      int count = 0;
      int temp = 0;
      double sum = 0;
      double avg = 0.0;
      System.out.print("Enter the lower bound for your range of atomic numbers: ");
      lower = console.nextInt();
      System.out.println();
      while (lower <= 0) {
         System.out.println("There are no records at " + lower);
         System.out.print("Please enter a number higher than or equal to 1" +
                          " for your lower bound: ");
         lower = console.nextInt();
         System.out.println();
      }
      System.out.print("Enter the upper bound for your range of atomic numbers: ");
      upper = console.nextInt();
      System.out.println();
      if (upper < lower) {
         temp = upper;
         upper = lower;
         lower = temp;
      }
      while (upper > recs) {
         System.out.println("There are no records at " + upper);
         System.out.print("Please enter a number lower than or equal to " + recs +
                            " for your upper bound: ");
         upper = console.nextInt();
         System.out.println();
      }
      for (int i = 0; i < recs; i++) {
         if (periodicTable[i].elementNum >= lower &&
             periodicTable[i].elementNum <= upper) {
           sum += periodicTable[i].elementMass;
           count++;
         }
      }
      avg = sum / count;
      System.out.printf("Average molecular weight for chemicals within range: " +
                        " %4.3f \n", avg);
      System.out.println();
   }
   
   public static void displayHistogram(Elements[] periodicTable, int recs) {
      String dec = "decade";
      String count = "count";
      int decNum = decadeWidth(periodicTable, recs);
      int decNumStart = decNum - 1;
      int[] decArray = fillDecadeArray(periodicTable, recs);
      System.out.printf("%6s  :" + "%6s \n", dec, count);
      for (int i = 0; i < 10; i++) {
          System.out.printf("%6d" + "%9d" + " ", decNumStart, decArray[i]);
          printStars(decArray[i]);
          System.out.println();
          decNumStart += decNum;
      }
      System.out.println();
   }

   public static int[] fillDecadeArray(Elements[] periodicTable, int recs) {
      double min = computeMin(periodicTable, recs);
      int decWidth = decadeWidth(periodicTable, recs);
      int result = 0;
      int[] decadeArray = new int[10];
      for (int i = 0; i < recs; i++) {
          result = (int) ((periodicTable[i].elementMass - min) / decWidth);
          decadeArray[result]++;
      }
      return decadeArray;
   }


   public static int decadeWidth (Elements[] periodicTable, int recs) {
      double min = computeMin(periodicTable, recs)   ;
      double max = computeMax(periodicTable, recs);
      double range = max - min;
      int decadeWid = (int) Math.ceil(range / 10);
      return decadeWid;
   }

   public static double computeMin(Elements[] periodicTable, int recs) {
      double lowest = periodicTable[0].elementMass;
      for (int i = 0; i < recs; i ++) {
          if (periodicTable[i].elementMass < lowest) {
              lowest = periodicTable[i].elementMass;
          }
      }
      return lowest;
   }

   public static double computeMax(Elements[] periodicTable, int recs) {
      double highest = periodicTable[0].elementMass;
      for (int i = 0; i < recs; i ++) {
          if (periodicTable[i].elementMass > highest) {
              highest = periodicTable[i].elementMass;
          }
      }
      return highest;
   }
   
   public static void quitProg() {
      System.out.println("You have quit the program.");
      System.out.println();
   }

   public static void printStars (int numStars) {
      for (int i = 0; i < numStars; i++) {
          System.out.print("*");
      }
   }
}

class Elements {
   public String elementName;
   public String elementSym;
   public int elementNum;
   public double elementMass;
}