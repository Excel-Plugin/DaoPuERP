#!/bin/bash

rm -f server.out
./gradlew :Lightx00.Server:run > server.out &
PID_OF_SERVER=$!
tail -f -n 1 server.out | echo | grep -qe "Initialization done"
./gradlew :Lightx00.Client:check
RETURN_CODE=$?
rm -f server.out
kill -9 $PID_OF_SERVER
exit $RETURN_CODE