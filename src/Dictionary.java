import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary {
  
  private AVLTree<String> words;

  public Dictionary() {
    words = new AVLTree<String>();
  }

  public Dictionary(String word) {
    words = new AVLTree<String>();
    words.insertAVL(word);
  }

  public Dictionary(File file) throws FileNotFoundException {
    words = new AVLTree<String>();
    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      words.insertAVL(scanner.nextLine());
    }
    scanner.close();
  }

  public void addWord(String word) throws WordAlreadyExistsException {
    if (words.search(word)) {
      throw new WordAlreadyExistsException("The word \"" + word + "\" is already in the dictionary");
    } 
    else {
      words.insertAVL(word);
    }
  }

  public boolean findWord(String word) {
    return words.search(word);
  }

  public void deleteWord(String word) throws WordNotFoundException {
    if (words.search(word)) {
      words.deleteAVL(word);
    } 
    else {
      throw new WordNotFoundException("The word \"" + word + "\" does not exist in the dictionary" );
    }
  }

  public String[] findSimilar(String word) {
    return findSimilar(words.root, word).split(" ");
  }

  private String findSimilar(BTNode<String> node, String word) {
    if (node == null) {
      return "";
    } 
    else if (lettersAreDifferent(node.data, word)) {
      return node.data + " " + findSimilar(node.right, word) + findSimilar(node.left, word);
    } 
    else {
      return findSimilar(node.left, word) + findSimilar(node.right, word);
    }
  }

  // private boolean lettersAreDifferent(String firstWord, String secondWord) {
  //   if (Math.abs(firstWord.length() - secondWord.length()) > 1) {
  //     return false;
  //   } 
  //   else {
  //     return lettersAreDifferent(firstWord, secondWord, 0, 0, true);
  //   }
  // }

  // private boolean lettersAreDifferent(String firstWord, String secondWord, int firstIndex, int secondIndex, boolean diff) {
  //   if (firstIndex >= firstWord.length() || secondIndex >= secondWord.length()) {
  //     return true;
  //   } 
  //   else if ((firstWord.charAt(firstIndex) != secondWord.charAt(secondIndex)) && !diff) {
  //     return false;
  //   } 
  //   else if ((firstWord.charAt(firstIndex) != secondWord.charAt(secondIndex)) && diff) {
  //     diff = false;
  //     if (firstWord.length() > secondWord.length()) {
  //       return lettersAreDifferent(firstWord, secondWord, firstIndex + 1, secondIndex, diff);
  //     } 
  //     else if (firstWord.length() < secondWord.length()) {
  //       return lettersAreDifferent(firstWord, secondWord, firstIndex, secondIndex + 1, diff);
  //     }
  //   }
  //   return lettersAreDifferent(firstWord, secondWord, firstIndex + 1, secondIndex + 1, diff);
  // }

  public void print() {
    words.levelOrderTraversal();
  }

  public void upload(String fileName) {
    try {
      BTNode<String> root = words.root;
      LabQueue<BTNode<String>> queue = new LabQueue();
      FileWriter fileWriter = new FileWriter(fileName);

      if (root != null) {
        queue.enqueue(root);

        while (!queue.isEmpty()) {
          root = queue.dequeue();
          fileWriter.write(root.data + "\n");

          if (root.left != null) {
            queue.enqueue(root.left);
          }

          if (root.right != null) {
            queue.enqueue(root.right);
          }
        }
      }

      fileWriter.close();
      System.out.println("Dictionary saved successfully.");
    }

    catch (IOException e) {
      System.out.println("The file " + fileName + " does not exist.");
    }
  }

  private boolean lettersAreDifferent(String firstWord, String secondWord) {
    if (Math.abs(firstWord.length() - secondWord.length()) > 1) {
      return false;
    }

    boolean diff = true;
    int i = 0;
    int j = 0;

    while (i < firstWord.length() && j < secondWord.length()) {
      if (firstWord.charAt(i) != secondWord.charAt(j)) {
        if (!diff) {
          return false;
        }
        diff = false;
        if (firstWord.length() > secondWord.length()) {
          i++;
        } 
        else if (firstWord.length() < secondWord.length()) {
          j++;
        } 
        else {
          i++;
          j++;
        }
      } 
      
      else {
        i++;
        j++;
      }
    }
    return true;
  }
}