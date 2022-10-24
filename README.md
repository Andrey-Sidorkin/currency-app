# Currency App

On start it downloads currency codes and corresponding currency names from an external API and stores them to DB.
You can operate the app and get json responses using the following endpoints:

```GET: /currencies``` — get all available currencies codes list along with their names<br>
```GET: /rates/{currency_code}``` — get the current rate for the specified currency code<br>
```GET: /rates/{currency code}/history?date_from=yyyy-mm-dd&date_to=yyyy-mm-dd``` — get historical rates 
for the specified currency code within specified dates range as request parameters<br>

All the requests get stored to H2 Db as two types of entities: Currency(code{also used as an id}, name) 
and Rate(id, date, current rate, currency_id).
