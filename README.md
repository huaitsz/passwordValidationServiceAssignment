# Password Validation Service

## Password rules
* Must consist of a mixture of lowercase letters and numerical digits only, with at least one
of each.
* Must be between 5 and 12 characters in length.
* Must not contain any sequence of characters immediately followed by the same sequence.

## How to test API

curl -X POST http://localhost:8888/password/verification -d password=[value]

When the api given password parameter "!!!!!!!!!!!!!!" is transfered, the response of API looks as below.
```
{
  "isValid": false,
  "messages": [
    "Password must contain 1 or more lowercase characters.",
    "Password must contain 1 or more digit characters.",
    "Password must be no more than 12 characters in length.",
    "Password contains the repeating sequences of characters '!!!!!!!!!!!!!!'."
  ]
}
```
If given password parameter "Abc34"
```
{
  "isValid": true,
  "messages": []
}
```