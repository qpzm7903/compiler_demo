import com.qpzm7903.compiler.SimpleLexer;
import com.qpzm7903.compiler.support.SimpleTokenReader;
import org.junit.jupiter.api.Test;


/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-09-6:38
 */
class SimpleLexerTest {

    @Test
    void test_simple_lexer_1() {
        SimpleLexer lexer = new SimpleLexer();
        String script = "age >= 45;";
        System.out.println("parse :" + script);
        SimpleTokenReader tokenReader = lexer.tokenize(script);
        String tokenString = lexer.dump(tokenReader);
        System.out.println(tokenString);
    }

    @Test
    void test_simple_lexer_gt() {
        SimpleLexer lexer = new SimpleLexer();
        String script = "age > 45;";
        System.out.println("parse :" + script);
        SimpleTokenReader tokenReader = lexer.tokenize(script);
        String tokenString = lexer.dump(tokenReader);
        System.out.println(tokenString);
    }

    @Test
    void test_simple_lexer_nit_and_assign() {
        SimpleLexer lexer = new SimpleLexer();
        String script = "int age = 45;";
        System.out.println("parse :" + script);
        SimpleTokenReader tokenReader = lexer.tokenize(script);
        String tokenString = lexer.dump(tokenReader);
        System.out.println(tokenString);
    }

    @Test
    void test_simple_lexer_init_and_assign() {
        SimpleLexer lexer = new SimpleLexer();
        String script = "inta age = 45;";
        System.out.println("parse :" + script);
        SimpleTokenReader tokenReader = lexer.tokenize(script);
        String tokenString = lexer.dump(tokenReader);
        System.out.println(tokenString);
    }

    @Test
    void test_plus() {
        SimpleLexer lexer = new SimpleLexer();
        String script = "inta age = 45+10;";
        System.out.println("parse :" + script);
        SimpleTokenReader tokenReader = lexer.tokenize(script);
        String tokenString = lexer.dump(tokenReader);
        System.out.println(tokenString);
    }

    @Test
    void test_plus_and_minus() {
        SimpleLexer lexer = new SimpleLexer();
        String script = "inta age = 45+10-100;";
        System.out.println("parse :" + script);
        SimpleTokenReader tokenReader = lexer.tokenize(script);
        String tokenString = lexer.dump(tokenReader);
        System.out.println(tokenString);
    }

    @Test
    void test_plus_minus_slash_star() {
        SimpleLexer lexer = new SimpleLexer();
        String script = "int age = 45+10-100*10/13;";
        System.out.println("parse :" + script);
        SimpleTokenReader tokenReader = lexer.tokenize(script);
        String tokenString = lexer.dump(tokenReader);
        System.out.println(tokenString);
    }
}