<details><summary>Unauthenticated endpoints</summary>
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

#
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

<br>

<details><summary>Authenticated endpoints</summary>
<br>
<details>
<summary>Product</summary>
<details>
<summary>Get All Products</summary>

```
GET localhost:9090/product/all
```

#
</details>

<details>
<summary>Find Product By ID</summary>

```
GET localhost:9090/product/1
```

#
</details>

<details>
<summary>Find Product By Productname</summary>

```
GET localhost:9090/product/find/carrot
```

</details>

#
</details>

<br>

<details>
<summary>Shopping Cart</summary>

<br>

<details>
<summary>Add Product To Shopping Cart</summary>

info

#
</details>

<details>
<summary>Remove Product From Shopping Cart</summary>

info

#
</details>

<details>
<summary>Clear Users Shopping Cart</summary>

info

#
</details>

<details>
<summary>Checkout</summary>

info
</details>

#
</details>

#
</details>