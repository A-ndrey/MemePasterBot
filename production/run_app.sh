#!/bin/bash
java -jar *.jar 2>/dev/null &
echo $! > app.pid