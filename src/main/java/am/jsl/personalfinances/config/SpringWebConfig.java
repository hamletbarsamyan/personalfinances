package am.jsl.personalfinances.config;

import am.jsl.personalfinances.util.Constants;
import am.jsl.personalfinances.web.converter.StringToLocalDateTimeConverter;
import am.jsl.personalfinances.web.dialect.PersonalFinancesDialect;
import am.jsl.personalfinances.web.format.RoleFormatter;
import am.jsl.personalfinances.web.interceptor.UserInterceptor;
import am.jsl.personalfinances.web.util.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.*;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Spring managed Spring MVC configuration.
 * @author hamlet
 */
@Configuration
@EnableWebMvc
public class SpringWebConfig implements WebMvcConfigurer {
    /**
     * User image directory.
     */
    @Value("${personalfinances.user.img.dir}")
    private String userImgDir;

    /**
     * User html directory.
     */
    @Value("${personalfinances.user.html.dir}")
    private String userHtmlDir;

    /**
     * The roles formatter
     */
    @Autowired
    private RoleFormatter roleFormatter;

    /**
     * The default constructor.
     */
    public SpringWebConfig() {
        super();
    }

    /**
     * Creates the {@link StandardServletMultipartResolver}
     * @return the StandardServletMultipartResolver
     */
    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /**
     * Maps view controllers to the url paths.
     * @param registry the ViewControllerRegistry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("user-public/login");
        registry.addViewController("/login").setViewName("user-public/login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /**
     *  Enables forwarding to the "default" Servlet.
     * @param configurer the DefaultServletHandlerConfigurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable("personalfinancesDefaultServlet");
    }

    /**
     * Creates the {@link Java8TimeDialect} for formatting Java 8 Time objects in thymeleaf pages.
     * @return the Java8TimeDialect
     */
    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    /**
     * Creates the {@link SpringSecurityDialect} for using spring security tags in thymeleaf pages.
     * @return the
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    /**
     * Creates custom {@link PersonalFinancesDialect} that used in thymeleaf pages.
     * @return the PersonalFinancesDialect
     */
    @Bean
    public PersonalFinancesDialect personalFinancesDialect() {
        return new PersonalFinancesDialect();
    }

    /**
     *  Creates {@link ResourceBundleMessageSource} for accessing resource bundles using specified base names.
     * @return the ResourceBundleMessageSource
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("i18n/messages");
        source.setDefaultEncoding(Constants.UTF8);
        return source;
    }

    /**
     * Creates the {@link I18n} instance wrapped with Spring MessagesSource.
     * @return the I18n
     */
    @Bean
    public I18n i18n() {
        I18n i18n = new I18n();
        i18n.setMessageSource(messageSource());
        return i18n;
    }

    /**
     * Creates new {@link StringToLocalDateTimeConverter}.
     * @return the StringToLocalDateTimeConverter
     */
    @Bean
    public StringToLocalDateTimeConverter stringToLocalDateTimeConverter(){
        return new StringToLocalDateTimeConverter();
    }

    /**
     * Dispatcher configuration for serving static resources.
     * @param registry the ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/img/**",
                "/css/**",
                "/js/**",
                "/font-awesome/**",
                "/fonts/**",
                "/html/**")
                .addResourceLocations(
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/js/",
                        "classpath:/static/font-awesome/",
                        "classpath:/static/fonts/",
                        "classpath:/static/html/").setCachePeriod(3600).resourceChain(true).
                addResolver(new GzipResourceResolver()).addResolver(new PathResourceResolver());

        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/", "classpath:/other-resources/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(false)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
                .addTransformer(new CssLinkResourceTransformer());

        registry.addResourceHandler(Constants.USER_IMG_PATHPATTERN).addResourceLocations("file:" + userImgDir);
        registry.addResourceHandler(Constants.USER_HTML_PATHPATTERN).addResourceLocations("file:" + userHtmlDir);
    }

    /**
     * Creates the {@link UserInterceptor} isntance.
     * @return the UserInterceptor
     */
    @Bean
    public UserInterceptor userInterceptor() {
        UserInterceptor userInterceptor = new UserInterceptor();
        return userInterceptor;
    }

    /**
     * Registers the UserInterceptor for prehandling reqeusts for userhtml directory.
     * @param registry the InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register user interceptor for userhtml path
        registry.addInterceptor(userInterceptor()).addPathPatterns(Constants.USER_HTML_PATHPATTERN);
    }

    /**
     * Creates the {@link ResourceUrlEncodingFilter} instance.
     * @return the ResourceUrlEncodingFilter
     */
    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        ResourceUrlEncodingFilter filter = new ResourceUrlEncodingFilter();
        return filter;
    }

    /**
     * Creates the {@link LocaleChangeInterceptor} instance.
     * @return the LocaleChangeInterceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(Constants.LANGUAGE);
        return localeChangeInterceptor;
    }

    /**
     * Creates the {@link StringHttpMessageConverter} instance.
     * @return the StringHttpMessageConverter
     */
    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName(Constants.UTF8));
    }

    /**
     * Registers the application formatters.
     * @param registry the FormatterRegistry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(roleFormatter);
    }
}
