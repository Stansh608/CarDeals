package com.example.cardeals.models

class CarData {
    var make: String?=null
    var model:String?=null
    var year: String?=null
    var kilometres: String?=null
    var wheels: String?=null
    var color: String?=null
    var interior: String?=null
    var gear: String?=null
    var seats: String?=null
    var cylinders: String?=null
    var airbags: String?=null
    var description: String?=null
    var image: String?=null
    var owner:String?=null
    var id: String?=null
    var price:String?=null
    var engine:String?=null


    constructor() {}
    constructor(make:String?,
                 model:String?,
                 year: String?,
                engine:String?,
                 kilometres: String?,
                 wheels: String?,
                 color: String?,
                 interior: String?,
                 gear: String?,
                 seats: String?,
                 cylinders: String?,
                 airbags: String?,
                 description: String?,
                 image: String?,
                owner:String?,
                id:String?,
                price:String?){
        this.make=make
        this.model =model
        this.year=year
        this.kilometres=kilometres
        this.wheels=wheels
        this.color=color
        this.interior=interior
        this.gear=gear
        this.seats=seats
        this.cylinders=cylinders
        this.airbags=airbags
        this.description=description
        this.image=image
        this.owner=owner
        this.id=id
        this.price=price
        this.engine=engine
}
}