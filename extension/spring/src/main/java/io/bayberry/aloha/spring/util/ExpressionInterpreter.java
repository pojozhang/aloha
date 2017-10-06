package io.bayberry.aloha.spring.util;

import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanExpressionContextAccessor;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ExpressionInterpreter {

    private static final TemplateParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext();
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    private ApplicationContext applicationContext;
    private StandardEvaluationContext evaluationContext;

    public ExpressionInterpreter(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.evaluationContext = new StandardEvaluationContext();
        this.evaluationContext.setBeanResolver(new BeanFactoryResolver(this.applicationContext));
        this.evaluationContext.addPropertyAccessor(new BeanExpressionContextAccessor());
    }

    public <T> T explain(String expression, Class<T> targetClass) {
        Expression parsedExpression = EXPRESSION_PARSER.parseExpression(expression, TEMPLATE_PARSER_CONTEXT);
        BeanExpressionContext beanExpressionContext = new BeanExpressionContext(((AbstractApplicationContext) this.applicationContext).getBeanFactory(), null);
        return parsedExpression.getValue(this.evaluationContext, beanExpressionContext, targetClass);
    }
}
