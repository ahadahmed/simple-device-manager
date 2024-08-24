### Build and Run application:
* Prerequisite [Docker](https://www.docker.com/) 
* Checkout the sourcecode and navigate to application root directory
* Run command `./mvnw clean package`
* Run command `docker compose up -d`
* After application start up, API documentation can be accessible here [Device APIs](http://localhost:8080/swagger-ui/index.html#/)


### Rest Endpoints

* Create new device 
  ``METHOD: POST``
      `http://localhost:8080/api/devices`
    ```
    SAMPLE JSON BODY:
    {
        "deviceId": "c46f0607-638c-4d9f-bdf2-35a570f548b8",
        "deviceName": "macbook-pro-14",
        "deviceBrand": "APPLE"
    }
    ```
* Get Device by ID
  ``MEHTOD: GET``
    `http://localhost:8080/api/devices/{deviceId}`
* Get devices
    ``MEHTOD: GET``
    `http://localhost:8080/api/devices`
* Get devices of a brand
    ``MEHTOD: GET``
    `http://localhost:8080/api/devices/brand/{deviceBrand}`
  * Supported Brands: `["APPLE", "SAMSUNG"]`
* Delete a device
    ``MEHTOD: DELETE``
    ``http://localhost:8080/api/devices/{deviceId}``

* update a device
    ``MEHTOD: PUT``
    ``http://localhost:8080/api/devices/{deviceId}``
    
    ```
    SAMPLE JSON BODY:
    {
        "deviceName": "macbook-pro-16",
        "deviceBrand": "APPLE"
    }
    ```

* Notes:
  * A sampleCSV file of consist of devices can be found into `test/resources` directory
  * A postman collection is also added into `test/resource` directory 

