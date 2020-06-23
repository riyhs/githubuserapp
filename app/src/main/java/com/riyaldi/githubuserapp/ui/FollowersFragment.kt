package com.riyaldi.githubuserapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.adapter.UserRecyclerViewAdapter
import com.riyaldi.githubuserapp.data.User
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_followers.*
import org.json.JSONArray
import org.json.JSONObject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FollowersFragment : Fragment() {

    private val listFollowers = arrayListOf<User>()

    // LoopJ
    private val client = AsyncHttpClient()
    private val mToken = "6d75636beac96e82445c4ae9e66a1084a7bd38ca"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity: DetailActivity = activity as DetailActivity
        val user: User = activity.getMyData()

        getSimpleUserData(user.username)
        showRecyclerView()
    }

    private fun showRecyclerView() {
        rvFragmentFollowers.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = UserRecyclerViewAdapter(listFollowers)
        }
    }

    private fun getSimpleUserData(login: String) {
        pbFollowers.visibility = View.VISIBLE

        val urlSearch = "https://api.github.com/users/$login/followers"

        client.addHeader("Authorization", "token $mToken")
        client.addHeader("User-Agent", "request")

        client.get(urlSearch, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                pbFollowers.visibility = View.INVISIBLE

                val result = responseBody?.let { String(it) }

                try {
                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()) {
                        val item = responseArray.getJSONObject(i)
                        val username = item.getString("login")

                        getDetailUserData(username)
                    }

                    rvFragmentFollowers.adapter = UserRecyclerViewAdapter(listFollowers)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                pbFollowers.visibility = View.INVISIBLE

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

    private fun getDetailUserData(username: String) {
        pbFollowers.visibility = View.VISIBLE
        val urlDetailUser = "https://api.github.com/users/$username"

        client.addHeader("Authorization", "token $mToken")
        client.addHeader("User-Agent", "request")

        client.get(urlDetailUser, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                pbFollowers.visibility = View.INVISIBLE

                val result = responseBody?.let { String(it) }

                try {
                    val responseObject = JSONObject(result)

                    val name = responseObject.getString("name")
                    val company = responseObject.getString("company")
                    val location = responseObject.getString("location")
                    val bio = responseObject.getString("bio")
                    val followers = responseObject.getString("followers")
                    val following = responseObject.getString("following")
                    val followersUrl = responseObject.getString("followers_url")
                    val followingUrl = responseObject.getString("following_url")
                    val repositories = responseObject.getString("public_repos")
                    val photoUrl = responseObject.getString("avatar_url")

                    val user = User(
                        name = name,
                        username = username,
                        company = company,
                        location = location,
                        bio = bio,
                        followers = followers,
                        following = following,
                        followersUrl = followersUrl,
                        followingUrl = followingUrl,
                        repositories = repositories,
                        photoUrl = photoUrl
                    )
                    listFollowers.add(user)
                    rvFragmentFollowers.adapter = UserRecyclerViewAdapter(listFollowers)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                pbFollowers.visibility = View.INVISIBLE

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