package com.example.mft_public


import LoginModel
import MessageModel
import RequestMessageModel
import UserModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by ahad on 11/29/2024.
 */
interface ApiService {

    @POST("signup")
    fun signup(@Body userModel: UserModel): Call<ResponseBody>

    @POST("login")
    fun login(@Body loginModel: LoginModel): Call<UserModel>

    @GET("messages")
    fun getMessages(
        @Query("userId") userId: Int,
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<List<MessageModel>>

    @POST("message")
    fun sendAMessage(
        @Query("userId") userId: Int,
        @Query("username") username: String,
        @Query("password") password: String,
        @Body requestMessageModel: RequestMessageModel
    ): Call<ResponseBody>

    @GET("singlemessage")
    fun getSingleMessage(
        @Query("userId") userId: Int,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("messageId") messageId: Int
    ): Call<MessageModel>

    @POST("comment")
    fun sendComment(
        @Query("userId") userId: Int,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("messageId") messageId: Int,
        @Body sendCommentModel: SendCommentModel
    ): Call<ResponseBody>

    @POST("like")
    fun sendLike(
        @Query("userId") userId: Int,
        @Query("username") username: String,
        @Query("password") password: String,
        @Body sendLikeModel: SendLikeModel
    ) : Call<ResponseBody>

}