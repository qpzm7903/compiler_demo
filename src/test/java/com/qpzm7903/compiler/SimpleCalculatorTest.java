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
        int evaluate = (int) simpleCalculator.evaluate("1+1;");
        Assertions.assertThat(evaluate).isEqualTo(2);
    }

    @Test
    void test_() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("1+2+3;");
        Assertions.assertThat(evaluate).isEqualTo(6);
    }

    @Test
    void test_2() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("1*2*3;");
        Assertions.assertThat(evaluate).isEqualTo(6);
    }

    @Test
    void test_evaluate_2() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("1+1*3;");
        Assertions.assertThat(evaluate).isEqualTo(4);
    }

    @Test
    void test_evaluate_3() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("1+1*3+3;");
        Assertions.assertThat(evaluate).isEqualTo(7);
    }

    @Test
    void test_evaluate_4() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("1+1*3+3/3;");
        Assertions.assertThat(evaluate).isEqualTo(5);
    }

    @Test
    void test_evaluate_5() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("1+1*3+3/3-1;");
        Assertions.assertThat(evaluate).isEqualTo(4);
    }

    @Test
    void test_assign() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("int a = 10;");
        Assertions.assertThat(evaluate).isEqualTo(10);
    }

    @Test
    void test_assign_to_var_and_add_a_var() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("int a = 10;a+10;");
        Assertions.assertThat(evaluate).isEqualTo(20);
    }

    @Test
    void test_use_a_var_before_assign() {

        Assertions.assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
            SimpleCalculator simpleCalculator = new SimpleCalculator();
            simpleCalculator.evaluate("a+10;");
        });
    }

    @Test
    void test_calculate_order() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("(1+2)*3;");
        Assertions.assertThat(evaluate).isEqualTo(9);
    }

    @Test
    void test_calculate_order_2() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("(1+2)*(3-2);");
        Assertions.assertThat(evaluate).isEqualTo(3);
    }

    @Test
    void test_order_and_var() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("int a = 10;(a+3)*3;");
        Assertions.assertThat(evaluate).isEqualTo(39);
    }

    @Test
    void test_order_and_var_but_syntax_error() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("int a = 10;(a+3)*3;");
        Assertions.assertThat(evaluate).isEqualTo(39);
    }

    @Test
    void test_order_and_var_but_syntax_error_1() {
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        int evaluate = (int) simpleCalculator.evaluate("1+2+3;");
        Assertions.assertThat(evaluate).isEqualTo(6);

    }

}