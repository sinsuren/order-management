package com.sinsuren.order.management.core.config;

import org.eclipse.jetty.server.NCSARequestLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by surender.s on 05/10/17.
 */

@Configuration
@Profile("!test")
@PropertySource("jetty.yml")
public class JettyConfiguration {

    /* read all the configs and create jettyCustomizer based on them
       add these customizer to JettyConfigur
       ideally create seperate customizer
       Out of the box
       Note : Customizers will be applied in the order of addition to the list
       author : ankita.d
      */


    @Value("${server.requestLog.print:false}")
    private boolean printRequestLog;

    @Value("${server.requestLog.fileName:}")
    private String filename;


    @Value("${server.requestLog.maxHistory:1}")
    private String maxHistory;

    @Value("${server.requestLog.timeZone:GMT+5:30}")
    private String logTimeZone;



    @Bean(name = "org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer")
    public JettyEmbeddedServletContainerCustomizer jettyCustomizer()
    {
        List<JettyServerCustomizer> jettyServerCustomizers = new ArrayList<>();
        jettyServerCustomizers.add(getLogCustomizer());
        return new JettyEmbeddedServletContainerCustomizer(jettyServerCustomizers);
    }

    private JettyServerCustomizer getLogCustomizer()
    {
        return server -> {
            if(printRequestLog)
            {
                NCSARequestLog requestLog;
                if(filename!=null)
                    requestLog = new NCSARequestLog(filename);
                else
                    requestLog = new NCSARequestLog();
                requestLog.setRetainDays(Integer.parseInt(maxHistory));
                requestLog.setLogTimeZone(logTimeZone);
                requestLog.setAppend(true);
                requestLog.setExtended(true);
                requestLog.setPreferProxiedForAddress(true);
                requestLog.setLogCookies(true);
                requestLog.setLogServer(true);
            }
        };
    }

}
