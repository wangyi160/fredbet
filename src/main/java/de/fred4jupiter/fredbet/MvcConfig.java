package de.fred4jupiter.fredbet;

import de.fred4jupiter.fredbet.web.ActivePageHandlerInterceptor;
import de.fred4jupiter.fredbet.web.ChangePasswordFirstLoginInterceptor;
import de.fred4jupiter.fredbet.web.WebSecurityUtil;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	private static final Logger LOG = LoggerFactory.getLogger(MvcConfig.class);

    private final WebSecurityUtil webSecurityUtil;

    public MvcConfig(WebSecurityUtil webSecurityUtil) {
        this.webSecurityUtil = webSecurityUtil;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("error");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ActivePageHandlerInterceptor());
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(changePasswordFirstLoginInterceptor());

        // registry.addInterceptor(new ExecutionTimeInterceptor());

        // for logging request header
        // registry.addInterceptor(new HeaderLogHandlerInterceptor());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        
        LOG.info("Getting default locale : {}", Locale.getDefault());
        localeResolver.setDefaultLocale(Locale.getDefault());
        return localeResolver;
    }

    @Bean
    public ChangePasswordFirstLoginInterceptor changePasswordFirstLoginInterceptor() {
        return new ChangePasswordFirstLoginInterceptor(webSecurityUtil);
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:org/springframework/security/messages", "classpath:/TranslationMessages", "classpath:/ValidationMessages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
