#!/bin/sh -l

time=$(date)

echo "Building App..."
cd /home/dev/springman/colosseo
git pull origin main
./gradlew clean build

echo "Stopping Containers..."
echo "Removing Images..."
docker-compose down --rmi all

echo "Setup New Containers..."
docker-compose -f docker-compose-local.yml up -d

echo "Complete!!!"