package com.example.doctors.autorization.data

import android.content.res.Resources
import com.example.doctors.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseAuthDataSource(private val auth: FirebaseAuth) {

    private val ioDispatcher = Dispatchers.IO

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("725196008383-kfqqmvg7re1odqvtr87bsrf7ch4uhcra.apps.googleusercontent.com")
        .requestEmail()
        .build()

    fun getUser() : FirebaseUser? = auth.currentUser

    suspend fun signOut() = withContext(ioDispatcher) {
        auth.signOut()
    }

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) : Task<AuthResult> = withContext(ioDispatcher) {
        return@withContext auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result
                }
            }
    }

    suspend fun signInWithGoogle(
        idToken: String) : Task<AuthResult> = withContext(ioDispatcher) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return@withContext auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result
                }
            }
    }

}