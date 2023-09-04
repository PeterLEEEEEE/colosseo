#!/bin/sh -l

time=$(date)

echo "Building App..."
cd /home/nunaps/dev/springman/colosseo
git pull origin main
./gradlew clean build

echo "Stopping Containers..."
echo "Removing Images..."
docker-compose down --rmi all

echo "Setup New Containers..."
docker-compose -f docker-compose.yml up -d

echo "Complete!!!"