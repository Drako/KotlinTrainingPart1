# Homework

* A person has the properties `firstName` and `lastName`.

* Add a new controller handling people
    * GET /person -> get a list of known persons (with pagination: offset & limit)
    * POST /person -> add new person and return Location header with URL
    * GET /person/{id} -> get a single person
    * DELETE /person/{id} -> delete the specified person
    * PUT /person/{id} -> update specified person

All of this without a Database.

* Add tests for the new controller.
