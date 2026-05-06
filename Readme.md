🔥 1. GET All Booking IDs

`curl -X GET "https://restful-booker.herokuapp.com/booking" \
-H "Accept: application/json"`

🔥 2. GET Booking by ID

👉 Replace {bookingId} dynamically

`curl -X GET "https://restful-booker.herokuapp.com/booking/{bookingId}" \
-H "Accept: application/json"`


🔥 3. POST Create Booking (String Payload)

`curl -X POST "https://restful-booker.herokuapp.com/booking" \
-H "Content-Type: application/json" \
-d '{
"firstname": "Amrut",
"lastname": "Raibagi",
"totalprice": 3000,
"depositpaid": true,
"bookingdates": {
"checkin": "2026-05-15",
"checkout": "2026-05-20"
},
"additionalneeds": "Dinner"
}'`


🔥 4. POST Create Booking (JSON File)

👉 Create bookingPayload.json

`curl -X POST "https://restful-booker.herokuapp.com/booking" \
-H "Content-Type: application/json" \
-d @bookingPayload.json`


🔥 5. POST Create Booking (POJO equivalent)

👉 Same as normal JSON (POJO converts to this)

`curl -X POST "https://restful-booker.herokuapp.com/booking" \
-H "Content-Type: application/json" \
-d '{
"firstname": "SDET",
"lastname": "Engineer",
"totalprice": 5000,
"depositpaid": true,
"bookingdates": {
"checkin": "2026-06-01",
"checkout": "2026-06-05"
},
"additionalneeds": "Lunch"
}'`


🔥 6. POST Auth Token

`curl -X POST "https://restful-booker.herokuapp.com/auth" \
-H "Content-Type: application/json" \
-d '{
"username": "admin",
"password": "password123"
}'`

👉 Response:

{
"token": "abc123xyz"
}


🔥 7. PUT Update Booking

👉 Replace {bookingId} and {token}

`curl -X PUT "https://restful-booker.herokuapp.com/booking/{bookingId}" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-H "Cookie: token={token}" \
-d '{
"firstname": "UpdatedAmrut",
"lastname": "UpdatedRaibagi",
"totalprice": 7000,
"depositpaid": false,
"bookingdates": {
"checkin": "2026-07-01",
"checkout": "2026-07-05"
},
"additionalneeds": "Dinner"
}'`


🔥 8. GET Updated Booking (API Chaining Validation)

`curl -X GET "https://restful-booker.herokuapp.com/booking/{bookingId}" \
-H "Accept: application/json"`


🔥 9. DELETE Booking

`curl -X DELETE "https://restful-booker.herokuapp.com/booking/{bookingId}" \
-H "Cookie: token={token}"`


🔥 10. File Upload (Image / Media)

👉 Replace sample-image.png with your file path

`curl -X POST "https://httpbin.org/post" \
-F "file=@sample-image.png"`


🚀 Interview Gold Tip

If interviewer asks:

👉 “How do you debug API outside code?”

Answer:

I use cURL or Postman to validate API independently before automation. It helps isolate API issues from automation issues.