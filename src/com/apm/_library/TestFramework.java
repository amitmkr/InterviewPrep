package com.apm._library;

import java.util.ArrayList;
import java.util.function.Function;

public class TestFramework<Arg, Result extends Comparable<Result>> {

  public static class Builder<Arg, Result extends Comparable<Result>> {
    private ArrayList<TestCase<Arg,Result>> testCases = new ArrayList<>();

    private Builder() {}

    public Builder withTestCase(Arg argument, Result expectedResult) {
      this.testCases.add(new TestCase<>(argument, expectedResult));
      return this;
    }

    public TestFramework<Arg, Result> build() {
      return new TestFramework<Arg, Result>(this);
    }
  }

  private static class TestCase<Arg, Result> {
    public Arg argument;
    public Result expectedResult;

    public TestCase(Arg argument, Result expectedResult) {
      this.argument = argument;
      this.expectedResult = expectedResult;
    }

    @Override
    public String toString() {
      return "Argument=[" + argument + "] Expected_Result=[" + expectedResult + "]";
    }
  }

  private ArrayList<TestCase<Arg,Result>> testCases;

  private TestFramework() {}

  public static <X, Y extends Comparable<Y>> Builder<X, Y> aTestFrameworkBuilder(Class<X> xClass, Class<Y> yClass) {
    return new Builder<X, Y>();
  }

  public TestFramework(Builder<Arg, Result> builder) {
    this.testCases = builder.testCases;
  }

  public void runTests(Function<Arg, Result> sut) {
    System.out.println("Running test cases with: " + sut);
    for (TestCase<Arg, Result> testCase : testCases) {
      Result actualResult = sut.apply(testCase.argument);
      if (actualResult.compareTo(testCase.expectedResult) == 0) {
        System.out.println("TEST PASSED: " + testCase);
      }
      else {
        System.out.println(">>>>>>>>>> TEST FAILED: " + testCase + " Actual_Result=[" + actualResult + "]");
      }
    }
    System.out.println("--- DONE ---");
  }

  public static void main(String[] args) {

    TestFramework<Integer, Integer> testFramework =
      com.apm._library.TestFramework.aTestFrameworkBuilder(Integer.class, Integer.class)
        .withTestCase(1,1)
        .build();

    testFramework.runTests(a -> { return a; });
    testFramework.runTests(a -> { return a-1; });
  }
}

