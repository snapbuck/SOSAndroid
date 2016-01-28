package vn.snapbuck.sos.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import vn.snapbuck.sos.app.SBApp;


/**
 * Created by Peter on 5/12/15.
 */
public class FileUtils {
    public static File getAppMediaStorageDir() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + SBApp.PACKAGE_NAME
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        return mediaStorageDir;
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(String fileName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + SBApp.PACKAGE_NAME
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return mediaFile;
    }

    public static File getOutputMediaFilePath(String fileName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + SBApp.PACKAGE_NAME
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return mediaFile;
    }

    public static File getGameFilePath(String fileName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + SBApp.PACKAGE_NAME
                + "/Files/Game");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return mediaFile;
    }

    public static File getThumbnailMediaFile(String fileName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + SBApp.PACKAGE_NAME
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "thumb_" + fileName);
        return mediaFile;
    }

    public static String getOutputLogFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + SBApp.PACKAGE_NAME
                + "/Logs");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        String logFileName = sdf.format(new Date(0)) + ".log";
        String logFile;
        logFile = mediaStorageDir.getPath() + File.separator + logFileName;
        return logFile;
    }

    public static String getFunctionLogFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + SBApp.PACKAGE_NAME
                + "/Logs");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        String logFileName = sdf.format(new Date(0)) + ".logger";
        String logFile;
        logFile = mediaStorageDir.getPath() + File.separator + logFileName;
        return logFile;
    }

    public static void copy(File src, File dst) {
        try {
            InputStream in = new FileInputStream(src);

            OutputStream out = new FileOutputStream(dst);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean saveImage(URL url, File file) {
        boolean result = false;
        try {
            URLConnection urlConnection = url.openConnection();
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
            }
            fileOutput.close();
            if (downloadedSize == totalSize)
                result = true;
        } catch (MalformedURLException e) {
            Log.d("saveFile", e.getMessage());
        } catch (Exception e) {
            Log.d("saveFile", e.getMessage());
        }
        return result;
    }

    public static void downloadFile(URL url, File file) {
        try {

            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayBuffer baf = new ByteArrayBuffer(64);
            int current = 0;
            while ((current = bis.read()) != -1)
                baf.append((byte) current);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baf.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {

        }
    }

    public static void downloadImageFile(URL url, String filePath) {
        try {
            URLConnection conn = url.openConnection();
            int contentLength = conn.getContentLength();
            DataInputStream stream = new DataInputStream(url.openStream());
            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();
            DataOutputStream fos = new DataOutputStream(new FileOutputStream(filePath));
            fos.write(buffer);
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    public static void downloadMediaFile(URL url, File filePath) {

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();

            FileOutputStream fileOutput = new FileOutputStream(filePath);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;

            }
            //close the output stream when complete //
            fileOutput.close();


        } catch (final MalformedURLException e) {
            e.printStackTrace();
        } catch (final IOException e) {
        } catch (final Exception e) {
        }
    }

    public static String getFileNameFromAzureUrl(String url){
        return url.substring(url.lastIndexOf('/') + 1, url.indexOf('?'));

    }

    public static void deletePhoto(Context context, File file){
//        String[] projection = { MediaStore.Images.Media._ID };
//
//// Match on the file path
//        String selection = MediaStore.Images.Media.DATA + " = ?";
//        String[] selectionArgs = new String[] { file.getAbsolutePath() };
//
//// Query for the ID of the media matching the file path
//        Uri queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        ContentResolver contentResolver = SBApp.getSBAppContext().getContentResolver();
//        Cursor c = contentResolver.query(queryUri, projection, selection, selectionArgs, null);
//        if (c.moveToFirst()) {
//            // We found the ID. Deleting the item via the content provider will also remove the file
//            long id = c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
//            Uri deleteUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
//            contentResolver.delete(deleteUri, null, null);
//        } else {
//            // File not found in media store DB
//        }
//        c.close();
        try {

            context.getContentResolver() .delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    MediaStore.Images.Media.DATA
                            + "='"
                            + file.getPath()
                            + "'", null);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        int index = 0;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(index, absolutePathOfImage);
        }
        ArrayList<String> tempElements = new ArrayList<String>(listOfAllImages);
        return tempElements;
    }

    public static boolean saveBitmapToFile(File filePath, Bitmap bm) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            bm.compress(Bitmap.CompressFormat.JPEG, 70,
                    fos);
            fos.flush();
            fos.close();
            fos = null;
            System.gc();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static File convertImageUriToFile (Uri imageUri, Activity activity)  {
        if(imageUri!=null) {
            if(imageUri.toString().contains("file://")){
                return new File(imageUri.toString().replace("file://",""));
            }else {
                Cursor cursor = null;
                try {
                    String[] proj = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION};
                    cursor = activity.getContentResolver().query(imageUri,
                            proj, // Which columns to return
                            null,       // WHERE clause; which rows to return (all rows)
                            null,       // WHERE clause selection arguments (none)
                            null); // Order-by clause (ascending by name)
                    int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    int orientation_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);
                    if (cursor.moveToFirst()) {
                        String orientation = cursor.getString(orientation_ColumnIndex);
                        return new File(cursor.getString(file_ColumnIndex));
                    }
                    return null;
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }else{
            return null;
        }
    }

    public static String getFileName(String fullPath){
        return fullPath.substring(fullPath.lastIndexOf("/")+1).replace("%20"," ");
    }

    public static String getFileExtension(String name) {
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    public static byte[] convertFileToByteArray(String filePath)
    {
        byte[] byteArray = null;
        try
        {
            InputStream inputStream = new FileInputStream(new File(filePath));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024*8];
            int bytesRead =0;

            while ((bytesRead = inputStream.read(b)) != -1)
            {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }


}
