@echo off
docker run --rm -v "%CD%/frontend/src/remote-client":/remote-client -v "%CD%/openapi.json":/opt/openapi.json openapitools/openapi-generator-cli generate -i /opt/openapi.json -g typescript-axios -o /remote-client
