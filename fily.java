import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class fily{
    
    static Scanner in = new Scanner(System.in);
    public static int getIntInput(Scanner in) {
        while (true) {
            try {
                return Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    static ArrayList<String> AvailableBooks = new ArrayList<String>();
    static ArrayList<String> BorrowedBooks = new ArrayList<String>();
    static ArrayList<String> History = new ArrayList<String>();
    static ArrayList<String> temp = new ArrayList<String>();

    
    
    
    static void update(){
        try {
        AvailableBooks = new ArrayList<>(Files.readAllLines(Paths.get("AvailableBooks.txt")));
        } catch (IOException e) {
            System.out.println("File not found");
        }
        try {
            BorrowedBooks = new ArrayList<>(Files.readAllLines(Paths.get("BorrowedBooks.txt")));
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    static void PrintAvailableBooks(){
        update();
        System.out.println("\nAvailable books:");
        for (int i = 0; i < AvailableBooks.size(); i++) {
            System.out.println((i+1)+". "+AvailableBooks.get(i));
        }
        
    }
    static void PrintBorrowedBooks(){
        update();
        System.out.println("\nBorrowed books:");
        if(BorrowedBooks.isEmpty()){
            System.out.println("No books are borrowed");
            return;
        }
        for (int i = 0; i < BorrowedBooks.size(); i++) {
            System.out.println((i+1)+". "+BorrowedBooks.get(i));
        }
    }


    static void Borrow(){
        update();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("BorrowedBooks.txt", true))) {
            System.out.println("\nBorrow a Book");
            if(AvailableBooks.isEmpty()){
                System.out.println("No books are available");
                return;
            }
            for (int i = 0; i < AvailableBooks.size(); i++) {
                System.out.println((i+1)+". "+AvailableBooks.get(i));
            }
            System.out.print("Enter the number of the book you want to borrow: ");
            int choice = getIntInput(in);
            while(choice > AvailableBooks.size() || choice < 1){
                System.out.println("Invalid input. Please enter a number between 1 and "+AvailableBooks.size());
                choice = getIntInput(in);
            }
            bw.write(AvailableBooks.get(choice-1));
            bw.newLine();
            bw.close();
            try(BufferedWriter bw2 = new BufferedWriter(new FileWriter("History.txt",true))){
                bw2.write("Borrowed: "+AvailableBooks.get(choice-1));
                bw2.newLine();
            }catch (IOException e) {
                System.out.println("Error writing to History.txt: " + e.getMessage());
            }
            temp = new ArrayList<String>(Files.readAllLines(Paths.get("AvailableBooks.txt")));
            temp.remove(choice-1);
            Files.write(Paths.get("AvailableBooks.txt"),temp);
        } catch (IOException e) {
            System.out.println("File not found");
        }

    }
    static void Return(){
        update();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("AvailableBooks.txt", true))) {
            System.out.println("\nReturn a Book");
            if(BorrowedBooks.isEmpty()){
                System.out.println("No books are borrowed");
                return;
            }
            for (int i = 0; i < BorrowedBooks.size(); i++) {
                System.out.println((i+1)+". "+BorrowedBooks.get(i));
            }
            System.out.print("Enter the number of the book you want to return: ");
            int choice = getIntInput(in);
            while(choice > BorrowedBooks.size() || choice < 1){
                System.out.println("Invalid input. Please enter a number between 1 and "+BorrowedBooks.size());
                choice = getIntInput(in);
            }
            bw.write(BorrowedBooks.get(choice-1));
            bw.newLine();
            bw.close();
            try(BufferedWriter bw2 = new BufferedWriter(new FileWriter("History.txt",true))){
                bw2.write("Retured: "+BorrowedBooks.get(choice-1));
                bw2.newLine();
            }catch (IOException e) {
                System.out.println("Error writing to History.txt: " + e.getMessage());
            }
            temp = new ArrayList<String>(Files.readAllLines(Paths.get("BorrowedBooks.txt")));
            temp.remove(choice-1);
            Files.write(Paths.get("BorrowedBooks.txt"),temp);
            
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    static void Search(){
        System.out.println("\nSearch for a Book in: ");
        System.out.print("Enter the name of the book you want to search for: ");
        String search = in.nextLine();
        for (int i = 0; i < AvailableBooks.size(); i++) {
            if(AvailableBooks.get(i).contains(search)){
                System.out.println(AvailableBooks.get(i)+" is available");
                return;
            }
        }
        System.out.println(search+" is not available");
    }

    static void ViewHistory(){
        try {
            History = new ArrayList<>(Files.readAllLines(Paths.get("History.txt")));
            System.out.println("\nHistory:");
            if(History.isEmpty()){
                System.out.println("No books have been borrowed or returned");
                return;
            }
            for (int i = 0; i < History.size(); i++) {
                System.out.println(History.get(i));
            }
            
        } catch (Exception e) {
            System.out.println("File not found");
        }   
    }

    static void ResetHistory(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("History.txt"))){
            System.out.println("\nHistory has been reset");
            bw.write("");
            bw.close();
        }catch (IOException e) {
            System.out.println("Error writing to History.txt: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        
        while(true){

            System.out.println("\nLibrary Management System.");
            System.out.println("1. Display Available Books.");
            System.out.println("2. Display Borrowed Books.");
            System.out.println("3. Borrow a Book.");
            System.out.println("4. Return a Book.");
            System.out.println("5. Search for a Book."); 
            System.out.println("6. View History.");   
            System.out.println("7. Reset History.");   
            System.out.println("8. Exit.");
            System.out.print("Enter your choice: ");
            int choice = getIntInput(in);
            switch (choice) {
                case 1:
                    PrintAvailableBooks();
                    break;
                case 2:
                    PrintBorrowedBooks();
                    break;
                case 3:
                    Borrow();
                    break;
                case 4:
                    Return();
                    break;
                case 5:
                    Search();
                    break;
                case 6:
                    ViewHistory();
                    break;
                case 7:
                    ResetHistory();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}