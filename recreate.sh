#!/bin/bash
docker rm yourshelfy_app_1
docker-compose pull
docker-compose up --force-recreate
