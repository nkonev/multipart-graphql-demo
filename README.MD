```
http://localhost:8889/graphiql
http://localhost:8899/graphiql

{
  cars {
    id
    name
  }
}

curl --location --request POST 'http://localhost:8889/graphql' \
--form 'operations="{ \"query\": \"mutation FileUpload($file: Upload!) {fileUpload(file: $file){id}}\" , \"variables\": {\"file\": null}}"' \
--form 'file=@"/home/nkonev/Pictures/mad.jpg"' \
--form 'map="{\"file\": [\"variables.file\"]}"'

curl --location --request POST 'http://localhost:8899/graphql' \
--form 'operations="{ \"query\": \"mutation FileUpload($file: Upload!) {fileUpload(file: $file){id}}\" , \"variables\": {\"file\": null}}"' \
--form 'file=@"/home/nkonev/Pictures/mad.jpg"' \
--form 'map="{\"file\": [\"variables.file\"]}"'

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