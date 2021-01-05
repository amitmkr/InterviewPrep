package com.apm.letters_are_unique;

import com.apm._library.TestFramework;

import java.util.HashSet;

/*
Implement an algorithm to determine if a string has all unique characters.
What if you cannot use additional data structures?
 */

public class LettersAreUnique {

  static boolean areCharactersUnique_UsingHashSet(String str) {

    HashSet<Character> characters = new HashSet<>();

    for (int i=0; i<str.length(); i++) {
      if (characters.contains(str.charAt(i))) {
        return false;
      }
      else {
        characters.add(str.charAt(i));
      }
    }

    return true;
  }

  static boolean areCharactersUnique_UsingBitManip(String str) {

    int bitPattern = 0;

    for (int i=0; i<str.length(); i++) {
      int charPos = str.charAt(i) - 'a';

      int flag = (1 << charPos);

      if ((bitPattern | flag) == bitPattern) {
        return false;
      }
      else {
        bitPattern = bitPattern | flag;
      }
    }

    return true;
  }

  public static void main(String[] args) {

    TestFramework<String, Boolean> testFramework =
      TestFramework.aTestFrameworkBuilder(String.class, Boolean.class)
        .withTestCase("abca", false)
        .withTestCase("abcde", true)
        .build();

    testFramework.runTests(LettersAreUnique::areCharactersUnique_UsingHashSet);

    testFramework.runTests(LettersAreUnique::areCharactersUnique_UsingBitManip);
  }
}
