import json
import requests
import os 


result = 0

LOCALHOST = "http://localhost:8080/"
CYTOPTO_HEROKU = "https://criptography-dnyanesh.herokuapp.com/"

ENDPOINT = LOCALHOST
headers = {
     "Content-Type": "application/json"
}
Requestdata = ""
r = requests.get(ENDPOINT+"microservice/clpeks/setup",data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata['p'])

p = Responddata['p']
master_key_lambda = Responddata['master_key_lamda']
PKc = Responddata["pkc"]

senderid = "senderid"
receiverid = "receiverid"
r = requests.get(ENDPOINT+"microservice/clpeks/ePPK/"+senderid,data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)

Qs = Responddata["qu"]
Ds = Responddata["du"]

r = requests.get(ENDPOINT+"microservice/clpeks/ePPK/"+receiverid,data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)

Qr = Responddata["qu"]
Dr = Responddata["du"]


r = requests.get(ENDPOINT+"microservice/clpeks/sSV",data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)

Sr = Responddata["su"]


r = requests.get(ENDPOINT+"microservice/clpeks/sSV",data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)

Ss = Responddata["su"]

# Ss = "JjU1s7gpBDRNm3NBPwIhMsty1NQ="
# Sr = "EnMlqn7XtZWhk+lyS37THfn4zbI="

Requestdata={
    "su":Ss,
    "du":Ds
}   
r = requests.get(ENDPOINT+"microservice/clpeks/gPriK",data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)

SKs1 = Responddata["sku1"]
SKs2 = Responddata["sku2"]

Requestdata={
    "su":Sr,
    "du":Dr
}   
r = requests.get(ENDPOINT+"microservice/clpeks/gPriK",data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)

SKr1 = Responddata["sku1"]
SKr2 = Responddata["sku2"]


Requestdata={
    "su":Ss
}

r = requests.get(ENDPOINT+"microservice/clpeks/gPubK",data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)

PKs1 = Responddata["pku1"]
PKs2 = Responddata["pku2"]


Requestdata={
    "su":Sr
}

r = requests.get(ENDPOINT+"microservice/clpeks/gPubK",data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)

PKr1 = Responddata["pku1"]
PKr2 = Responddata["pku2"]

Requestdata={
    "pkr1":PKs1,
    "pkr2":PKs2
} 
r = requests.get(ENDPOINT+"microservice/clpeks/pairingcheck",data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)
if Responddata['checked']:
    print("------------------------------------")
    print(Responddata['description'])
else:
    print("--------------------FAILED--------------------")

Requestdata={
    "pkr1":PKr1,
    "pkr2":PKr2
} 
r = requests.get(ENDPOINT+"microservice/clpeks/pairingcheck",data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)
if Responddata['checked']:
    print("------------------------------------")
    print(Responddata['description'])
else:
    print("--------------------FAILED--------------------")

word = "wordto_encrption"

Requestdata={
  "word": word,
  "pkr2": PKr2,
  "qs": Qs,
  "qr": Qr,
  "pks2": PKs2
}
r = requests.get(ENDPOINT+"microservice/clpeks/encript",data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)

Ui = Responddata["ui"]
Vi = Responddata["vi"]
print("big int------- ",Vi)

Requestdata={
  "skr2": SKr2,
  "pks1": PKs1
}

wordchecking = "wordto_encrption"

r = requests.get(ENDPOINT+"microservice/clpeks/trapdoor/"+wordchecking,data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)

T1 = Responddata["t1"]
T2byte = Responddata["t2byte"]
T3byte = Responddata["t3byte"]


Requestdata={
  "t1": T1,
  "ui": Ui,
  "vi": Vi,
  "t2byte": T2byte,
  "t3byte": T3byte,
  "sks1": SKs1,
  "sks2": SKs2
}
print("00000000---------------000000000")
print(json.dumps(Requestdata))
print("00000000---------------000000000")
r = requests.get(ENDPOINT+"microservice/clpeks/test",data=json.dumps(Requestdata),headers= headers)
print(r)
Responddata = r.json()
print(Responddata)