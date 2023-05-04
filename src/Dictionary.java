import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary {
  
  private AVLTree<String> dictionary;

  public Dictionary() {
    dictionary = new AVLTree<String>();
  }

  public Dictionary(String s) {
    dictionary = new AVLTree<>();
    dictionary.insertAVL(s);
  }

  public Dictionary(File file) throws FileNotFoundException {
    dictionary = new AVLTree<>();
    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      dictionary.insertAVL(scanner.nextLine());
    }
    scanner.close();
  }

  public void addWord(String s) throws WordAlreadyExistsException {
    if (dictionary.search(s) == true) {
      throw new WordAlreadyExistsException("The word \"" + s + "\" is already in the dictionaty");
    }

    else {
      dictionary.insertAVL(s);
    }
  }

  public boolean findWord(String s) {
    return dictionary.search(s);
  }

  public void deleteWord(String s) throws WordNotFoundException {
    if (dictionary.search(s) == false) {
      throw new WordNotFoundException("The word \"" + s + "\"does not exist in the dictionary" );
    }

    else {
      dictionary.deleteAVL(s);
    }
  }

  public String[] findSimilar(String s) {
    return findSimilar(dictionary.root, s).split(" ");
  }

  private String findSimilar(BTNode<String> node, String s) {
    if (node == null) {
      return "";
    }

    else if (lettersAreDifferent(node.data, s)) {
      return node.data + " " + findSimilar(node.right, s) + findSimilar(node.left, s);
    }

    else {
      return findSimilar(node.left, s) + findSimilar(node.right, s);
    }
  }

  private boolean lettersAreDifferent(String firstWord, String secondWord) {
    if (Math.abs(firstWord.length() - secondWord.length()) > 1) { // cant it be more than 0?
      return false;
    }

    else {
      return lettersAreDifferent(firstWord, secondWord, 0, 0, true);
    }
  }

  private boolean lettersAreDifferent(String firstWord, String secondWord, int firstIndex, int secondIndex, boolean diff) {
    if (firstIndex >= firstWord.length() || secondIndex >= secondWord.length()) {
      return true;
    }

    else if ((firstWord.charAt(firstIndex) != secondWord.charAt(secondIndex)) && !diff) {
      return false;
    }

    else if ((firstWord.charAt(firstIndex) != secondWord.charAt(secondIndex)) && diff) {
      diff = false;

      if (firstWord.length() > secondWord.length()) {
        return lettersAreDifferent(firstWord, secondWord, firstIndex++, secondIndex, diff);
      }

      else {
        return lettersAreDifferent(firstWord, secondWord, firstIndex, secondIndex++, diff);
      }
    }

    else {
      return lettersAreDifferent(firstWord, secondWord, firstIndex + 1, secondIndex + 1, diff);
    }
  }

  public void print() {
    dictionary.levelOrderTraversal();
  }

  public void upload(String fileName) {
    try {
      BTNode<String> root = dictionary.root;
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
}
