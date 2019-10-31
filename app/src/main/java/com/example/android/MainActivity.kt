    package com.example.android
    import android.database.Cursor
    import android.graphics.Bitmap
    import android.graphics.BitmapFactory
    import android.net.Uri
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.provider.MediaStore
    import android.util.Log
    import com.google.android.gms.tasks.Task
    import com.google.firebase.ml.vision.FirebaseVision
    import com.google.firebase.ml.vision.common.FirebaseVisionImage
    import com.google.firebase.ml.vision.text.FirebaseVisionText
    import android.provider.MediaStore.MediaColumns
    import android.content.pm.PackageManager
    import android.os.Environment
    import android.widget.Toast
    import java.io.File
    import java.io.FileOutputStream
   // import java.io.IOException
    import android.os.Build
    import android.widget.Button
    import android.widget.TextView
    // import android.widget.SeekBar
    import androidx.core.app.ActivityCompat
    import androidx.core.content.ContextCompat
   // import kotlinx.android.synthetic.main.activity_main.*
    import java.io.FileInputStream
    import java.nio.channels.FileChannel


    class MainActivity : AppCompatActivity() {
        private val PERMISSION_REQUEST_CODE = 1
        var imagess = HashMap<Bitmap,String>();
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            var images = ArrayList<Int>();

            images.add(R.drawable.honoer)
            images.add(R.drawable.kranthi)
            images.add(R.drawable.ki)

             if (Build.VERSION.SDK_INT >= 23)
            {
                if (checkPermission())
                {
                    var uri: Uri;
            var cursor: Cursor?;
            var column_index_data: Int;
            var column_index_folder_name: Int;
            var listOfAllImages = ArrayList<String>();
            var absolutePathOfImage: String? = null;
            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            val projection = arrayOf(MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            cursor = applicationContext.contentResolver.query(
            uri, projection, null,
            null, null
            );
            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

                        while (cursor.moveToNext()) {
                            absolutePathOfImage = cursor.getString(column_index_data);
                            var file = File(absolutePathOfImage)
                            if (file.exists()) {
                                val bitmaps = BitmapFactory.decodeFile(file.absolutePath);
                                Log.i("bitss", bitmaps.toString())
                               //stiii(bitmaps, file)
                                imagess.put(bitmaps,file.absolutePath)
                            }
                        }

                stii()

                } else {
    requestPermission();
    }
    }
    else
    {


    }


    }

    fun checkPermission():Boolean{
    var result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
    if (result == PackageManager.PERMISSION_GRANTED) {
    return true;
    } else {
    return false;
    }
    }

    fun requestPermission():Unit{
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
    Toast.makeText(this, "  permission ", Toast.LENGTH_LONG).show();
    } else {
    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE);
    }
    }

    fun onRequestPermissionResult(requestCode:Int, grantResults:ArrayList<Int>,permission:ArrayList<String>){
    when (requestCode) {
    PERMISSION_REQUEST_CODE ->
    if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Log.e("value", "Permission Granted.");
    } else {
        Log.e("value", "Permission Denied ");
    }
    }
    }

        fun stii(){
            for (i in imagess.keys) {
                Log.i("hello","check loop")
                val detector = FirebaseVision.getInstance()
                    .onDeviceTextRecognizer
                     val im = FirebaseVisionImage.fromBitmap(i)
                    Log.i("bitmap",i.toString())
                    var result: Task<FirebaseVisionText> = detector.processImage(im)
                    .addOnSuccessListener { firebaseVisionText ->
                        var blocks: List<FirebaseVisionText.TextBlock> = firebaseVisionText.textBlocks
                        if (!(blocks.size == 0)) {

                            var text:String = ""
                            for (i in firebaseVisionText.textBlocks) {
                                text += i.text;
                                Log.i("text", text);

                            }
                                var files = File(imagess[i]);
                                    if(files.exists()){
                                        if (text.contains("good morning") || text.contains("GOOD MORNING")) {
                                            files.delete()

                                        }
                                    }
                               // var newfile = File(Environment.getExternalStorageDirectory(), "kittus")
                              /*  if (!newfile.exists()) {
                                    newfile.mkdir()
                                } else {
                                    copyOrMoveFile(files, newfile, false)
                                }*/


                        } else {
                            Log.i("hell", "text not found")
                        }
                    }
                    .addOnFailureListener {
                        Log.e("kittu", "failed")


                    }
            }
        }

          fun copyOrMoveFile( file:File,dir:File,isCopy:Boolean)  {
        var newFile =  File(dir,file.name);
     var outChannel:FileChannel ;
     var inputChannel:FileChannel ;
    try {
        outChannel = FileOutputStream(newFile).getChannel();
        inputChannel =  FileInputStream(file).getChannel();


        inputChannel.transferTo(0, inputChannel.size(), outChannel);
        inputChannel.close();
        if(!isCopy)
        file.delete();
    } finally {
       print("hi")
    }
}
        fun stiii(i:Bitmap,f:File){
        val detector = FirebaseVision.getInstance()
            .onDeviceTextRecognizer
        val im = FirebaseVisionImage.fromBitmap(i)
        Log.i("bitmap",i.toString())
        var result: Task<FirebaseVisionText> = detector.processImage(im)
            .addOnSuccessListener { firebaseVisionText ->
                var blocks: List<FirebaseVisionText.TextBlock> = firebaseVisionText.textBlocks
                if (!(blocks.size == 0)) {

                    var text:String = ""
                    for (i in firebaseVisionText.textBlocks) {
                        text += i.text;
                        Log.i("text", text);

                    }
                    if (text.contains("good morning") || text.contains("GOOD MORNING") ) {
                        f.delete()

                    }
                    else{
                     /*   var files = File(f.absolutePath)

                        var newfile = File(Environment.getExternalStorageDirectory(), "memes")
                        if (!newfile.exists()) {
                            newfile.mkdir()
                        } else {

                            copyOrMoveFile(files, newfile, false)
                        }*/
                    }

                } else {
                    Log.i("hell", "text not found")
                }
            }
            .addOnFailureListener {
                Log.e("kittu", "failed")


            }
    }
    }



/*
for (i in firebaseVisionText.textBlocks) {
                                text += i.text;
                                Log.i("text", text);

                            }
 */