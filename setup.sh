#!/bin/sh -l

time=$(date)

echo "Building App..."
cd /home/dev/springman/colosseo
git pull origin main
./gradlew clean build

echo "Stopping Containers..."
echo "Removing Images..."
docker-compose -f docker-compose-local.yml down --volumes --rmi all
docker builder prune

echo "Setup New Containers..."
docker-compose -f docker-compose-local.yml up -d

echo "Complete!!!"