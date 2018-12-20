from flask import Flask, flash, request, jsonify
import json

app = Flask(__name__)

users = [
    {
        "phone_number": "000",
        "name": "Admin",
        "password": "000"
    }
]

# plans = [
#     {
#         "members": [

#         ],
#         "discussion": [
#             {
#                 "user": "",
#                 "send_time": ""
#             }
#         ],
#         "places": [
#             {
#                 "name": "",
#                 "time": ""
#             }
#         ]
#     }
# ]


def save_users():
    global users
    with open("users.json", "w") as fp:
        fp.write(json.dumps(users))

def load_users():
    global users
    with open("users.json", "r") as fp:
        users = json.loads(fp.read())
       
@app.route("/signup", methods = ["POST"])
def sign_up():
    content = request.json
    for user in users:
        if (content["phone_number"] == user["phone_number"]):
            return jsonify({"ok": False})
    users.append(content)
    save_users()
    return jsonify({"ok": True})

@app.route("/signin", methods = ["POST"])
def sign_in():
    content = request.json
    for user in users:
        if (content["phone_number"] == user["phone_number"] and \
            content["password"] == user["password"]):
            return jsonify({"ok": True})
    return jsonify({"ok": False})


if (__name__ == '__main__'):
    load_users()
    app.run(host = "0.0.0.0", port = 8174)