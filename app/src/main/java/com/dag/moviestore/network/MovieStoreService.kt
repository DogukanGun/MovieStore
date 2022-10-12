package com.dag.moviestore.network

import retrofit2.http.*

interface MovieStoreService {
    @POST
    fun genericPostRequest(@Url url: String?, @Body body:Any? = {}): BaseResult<BaseResponse?>

    @GET
    fun genericGetRequest(@Url url: String?): BaseResult<BaseResponse>

    @PUT
    fun genericPutRequest(@Url url: String?, @Body body:Any? = {}): BaseResult<BaseResponse?>

    @DELETE
    fun genericDeleteRequest(@Url url: String?): BaseResult<BaseResponse>

}