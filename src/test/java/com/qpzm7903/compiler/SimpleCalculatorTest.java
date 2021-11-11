package com.qpzm7903.compiler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-10-7:06
 */
class SimpleCalculatorTest {
    @Test
    void test_evaluate() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("1+1;");
        Assertions.assertThat(evaluate).isEqualTo(2);
    }

    @Test
    void test_() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("1+2+3;");
        Assertions.assertThat(evaluate).isEqualTo(6);
    }

    @Test
    void test_2() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("1*2*3;");
        Assertions.assertThat(evaluate).isEqualTo(6);
    }

    @Test
    void test_evaluate_2() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("1+1*3;");
        Assertions.assertThat(evaluate).isEqualTo(4);
    }

    @Test
    void test_evaluate_3() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("1+1*3+3;");
        Assertions.assertThat(evaluate).isEqualTo(7);
    }

    @Test
    void test_evaluate_4() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("1+1*3+3/3;");
        Assertions.assertThat(evaluate).isEqualTo(5);
    }

    @Test
    void test_evaluate_5() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("1+1*3+3/3-1;");
        Assertions.assertThat(evaluate).isEqualTo(4);
    }

    @Test
    void test_assign() {

        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("int a = 10;");
        Assertions.assertThat(evaluate).isEqualTo(10);
    }

}