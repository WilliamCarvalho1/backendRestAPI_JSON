# backendRestAPI_JSON

### To test this API with Heroku and Postman:
To create a Terminal object, send a JSON request in the following format via POST method using the URI https://backend-rest-api-json.herokuapp.com/v1/terminal/, it creates a Terminal object, saves it into the Data Base, and returns a JSON object:
{
  "serial": "123",
  "model": "PWWIN",
  "sam": 0,
  "version": "8.00b3"
}

To update a Terminal object, send a JSON request in the following format together with the id number of the Terminal object to be updated via PUT method using the URI https://backend-rest-api-json.herokuapp.com/v1/terminal/{id}, it updates an existing Terminal object, saves it the Data Base, and returns a JSON object:
{
  "serial": "124",
  "model": "PWWIN",
  "sam": 1,
  "version": "8.00b3"
}

To fetch a particular Terminal object, send a request with the Id number of the Terminal object via GET method using the URI https://backend-rest-api-json.herokuapp.com/v1/terminal/{id}, it returns an existing Terminal object in JSON format.

To fetch all existing Terminal objects, send a request via GET method using the URI https://backend-rest-api-json.herokuapp.com/v1/terminal/, it returns all existing Terminal objects in JSON format.

To delete a Terminal object, send a request with the Id number of the Terminal object via DELETE method using the URI https://backend-rest-api-json.herokuapp.com/v1/terminal/{id}, it deletes the object from the Data Base, and returns a success message.
