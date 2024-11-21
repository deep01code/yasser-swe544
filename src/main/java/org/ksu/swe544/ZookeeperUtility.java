package org.ksu.swe544;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class ZookeeperUtility {
    private static final String ZK_ADDRESS ="localhost:2181";
    //private static final String ZK_ADDRESS = "host.docker.internal:2181";
    private static final int SESSION_TIMEOUT = 30000;
    //private static final String MASTER_ZNODE = "/master";

    private static ZooKeeper zooKeeper;

    public ZookeeperUtility() throws IOException {
        this.zooKeeper = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, event -> {
            if (event.getType() == EventType.NodeDeleted && event.getPath().equals(System.getProperty(LookupValues.DOOR_CLUSTER_NUMBER))) {
                try {
                    attemptToBeMaster();
                    attemptToCreateCounter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void attemptToBeMaster() throws KeeperException, InterruptedException {
        try {

/*            System.out.println(System.getProperty("DOOR_CLUSTER_NUMBER"));
            System.out.println(System.getProperty("INSTANCE_NUMBER"));*/

            zooKeeper.create("/"+System.getProperty(LookupValues.DOOR_CLUSTER_NUMBER), System.getProperty(LookupValues.INSTANCE_NUMBER).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println(System.getProperty(LookupValues.INSTANCE_NUMBER)+" is master for :"+System.getProperty(LookupValues.DOOR_CLUSTER_NUMBER));

         } catch (KeeperException.NodeExistsException e) {
            System.out.println("Another node is the master. Watching for changes...");
            watchMasterNode();
           // isMaster = false;
        }
    }

    public void attemptToCreateCounter() throws KeeperException, InterruptedException {
        try {


            zooKeeper.create("/"+System.getProperty(LookupValues.DOOR_CLUSTER_CARS_COUNTER_PATH), "10".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        } catch (KeeperException.NodeExistsException e) {

            System.out.println(System.getProperty(LookupValues.DOOR_CLUSTER_CARS_COUNTER_PATH)+"Node already exists....");

        }
    }

    private void watchMasterNode() throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists("/"+System.getProperty(LookupValues.DOOR_CLUSTER_NUMBER), event -> {
            if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
                System.out.println("Master node deleted. Retrying to become master...");
                try {
                    attemptToBeMaster();
                } catch (KeeperException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        if (stat == null) {
            System.out.println("Master node does not exist, retrying to become master...");
            attemptToBeMaster();
        }
    }


    public boolean isMaster() throws InterruptedException, KeeperException {

        // Get data from the znode
        boolean isMaster = false;

        try {
            String dataValue = getValueFromZooPath("/"+System.getProperty(LookupValues.DOOR_CLUSTER_NUMBER));
            System.out.println(dataValue);
            // Check if the value is "door"
            if (System.getProperty(LookupValues.INSTANCE_NUMBER).equals(dataValue)) {
                System.out.println("This Node is master "+System.getProperty("DOOR_CLUSTER_NUMBER")+"  is "+System.getProperty("INSTANCE_NUMBER"));
                isMaster=true;
            } else {
                System.out.println("This Node is not master "+System.getProperty("DOOR_CLUSTER_NUMBER")+", master is "+dataValue);

            }
        } catch (Exception e) {
            e.printStackTrace();
            isMaster = false;
        }
        return isMaster;
    }

    public static String getValueFromZooPath(String path) throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData(path, false, null);

        // Convert data to String
        String dataValue = new String(data);
        return dataValue;
    }

    public static void writeValueToZooPath(String path, String value) throws KeeperException, InterruptedException {
        zooKeeper.setData(path, value.getBytes(), -1);
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }



    public static void incrementCarCounter()  {
       try {
           String carCounter=getValueFromZooPath("/"+System.getProperty(LookupValues.DOOR_CLUSTER_CARS_COUNTER_PATH));
           int carCounterValue=Integer.parseInt(carCounter);
           if(carCounterValue<LookupValues.MAX_CAR_COUNTER){
               carCounterValue++;
               writeValueToZooPath("/"+System.getProperty(LookupValues.DOOR_CLUSTER_CARS_COUNTER_PATH),String.valueOf(carCounterValue));
           }

       }catch (Exception e) {
           e.printStackTrace();
       }
    }

    public static void decrementCarCounter(){
        try {
            String carCounter=getValueFromZooPath("/"+System.getProperty(LookupValues.DOOR_CLUSTER_CARS_COUNTER_PATH));
            int carCounterValue=Integer.parseInt(carCounter);
            if(carCounterValue>-1){
                carCounterValue--;
                writeValueToZooPath("/"+System.getProperty(LookupValues.DOOR_CLUSTER_CARS_COUNTER_PATH),String.valueOf(carCounterValue));
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }



}