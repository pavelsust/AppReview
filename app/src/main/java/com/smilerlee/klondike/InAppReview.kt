//package com.smilerlee.klondike
//
//import android.app.Activity
//import android.util.Log
//import com.google.android.gms.tasks.Task
//import com.google.android.play.core.review.ReviewInfo
//import com.google.android.play.core.review.ReviewManagerFactory
//
//class InAppReview {
//
//    fun askUserForReview(activity: Activity){
//        var reviewManager = ReviewManagerFactory.create(activity)
//        var request : Task<ReviewInfo> = reviewManager.requestReviewFlow()
//        request.addOnCompleteListener{task ->
//            Log.d("APP_STATUS", "Review Error is -> ${request.isSuccessful}")
//
//            try {
//                if (request.isSuccessful){
//                    val reviewInfo = task.result
//                    val reviewFlow : Task<Void> = reviewManager.launchReviewFlow(activity, reviewInfo)
//                    reviewFlow.addOnCompleteListener{ task1 ->
//                        Log.d("APP_STATUS", "Task1 -> ${task1.isSuccessful}")
//                    }.addOnFailureListener { error1 ->
//                        Log.d("APP_STATUS", "Review Error is -> ${error1.message.toString()}")
//                    }
//                }else{
//                    Log.d("APP_STATUS", "Review Error is ->  ${request.exception.toString()}")
//                }
//            } catch (e : Exception){
//                Log.d("APP_STATUS", "Review Error is -> ${e.message.toString()}")
//            }
//        }.addOnFailureListener { error ->
//            Log.d("APP_STATUS", "Review Error is -> ${error.message.toString()}")
//        }
//    }
//}