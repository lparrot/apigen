package fr.lauparr.apigen.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.repository.support.Repositories;

@Configuration
public class JpaConfig {

  @Bean
  public SpelAwareProxyProjectionFactory projectionFactory() {
    return new SpelAwareProxyProjectionFactory();
  }

  @Bean
  public Repositories repositories(ApplicationContext context) {
    return new Repositories(context);
  }

}
