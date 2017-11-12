
package com.warbyparker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * An instance of this class encapsulates a trie data structure.
 */
public class Trie {
  
  /**
   * Trie data structure
   */
  private class TrieNode {
    private final Map<String, TrieNode> trieMap;
    
    private boolean isEndOfWord;
  
  
    public TrieNode() {
      this.trieMap = new HashMap<>();
    }

    public Map<String, TrieNode> getTrieMap() {
      return trieMap;
    }
    
    @Override
    public String toString() {
      return new StringBuilder().append(this.trieMap).toString();
    }

    public boolean isEndOfWord() {
      return isEndOfWord;
    }

    public void setEndOfWord(boolean isEndOfWord) {
      this.isEndOfWord = isEndOfWord;
    }
    
  }
  
  private final TrieNode root;
  
  public Trie() {
    this.root  = new TrieNode();
  }
  
  /**
   * Adds the pattern to a trie.
   * 
   * @param patterns add patterns to the string
   */
  public void addPattern(String p) {
    this.addPatternToTrie(p.split(","));
  }
  
  /**
   * Adds pattern to the trie data structure.
   * 
   * @param patterns array of patterns to be added in a tree
   */
  private void addPatternToTrie(String[] patterns) {
    TrieNode current = this.root;
    for (String pattern: patterns) {
      Map<String, TrieNode> trieMap = current.getTrieMap();
      TrieNode next = trieMap.getOrDefault(pattern, new TrieNode());
      trieMap.put(pattern, next);
      current = next;
    }
    current.setEndOfWord(true);
  }
  
  /**
   * Finds the match pattern
   * 
   * @param expression expression whose matching pattern is to be found
   * @return matching pattern is found; {@code NO MATCH} if not
   */
  String findMatchingPattern(String expression) {
    if (expression == null || expression.isEmpty()) {
      return "NO MATCH";
    }
    if (expression.startsWith("/")) {
      expression = expression.substring(1);
    }
    String[] expressions = expression.split("/");
    String match =  this.findMatchingPattern(expressions);
    if (match == null || match.isEmpty()) {
      return "NO MATCH";
    }
    return match;
  }
  
  /**
   * Finds the best pattern among a group of pattern.
   * 
   * @param expressions comma separated array
   * @return best matching pattern
   */
  private String findMatchingPattern(String[] expressions) {
    if (expressions == null || expressions.length == 0) {
      return null;
    }
    TrieNode current = root;
    Set<String> matches = new LinkedHashSet<>();
    Set<Integer> wildcardIndexes = new LinkedHashSet<>();
    this.findMatchingPattern(expressions, "", 0, current, matches, wildcardIndexes);
    return this.findBestPattern(expressions, matches);
  }
  
  /**
   * Finds a set of all matching patterns.
   * 
   * @param expressions array of characters to be matched against a regex
   * @param fragment concatenated matching pattern fragment
   * @param index index at which the character is to be checked
   * @param node trie node instance
   * @param matches set to hold all matching patterns.
   * @param wildcardIndexes set to hold wild card index while iterating
   * @return
   */
  private String findMatchingPattern(
      String[] expressions, String fragment, int index, TrieNode node,
      Set<String> matches, Set<Integer> wildcardIndexes) {
    if (index == expressions.length) {
      if (node.isEndOfWord()) {
        return fragment;
      }
      return null;
    }
    
    // Check for matches.
    Map<String, TrieNode> trieMap = node.getTrieMap();
    String matchedOutput = null;
    if (trieMap.containsKey(expressions[index])) {
      TrieNode next = trieMap.get(expressions[index]);
      matchedOutput = findMatchingPattern(
          expressions, fragment.isEmpty() ? expressions[index] : fragment +"," +  expressions[index],
          index + 1, next, matches, wildcardIndexes);
    } else if (trieMap.containsKey("*")) {
      TrieNode next = trieMap.get("*");
      wildcardIndexes.add(index);
      matchedOutput = findMatchingPattern(
          expressions, fragment.isEmpty() ? "*" : fragment + ",*", index + 1, next,
          matches, wildcardIndexes);
    } else {
      return fragment;
    }
    // Fragment matches. Add it to the string.
    if (matchedOutput != null && matchedOutput.split(",").length == expressions.length) {
      matches.add(matchedOutput);
    }
    
    // Checking for wild cards at every stage. No wild cards found. No matching characters found.
    // Exit immediately.
    if (!node.isEndOfWord() && !trieMap.containsKey("*") ||
        trieMap.get("*").getTrieMap().isEmpty() || wildcardIndexes.contains(index)) {
      return fragment;
    }
    
    // If the index is smaller than the wild card indexes, we are starting a new search, so all
    // previous records can be removed.
    for (Iterator<Integer> iter = wildcardIndexes.iterator(); iter.hasNext();) {
      int i = iter.next();
      if (i > index) {
        iter.remove();
      }
    }
    
    // There is a wild card. Check for it.
    TrieNode next = trieMap.get("*");
    if (!next.isEndOfWord() && next != null && !next.getTrieMap().isEmpty() && !wildcardIndexes.contains(index)) {
      wildcardIndexes.add(index);
    }
    matchedOutput = findMatchingPattern(
        expressions, fragment.isEmpty() ? "*" : fragment + ",*", index + 1, next, matches,
        wildcardIndexes);
    if (matchedOutput == null || matchedOutput.isEmpty()) {
        return fragment;
    }
    if (matchedOutput.split(",").length == expressions.length) {
      matches.add(matchedOutput);
    }
    return fragment;
  }
  
