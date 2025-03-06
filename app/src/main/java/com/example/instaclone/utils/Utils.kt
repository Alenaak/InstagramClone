package com.example.instaclone.utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

private const val TAG = "Utils"
fun uploadImage(uri: Uri,foldername:String,callBack:(String?)->Unit){
    var imageUrl:String?=null
    FirebaseStorage.getInstance().getReference(foldername).child(UUID.randomUUID().toString())
       .putFile(uri)
       .addOnSuccessListener {
          it.storage.downloadUrl.addOnSuccessListener {
            
              imageUrl=it.toString()
              Log.d(TAG, "uploadImage: $imageUrl")
              callBack(imageUrl)
          }
       }

}
fun uploadVideo(uri: Uri, foldername:String,progressDialog: ProgressDialog, context: Context, callBack:(String?)->Unit){
    var videoUrl:String?=null
    progressDialog.setTitle("Uploading...")
    progressDialog.show()
    FirebaseStorage.getInstance().getReference(foldername).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {

                videoUrl=it.toString()
                progressDialog.dismiss()
                Log.d(TAG, "uploadImage: $videoUrl")
                callBack(videoUrl)
            }

        }
        .addOnProgressListener {
            var uploadedValue:Long=it.bytesTransferred/it.totalByteCount
             progressDialog.setMessage("Uploaded $uploadedValue %")
        }

}