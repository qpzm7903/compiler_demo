import com.qpzm7903.compiler.SimpleLexer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import com.qpzm7903.compiler.support.SimpleTokenReader;


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
    void test_simple_lexer_init_and_asign() {
        SimpleLexer lexer = new SimpleLexer();
        String script = "int age = 45;";
        System.out.println("parse :" + script);
        SimpleTokenReader tokenReader = lexer.tokenize(script);
        String tokenString = lexer.dump(tokenReader);
        System.out.println(tokenString);
        // todo 未识别关键字

    }
}