package fr.lauparr.apigen.config;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.TransformedResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class SinglePageApplicationConfig implements WebMvcConfigurer {

  private static final String PATH_PATTERNS = "/**";
  private static final String FRONT_CONTROLLER = "index.html";
  private static final String CONTEXT_PATH_PLACEHOLDER = "#context-path#";
  private static final String FRONT_CONTROLLER_ENCODING = StandardCharsets.UTF_8.name();
  private final String contextPath;
  private final WebProperties.Resources resourceProperties;

  public SinglePageApplicationConfig(@Value("${server.servlet.context-path:/}") final String contextPath, final WebProperties.Resources resourceProperties) {
    this.contextPath = contextPath;
    this.resourceProperties = resourceProperties;
  }

  @Override
  public void addViewControllers(final ViewControllerRegistry pRegistry) {
    pRegistry.addViewController("/").setViewName("forward:/" + SinglePageApplicationConfig.FRONT_CONTROLLER);
  }

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler(SinglePageApplicationConfig.PATH_PATTERNS)
      .addResourceLocations(this.resourceProperties.getStaticLocations())
      .resourceChain(true)
      .addResolver(new SinglePageAppResourceResolver());
  }

  private class SinglePageAppResourceResolver extends PathResourceResolver {

    public static final String URL_SEPARATOR = "/";

    private TransformedResource transformedResource(final Resource resource) throws IOException {
      String fileContent = IOUtils.toString(resource.getInputStream(), SinglePageApplicationConfig.FRONT_CONTROLLER_ENCODING);
      fileContent = fileContent.replace(SinglePageApplicationConfig.CONTEXT_PATH_PLACEHOLDER, SinglePageApplicationConfig.this.contextPath + SinglePageAppResourceResolver.URL_SEPARATOR);
      return new TransformedResource(resource, fileContent.getBytes());
    }

    @Override
    protected Resource getResource(final String resourcePath, final Resource location) throws IOException {
      Resource resource = location.createRelative(resourcePath);
      if (resource.exists() && resource.isReadable()) {
        // if the asked resource is index.html, we serve it with the base-href rewritten
        if (resourcePath.contains(SinglePageApplicationConfig.FRONT_CONTROLLER)) {
          return this.transformedResource(resource);
        }

        return resource;
      }

      // do not serve a Resource on an reserved URI
      if ((SinglePageAppResourceResolver.URL_SEPARATOR + resourcePath).startsWith("/api")) {
        return null;
      }

      // we have just refreshed a page, no ?
      resource = location.createRelative(SinglePageApplicationConfig.FRONT_CONTROLLER);
      if (resource.exists() && resource.isReadable()) {
        return this.transformedResource(resource);
      }

      return null;
    }
  }

}
