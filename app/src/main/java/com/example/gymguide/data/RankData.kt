package com.example.gymguide.data

object RankDataSource {
    var ranks: List<Rank> = generateDummyRanks()

    private fun generateDummyRanks(): ArrayList<Rank> {
        val ranks: ArrayList<Rank> = ArrayList()

        ranks.add(Rank("Monkey D. Luffy", "SHP01", "https://reqres.in/img/faces/1-image.jpg", (0..5000).random()))
        ranks.add(Rank("Roronoa Zoro", "SHP02", "https://reqres.in/img/faces/2-image.jpg", (0..5000).random()))
        ranks.add(Rank("Nami", "SHP03", "https://reqres.in/img/faces/3-image.jpg", (0..5000).random()))
        ranks.add(Rank("Sanji", "SHP05", "https://reqres.in/img/faces/4-image.jpg", (0..5000).random()))
        ranks.add(Rank("Tony Tony Chopper", "SHP06", "https://reqres.in/img/faces/5-image.jpg", (0..5000).random()))
        ranks.add(Rank("Nico Robin", "SHP07", "https://reqres.in/img/faces/6-image.jpg", (0..5000).random()))
        ranks.add(Rank("Usopp", "SHP04", "https://reqres.in/img/faces/7-image.jpg", (0..5000).random()))
        ranks.add(Rank("Franky", "SHP08", "https://reqres.in/img/faces/8-image.jpg", (0..5000).random()))
        ranks.add(Rank("Brook", "SHP09", "https://reqres.in/img/faces/9-image.jpg", (0..5000).random()))
        ranks.add(Rank("Jimbei", "SHP010", "https://reqres.in/img/faces/10-image.jpg", (0..5000).random()))

        return ranks
    }
}
