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
    void test_evaluate() throws Exception {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("1+1");
        Assertions.assertThat(evaluate).isEqualTo(2);
    }
    @Test
    void test_evaluate_2() throws Exception {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("1+1*3");
        Assertions.assertThat(evaluate).isEqualTo(4);
    }

    @Test
    void test_evaluate_3() throws Exception {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("1+1*3+3");
        Assertions.assertThat(evaluate).isEqualTo(7);
    }

    @Test
    void test_evaluate_4() throws Exception {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("1+1*3+3/3");
        Assertions.assertThat(evaluate).isEqualTo(5);
    }

    @Test
    void test_evaluate_5() throws Exception {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = simpleCalculator.evaluate("1+1*3+3/3-1");
        Assertions.assertThat(evaluate).isEqualTo(4);
    }
}