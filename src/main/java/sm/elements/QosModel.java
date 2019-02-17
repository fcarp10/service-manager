/**
 * QoS model class
 * Part of the mF2C Project: http://www.mf2c-project.eu/
 * <p>
 * This code is licensed under an Apache 2.0 license. Please, refer to the LICENSE.TXT file for more information
 *
 * @author Francisco Carpio - TUBS
 */
package sm.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QosModel {

   @JsonProperty("service_id")
   private String serviceId;
   @JsonProperty("agreement_id")
   private String agreementId;
   @JsonProperty("agents_ids")
   private List<String> agentsIds;
   @JsonProperty("used_agents")
   private boolean[] blockedAgents;
   private String config;
   @JsonProperty("violation_ratio")
   private float violationRatio;
   @JsonProperty("num_service_instances")
   private Integer numServiceInstances;
   @JsonProperty("num_service_failures")
   private Integer numServiceFailures;

   public QosModel() {
   }

   public QosModel(String serviceId, String agreementId, List<String> agentsIds) {
      this.serviceId = serviceId;
      this.agreementId = agreementId;
      this.agentsIds = agentsIds;
      this.blockedAgents = new boolean[agentsIds.size()];
      this.violationRatio = 0;
      this.numServiceInstances = 0;
      this.numServiceFailures = 0;
   }

   public String getServiceId() {
      return serviceId;
   }

   public void setServiceId(String serviceId) {
      this.serviceId = serviceId;
   }

   public String getAgreementId() {
      return agreementId;
   }

   public void setAgreementId(String agreementId) {
      this.agreementId = agreementId;
   }

   public List<String> getAgentsIds() {
      return agentsIds;
   }

   public void setAgentsIds(List<String> agentsIds) {
      this.agentsIds = agentsIds;
   }

   public boolean[] getBlockedAgents() {
      return blockedAgents;
   }

   public void setBlockedAgents(boolean[] blockedAgents) {
      this.blockedAgents = blockedAgents;
   }

   public String getConfig() {
      return config;
   }

   public void setConfig(String config) {
      this.config = config;
   }

   public float getViolationRatio() {
      return violationRatio;
   }

   public void setViolationRatio(float violationRatio) {
      this.violationRatio = violationRatio;
   }

   public Integer getNumServiceInstances() {
      return numServiceInstances;
   }

   public void setNumServiceInstances(Integer numServiceInstances) {
      this.numServiceInstances = numServiceInstances;
   }

   public Integer getNumServiceFailures() {
      return numServiceFailures;
   }

   public void setNumServiceFailures(Integer numServiceFailures) {
      this.numServiceFailures = numServiceFailures;
   }
}