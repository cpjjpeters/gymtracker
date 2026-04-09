#!/bin/bash
mvn spring-boot:run &
APP_PID=$!
echo "Waiting for application to start..."
for i in {1..30}; do
    if curl -s http://localhost:8080/api/workouts > /dev/null; then
        echo "Application started. Fetching workouts..."
        curl -s http://localhost:8080/api/workouts
        kill $APP_PID
        exit 0
    fi
    sleep 2
done
echo "Application failed to start within 60s or endpoint is unreachable."
kill $APP_PID
exit 1
