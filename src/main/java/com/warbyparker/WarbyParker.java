package com.warbyparker;

import java.io.InputStream;
import java.util.Scanner;

/**
 * An entry point into the application.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public class WarbyParker {

  public static void main(String[] args) {
    WarbyParker parker = new WarbyParker();
    parker.readInput(System.in);
  }

  /**
   * Reads input 
   */
 void readInput(InputStream is) {

    boolean patternReadStatus = false;
    boolean inputStatus = false;
    try (Scanner scanner = new Scanner(is)) {
      
      Trie trieNode = new Trie();
      while (!patternReadStatus && !inputStatus) {
        if (!patternReadStatus) {
          int numberOfPatterns = scanner.nextInt();
          for (int i = 0; i < numberOfPatterns; i++) {
            String str = scanner.next();
            trieNode.addPattern(str);
          }
        }
        trieNode.setEndOfWord(true);
        System.out.println(trieNode);
        /*patternReadStatus = true;
        if (!inputStatus) {
          int numberOfExpressions = scanner.nextInt();
          for (int i = 0; i < numberOfExpressions; i++) {
           
          }
        }*/
        inputStatus = true;
      }
    }
  }

}
