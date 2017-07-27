package io.bayberry.aloha.ext.spring;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.annotation.Consume;
import io.bayberry.aloha.ConsumerAnnotationResolver;
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

import java.lang.reflect.Method;

public class SpringListenerResolver extends ConsumerAnnotationResolver {

    private static final TemplateParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext();
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    private ApplicationContext applicationContext;
    private StandardEvaluationContext evaluationContext;

    public SpringListenerResolver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.evaluationContext = new StandardEvaluationContext();
        this.evaluationContext.setBeanResolver(new BeanFactoryResolver(this.applicationContext));
        this.evaluationContext.addPropertyAccessor(new BeanExpressionContextAccessor());

    }

    @Override
    protected Channel getResolvedChannel(Consume subscribe, Method method, MessageBus messageBus) {
        Expression expression = EXPRESSION_PARSER.parseExpression(super.getResolvedChannel(subscribe, method, messageBus).getName(), TEMPLATE_PARSER_CONTEXT);
        BeanExpressionContext beanExpressionContext = new BeanExpressionContext(((AbstractApplicationContext) this.applicationContext).getBeanFactory(), null);
        return new Channel(expression.getValue(this.evaluationContext, beanExpressionContext, String.class));
    }
}
