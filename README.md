<details>
<summary>Unauthenticated endpoints</summary>
<br>
<details>
<summary>User</summary>

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
GET localhost:9090/product/<productID>
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



<details>
<summary>Shopping Cart</summary>

<details>
<summary>Add Product To Shopping Cart</summary>

```
POST localhost:9090/shoppingcart/addtouser/<userID>
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
POST localhost:9090/shoppingcart/removefromuser/<userID>
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
POST localhost:9090/shoppingcart/clearforuser/<userID>
```

#
</details>

<details>
<summary>Checkout</summary>

```
POST localhost:9090/shoppingcart/checkoutforuser/<userID>
```

</details>

#
</details>

<details>
<summary>Receipt</summary>

<details>
<summary>Find Receipt By ID</summary>

```
GET localhost:9090/user/receipt/<id>
```

</details>
</details>

#
</details>

<br>

<details>
<summary>Admin endpoints</summary>

<br>
<details>
<summary>User</summary>

<details>
<summary>Get All Users</summary>

```
GET localhost:9090/user/all
```

#
</details>

<details>
<summary>Get User By ID</summary>

```
GET localhost:9090/user/<id>
```

#
</details>

<details>
<summary>Get User By Username</summary>

```
GET localhost:9090/user/<username>
```

#
</details>

<details>
<summary>Update User</summary>

```
PUT localhost:9090/user/update/<userID>
```

#
</details>

<details>
<summary>Delete User</summary>

```
DELETE localhost:9090/user/update/<userID>
```

</details>

#
</details>

<details>
<summary>Product</summary>
<details>
<summary>Create Product</summary>

```
POST localhost:9090/product/create
```

Body<br>
```
{
    "name": "carrot",
    "price": 5,
    "quantity": 10
}
```

#
</details>

<details>
<summary>Update Product</summary>

```
PUT localhost:9090/product/update/<productID>
```

Body (It's enough just writing "name": "peanut" if you only want to change the name of the product)
```
{
    "name": "peanut",
    "price": 3,
    "quantity": 5
}
```

#
</details>

<details>
<summary>Delete Product</summary>

```
DELETE localhost:9090/product/delete/<productID>
```


</details>

#
</details>

<details>
<summary>Shopping Cart</summary>

<details>
<summary>Get All Shopping Carts</summary>

```
GET localhost:9090/shoppingcart/all
```

</details>

#
</details>

<details>
<summary>Shopping Cart Item</summary>

<details>
<summary>Get All Shopping Cart Items</summary>

```
GET localhost:9090/item/all
```

</details>

#
</details>

<details>
<summary>Receipt</summary>

<details>
<summary>Get All Receipts</summary>

```
GET localhost:9090/user/receipts
```

</details>

</details>

</details>