#!/bin/sh -l

echo "Stopping Containers..."
time=$(date)
echo "Removing Images..."
docker-compose down --rmi all

echo "Setup New Containers..."
docker-compose -f dokcer-compose.yml up -d

echo "Complete!!!"
#echo "time=$time" >> $GITHUB_OUTPUT
