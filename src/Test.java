import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {

  public static void main(String[] args) {

    Dictionary dictionary = new Dictionary();

    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter a file name> ");

    String fileName = scanner.nextLine();

    
    try {
      File file = new File(fileName);
      dictionary = new Dictionary(file);
    } 
    
    catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
    }

    System.out.print("\nadd a word> ");
    String word = scanner.nextLine();

    if (word.length() > 0) {

      try {
        dictionary.addWord(word);

      }

      catch (WordAlreadyExistsException e) {
        // TODO Auto-generated catch block
      }
    }

    System.out.print("\nRemove a word> ");
    word = scanner.nextLine();

    if (word.length() > 0) {
      try {
        dictionary.deleteWord(word);
      } 
      
      catch (WordNotFoundException e) {
        // TODO Auto-generated catch block
      }
    }

    System.out.print("\nSearch for similar words> ");
    word = scanner.nextLine();

    String[] similarWords =  dictionary.findSimilar(word);

    for(int i = 0; i < similarWords.length; i++) {
      if (i <= similarWords.length - 1) {
        System.out.print(similarWords[i] + ", ");
      }

      else {
        System.out.println(similarWords[i] + ".");
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
