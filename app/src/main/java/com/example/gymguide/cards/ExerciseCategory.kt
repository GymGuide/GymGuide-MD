package com.example.gymguide.cards

import android.os.Bundle

/**
 * Category item representing different exercise categories
 */
class ExerciseCategory private constructor(val category: String) {

    /** Use in conjunction with [ExerciseCategory.fromBundle]  */
    fun toBundle(): Bundle {
        val args = Bundle(1)
        args.putString(ARGS_BUNDLE, category)
        return args
    }

    override fun toString(): String {
        return category
    }

    companion object {
        internal val ARGS_BUNDLE = ExerciseCategory::class.java.name + ":Bundle"

        val CATEGORIES = listOf("General", "Cardio", "Strength", "Yoga", "Lifestyle")

        /** Use in conjunction with [ExerciseCategory.toBundle]  */
        fun fromBundle(bundle: Bundle): ExerciseCategory {
            val category = bundle.getString(ARGS_BUNDLE)
            return ExerciseCategory(category!!)
        }
    }
}
