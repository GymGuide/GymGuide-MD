package com.example.gymguide.data

object TrainerDataSource {
    var trainers: List<Trainer> = generateDummyTrainers()
    private fun generateDummyTrainers(): ArrayList<Trainer> {
        val trainers: ArrayList<Trainer> = ArrayList()
        trainers.add(Trainer("Monkey D. Luffy", "SHP01", "https://reqres.in/img/faces/1-image.jpg", 4.3))
        trainers.add(Trainer("Roronoa Zoro", "SHP02", "https://reqres.in/img/faces/2-image.jpg", 4.5))
        trainers.add(Trainer("Nami", "SHP03", "https://reqres.in/img/faces/3-image.jpg", 5.0))
        trainers.add(Trainer("Sanji", "SHP05", "https://reqres.in/img/faces/4-image.jpg", 2.0))
        trainers.add(Trainer("Tony Tony Chopper", "SHP06", "https://reqres.in/img/faces/5-image.jpg", 3.0))
        trainers.add(Trainer("Nico Robin", "SHP07", "https://reqres.in/img/faces/6-image.jpg", 4.0))
        trainers.add(Trainer("Usopp", "SHP04", "https://reqres.in/img/faces/7-image.jpg", 5.0))
        trainers.add(Trainer("Franky", "SHP08", "https://reqres.in/img/faces/8-image.jpg", 3.7))
        trainers.add(Trainer("Brook", "SHP09", "https://reqres.in/img/faces/9-image.jpg", 3.8))
        trainers.add(Trainer("Jimbei", "SHP010", "https://reqres.in/img/faces/10-image.jpg", 3.9))
        return trainers
    }
}