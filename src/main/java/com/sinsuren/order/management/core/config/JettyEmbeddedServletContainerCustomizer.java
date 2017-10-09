package com.sinsuren.order.management.core.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;

import java.util.List;

/**
 * Created by surender.s on 05/10/17.
 */

@AllArgsConstructor
public class JettyEmbeddedServletContainerCustomizer implements EmbeddedServletContainerCustomizer {

    List<JettyServerCustomizer> jettyServerCustomizerList;

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        if(container instanceof JettyEmbeddedServletContainerFactory && jettyServerCustomizerList!=null)
        {
            for(JettyServerCustomizer jettyServerCustomizer: jettyServerCustomizerList)
            {
                ((JettyEmbeddedServletContainerFactory) container).addServerCustomizers(jettyServerCustomizer);
            }

        }
    }
}
