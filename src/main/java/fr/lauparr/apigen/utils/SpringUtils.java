package fr.lauparr.apigen.utils;

import fr.lauparr.apigen.providers.ApplicationContextProvider;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

public abstract class SpringUtils {

	public static <T> T getBean(Class<T> clazz) {
		if (ApplicationContextProvider.getApplicationContext() == null) {
			return null;
		}
		return ApplicationContextProvider.getApplicationContext().getBean(clazz);
	}

	public static <T> T convertElExpression(String elExpression, Class<T> clazz, Map<String, Object> parameters) {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setBeanResolver(new BeanFactoryResolver(ApplicationContextProvider.getApplicationContext()));
		parameters.forEach(context::setVariable);
		return parser.parseExpression(elExpression).getValue(context, clazz);
	}

}
