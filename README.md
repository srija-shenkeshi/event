# event

## Getting Started

1. Clone this repo and open it in your favorite IDE

2. run below command to start local dependencies like MongoDB.

    ```shell
    docker compose --project-name dependencies up
    ```
   mongo-express will be available at <http://localhost:8881> <br />
  
This Repository has five REST endpoints. To create, get, edit and delete the events in a calendar.

## REST Services

### Version: 1.0

#### /createEvent

##### POST

##### Responses

| Code | Description       |
|------|-------------------|
| 200  | OK                |
| 201  | Created           |
| 400  | Bad Request       |
| 204  | No Content |
| 404  | Not Found         |
| 422  | UnProcessable Entity |

###### Summary

createEvent

###### RequestBody

 ```shell
{
    "eventName": "MEETING",
    "eventDate": "2025-05-17",
    "description":"Interview Srija",
    "location": "Hyderabad"
}
```

##### ResponseBody

 ```shell
    {
    "eventId": "47362c46-7742-4157-a7d2-d4de51c71355",
    "eventName": "MEETING",
    "eventDate": "2025-05-17",
    "description": "Interview Srija",
    "location": "Hyderabad"
}
   ```

#### /getEventByName/{eventName}

##### GET

###### Summary

getEventByName

###### ResponseBody

 ```shell
{
    "eventId": "47362c46-7742-4157-a7d2-d4de51c71355",
    "eventName": "MEETING",
    "eventDate": "2025-05-17",
    "description": "Interview Srija",
    "location": "Hyderabad"
}
   ```

#### /getEventByDate/{eventDate}

##### GET

###### Summary

getEventByDate

###### ResponseBody

 ```shell
{
    "eventId": "47362c46-7742-4157-a7d2-d4de51c71355",
    "eventName": "MEETING",
    "eventDate": "2025-05-17",
    "description": "Interview Srija",
    "location": "Hyderabad"
}
   ```

#### /editEvent/{eventId}

##### PUT

###### Summary

editEvent

###### RequestBody

 ```shell
{
    "eventName": "BIRTHDAY",
    "eventDate": "2025-05-17",
    "description":"Srija's Birthday",
    "location": "Hyderabad"
}
```

###### ResponseBody

 ```shell
{
    "eventId": "47362c46-7742-4157-a7d2-d4de51c71355",
    "eventName": "BIRTHDAY",
    "eventDate": "2025-05-17",
    "description": "Srija's Birthday",
    "location": "Hyderabad"
}
   ```

#### /deleteEvent/{eventId}

##### DELETE

###### Summary

deleteEvent

###### ResponseBody

 ```shell
204No Content
```
