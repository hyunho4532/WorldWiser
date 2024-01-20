package com.hyun.worldwiser

import android.graphics.Bitmap
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class FirebaseStorageManager {
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = storage.reference

    fun uploadImages(bitmaps: List<Bitmap?>, onComplete: (List<Uri>) -> Unit) {
        val imageUrls = mutableListOf<Uri>()

        val uploadTasks = mutableListOf<Task<*>>()

        for ((index, bitmap) in bitmaps.withIndex()) {
            if (bitmap != null) {
                val imageRef = storageReference.child("images/${System.currentTimeMillis()}_${index + 1}.jpg")

                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val imageData = byteArrayOutputStream.toByteArray()

                val uploadTask = imageRef.putBytes(imageData)

                uploadTasks.add(uploadTask)

                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { exception ->
                            throw exception
                        }
                    }
                    imageRef.downloadUrl
                }.addOnSuccessListener { uri ->
                    imageUrls.add(uri)
                    onComplete(imageUrls)
                }
            }
        }

        Tasks.whenAll(uploadTasks)
            .addOnFailureListener { exception ->
                // 이미지 업로드 중 실패한 경우 처리
            }
    }

}
