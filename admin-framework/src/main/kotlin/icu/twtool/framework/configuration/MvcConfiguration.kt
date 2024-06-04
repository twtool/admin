package icu.twtool.framework.configuration

import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@SpringBootConfiguration
class MvcConfiguration {

    @Bean
    fun messageSource(): ReloadableResourceBundleMessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setDefaultEncoding(Charsets.UTF_8.name())
        messageSource.addBasenames("classpath:messages/FrameworkMessages")
        return messageSource
    }

    @Bean
    fun localValidatorFactoryBean(): LocalValidatorFactoryBean {
        val bean = LocalValidatorFactoryBean()
        bean.setValidationMessageSource(messageSource())
        return bean
    }
}