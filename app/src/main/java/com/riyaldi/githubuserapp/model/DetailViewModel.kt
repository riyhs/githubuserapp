package com.riyaldi.githubuserapp.model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.riyaldi.githubuserapp.data.User
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailViewModel : ViewModel() {
    val liveDataUser = MutableLiveData<ArrayList<User>>()
    val list = ArrayList<User>()

    fun getUserData(): MutableLiveData<ArrayList<User>> {
        return liveDataUser
    }

    fun setUserData(login: String, context: Context, method: String) {
        val urlSearch = "https://api.github.com/users/$login/$method"
        val client = AsyncHttpClient()
        val mToken = "6d75636beac96e82445c4ae9e66a1084a7bd38ca"

        client.addHeader("Authorization", "token $mToken")
        client.addHeader("User-Agent", "request")

        client.get(urlSearch, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {

                val result = responseBody?.let { String(it) }
                try {
                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()) {
                        val item = responseArray.getJSONObject(i)
                        val username = item.getString("login")
                        getDetailUserData(username, context)
                    }
                    liveDataUser.postValue(list)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getDetailUserData(username: String, context: Context) {
        val urlDetailUser = "https://api.github.com/users/$username"
        val client = AsyncHttpClient()
        val mToken = "6d75636beac96e82445c4ae9e66a1084a7bd38ca"

        client.addHeader("Authorization", "token $mToken")
        client.addHeader("User-Agent", "request")

        client.get(urlDetailUser, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val result = responseBody?.let { String(it) }
                try {
                    val responseObject = JSONObject(result)

                    val user = User(
                        name = responseObject.getString("name"),
                        username = username,
                        company = responseObject.getString("company"),
                        location = responseObject.getString("location"),
                        bio = responseObject.getString("bio"),
                        followers = responseObject.getString("followers"),
                        following = responseObject.getString("following"),
                        followersUrl = responseObject.getString("followers_url"),
                        followingUrl = responseObject.getString("following_url"),
                        repositories = responseObject.getString("public_repos"),
                        photoUrl = responseObject.getString("avatar_url")
                    )
                    list.add(user)
                    liveDataUser.postValue(list)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}