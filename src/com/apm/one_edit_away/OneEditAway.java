package com.apm.one_edit_away;

import com.apm._library.TestFramework;

/*
There are three types of edits that can be performed on strings: insert a character,
remove a character, or replace a character. Given two strings, write a function to check if they are
one edit (or zero edits) away.
EXAMPLE
pale, pIe-> true
pales, pale -> true
pale, bale -> true
pale, bake -> false

 */

public class OneEditAway {

  static class FuncArgs {
    public String str1;
    public String str2;

    public FuncArgs(String str1, String str2) {
      this.str1 = str1;
      this.str2 = str2;
    }

    @Override
    public String toString() {
      return "str1=[" + str1 + "] str2=[" + str2 + "]";
    }
  }

  static boolean isOneEditAway_v2(FuncArgs args) {
    String str1 = args.str1;
    String str2 = args.str2;

    int lenStr1 = str1.length();
    int lenStr2 = str2.length();

    if (Math.abs(lenStr1 - lenStr2) > 1) {
      System.out.println("FALSE - Length is Off by more than 1");
      return false;
    }

    if (lenStr1 == lenStr2) {
      return checkForSingleReplace(str1, str2);
    }
    else {
      return checkForSingleEdit(str1, str2);
    }
  }

  static boolean checkForSingleReplace(String str1, String str2) {
    boolean oneOperationDone = false;
    for (int pos=0; pos<str1.length(); pos++) {
      if (str1.charAt(pos) != str2.charAt(pos)) {
        if (oneOperationDone)
          return false;
        else
          oneOperationDone = true;
      }
    }

    return true;
  }

  static boolean checkForSingleEdit(String str1, String str2) {

    String smallerStr, largerStr;

    if (str1.length() > str2.length()) {
      largerStr = str1;
      smallerStr = str2;
    }
    else {
      largerStr = str2;
      smallerStr = str1;
    }

    int lenSmallerStr = smallerStr.length();
    int lenLargerStr = largerStr.length();

    int posLargerStr = 0, posSmallerStr = 0;

    boolean oneOperationDone = false;

    while (posLargerStr < lenLargerStr && posSmallerStr < lenSmallerStr) {
      if (largerStr.charAt(posLargerStr) == smallerStr.charAt(posSmallerStr)) {
        ++posLargerStr;
        ++posSmallerStr;
      }
      else {
        if (oneOperationDone)
          return false;
        else {
          oneOperationDone = true;
          ++posLargerStr;
          posSmallerStr += 2;
        }
      }
    }

    return true;
  }

  static boolean isOneEditAway_v1(FuncArgs args) {
    String str1 = args.str1;
    String str2 = args.str2;

    int lenStr1 = str1.length();
    int lenStr2 = str2.length();

    if (Math.abs(lenStr1 - lenStr2) > 1) {
      System.out.println("FALSE - Length is Off by more than 1");
      return false;
    }

    boolean checkForReplace = false;
    boolean checkForEdits = false;

    if (lenStr1 - lenStr2 == 0) {
      checkForReplace = true;
    }
    else {
      checkForEdits = true;
    }

    boolean oneOperationDone = false;

    int posStr1 = 0;
    int posStr2 = 0;
    while (posStr1 < lenStr1 && posStr2 < lenStr2) {
      if (str1.charAt(posStr1) == str2.charAt(posStr2)) {
        ++posStr1;
        ++posStr2;
      }
      else {
        if (oneOperationDone) {
          System.out.println("Second operation found");
          return false;
        }
        else {
          if (checkForReplace) {
            // We can live with one replace
            System.out.println("Replace found");
            oneOperationDone = true;

            ++posStr1;
            ++posStr2;
          }
          else if (checkForEdits) {
            // If either of the Strings are exhausted, we can insert
            if ((posStr1 + 1 == lenStr1) || (posStr2 + 1 == lenStr2)) {
              System.out.println("Found edit at end");
              oneOperationDone = true;
            }
            else {
              // The larger string's next char should match smaller string's current character
              if ( (lenStr1 > lenStr2 && str1.charAt(posStr1+1) == str2.charAt(posStr2)) ||
                    (lenStr2 > lenStr1 && str2.charAt(posStr2+1) == str1.charAt(posStr1) )) {
                System.out.println("Found edit at s1=" + str1.charAt(posStr1) + " s2=" + str2.charAt(posStr2));
                oneOperationDone = true;

                if (lenStr1 > lenStr2) {
                  ++posStr1;
                  posStr2 += 2;
                }
                else {
                  ++posStr2;
                  posStr1 += 2;
                }
              }
              else {
                System.out.println("Incompatible edit at s1=" + str1.charAt(posStr1) + " s2=" + str2.charAt(posStr2));
                return false;
              }
            }
          }
        }
      }
    }

    return true;
  }

  public static void main(String[] args) {

    TestFramework<FuncArgs, Boolean> testFramework =
      TestFramework.aTestFrameworkBuilder(FuncArgs.class, Boolean.class)
        .withTestCase(new FuncArgs("pale", "ple"), true)
        .withTestCase(new FuncArgs("pales", "pale"), true)
        .withTestCase(new FuncArgs("pale", "bale"), true)
        .withTestCase(new FuncArgs("pale", "bake"), false)
        .build();

    testFramework.runTests(OneEditAway::isOneEditAway_v1);

    testFramework.runTests(OneEditAway::isOneEditAway_v2);
  }
}
