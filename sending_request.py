import json
import requests
import os 


result = 0


ENDPOINT = "http://localhost:8080/pbc/clpeks/demo"
headers2 = {
     "Content-Type": "application/json"
}



yes = 0
no = 0
fullstring = "StackAbuse"
substringno = "No !!!"
substringyes = "Yes !!!"
setup = 0
sender = 0 
receiver = 0
cipher = 0
trapdoor = 0
for i in range(1000):
    result = 0
    r = requests.get(ENDPOINT,headers= headers2)
    quote = r.text
    result = quote.find('requiredTime',result + 1 ,len(quote)-1)
    # print(" ", result,quote[result+15],quote[result+15:result+18])
    setup += int(quote[result+15:result+18])
    result = quote.find('requiredTime',result + 1 ,len(quote)-1)
    # print(" ", result,quote[result+15],quote[result+15:result+18])
    sender += int(quote[result+15:result+18])
    result = quote.find('requiredTime',result + 1 ,len(quote)-1)
    # print(" ", result,quote[result+15],quote[result+15:result+18])
    receiver += int(quote[result+15:result+18])
    result = quote.find('requiredTime',result + 1 ,len(quote)-1)
    # print(" ", result,quote[result+15],quote[result+15:result+18])
    cipher += int(quote[result+15:result+18])
    result = quote.find('requiredTime',result + 1 ,len(quote)-1)
    # print(" ", result,quote[result+15],quote[result+15:result+18])
    trapdoor += int(quote[result+15:result+18])
    if substringyes in r.text:
        yes += 1
    else:
        no += 1
    print("Positive  ",yes," Nagative  ",no)

print("setup",setup)
print("sender",sender)
print("receiver",receiver)
print("cipher",cipher)
print("trapodrorr",trapdoor)
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

# Positive   28  Nagative   72
# Positive   26  Nagative   74
# Positive   22  Nagative   78
# Positive   18  Nagative   82
# Positive   24  Nagative   76
# Positive   22  Nagative   78
# Positive   23  Nagative   77
# Positive   28  Nagative   72

# 5254430477340645926707307053786342127794888580815991133949402902900724220725240694867404964793288116291351483592113916284966500405679979998927847688219849,686595634627966064045336618054349393288166405504504894613173259181793746109834098542276202610027555320660364100521976908887576466255138826125193911216524,0
# 4202874367563973336169768251023813345294582480721402622960973937524148703358339541182704856002169030631837698417635511764093747618991553670878106965664269,6178539002605095336840875311091357370471852812192332713974972515105786281004291029345691321689941494527936768683859087233970718678835856914577062408663134,0

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
