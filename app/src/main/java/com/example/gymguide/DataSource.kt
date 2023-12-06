package com.example.gymguide

object DataSource {
    var students = generateDummyStudents()
    private fun generateDummyStudents(): ArrayList<Student> {
        val students = ArrayList<Student>()
        students.add(Student("Monkey D. Luffy", "SHP01"))
        students.add(Student("Roronoa Zoro", "SHP02"))
        students.add(Student("Nami", "SHP03"))
        students.add(Student("Usopp", "SHP04"))
        students.add(Student("Sanji", "SHP05"))
        students.add(Student("Tony Tony Chopper", "SHP06"))
        students.add(Student("Nico Robin", "SHP07"))
        students.add(Student("Franky", "SHP08"))
        students.add(Student("Brook", "SHP09"))
        students.add(Student("Jimbei", "SHP010"))
        return students
    }
}