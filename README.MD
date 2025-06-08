
# Sport Matcher

# Backend

## Running in a container:
1. In `/backend/sportmatcher/`: `./gradlew build -x test`
2. In `/deployment/`: `docker compose -f ./docker-compose-dev.yaml up --build`
3. The API is available at `http://localhost:8080/api`


## Running locally:
`./gradlew bootRun --args='--spring.profiles.active=dev'`

You can run the database separately with 
`docker compose up`
or, if it's not running, gradle will run the compose on its own

# Frontend
## Locally:
`npm install`

`npm run dev`