from flask import Flask, flash, request, jsonify
import json
import datetime

app = Flask(__name__)

users = [
    {
        "phone_number": "000",
        "name": "Admin",
        "password": "000"
    }
]

plans = [
    {
        "group_id": "",
        "group_name": "",
        "start": "",
        "end": "",
        "members": [

        ],
        "discussion": [
            {
                "user": "",
                "time": ""
            }
        ],
        "places": [
            {
                "name": "",
                "time": ""
            }
        ]
    }
]


def save_json():
    global users
    with open("users.json", "w") as fp:
        fp.write(json.dumps(users))
    global plans
    with open("plans.json", "w") as fp:
        fp.write(json.dumps(plans))

def load_json():
    global users
    with open("users.json", "r") as fp:
        users = json.loads(fp.read())
    global plans
    with open("plans.json", "r") as fp:
        plans = json.loads(fp.read())
       
@app.route("/signup", methods = ["POST"])
def sign_up():
    content = request.json
    for user in users:
        if (content["phone_number"] == user["phone_number"]):
            return jsonify({"ok": False})
    users.append(content)
    save_json()
    return jsonify({"ok": True})

@app.route("/signin", methods = ["POST"])
def sign_in():
    content = request.json
    for user in users:
        if (content["phone_number"] == user["phone_number"] and \
            content["password"] == user["password"]):
            return jsonify({"ok": True})
    return jsonify({"ok": False})

@app.route("/creategroup", methods = ["POST"])
def create_group():
    content = request.json
    group_id = datetime.datetime.now().isoformat()
    plan = {
        "group_id": group_id,
        "group_name": content["group_name"],
        "start": content["start"],
        "end": content["end"],
        "members": content["members"],
        "discussion": [],
        "places": []
    }
    plans.append(plan)
    save_json()
    return jsonify({"group_id": group_id})

@app.route("/addplace", methods = ["POST"])
def add_place():
    content = request.json
    for plan in plans:
        if (plan["group_id"] == content["group_id"]):
            plan["places"].append({
                "name": content["name"],
                "time": content["time"]
            })
            save_json()
            return jsonify({"ok": True})
    return jsonify({"ok": False})


if (__name__ == '__main__'):
    load_json()
    app.run(host = "0.0.0.0", port = 8174)