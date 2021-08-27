package fr.lauparr.apigen.providers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {
	private static ApplicationContext context;

	public static ApplicationContext getApplicationContext() {
		return ApplicationContextProvider.context;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) {
		ApplicationContextProvider.updateContext(ctx);
	}

	private static void updateContext(ApplicationContext ctx) {
		ApplicationContextProvider.context = ctx;
	}
}
