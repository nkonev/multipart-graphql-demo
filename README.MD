```
http://localhost:8889/graphiql

echo -n 'admin:admin' | base64 -w0
YWRtaW46YWRtaW4=

Header
{"Authorization":"Basic YWRtaW46YWRtaW4="}


mutation Cm($input: CarInput!) {
  carMutation(input: $input) {
    id
    name
  }
}
{"input":{
  "id": 12,
  "name": null
}}


subscription S{
  secretCarEvents{
    id
    name
  }
}


{
  cars{
    id
    name,
    owner {
      id
      name
    }
  }
}
```