package io.bayberry.aloha.ext.spring.util;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SpelUtils {

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    public static String getString(String expression) {
        //TODO
//        final ExpressionParser parser = new SpelExpressionParser();
//        StandardEvaluationContext context = new StandardEvaluationContext();
//        context.setBeanResolver(new BeanFactoryResolver(beanFactory));
//        context.addPropertyAccessor(new BeanExpressionContextAccessor());
//        Expression expression = parser.parseExpression("someOtherBean.getData()");
//        BeanExpressionContext rootObject = new BeanExpressionContext(beanFactory, null);
//
//        String value = expression.getValue(context, rootObject, String.class);
        return PARSER.parseExpression(expression).getValue(String.class);
    }
}
