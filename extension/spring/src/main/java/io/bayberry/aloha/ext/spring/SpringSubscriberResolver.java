package io.bayberry.aloha.ext.spring;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.MessageBus;
import io.bayberry.aloha.annotation.Subscribe;
import io.bayberry.aloha.support.GenericSubscriberResolver;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanExpressionContextAccessor;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

public class SpringSubscriberResolver extends GenericSubscriberResolver {

    private static final ExpressionParser PARSER = new SpelExpressionParser();
    private ApplicationContext applicationContext;

    public SpringSubscriberResolver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected Channel getResolvedChannel(Subscribe subscribe, Method method, MessageBus messageBus) {
        String channel = subscribe.channel();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(this.applicationContext));
        context.addPropertyAccessor(new BeanExpressionContextAccessor());
        if (channel.startsWith("#")) {
            Expression expression = parser.parseExpression("#{ systemProperties['java.version'] }", new TemplateParserContext());
            BeanExpressionContext rootObject = new BeanExpressionContext(beanFactory, null);

            String value = expression.getValue(context, rootObject, String.class);
        }
        return super.getResolvedChannel(subscribe, method, messageBus);
    }
}
