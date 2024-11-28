# yasser-swe544
# yasser-swe544
# yasser-swe544


topics names

CARS_COUNTER

DOOR_1_CROSS_OUT
DOOR_1_CROSS_IN


DOOR_2_CROSS_OUT
DOOR_2_CROSS_IN


DOOR_3_CROSS_OUT
DOOR_3_CROSS_IN

DOOR_4_CROSS_OUT
DOOR_4_CROSS_IN


NEXT_NODE_Name
NEXT_NODE_URL

CURRENT_NODE_Name
CURRENT_NODE_URL












java -jar swe544-1.0.0.jar --CURRENT_NODE_Name=Door1 --CURRENT_NODE_URL=http://localhost:8081 --NEXT_NODE_Name=Door2 --NEXT_NODE_URL=http://localhost:8082 --server.port=8081
java -jar swe544-1.0.0.jar --CURRENT_NODE_Name=Door2 --CURRENT_NODE_URL=http://localhost:8082 --NEXT_NODE_Name=Door3 --NEXT_NODE_URL=http://localhost:8083 --server.port=8082
java -jar swe544-1.0.0.jar --CURRENT_NODE_Name=Door3 --CURRENT_NODE_URL=http://localhost:8083 --NEXT_NODE_Name=Door4 --NEXT_NODE_URL=http://localhost:8084 --server.port=8083
java -jar swe544-1.0.0.jar --CURRENT_NODE_Name=Door4 --CURRENT_NODE_URL=http://localhost:8084 --NEXT_NODE_Name=Door1 --NEXT_NODE_URL=http://localhost:8081 --server.port=8084
