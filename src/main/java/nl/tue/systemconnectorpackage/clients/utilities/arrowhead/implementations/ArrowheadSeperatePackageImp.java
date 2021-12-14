package nl.tue.systemconnectorpackage.clients.utilities.arrowhead.implementations;

import java.util.ArrayList;
import java.util.List;

import nl.tue.systemconnectorpackage.clients.services.systemInformation.SystemInformationService;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eu.arrowhead.client.library.ArrowheadService;
import eu.arrowhead.common.Utilities;
import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.ServiceRegistryResponseDTO;
import eu.arrowhead.common.dto.shared.SystemResponseDTO;
import eu.arrowhead.common.http.HttpService;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.ArrowheadHelper;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.exceptions.ArrowheadServiceRegistryException;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models.ArrowheadServiceInformation;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models.ArrowheadSystemInformation;
import nl.tue.systemconnectorpackage.clients.utilities.arrowhead.models.ServiceRegistryListResponseDTO;
import nl.tue.systemconnectorpackage.common.FileUtilityService;
import nl.tue.systemconnectorpackage.common.exceptions.InvalidParameterException;

public class ArrowheadSeperatePackageImp extends ArrowheadHelperImpBase
        implements ArrowheadHelper, SystemInformationService {
    public ArrowheadSeperatePackageImp(ArrowheadService arrowheadService, FileUtilityService fileUtilityService,
            HttpService httpService) {
        super(arrowheadService, fileUtilityService, httpService,
                LogManager.getLogger(ArrowheadSeperatePackageImp.class));
    }

    @Override
    public OrchestrationResponseDTO proceedOrchestration(String serviceName) {
        return proceedOrchestration(serviceName, arrowheadService.getOrchestrationFormBuilder());
    }

    @Override
    protected void registerConsumers(List<ArrowheadSystemInformation> consumers) {
        if (consumers == null)
            throw new InvalidParameterException("consumers cannot be null!");

        ServiceRegistryListResponseDTO allServiceRegistriesInArrowhead = getAllServiceRegistriesFromArrowhead();

        for (ArrowheadSystemInformation consumer : consumers) {
            setLocalRepoByRegisteredConsumer(consumer, sendSystemRegistrationRequest(consumer));

            List<ArrowheadServiceInformation> consumedServices = filterConsumedServicesFomServiceRegistryList(
                    consumer.getConsumedServiceNames(), allServiceRegistriesInArrowhead);

            for (ArrowheadServiceInformation consumedService : consumedServices) {
                sendConsumedServiceRegistrationRequest(consumer, consumedService);
            }
        }
    }

    private void setLocalRepoByRegisteredConsumer(ArrowheadSystemInformation registeredConsumer,
            SystemResponseDTO systemRegistrationResponse) {
        registeredConsumer.setId(systemRegistrationResponse.getId());
    }

    private ServiceRegistryListResponseDTO getAllServiceRegistriesFromArrowhead() {
        ResponseEntity<ServiceRegistryListResponseDTO> responseFromServiceRegistry = httpService.sendRequest(
                Utilities.createURI(getUriScheme(), serviceRegistryAddress, serviceRegistryPort,
                        "/serviceregistry/mgmt"),
                HttpMethod.GET, ServiceRegistryListResponseDTO.class);
        if (responseFromServiceRegistry == null || responseFromServiceRegistry.getStatusCode() != HttpStatus.OK
                || responseFromServiceRegistry.getBody() == null)
            throw new ArrowheadServiceRegistryException("Receiving service registries from Arrowhead unsuccessful!");
        return responseFromServiceRegistry.getBody();
    }

    private List<ArrowheadServiceInformation> filterConsumedServicesFomServiceRegistryList(
            List<String> consumedServiceNames, ServiceRegistryListResponseDTO serviceRegistryListResponseDTO) {
        List<ArrowheadServiceInformation> consumedServices = new ArrayList<>();
        for (ServiceRegistryResponseDTO serviceRegistryInfo : serviceRegistryListResponseDTO.getData()) {
            if (serviceRegistryInfo == null || !consumedServiceNames
                    .contains(serviceRegistryInfo.getServiceDefinition().getServiceDefinition()))
                continue;
            consumedServices.add(convertServiceRegistryResponseToArrowheadServiceInformation(serviceRegistryInfo));
        }
        return consumedServices;
    }

    private ArrowheadServiceInformation convertServiceRegistryResponseToArrowheadServiceInformation(
            ServiceRegistryResponseDTO serviceRegistryInfo) {
        return new ArrowheadServiceInformation(
                serviceRegistryInfo.getServiceDefinition().getId(),
                serviceRegistryInfo.getProvider().getId(),
                serviceRegistryInfo.getServiceDefinition().getServiceDefinition(),
                serviceRegistryInfo.getServiceUri(),
                serviceRegistryInfo.getMetadata());
    }
}
