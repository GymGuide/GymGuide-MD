package com.example.gymguide.data

import com.example.gymguide.R

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file DataSource, 18/02/2023 21.09 by Muammar Ahlan Abimanyu
 */
object TrainerDataSource {
    var trainers: List<Trainer> = generateDummyTrainers()
    private fun generateDummyTrainers(): ArrayList<Trainer> {
        val trainers: ArrayList<Trainer> = ArrayList()

        trainers.add(
            Trainer(
                "Monkey D. Luffy",
                "SHP01",
                "https://reqres.in/img/faces/1-image.jpg",
                4.3,
                "Embark on a fitness adventure with Monkey D. Luffy, your guide to achieving peak fitness. Discover the boundless world of exercise as Luffy takes you on a journey of self-discovery and strength building. Unleash your inner pirate spirit, overcome fitness challenges, and sculpt a physique worthy of the Pirate King himself."
            )
        )

        trainers.add(
            Trainer(
                "Roronoa Zoro",
                "SHP02",
                "https://reqres.in/img/faces/2-image.jpg",
                4.5,
                "Unleash your inner swordsman with Roronoa Zoro. With his expert guidance, you'll learn the art of three-sword style and become a master of the blade."
            )
        )

        trainers.add(
            Trainer(
                "Nami",
                "SHP03",
                "https://reqres.in/img/faces/3-image.jpg",
                5.0,
                "Navigate the seas of fitness with Nami, your expert navigator. She'll help you chart a course to optimal health and wellness."
            )
        )

        trainers.add(
            Trainer(
                "Sanji",
                "SHP05",
                "https://reqres.in/img/faces/4-image.jpg",
                2.0,
                "Join Sanji in the kitchen of fitness. He'll teach you the importance of a balanced diet and the culinary secrets to a healthier lifestyle."
            )
        )

        trainers.add(
            Trainer(
                "Tony Tony Chopper",
                "SHP06",
                "https://reqres.in/img/faces/5-image.jpg",
                3.0,
                "Embrace the spirit of adventure with Tony Tony Chopper. His unique approach to fitness combines strength training with a touch of reindeer magic."
            )
        )

        trainers.add(
            Trainer(
                "Nico Robin",
                "SHP07",
                "https://reqres.in/img/faces/6-image.jpg",
                4.0,
                "Unlock the mysteries of fitness with Nico Robin. Her expertise will guide you through a diverse range of exercises for a well-rounded routine."
            )
        )

        trainers.add(
            Trainer(
                "Usopp",
                "SHP04",
                "https://reqres.in/img/faces/7-image.jpg",
                5.0,
                "Embark on a fitness journey with Usopp, the master marksman. His workouts are as accurate and effective as his shots."
            )
        )

        trainers.add(
            Trainer(
                "Franky",
                "SHP08",
                "https://reqres.in/img/faces/8-image.jpg",
                3.7,
                "Get ready to unleash the power of the cola-fueled workouts with Franky. Become a fitness cyborg and transform your body with his unique approach."
            )
        )

        trainers.add(
            Trainer(
                "Brook",
                "SHP09",
                "https://reqres.in/img/faces/9-image.jpg",
                3.8,
                "Dance your way to fitness with the soulful guidance of Brook. His rhythm-infused workouts will have you moving and grooving to a healthier you."
            )
        )

        trainers.add(
            Trainer(
                "Jimbei",
                "SHP010",
                "https://reqres.in/img/faces/10-image.jpg",
                3.9,
                "Dive into fitness with Jimbei, the fish-man karate master. His workouts will help you flow through exercises with the grace of the ocean currents."
            )
        )

        return trainers
    }

    var articles: List<Article> = generateDummyArticles()
    private fun generateDummyArticles(): ArrayList<Article> {
        val articles: ArrayList<Article> = ArrayList()
        articles.add(
            Article(
                name = "Exercise and the Brain",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                pictureUrl = R.drawable.sample_dumbbell
            )
        )
        articles.add(
            Article(
                name = "5 Benefits of HIIT",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                pictureUrl = R.drawable.sample_exercise_photo
            )
        )
        return articles
    }
}