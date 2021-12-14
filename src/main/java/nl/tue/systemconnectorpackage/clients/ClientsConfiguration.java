package nl.tue.systemconnectorpackage.clients;

import eu.arrowhead.client.library.ArrowheadService;
import eu.arrowhead.common.exception.UnavailableServerException;
import eu.arrowhead.common.http.HttpService;
import nl.tue.systemconnectorpackage.clients.maas.MAASClient;
import nl.tue.systemconnectorpackage.clients.maas.implementations.*;
import nl.tue.systemconnectorpackage.clients.services.systemInformation.SystemInformationService;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.implementations.ArrowheadSeperatePackageImp;
import nl.tue.systemconnectorpackage.common.HttpUtilityService;
import nl.tue.systemconnectorpackage.common.StringUtilities;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;
import nl.tue.systemconnectorpackage.common.implementations.HttpUtilityServiceDefaultImp;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures dependencies of nl.tue.clients package via Spring Boot
 */
@Configuration
public class ClientsConfiguration {
    @Value("${maas_client_type:defaultImp}")
    private String implementationTypeOfMAASClient;
    @Value("${system_definition_list_resource_path}")
    private String systemDefinitionListResourcePath;
    @Value("${custom_arrowhead_imp_bean_name}")
    private String customArrowheadImpBeanName;

    @Bean
    public ArrowheadHelper arrowheadHelper(@Autowired ArrowheadService arrowheadService,
            @Autowired HttpService httpService, @Autowired ApplicationContext context)
            throws InvalidParameterException {
        if (StringUtilities.isValid(customArrowheadImpBeanName) && context.containsBean(customArrowheadImpBeanName)
                && context.getBean(customArrowheadImpBeanName) instanceof ArrowheadHelper) {
            return (ArrowheadHelper) context.getBean(customArrowheadImpBeanName);
        }

        return new ArrowheadSeperatePackageImp(arrowheadService, httpService);
    }

    @Bean
    public MAASClient maasClient(@Autowired ArrowheadHelper arrowheadHelper,
            @Autowired HttpUtilityService httpUtilityService)
            throws UnavailableServerException, JsonSyntaxException, IOException {
        if (implementationTypeOfMAASClient.equals("defaultImp")) {
            return new MAASClientDefaultImp(arrowheadHelper,
                    new RepositoryManagerClientDefaultImp(arrowheadHelper, httpUtilityService),
                    new ModelFilterDefaultClient(arrowheadHelper),
                    new ModelTransformerClientDefaultImp(arrowheadHelper),
                    new ModelCrawlerClientDefaultImp(arrowheadHelper),
                    systemDefinitionListResourcePath);
        }
        throw new UnsupportedOperationException("There is no any implementation by maas_client_type!");
    }

    @Bean
    public SystemInformationService systemInformationService(@Autowired ArrowheadHelper arrowheadHelper) {
        return (ArrowheadSeperatePackageImp) arrowheadHelper;
    }

    @Bean
    public HttpUtilityService httpUtilityService() {
        return new HttpUtilityServiceDefaultImp();
    }
}
