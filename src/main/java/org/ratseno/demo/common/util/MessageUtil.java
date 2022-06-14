package org.ratseno.demo.common.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil extends ReloadableResourceBundleMessageSource implements MessageSource {
    private final ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

    public MessageUtil(ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource){
        this.reloadableResourceBundleMessageSource = reloadableResourceBundleMessageSource;
    }

    private ReloadableResourceBundleMessageSource getReloadableResourceBundleMessageSource() {
        return reloadableResourceBundleMessageSource;
    }

    public String getMessage(String code) {
        return getReloadableResourceBundleMessageSource().getMessage(code, null, Locale.getDefault());
    }

    public String getMessage(String code, Object[] args) {
        return getReloadableResourceBundleMessageSource().getMessage(code, args, Locale.getDefault());
    }
}