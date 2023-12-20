package com.example.gymguide.ui

object ExerciseConstants {
    val classes = arrayOf(
        "abdominal-machine",
        "arm-curl",
        "arm-extension",
        "back-extension",
        "back-row-machine",
        "bench-press",
        "cable-lat-pulldown",
        "chest-fly",
        "chest-press",
        "dip-chin-assist",
        "hip-abduction-adduction",
        "incline-bench",
        "lat-pulldown",
        "leg-extension",
        "leg-press",
        "lying-down-leg-curl",
        "overhead-shoulder-press",
        "pulley-machine",
        "seated-cable-row",
        "seated-leg-curl",
        "smith-machine",
        "squat-rack",
        "torso-rotation-machine"
    )

    val classesText = arrayOf(
        "Abdominal Machine",
        "Arm Curl",
        "Arm Extension",
        "Back Extension",
        "Back Row Machine",
        "Bench Press",
        "Cable Lat Pulldown",
        "Chest Fly",
        "Chest Press",
        "Dip Chin Assist",
        "Hip Abduction Adduction",
        "Incline Bench",
        "Lat Pulldown",
        "Leg Extension",
        "Leg Press",
        "Lying Down Leg Curl",
        "Overhead Shoulder Press",
        "Pulley Machine",
        "Seated Cable Row",
        "Seated Leg Curl",
        "Smith Machine",
        "Squat Rack",
        "Torso Rotation Machine"
    )

    val classToTextMap: Map<String, String> = classes.zip(classesText).toMap()
}