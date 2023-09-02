package com.aurora.store.viewmodel.review

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.data.models.Review
import com.aurora.gplayapi.data.models.ReviewCluster
import com.aurora.gplayapi.helpers.ReviewsHelper
import com.aurora.store.data.RequestState
import com.aurora.store.data.network.HttpClient
import com.aurora.store.data.providers.AuthProvider
import com.aurora.store.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ReviewViewModel(application: Application) : BaseAndroidViewModel(application) {

    var authData: AuthData = AuthProvider
        .with(application)
        .getAuthData()

    var reviewsHelper: ReviewsHelper = ReviewsHelper(authData)
        .using(HttpClient.getPreferredClient())

    val liveData: MutableLiveData<ReviewCluster> = MutableLiveData()

    private lateinit var reviewsCluster: ReviewCluster

    fun fetchReview(packageName: String, filter: Review.Filter) {
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    reviewsCluster = reviewsHelper.getReviews(packageName, filter)
                    liveData.postValue(reviewsCluster)
                } catch (e: Exception) {
                    requestState = RequestState.Pending
                }
            }
        }
    }

    fun next(nextReviewPageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                try {
                    val nextReviewCluster = reviewsHelper.next(nextReviewPageUrl)

                    reviewsCluster.apply {
                        reviewList.addAll(nextReviewCluster.reviewList)
                        nextPageUrl = nextReviewCluster.nextPageUrl
                    }

                    liveData.postValue(reviewsCluster)
                } catch (e: Exception) {
                    requestState = RequestState.Pending
                }
            }
        }
    }

    override fun observe() {

    }
}