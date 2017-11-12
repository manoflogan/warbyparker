package com.warbyparker;

import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * An entry point into the application. The application is responsible for finding the appropriate
 * regular expression for a string.
 */
public class WarbyParker {

  public static void main(String[] args) {
    WarbyParker parker = new WarbyParker();
    Trie trie = new Trie();
    Set<String> expressions = parker.readInput(System.in, trie);
    parker.findBestPattern(expressions, trie);
  }

  /**
   * Reads input from the input stream.
   * 
   * <p>The implementation is responsible for the following
   * <ul>
   *   <li>Populates the patterns in a trie.</li>
   *   <li>Populates the words whose regular expression is to determined.</li>
   * </ul>
   * 
   * @param is input stream from the patterns and expressions are read
   * @param trie trie data structure to be initialised
   */
 Set<String> readInput(InputStream is, Trie trie) {

    boolean patternReadStatus = false;
    boolean inputStatus = false;
    
    try (Scanner scanner = new Scanner(is)) {
      Set<String> words = new LinkedHashSet<>();
      
      while (!patternReadStatus && !inputStatus) {
        // Read the patterns.
        if (!patternReadStatus) {
          int numberOfPatterns = scanner.nextInt();
          for (int i = 0; i < numberOfPatterns; i++) {
            String str = scanner.next();
            trie.addPattern(str);
          }
        }
        patternReadStatus = true;
        
        // Read the words.
        if (!inputStatus) {
          int numberOfExpressions = scanner.nextInt();
          for (int i = 0; i < numberOfExpressions; i++) {
            words.add(scanner.next());
          }
        }
        inputStatus = true;
      }
      return words;
    }
  }
 
 /**
  * Finds and prints the best matching pattern.
  * 
  * @param expressions set of expressions
  * @param trie trie data structure
  */
  void findBestPattern(Set<String> expressions, Trie trie) {
    System.out.println("The outputs");
    System.out.println("-----------");
    for (String expression : expressions) {
      String pattern = trie.findMatchingPattern(expression);
      System.out.println(expression + " - " + pattern);
    }
  }

}
