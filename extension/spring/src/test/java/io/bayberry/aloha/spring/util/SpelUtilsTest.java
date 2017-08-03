package io.bayberry.aloha.spring.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.expression.BeanExpressionContextAccessor;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@SpringBootApplication
public class SpelUtilsTest {

    @Autowired
    ConfigurableBeanFactory beanFactory;

    @Test
    public void ss() {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(beanFactory));
        context.addPropertyAccessor(new BeanExpressionContextAccessor());
        Expression expression = parser.parseExpression("#{ systemProperties['java.version'] }", new TemplateParserContext());
        BeanExpressionContext rootObject = new BeanExpressionContext(beanFactory, null);

        String value = expression.getValue(context, rootObject, String.class);
        System.out.println(value);
    }
}