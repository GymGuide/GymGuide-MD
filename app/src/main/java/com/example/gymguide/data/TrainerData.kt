package com.example.gymguide.data

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file DataSource, 18/02/2023 21.09 by Muammar Ahlan Abimanyu
 */
object DataSource {
    var trainers: List<Trainer> = generateDummyStudents()
    private fun generateDummyStudents(): ArrayList<Trainer> {
        val trainers: ArrayList<Trainer> = ArrayList()
        trainers.add(Trainer("Monkey D. Luffy", "SHP01"))
        trainers.add(Trainer("Roronoa Zoro", "SHP02"))
        trainers.add(Trainer("Nami", "SHP03"))
        trainers.add(Trainer("Sanji", "SHP05"))
        trainers.add(Trainer("Tony Tony Chopper", "SHP06"))
        trainers.add(Trainer("Nico Robin", "SHP07"))
        trainers.add(Trainer("Usopp", "SHP04"))
        trainers.add(Trainer("Franky", "SHP08"))
        trainers.add(Trainer("Brook", "SHP09"))
        trainers.add(Trainer("Jimbei", "SHP010"))
        return trainers
    }
}