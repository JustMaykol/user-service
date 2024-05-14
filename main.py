from uuid import uuid4
from pydantic import BaseModel

from fastapi import FastAPI
from pymongo import MongoClient

app = FastAPI(
    title='User API',
    description='User API with FastAPI and MongoDB',
)


class User(BaseModel):
    name: str
    password: str

    money: int
    admin: bool


client = MongoClient('mongodb://localhost:27017/')
db = client['current']['user']


# user crud

@app.post('/user', description="Method to create a new User")
async def create_user(user: User):
    db.insert_one({
        '_id': str(uuid4()),

        'name': user.name,
        'password': user.password,

        'money': user.money,
        'admin': user.admin
    })

    return {'message': 'created'}, 200


@app.get('/user/{_id}', description="Method to read a User by id")
async def read_user(_id: str):
    document = db.find_one({'_id': _id})

    if not document:
        return {'message': 'user not found'}, 404

    return document, 200


@app.put('/user/{_id}', description="Method to update a User by id")
async def update_user(_id: str, user: User):
    document = db.find_one({'_id': _id})

    if not document:
        return {'message': 'user not found'}, 404

    db.update_one({'_id': _id}, {
        '$set': {
            'name': user.name,
            'password': user.password,

            'money': user.money,
            'admin': user.admin
        }
    })

    return {'message': 'updated'}, 200


@app.delete('/user/{_id}', description="Method to delete a User by id")
async def delete_user(_id):
    document = db.find_one({'_id': _id})

    if not document:
        return {'message': 'user not found'}, 404

    db.delete_one({'_id': _id})

    return {'message': 'deleted'}, 200


# user search


@app.get('/user/', description="Method to collect all Users")
async def read_user():
    users = list(db.find())

    if not users:
        return {'message': 'empty'}, 404

    return users


# login

@app.post('/login', description='Method to validate Login')
async def login(name: str, password: str):
    document = db.find_one({'name': name, 'password': password})

    if not document:
        return {'message': 'invalid login'}, 401

    # change to user id
    return {'message': 'valid login'}, 200
