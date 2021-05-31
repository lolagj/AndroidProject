package com.fpr.appcoink.data.remote.auth

import android.graphics.Bitmap
import android.net.Uri
import com.fpr.appcoink.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class AuthDataSource {

    suspend fun signIn(email:String,password:String):FirebaseUser?{
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).await()
        return authResult.user
    }

    suspend fun signUp(email: String, password: String, username: String): FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).await()
        authResult.user?.uid?.let{
            FirebaseFirestore.getInstance().collection("users").document(it).set(User(email,username,"Foto_URL.png")).await()
        }
        return authResult.user
    }

    suspend fun updateUserProfile(imageBitmap: Bitmap,username: String){
        val user = FirebaseAuth.getInstance().currentUser
        val imageRef = FirebaseStorage.getInstance().reference.child("${user?.uid}/profile_picture")
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val downloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName("Jane Q. User")
            .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
            .build()

        user?.updateProfile(profileUpdates)?.await()
    }
}