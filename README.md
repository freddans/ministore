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

```
POST localhost:9090/shoppingcart/addtouser/1
```

Params
```
productId: 1
quantity: 4
```

#
</details>

<details>
<summary>Remove Product From Shopping Cart</summary>

```
POST localhost:9090/shoppingcart/removefromuser/1
```

Params
```
shoppingCartItemId: 1
quantity: 6
```

#
</details>

<details>
<summary>Clear Users Shopping Cart</summary>

```
POST localhost:9090/shoppingcart/clearforuser/1
```

#
</details>

<details>
<summary>Checkout</summary>

```
POST localhost:9090/shoppingcart/checkoutforuser/1
```

</details>

#
</details>

<details>
<summary>Receipt</summary>

<br>

<details>
<summary>Find Receipt By ID</summary>

```
GET localhost:9090/user/receipt/1
```

</details>
</details>


#
</details>