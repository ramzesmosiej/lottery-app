### Database design
At the moment the database "socials" consists of 5 relations. It has one-to-one, many-to-one and also many-to-many relationships which require some additional knowledge about the JPA annotations that should be used inside entities.
##### One-to-one relationship
The appUser is allowed to have only one CV and cv can belong to only one appUser. That's why I modeled this as 1:1 using entities CV and User.
CV has a FK to appUser.
```
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false
    )
    private User appUser;
```
User also has a CV
```
    @OneToOne(mappedBy = "appUser", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private CV cv;
```
