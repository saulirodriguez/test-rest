# test-rest
### API URL
https://infinite-savannah-31569.herokuapp.com/

### GET /api/v1/to-do-list/task

Status Code: 200

Returns an array of Tasksrver.


### GET /api/v1/to-do-list/task/:id

Status Code: 200

Returns a Task with properties: id(Number), title(Text), text(Text), createdAt(Date Object)

### POST /api/v1/to-do-list/task

Status Code: 201

Request Body:
```
{
	"title": "Task",
	"text": "Description task"
}
```

Returns a Task with properties: id(Number), title(Text), text(Text), createdAt(Date Object)

### PUT /api/v1/to-do-list/task/:id

Status Code: 200

Request Body:

```
{
	"title": "Task",
	"text": "Description task"
}
```

### DELETE /api/v1/to-do-list/task/:id

Status Code: 204
