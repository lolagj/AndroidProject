package com.fpr.appcoink.domain.home

import com.fpr.appcoink.core.Result
import com.fpr.appcoink.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatesPost(): Result<List<Post>>
}