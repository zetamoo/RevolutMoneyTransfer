Possible calls:

Create User:

    POST localhost:8080/user
    
    Responses with user_id.

Change user balance:

    POST localhost:8080/deposit
    {
        "user_id": "2",
        "amount": "20",
        "currency": "EUR"
    }

Get user info:

    GET localhost:8080/user/info?user_id=2
    
    Responses with user accounts.

Transfer money:

    POST localhost:8080/transfer
    {
        "from": "2",
        "to": "3",
        "amount": "0",
        "currency": "EUR"
    }
