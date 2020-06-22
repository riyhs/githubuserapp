package com.riyaldi.githubuserapp.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.adapter.UserRecyclerViewAdapter
import com.riyaldi.githubuserapp.data.User
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity() {

    private var list = ArrayList<User>()

    // LoopJ
    private val client = AsyncHttpClient()
    private val mToken = "6d75636beac96e82445c4ae9e66a1084a7bd38ca"

    companion object{
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pbMain.visibility = View.INVISIBLE

        searchUser()
        showRecyclerList()
    }

    private fun searchUser(){
        svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.isNotEmpty()) {
                        pbMain.visibility = View.VISIBLE
                        list.clear()
                        getSimpleUserData(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun getSimpleUserData(searchText: String) {
        pbMain.visibility = View.VISIBLE

        val urlSearch = "https://api.github.com/search/users?q=$searchText"

        client.addHeader("Authorization", "token $mToken")
        client.addHeader("User-Agent", "request")

        client.get(urlSearch, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                pbMain.visibility = View.INVISIBLE

                val result = responseBody?.let { String(it) }
                Log.d(TAG, result)

                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")

                        getDetailUserData(username)
                    }

                    rvMain.adapter = UserRecyclerViewAdapter(list)
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                pbMain.visibility = View.INVISIBLE

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getDetailUserData(username: String) {
        pbMain.visibility = View.VISIBLE
        val urlDetailUser = "https://api.github.com/users/$username"

        client.addHeader("Authorization", "token $mToken")
        client.addHeader("User-Agent", "request")

        client.get(urlDetailUser, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                pbMain.visibility = View.INVISIBLE

                val result = responseBody?.let { String(it) }
                Log.d(TAG, result)

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
                    list.add(user)
                    rvMain.adapter =
                        UserRecyclerViewAdapter(
                            list
                        )
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                pbMain.visibility = View.INVISIBLE

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showRecyclerList() {
        rvMain.setHasFixedSize(true)
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = UserRecyclerViewAdapter(list)
    }
}