package com.fpr.appcoink.domain.home

import com.fpr.appcoink.core.Result
import com.fpr.appcoink.data.model.Post
import com.fpr.appcoink.data.remote.home.HomeScreenDataSource

class HomeScreenReporImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {

    override suspend fun getLatesPost(): Result<List<Post>> = dataSource.getLastestPosts()
}