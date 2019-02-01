/**
 * Categorizing module inside the Service Manager
 * Part of the mF2C Project: http://www.mf2c-project.eu/
 * <p>
 * This code is licensed under an Apache 2.0 license. Please, refer to the LICENSE.TXT file for more information
 *
 * @author Francisco Carpio - TUBS
 */
package sm.categorization;

import org.deeplearning4j.clustering.cluster.ClusterSet;
import org.deeplearning4j.clustering.cluster.Point;
import org.deeplearning4j.clustering.cluster.PointClassification;
import org.deeplearning4j.clustering.kmeans.KMeansClustering;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sm.cimi.CimiInterface;
import sm.elements.Service;

import java.util.List;
import java.util.concurrent.*;

import static sm.Parameters.*;

public class Categorizer {

   private static Logger log = LoggerFactory.getLogger(Categorizer.class);
   private KMeansClustering kMeansClustering;
   private ClusterSet clusterSet;

   public Categorizer() {
      kMeansClustering = KMeansClustering.setup(CLUSTER_COUNT, MAX_ITERATION_COUNT, "euclidean");
      ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
      Runnable task = () -> {
         List<Service> services = CimiInterface.getServices();
         if (services.size() > CLUSTER_COUNT) {
            log.info("Running clustering...");
            float[][] inputServices = createInputForServices(services);
            List<Point> points = generatePoints(inputServices);
            clusterSet = runClustering(points);
            for (int i = 0; i < clusterSet.getClusters().size(); i++)
               clusterSet.getClusters().get(i).setId(String.valueOf(i));
            for (int i = 0; i < points.size(); i++) {
               PointClassification pointClassification = clusterSet.classifyPoint(points.get(i));
               services.get(i).setCategory(Integer.valueOf(pointClassification.getCluster().getId()));
               log.info(services.get(i) + " -> " + pointClassification.getCluster().getId());
               CimiInterface.putService(services.get(i));
            }
         }
      };
      scheduledExecutorService.scheduleAtFixedRate(task, 0, RETRAINING_TIME, TimeUnit.SECONDS);
   }

   public Service run(Service service) {
      if (checkFormat(service)) {
         float[] inputService = createInputForService(service);
         List<Point> points = generatePoints(inputService);
         if (clusterSet != null) {
            PointClassification pointClassification = clusterSet.classifyPoint(points.get(0));
            service.setCategory(Integer.valueOf(pointClassification.getCluster().getId()));
         } else {
            service.setCategory(0);
         }
         return service;
      } else {
         log.error("Error categorizing the service: " + service.getName());
         return null;
      }
   }

   private boolean checkFormat(Service s) {
      return s.getExecType() != null && s.getAgentType() != null;
   }

   float[][] createInputForServices(List<Service> services) {
      float[][] inputServices = new float[services.size()][11];
      for (int s = 0; s < services.size(); s++)
         inputServices[s] = createInputForService(services.get(s));
      return inputServices;
   }

   private float[] createInputForService(Service service) {
      float[] inputService = new float[11];
      inputService[0] = service.getExecType().hashCode();
      inputService[1] = service.getAgentType().hashCode();
      if (service.getExecPorts() != null)
         inputService[2] = service.getExecPorts().length;
      if (service.getNumAgents() != 0)
         inputService[3] = service.getNumAgents();
      if (service.getCpuArch() != null)
         inputService[4] = service.getCpuArch().hashCode();
      if (service.getOs() != null)
         inputService[5] = service.getOs().hashCode();
      if (service.getMemoryMin() != 0)
         inputService[6] = service.getMemoryMin();
      if (service.getStorageMin() != 0)
         inputService[7] = service.getStorageMin();
      if (service.getDisk() != 0)
         inputService[8] = service.getDisk();
      if (service.getReqResource() != null)
         inputService[9] = service.getReqResource().length;
      if (service.getOptResource() != null)
         inputService[10] = service.getOptResource().length;
      return inputService;
   }

   List<Point> generatePoints(float[][] input) {
      INDArray serviceINDArray = Nd4j.create(input);
      return Point.toPoints(serviceINDArray);
   }

   List<Point> generatePoints(float[] input) {
      INDArray serviceINDArray = Nd4j.create(input);
      return Point.toPoints(serviceINDArray);
   }

   ClusterSet runClustering(List<Point> points) {
      return kMeansClustering.applyTo(points);
   }
}

