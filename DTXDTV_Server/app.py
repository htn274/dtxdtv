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
                "text": "",
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
    if (content["phone_number"] == "" or content["name"] == "" or content["password"] == ""):
        return jsonify({"ok": False}) 
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

@app.route("/groupsofauser", methods = ["POST"])
def groups_of_a_user():
    content = request.json
    data = []
    for plan in plans:
        if (content["user"] in plan["members"]):
            data.append(plan)
    
    if (len(data) != 0):
        return jsonify({"ok": True, "data": data})
    else:
        return jsonify({"ok": False, "data": []})
    
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

@app.route("/addchat", methods = ["POST"])
def add_chat():
    content = request.json
    for plan in plans:
        if (plan["group_id"] == content["group_id"]):
            plan["discussion"].append({
                "text": content["text"],
                "user": content["user"],
                "time": datetime.datetime.now().timestamp()
            })
            save_json()
            return jsonify({"ok": True})
    return jsonify({"ok": False})

@app.route("/viewgroup", methods = ["POST"])
def view_group():
    content = request.json
    for plan in plans:
        if (plan["group_id"] == content["group_id"]):
            return jsonify({"ok": True, "data": plan})
    return jsonify({"ok": False, "data": {}})

@app.route("/viewmember", methods = ["POST"])
def view_member():
    content = request.json
    for user in users:
        if (user["phone_number"] == content["user"]):
            return jsonify({"ok": True, "name": user["name"]})
    return jsonify({"ok": False, "name": ""})

@app.route("viewmembers", methods = ["POST"])
def view_members():
    content = request.json
    res = []
    for user in users:
        if user["phone_number"] in content["users"]:
            res.append({"phone_number": user["phone_number"], "name": user["name"]})
    return jsonify({"users": res})


if (__name__ == '__main__'):
    load_json()
    app.run(host = "0.0.0.0", port = 8174)
