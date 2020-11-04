package com.yogesh.newsapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient:GoogleSignInClient
    lateinit var gso:GoogleSignInOptions
    var TAG="LoginActivity"
    val RC_SIGN_IN=107
    lateinit var sharedPreferences: SharedPreferences
    var isLoggedIn=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        sharedPreferences= getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        isLoggedIn=sharedPreferences.getBoolean("LOGGED",false)
        if (isLoggedIn){
             val intent = Intent(this,MainActivity::class.java)
             startActivity(intent)
             finish()
        }
        signInButton.setOnClickListener {
            SignIn()
        }
    }
    private fun SignIn(){
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
            completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            val editor:SharedPreferences.Editor= sharedPreferences.edit()
            editor.putBoolean("LOGGED",true)
            editor.apply()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }
}
