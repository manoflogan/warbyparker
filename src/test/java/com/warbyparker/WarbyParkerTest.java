package com.warbyparker;

import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit test for Warby Parker
 * @author krishnanand (Kartik Krishnanand)
 */
public class WarbyParkerTest {
  
  private WarbyParker warbyParker;
  
  @BeforeMethod
  public void setUp() throws Exception {
    this.warbyParker = new WarbyParker();
  }
  
  @AfterMethod
  public void tearDown() throws Exception {
    this.warbyParker = null;
  }
  
  @Test
  public void testReadInput() throws Exception {
    try(InputStream is = ClassLoader.getSystemResourceAsStream("warbyinput.txt")) {
      Trie trie = new Trie();
      Set<String> actual = this.warbyParker.readInput(is, trie);
      Set<String> expected = new LinkedHashSet<>();
      expected.add("/w/x/y/z/");
      expected.add("a/b/c");
      expected.add("foo/");
      expected.add("foo/bar/");
      expected.add("foo/bar/baz/");
      expected.add("a/b/c/d/e/f");
      Assert.assertEquals(actual, expected);
    }
  }

}
