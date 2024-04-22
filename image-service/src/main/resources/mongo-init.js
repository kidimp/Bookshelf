db.createUser(
    {
        user: "chous",
        pwd: "chous",
        roles: [
            {
                role: "readWrite",
                db: "bookshelf"
            }
        ]
    }
);