package nl.tue.systemconnectorpackage.configuration;

import eu.arrowhead.client.library.ArrowheadService;
import eu.arrowhead.common.exception.UnavailableServerException;
import eu.arrowhead.common.http.HttpService;
import nl.tue.systemconnectorpackage.clients.maas.MAASClient;
import nl.tue.systemconnectorpackage.clients.maas.implementations.*;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.implementations.ArrowheadHelperDefaultImp;
import nl.tue.systemconnectorpackage.clients.xama.XAMAClient;
import nl.tue.systemconnectorpackage.clients.xama.implementations.XAMAClientDefaultImp;
import nl.tue.systemconnectorpackage.common.FileUtilityService;
import nl.tue.systemconnectorpackage.common.HttpUtilityService;
import nl.tue.systemconnectorpackage.common.StringUtilities;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;
import nl.tue.systemconnectorpackage.common.implementations.FileUtilityServiceDefaultImp;
import nl.tue.systemconnectorpackage.common.implementations.HttpUtilityServiceDefaultImp;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;

import com.google.gson.JsonSyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configures dependencies of nl.tue package via Spring Boot
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("eu.arrowhead")
public class SystemConnectorConfiguration {
    @Value("${maas_client_type:defaultImp}")
    private String implementationTypeOfMAASClient;
    @Value("${xama_client_type:defaultImp}")
    private String implementationTypeOfXAMAClient;
    @Value("${custom_arrowhead_imp_bean_name:}")
    private String customArrowheadImpBeanName;

    @Bean
    public ArrowheadHelper arrowheadHelper(@Autowired ArrowheadService arrowheadService,
            @Autowired FileUtilityService fileUtilityService,
            @Autowired HttpService httpService, @Autowired ApplicationContext context)
            throws InvalidParameterException {
        if (StringUtilities.isValid(customArrowheadImpBeanName) && context.containsBean(customArrowheadImpBeanName)
                && context.getBean(customArrowheadImpBeanName) instanceof ArrowheadHelper) {
            return (ArrowheadHelper) context.getBean(customArrowheadImpBeanName);
        }
        return new ArrowheadHelperDefaultImp(arrowheadService, fileUtilityService, httpService);
    }

    @Bean
    public MAASClient maasClient(@Autowired ArrowheadHelper arrowheadHelper,
            @Autowired HttpUtilityService httpUtilityService)
            throws UnavailableServerException, JsonSyntaxException {
        if (implementationTypeOfMAASClient.equals("defaultImp")) {
            return new MAASClientDefaultImp(arrowheadHelper,
                    new RepositoryManagerClientDefaultImp(arrowheadHelper, httpUtilityService),
                    new ModelFilterDefaultClient(arrowheadHelper),
                    new ModelTransformerClientDefaultImp(arrowheadHelper),
                    new ModelCrawlerClientDefaultImp(arrowheadHelper));
        }
        throw new UnsupportedOperationException("There is no any MAASClient implementation by maas_client_type!");
    }

    @Bean
    public HttpUtilityService httpUtilityService() {
        return new HttpUtilityServiceDefaultImp();
    }

    @Bean
    public FileUtilityService fileUtilityService() {
        return new FileUtilityServiceDefaultImp();
    }

    @Bean
    public XAMAClient xamaClient(@Autowired ArrowheadHelper arrowheadHelper,
            @Autowired HttpUtilityService httpUtilityService) {
        if (implementationTypeOfMAASClient.equals("defaultImp")) {
            return new XAMAClientDefaultImp(arrowheadHelper, httpUtilityService);
        }
        throw new UnsupportedOperationException("There is no any XAMAClient implementation by xama_client_type!");
    }
}
