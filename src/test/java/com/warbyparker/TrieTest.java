package com.warbyparker;

import java.io.InputStream;
import java.util.Scanner;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Unit test for {@link Trie}.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
public class TrieTest {
  
  private Trie trie;
  
  @BeforeTest
  public void setUp() throws Exception {
    this.trie = new Trie();
    try(InputStream is = ClassLoader.getSystemResourceAsStream("warbyinput.txt");
        Scanner scanner = new Scanner(is);) {
      int numberOfPatterns = scanner.nextInt();
      System.out.println(numberOfPatterns);
      for (int i = 0; i < numberOfPatterns; i++) {
        this.trie.addPattern(scanner.next());
      }
    }
  }
  
  @AfterTest
  public void tearDown() {
    this.trie = null;
  }
  
  @Test
  public void testRegexOne() throws Exception {
    Assert.assertNotNull(this.trie.findMatchingPattern("/w/x/y/z/"));
    /*
     * 
a/b/c
foo/
foo/bar/
foo/bar/baz/
     */
  }

}
