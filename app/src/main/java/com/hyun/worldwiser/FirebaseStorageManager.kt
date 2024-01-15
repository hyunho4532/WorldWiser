package com.hyun.worldwiser

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.*

class FirebaseStorageManager {
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = storage.reference
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun uploadImage(bitmap: Bitmap?, onComplete: (imageUrl: String) -> Unit) {
        val imageRef = storageReference.child("images/${auth.currentUser!!.uid}.jpg")

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageData = byteArrayOutputStream.toByteArray()

        val uploadTask = imageRef.putBytes(imageData)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { exception ->
                    throw exception
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                onComplete(downloadUri.toString())
            } else {

            }
        }
    }
}