package com.pavankumarpatruni.githubapp.api.models

import com.google.gson.annotations.SerializedName

data class GetRepos(
        @SerializedName("total_count") val totalCount: Int,
        @SerializedName("incomplete_results") val incompleteResults: Boolean,
        @SerializedName("items") val items: List<Item>
)