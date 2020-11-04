package com.yogesh.newsapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.yogesh.newsapp.api.NewsApiService
import com.yogesh.newsapp.data.Articles
import com.yogesh.newsapp.data.MyArticle
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),Callback<MyArticle>, CellClickListener {

    private val data=ArrayList<Articles>()
    lateinit var mGoogleSignInClient:GoogleSignInClient
    lateinit var preferences: SharedPreferences
    lateinit var layoutManager:LinearLayoutManager
    var pageId=1
    var visibleItemCount:Int=0
    var pastVisibleItemCount:Int=0
    var totalItemCount:Int=0
    var totalResult:Int=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutManager=LinearLayoutManager(this)
        preferences=getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

       //getTopHighlight()
        getEverything()
        //getSources()

        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=NewsAdapter(data,this)


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                 if (dy>0){
                    //visibleItemCount=layoutManager.childCount
                    totalItemCount=layoutManager.itemCount
                    pastVisibleItemCount=layoutManager.findFirstVisibleItemPosition()
                    if ((totalResult>totalItemCount) &&(pastVisibleItemCount>=totalItemCount-5)){
                        pageId++
                        getTopHighlight()
                    }
                 }
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })





        fab.setOnClickListener {
            SignOut()
        }

    }
    private fun getSources(){
        val newsData=NewsApiService.onCreate()
        newsData.getsources("f08dd0c9c42b4fe8964e9e1d1b9a42d2").enqueue(this)
    }
    private fun getEverything(){
        val newsData=NewsApiService.onCreate()
        newsData.getEverything("bitcoin","f08dd0c9c42b4fe8964e9e1d1b9a42d2",pageId).enqueue(this)
    }

    private fun getTopHighlight() {
        //val header = HashMap<String, String>()
       // header["country"] = "in"
       // header["apiKey"] = "fbdd144b897d474d8f7aa6c48081080e"
        //header["page"]= pageId.toString()
        val newsData=NewsApiService.onCreate()
        newsData.getTopHighlight("in","f08dd0c9c42b4fe8964e9e1d1b9a42d2",pageId).enqueue(this)
    }

    override fun onFailure(call: Call<MyArticle>, t: Throwable) {}

    override fun onResponse(call: Call<MyArticle>, response: Response<MyArticle>) {
        val res=response.body()
        if (res != null) {
            totalResult=res.totalResults
        }
        if (res!=null){
            data.clear()
            data.addAll(res.articles)
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun setUpAdapter() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
               // if (dy>0){
                    visibleItemCount=layoutManager.childCount
                    totalItemCount=layoutManager.itemCount
                    pastVisibleItemCount=layoutManager.findFirstVisibleItemPosition()
                    if ((totalResult>totalItemCount) &&(pastVisibleItemCount>=totalItemCount-5)){
                        pageId++
                       // getTopHighlight()
                    }
               // }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun SignOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                val editor:SharedPreferences.Editor=preferences.edit()
                editor.putBoolean("LOGGED",false)
                editor.apply()
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
    }
    override fun onCellClickListener(data: Articles) {
        val url = data.url
        val intent=Intent(this,FullStoryActivity::class.java)
        intent.putExtra("STORY",url)
        startActivity(intent)
    }
}
