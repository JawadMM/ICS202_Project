import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main2 {
  public static void main(String[] args) throws IOException, WordAlreadyExistsException, WordNotFoundException {

    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter a filename> ");
    String fileName = scanner.nextLine();
    Dictionary dictionary = new Dictionary(new File(fileName));

    System.out.print("\ncheck word> ");
    String word = scanner.nextLine();
    if (dictionary.findWord(word)) {
      System.out.println(word + " found in dictionary");
    }
    else {
      System.out.println(word + " not found in dictionary");
    }

    System.out.print("\nAdd a word> ");
    word = scanner.nextLine();
    if (word.length() > 0) {
      try {
        dictionary.addWord(word);
        System.out.println(word + " is added successfully");
      }
      catch(WordAlreadyExistsException e) {
        
      }
    }

    System.out.print("\nRemove a word> ");
    word = scanner.nextLine();
    if (word.length() > 0) {
      try {
        dictionary.deleteWord(word);
        System.out.println(word + " was deleted successfully");
      }
      catch (WordNotFoundException e) {
        
      }
    }

    System.out.print("\nSearch for similar words> ");
    word = scanner.nextLine();
    if (word.length() > 0) {
      try {
        String[] array = dictionary.findSimilar(word);
        for (int i = 0; i < array.length - 1; i++) {
          System.out.print(array[i] + ", ");
        }
        System.out.println(array[array.length - 1]);
      }
      catch (Exception e) {
          
      }
    }

    System.out.print("Save updated dictionary (Y/N)> ");
    word = scanner.nextLine();
    if (word.toUpperCase().equals("Y")) {
      System.out.print("Enter filename> ");
      String filename = scanner.nextLine();
      dictionary.upload(filename);
    }
  }
}