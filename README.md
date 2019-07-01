# Spring Boot playground

Yup!

## JWT test

 Call `http://localhost:8080/api/login` with

    { "user" : "user", "password" : "pass" }

A token will be returned:

    eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNDg5MTUyOTcwfQ.7Gie2FMM5a38hDn120VXkUdDyrmec1fEIa5H9-_hoFJL_h-t0YLQ8SfMnw0X8g8svjx1qn_JSCm9lozuLNmTCg

Call `http://localhost:8080/api/greeting?name=florp` with header:

    Authorization: Bearer ${TOKEN}

where token is the thing above. This should work thanks to the `AuthFilter`.

## cURL testing

	curl -v -X POST --data '{ "name" : "Kevin" } ' -H "Content-Type: application/json" -H "type: CreateCustomerCommand" http://localhost:8080/api/customer

	curl -v -X POST --data '{ "uuid" : "8882ad01-b5d8-434c-bf99-c638c926f8fa", "someProperty" : "bleh bleh", "name" : "Roffl" } ' -H "Content-Type: application/json" -H "type: ModifyCustomerCommand" http://localhost:8080/api/customer

