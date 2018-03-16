/**
 * QoS Provider module inside the Service Manager
 * Part of the mF2C Project: http://www.mf2c-project.eu/
 * <p>
 * This code is licensed under an Apache 2.0 license. Please, refer to the LICENSE.TXT file for more information
 *
 * @author Francisco Carpio - TUBS
 */
package sm.qos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import sm.elements.Service;
import sm.elements.ServiceInstance;
import sm.qos.elements.SharingModel;

import static sm.utils.Parameters.*;

public class QosProvider {
    private static Logger log = LoggerFactory.getLogger(QosProvider.class);

    public ServiceInstance check(ServiceInstance serviceInstance) {
        log.info("Checking QoS requirements @id-" + serviceInstance.getInstanceId());

        // 1. Get the user sharing model
        // SharingModel sharingModel = getSharingModel();

        // 1.1 Check if the service accomplishes the sharing model

        // 2. Get the SLA violation history

        // 3. Run the algorithm to accept or reject resources

        // 4. Return the admitted resources that the service can use.
        return serviceInstance;
    }

    private SharingModel getSharingModel() {
        RestTemplate restTemplate = new RestTemplate();
        SharingModel sharingModel = null;
        try {
            sharingModel = restTemplate.getForObject(CIMI_IP + CIMI_PORT + CIMI_ROOT + USER_MANAGEMENT + GET_SHARING_MODEL, SharingModel.class);
        } catch (Exception e) {
            log.error("Getting sharing model from CIMI");
        }
        return sharingModel;
    }

    private boolean checkSharingModel(Service service, SharingModel sharingModel) {

        return true;
    }

}
