package com.qpzm7903.compiler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * 测试基础语句
 *
 * @author qpzm7903
 * @since 2021-11-13-22:26
 */
public class StatementTest {

    SimpleCalculator simpleCalculator = new SimpleCalculator();

    @Nested
    class TestSimpleConditionStatement {

        @Test
        void test_greater_than() {
            String script = "10>100;";
            Object evaluate = simpleCalculator.evaluate(script);
            Assertions.assertThat(evaluate).isEqualTo(Boolean.FALSE);
        }

        @Test
        void test_less_than() {
            String script = "10<100;";
            Object evaluate = simpleCalculator.evaluate(script);
            Assertions.assertThat(evaluate).isEqualTo(Boolean.TRUE);
        }

        @Test
        void test_LE() {
            String script = "100<=100;";
            Object evaluate = simpleCalculator.evaluate(script);
            Assertions.assertThat(evaluate).isEqualTo(Boolean.TRUE);
        }

        @Test
        void test_GE() {
            String script = "100>=100;";
            Object evaluate = simpleCalculator.evaluate(script);
            Assertions.assertThat(evaluate).isEqualTo(Boolean.TRUE);
        }

        @Test
        void test_eq() {
            String script = "100==100;";
            Object evaluate = simpleCalculator.evaluate(script);
            Assertions.assertThat(evaluate).isEqualTo(Boolean.TRUE);
        }

        @Test
        void test_neq() {

            String script = "10!=100;";
            Object evaluate = simpleCalculator.evaluate(script);
            Assertions.assertThat(evaluate).isEqualTo(Boolean.TRUE);
        }

    }

    @Nested
    class TestAssign {
        @Test
        void test_simple_assign() {
            SimpleCalculator simpleCalculator = new SimpleCalculator();
            int evaluate = (int) simpleCalculator.evaluate("int a = 10;");
            Assertions.assertThat(evaluate).isEqualTo(10);
        }

        @Test
        void test_simple_assign_and_add() {
            SimpleCalculator simpleCalculator = new SimpleCalculator();
            int evaluate = (int) simpleCalculator.evaluate("int a = 10;a=a+10;");
            Assertions.assertThat(evaluate).isEqualTo(20);
        }

        @Test
        void test_simple_assign_and_add_assign() {
            SimpleCalculator simpleCalculator = new SimpleCalculator();
            int evaluate = (int) simpleCalculator.evaluate("int a = 10;a+=1;");
            Assertions.assertThat(evaluate).isEqualTo(11);
        }

        @Test
        void test_simple_assign_and_inc() {
            SimpleCalculator simpleCalculator = new SimpleCalculator();
            int evaluate = (int) simpleCalculator.evaluate("int a = 10;a++;");
            Assertions.assertThat(evaluate).isEqualTo(11);
        }

        @Test
        void test_simple_assign_and_dec() {
            SimpleCalculator simpleCalculator = new SimpleCalculator();
            int evaluate = (int) simpleCalculator.evaluate("int a = 10;a--;");
            Assertions.assertThat(evaluate).isEqualTo(9);
        }
    }


    @Nested
    class TestIfStatement {

        @DisplayName("simple-if-> if(10>4){10+10;}")
        @Test
        void test_simple_if() {
            String script = "if(10>4){10+10;}";
            Object evaluate = simpleCalculator.evaluate(script);
            Assertions.assertThat(evaluate).isEqualTo(20);
        }

        @DisplayName("simple-if-else -> if(10>100){100+100}else{300+300;}")
        @Test
        void test_simple_if_else_if() {
            String script = "if(10>100){100+100}else{300+300;}";
            Object evaluate = simpleCalculator.evaluate(script);
            Assertions.assertThat(evaluate).isEqualTo(600);
        }
    }

    @Nested
    class TestForStatement {
        @Test
        void test_simple_for() {
            String script = "int a = 0; for(int i=0;i<10;i++){a++;}";
            Object evaluate = simpleCalculator.evaluate(script);
            Assertions.assertThat(evaluate).isEqualTo(10);
        }

    }

    @Nested
    class TestWhileStatement {
        @Test
        void test_simple_while() {
            String script = "int a = 0; while(a<10){a++;}";
            Object evaluate = simpleCalculator.evaluate(script);
            Assertions.assertThat(evaluate).isEqualTo(10);
        }
    }

}
