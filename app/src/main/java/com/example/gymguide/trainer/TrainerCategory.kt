package com.example.gymguide.trainer

import android.os.Bundle

/**
 * Category item representing different exercise categories
 */
class TrainerCategory private constructor(val category: String) {

    /** Use in conjunction with [TrainerCategory.fromBundle]  */
    fun toBundle(): Bundle {
        val args = Bundle(1)
        args.putString(ARGS_BUNDLE, category)
        return args
    }

    override fun toString(): String {
        return category
    }

    companion object {
        internal val ARGS_BUNDLE = TrainerCategory::class.java.name + ":Bundle"

        val CATEGORIES = listOf("General", "Cardio", "Strength", "Yoga", "Lifestyle")

        /** Use in conjunction with [TrainerCategory.toBundle]  */
        fun fromBundle(bundle: Bundle): TrainerCategory {
            val category = bundle.getString(ARGS_BUNDLE)
            return TrainerCategory(category!!)
        }
    }
}
