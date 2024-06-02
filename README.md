Unauthorized endpoints:

<details><summary>Create User</summary>
<br>
<details>
<summary>Create User with automatical USER-role</summary>

```
POST localhost:9090/user/create
```

Body<br>
```
{
    "username": "user",
    "password": "user"
}
```
</details>


<details><summary>Create User with ADMIN role</summary>

```
POST localhost:9090/user/create
```

Body<br>
```
{
    "username": "user",
    "password": "user",
    "roles": "ADMIN"
}
```
</details>

#
</details>