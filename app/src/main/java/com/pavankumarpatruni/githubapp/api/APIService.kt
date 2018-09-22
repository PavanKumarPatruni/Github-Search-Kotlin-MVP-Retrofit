package com.pavankumarpatruni.githubapp.api

import com.pavankumarpatruni.githubapp.api.models.GetRepos
import com.pavankumarpatruni.githubapp.api.models.Item
import com.pavankumarpatruni.githubapp.api.models.Owner
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET(Constants.GET_REPOS)
    fun getRepos(@Query("q") q: String,
                 @Query("sort") sort: String,
                 @Query("order") order: String,
                 @Query("per_page") per_page: Int,
                 @Query("page") page: Int): Call<GetRepos>

    @GET(Constants.GET_CONTRIBUTORS)
    fun getContributors(@Path("ownerName") ownerName: String, @Path("repoName") repoName: String): Call<List<Owner>>

    @GET(Constants.GET_REPOS_BY_NAME)
    fun getReposByName(@Path("ownerName") ownerName: String): Call<List<Item>>

}