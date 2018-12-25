# oauth2-client
Sample Oauth2 client to perform complete authentication and authorization using JWT

The application is started on the Tomcat port 8090.

Oauth controller to fetch access tokens/refresh tokens using grant type password/client_credentials

http://localhost:8090/oauth/token

add the Request Headers as follows − 
grant_type = password/client_credentials
client_id = clientId
client_secret = clientSecret

<b> required incase of grant_type=password <b>
username = your username
password = your password

Now, hit the API and get the access_token/refresh_token

Request.Method.GET
http://localhost:8090/v1/resource/getDocuments 
Authorization - Bearer <access_token>

NOTE: 
8080 -- server
8090 -- client

Enjoy :)
