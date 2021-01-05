package com.apm.urlify_spaces;

import com.apm._library.TestFramework;

/*
Write a method to replace all spaces in a string with '%20: You may assume that the string
has sufficient space at the end to hold the additional characters, and that you are given the "true"
length of the string. (Note: If implementing in Java, please use a character array so that you can
perform this operation in place.)

EXAMPLE
Input:
"Mr John Smith    "J 13
Output:
"Mr%20John%20Smith"
 */

public class UrlifySpaces {

  static String urlify(String url) {
    char[] characters = url.toCharArray();

    boolean canTransform = (characters[url.length() - 1] == ' ');
    int writePtr = url.length() - 1;

    boolean transformingStarted = false;
    for (int readPtr=url.length()-1; readPtr >= 0; readPtr--) {
      if (! transformingStarted && characters[readPtr] == ' ') {
        // Do nothing, keep skipping over trailing spaces
      }
      else if (! transformingStarted && characters[readPtr] != ' ') {
        transformingStarted = true;
        characters[writePtr] = characters[readPtr];
        --writePtr;
      }
      else if (transformingStarted) {
        if (characters[readPtr] == ' ') {

          // Check if we can transform, else return
          if (! canTransform || writePtr-2 < 0)
            return url;

          characters[writePtr] = '0'; --writePtr;
          characters[writePtr] = '2'; --writePtr;
          characters[writePtr] = '%'; --writePtr;
        }
        else {
          if (writePtr < 0)
            return url;

          characters[writePtr] = characters[readPtr];
          --writePtr;
        }
      }

      //System.out.println("W=" + writePtr + " R=" + readPtr + " C=[" + String.valueOf(characters) + "]");
    }

    return String.valueOf(characters);
  }

  public static void main(String[] args) {

    TestFramework<String, String> testFramework =
      TestFramework.aTestFrameworkBuilder(String.class, String.class)
        .withTestCase("Mr John Smith    ", "Mr%20John%20Smith")
        .withTestCase(" A  ", "%20A")
        .withTestCase("ABCD", "ABCD")
        .withTestCase("  A BCD", "  A BCD")
        .build();

    testFramework.runTests(UrlifySpaces::urlify);
  }
}
