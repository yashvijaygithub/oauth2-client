# oauth2-client
Sample Oauth2 client to perform complete authentication and authorization using JWT

The application is started on the Tomcat port 8090.

Oauth controller to fetch access tokens/refresh tokens using grant type password/client_credentials
<br>
http://localhost:8090/oauth/token
<br>
add the Request Headers as follows − 
grant_type = password/client_credentials
client_id = clientId
client_secret = clientSecret
<br>
<b> Required incase of grant_type=password <b> <br>

username = your username <br>
password = your password <br>

<br>
Now, hit the API and get the access_token/refresh_token

Request.Method.GET
<br>
http://localhost:8090/v1/resource/getDocuments <br>
Authorization - Bearer <access_token>


NOTE: <br>
8080 -- server<br>
8090 -- client

Enjoy :)
