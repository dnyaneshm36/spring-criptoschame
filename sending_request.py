import json
import requests
import os 





ENDPOINT = "http://localhost:8080/pbc/clpeks/demo"
headers2 = {
     "Content-Type": "application/json"
}



yes = 0
no = 0
fullstring = "StackAbuse"
substringno = "No !!!"
substringyes = "Yes !!!"
for i in range(100):
    r = requests.get(ENDPOINT,headers= headers2)
    if substringyes in r.text:
        yes += 1
    else:
        no += 1
    print("Positive  ",yes," Nagative  ",no)


# this are result for 8 differet terminals for 100 requests

# Positive   18  Nagative   82
# Positive   25  Nagative   75
# Positive   13  Nagative   87
# Positive   28  Nagative   72
# Positive   25  Nagative   75
# Positive   14  Nagative   86
# Positive   21  Nagative   79
# Positive   17  Nagative   83



# Positive   25  Nagative   75
# Positive   21  Nagative   79
# Positive   20  Nagative   80
# Positive   27  Nagative   73
# Positive   22  Nagative   78
# Positive   20  Nagative   80
# Positive   22  Nagative   78
# Positive   17  Nagative   83

# AUTH_ENDPOINT = "http://127.0.0.1:8000/api/auth/register/"

# REFRESH_ENDPOINT = AUTH_ENDPOINT+"jwt/refresh/"

# ENDPOINT = "https://127.0.0.1:8000/status"

# headers = {
#     "Content-Type": "application/json",
#     "Authorization":"JWT "+'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjozMCwidXNlcm5hbWUiOiJtYW5vajE2IiwiZXhwIjoxNjA0Mzk0OTU1LCJlbWFpbCI6Im1hbmFnMTZAZ2FtaWwuY29tIiwib3JpZ19pYXQiOjE2MDQzOTQ2NTV9.UPRZchos8r1asd30Z0tIltQ8BfsygtMELt58XYaNK7I'
# }

# data = {
  
#        'password': 'qwerty1234',
#     'username' : "manoj17",
#     'email' : 'manag17@gamil.com',
#         'password2': 'qwerty1234',

# }
 
# r = requests.post(AUTH_ENDPOINT,data=json.dumps(data),headers= headers)
# print(r)
# token = r.json()
# print(token)







# AUTH_ENDPOINT = "https://dnyaneshrestapi.herokuapp.com/api/auth/"

# REFRESH_ENDPOINT = AUTH_ENDPOINT+"jwt/refresh/"

# headers = {
#     "Content-Type": "application/json",
#     # "Authorization":"JWT "+'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjozMCwidXNlcm5hbWUiOiJtYW5vajE2IiwiZXhwIjoxNjA0Mzk0OTU1LCJlbWFpbCI6Im1hbmFnMTZAZ2FtaWwuY29tIiwib3JpZ19pYXQiOjE2MDQzOTQ2NTV9.UPRZchos8r1asd30Z0tIltQ8BfsygtMELt58XYaNK7I'
# }

# data = {
#        'password': 'qwerty1234',
#     'username' : "dnyanesh",
# }
 
# r = requests.post(AUTH_ENDPOINT,data=json.dumps(data),headers= headers)
# print(r)
# token = r.json()['token']
# print(token)
