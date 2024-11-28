# yasser-swe544
# yasser-swe544
# yasser-swe544


topics names

CARS_COUNTER

DOOR_1_CROSS_OUT
DOOR_1_CROSS_IN
DOOR_1_NOTIFICATION

DOOR_2_CROSS_OUT
DOOR_2_CROSS_IN
DOOR_2_NOTIFICATION

DOOR_3_CROSS_OUT
DOOR_3_CROSS_IN
DOOR_3_NOTIFICATION

DOOR_4_CROSS_OUT
DOOR_4_CROSS_IN
DOOR_4_NOTIFICATION

----env values
NEXT_NODE_Name
NEXT_NODE_URL

CURRENT_NODE_Name
CURRENT_NODE_URL












java -jar build/libs/swe544-1.0.0.jar --CURRENT_NODE_Name=DOOR_1 --CURRENT_NODE_URL=http://localhost:8081 --NEXT_NODE_Name=DOOR_2 --NEXT_NODE_URL=http://localhost:8082   --server.port=8081
java -jar build/libs/swe544-1.0.0.jar --CURRENT_NODE_Name=DOOR_2 --CURRENT_NODE_URL=http://localhost:8082 --NEXT_NODE_Name=DOOR_3 --NEXT_NODE_URL=http://localhost:8083   --server.port=8082
java -jar build/libs/swe544-1.0.0.jar --CURRENT_NODE_Name=DOOR_3 --CURRENT_NODE_URL=http://localhost:8083 --NEXT_NODE_Name=DOOR_4 --NEXT_NODE_URL=http://localhost:8084   --server.port=8083
java -jar build/libs/swe544-1.0.0.jar --CURRENT_NODE_Name=DOOR_4 --CURRENT_NODE_URL=http://localhost:8084 --NEXT_NODE_Name=DOOR_5 --NEXT_NODE_URL=http://localhost:8081   --server.port=8084



./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic DOOR_1_CROSS_OUT
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic DOOR_1_CROSS_IN
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic CARS_COUNTER