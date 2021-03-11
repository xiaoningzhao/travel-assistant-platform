# User location tracking backend

## Introduction
This service has the following functions: 
1. save a user location in the format of: 
    - userId
    - latitude
    - longitude
    - createdAt (TIMESTAMP)
2. retrieve the latest location record by user id
3. retrieve the previous 1000 location records by user id.

## Notes
1. Please refer to `application.properties` for access to Open Api docs.
2. Please refer to `application.properties` for data source configurations. (H2-database as of now.)