  /**
   * Finds the best pattern among theet of matches.
   * 
   * <p>The algorithm is given below
   * <ul>
   *   <li>Iterate through every pattern in an array.<li>
   *   <li>Each individual pattern is converted to an array of comma separated strings and
   *     <ul>
   *       <li>The pattern array is compared against input array string. If the contents of
   *       the arrays exactly match, then the array is returned.</li>
   *       <li>If the pattern contains wild cards then number of wild cards are counted.</li>
   *       <li>If there is only one pattern with the least number of wild cards, then that
   *       pattern is returned.<li>
   *       <li>If there are multiple patterns with the same number of wild cards, then the
   *       pattern with furthest unmatched wild card from the left is chosen.</li>
   *     </ul>
   * </ul>
   * 
   * @param expressions comma separated array of string
   * @param matches set of patterns that have matched the string
   * @return best matched pattern; {@code null} if no pattern is found
   */
  private String findBestPattern(String[] expressions, Set<String> matches) {
    if (matches == null || matches.isEmpty()) {
      return null;
    }
    int minNumberOfWildCards = Integer.MAX_VALUE;
    int rightMostWildCardPosition = Integer.MIN_VALUE;
    String bestMatch = null;
    for (String match : matches) {
      String[] matchSplit = match.split(",");
      // Looking for an exact match.
      if (Arrays.equals(expressions, matchSplit)) {
        return match;
      }
      
      // Counting the number of wild cards.
      int numberOfWildCards = 0;
      for (int i = 0; i < matchSplit.length; i++) {
        if (matchSplit[i].equals("*")) {
          numberOfWildCards ++;
        }
      }
      
      // The pattern with the least number of wild card is chosen.
      if (numberOfWildCards < minNumberOfWildCards) {
        minNumberOfWildCards = numberOfWildCards;
        bestMatch = match;
        rightMostWildCardPosition = match.indexOf("*");
      } else if (numberOfWildCards == minNumberOfWildCards) {
        // Checking for the furthest wild card from the left. 
        int lastWildCardPosition = match.indexOf("*");
        if (lastWildCardPosition > rightMostWildCardPosition) {
          rightMostWildCardPosition = lastWildCardPosition;
          bestMatch = match;
        } else if (lastWildCardPosition == rightMostWildCardPosition) {
          // Applying rule recursively if the wild cards match.
          bestMatch = this.findFurthestWildCardFromLeft(
              bestMatch, rightMostWildCardPosition + 1, match, lastWildCardPosition + 1);
        }
      }
    }
    return bestMatch;
  }
  
  /**
   * Finds the string that has the furthest wild card from the left.
   * 
   * @param first first string
   * @param firstIndex index of the first string
   * @param second  string string
   * @param secondIndex index of the second string
   * @return string with the wild card that is furthest from the left
   */
  private String findFurthestWildCardFromLeft(
      String first, int firstIndex, String second, int secondIndex) {
    if (firstIndex >= first.length() && secondIndex >= second.length()) {
      return "";
    }
    if (firstIndex == -1) {
      return first;
    }
    if (secondIndex == -1) {
      return second;
    }
    String firstString = first.substring(firstIndex);
    String secondString = second.substring(secondIndex);
    int firstWildPosition = firstString.indexOf("*");
    int secondWildPosition = secondString.indexOf("*");
    if (firstWildPosition < secondWildPosition) {
      return second;
    } else if (firstWildPosition > secondWildPosition) {
      return first;
    } else {
      return this.findFurthestWildCardFromLeft(
          firstString, firstWildPosition + 1, secondString, secondWildPosition + 1);
    }
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return new StringBuilder().append(this.root).toString();
  }
}
