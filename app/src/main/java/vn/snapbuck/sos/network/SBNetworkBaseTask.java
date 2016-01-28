package vn.snapbuck.sos.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Pair;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import vn.snapbuck.sos.app.SBApp;
import vn.snapbuck.sos.log.SBLog;
import vn.snapbuck.sos.utils.BitmapUtils;
import vn.snapbuck.sos.utils.CommonUtils;
import vn.snapbuck.sos.utils.FileUtils;
import vn.snapbuck.sos.utils.HeaderHelper;
import vn.snapbuck.sos.utils.SBConstant;
import vn.snapbuck.sos.utils.SBDateTimeUtils;
import vn.snapbuck.sos.utils.SBEnums;

/**
 * Created by sb4 on 4/20/15.
 */
public abstract class SBNetworkBaseTask extends SBBaseTask {

    private JSONObject _jsonData = new JSONObject();
    private JSONObject _jsonFile = new JSONObject();
    private Pair<String, ArrayList<Integer>> _listIDs;
    private String _queryURL;
    private  String fileUrl;

    private String delimiter = "--";

    @Override
    public JSONObject getResult() {
        SBNetworkResponse response = getSBNetworkResult();
        if (response.success) {
            JSONObject result = response.result;
            _isFail = false;
            return result;
        } else {
            JSONObject result = new JSONObject();
            try {
                result.put("statusCode", response.errorCode);
                result.put("errorMessage", response.errorMessage);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            _isFail = true;
            return result;
        }
    }

    public abstract SBNetworkResponse getSBNetworkResult();

    protected SBNetworkResponse getGoogleResponse() {
        SBNetworkResponse response = new SBNetworkResponse();
        try {
            int timeoutConnection = SBConstant.TIME_OUT;
            int timeoutSocket = SBConstant.TIME_OUT;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters,
                    timeoutSocket);
            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpGet httpGet = new HttpGet();
            URI uri = new URI(this._queryURL);
            httpGet.setURI(uri);
            httpGet.setHeader("User-Agent", SBApp.getUserAgent());
            httpGet.addHeader("Authorization", "Bearer " + SBApp.getAccToken());

            response = client.execute(httpGet, new SBGoogleResponseHandler());
        } catch (ClientProtocolException e) {
            response.errorMessage = e.getMessage();
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            response.errorCode = -101;
            response.errorMessage = "Connect Timeout";
        } catch (SocketTimeoutException e) {
            response.errorCode = -101;
            response.errorMessage = "Socket Timeout";
        } catch (IOException e) {
            response.errorCode = -100;
            response.errorMessage = "No Connect Internet";
        } catch (URISyntaxException e) {
            response.errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            return response;
        }
    }

    protected SBNetworkResponse GET() {
        SBNetworkResponse response = new SBNetworkResponse();
        try {
            int timeoutConnection = SBConstant.TIME_OUT_10;
            int timeoutSocket = SBConstant.TIME_OUT_10;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters,
                    timeoutSocket);
            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpGet httpGet = new HttpGet();
            URI uri = new URI(this._queryURL);
            httpGet.setURI(uri);



            String dateTime = SBDateTimeUtils.getCurrentUTCDateString();
            String auc = HeaderHelper.createAuthorizationValue("GET", dateTime,
                    SBApp.getAccToken(), SBApp.getClientAppID(), 0);
            String agent = SBApp.getUserAgent();
            String usrId = SBApp.getUserId();

            httpGet.setHeader("User-Agent", agent);
            httpGet.addHeader("DateTime", dateTime);
            httpGet.addHeader("Authorization", auc);

            response = client.execute(httpGet, new SBNetworkResponseHandler());

        } catch (ClientProtocolException e) {
            response.errorMessage = e.getMessage();
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            response.errorCode = -101;
            response.errorMessage = "Connect Timeout";
        } catch (SocketTimeoutException e) {
            response.errorCode = -101;
            response.errorMessage = "Socket Timeout";
        } catch (IOException e) {
            response.errorCode = -100;
            response.errorMessage = "No internet connection";//e.getMessage();
            e.printStackTrace();
        } catch (URISyntaxException e) {
            response.errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            return response;
        }

    }

    @SuppressWarnings("finally")
    protected SBNetworkResponse POST() {
        SBNetworkResponse response = new SBNetworkResponse();
        try {
            int timeoutConnection = SBConstant.TIME_OUT_10;
            int timeoutSocket = SBConstant.TIME_OUT_10;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters,
                    timeoutSocket);
            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost();
            URI uri = new URI(this._queryURL);
            httpPost.setURI(uri);

            StringEntity entity = getEntityFromString(_jsonData);

            if (entity != null) {
                httpPost.setEntity(entity);
            }
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");

            String usrid = SBApp.getUserId();
            String dateTime = SBDateTimeUtils.getCurrentUTCDateString();
            String agent = SBApp.getUserAgent();
            httpPost.setHeader("User-Agent", agent);
            httpPost.addHeader("DateTime", dateTime);
            String autho = HeaderHelper.createAuthorizationValue("POST", dateTime,
                    SBApp.getAccToken(), SBApp.getClientAppID(), (int) entity.getContentLength());
            httpPost.addHeader("Authorization", autho);

            response = client.execute(httpPost, new SBNetworkResponseHandler());
        } catch (ClientProtocolException e) {
            response.errorMessage = e.getMessage();
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            response.errorCode = -101;
            response.errorMessage = "Connect Timeout";
        } catch (SocketTimeoutException e) {
            response.errorCode = -101;
            response.errorMessage = "Socket Timeout";
        } catch (IOException e) {
            response.errorCode = -100;
            response.errorMessage = "No Connect Internet";
        } catch (URISyntaxException e) {
            response.errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            return response;
        }
    }

    @SuppressWarnings("finally")
    protected SBNetworkResponse PUT() {
        SBNetworkResponse response = new SBNetworkResponse();
        try {
            int timeoutConnection = SBConstant.TIME_OUT;
            int timeoutSocket = SBConstant.TIME_OUT;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters,
                    timeoutSocket);
            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpPut httpPut = new HttpPut();
            URI uri = new URI(this._queryURL);
            httpPut.setURI(uri);

            StringEntity entity = getEntityFromString(_jsonData);
            if (entity != null) {
                httpPut.setEntity(entity);
            }
            httpPut.setHeader("Content-Type", "application/json");

            String dateTime = SBDateTimeUtils.getCurrentUTCDateString();
            httpPut.setHeader("User-Agent", SBApp.getUserAgent());
            httpPut.addHeader("DateTime", dateTime);
            httpPut.addHeader("Authorization", HeaderHelper.createAuthorizationValue("PUT", dateTime,
                    SBApp.getAccToken(), SBApp.getClientAppID(), (int) entity.getContentLength()));


            response = client.execute(httpPut, new SBNetworkResponseHandler());
        } catch (ClientProtocolException e) {
            response.errorMessage = e.getMessage();
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            response.errorCode = -101;
            response.errorMessage = "Connect Timeout";
        } catch (SocketTimeoutException e) {
            response.errorCode = -101;
            response.errorMessage = "Socket Timeout";
        } catch (IOException e) {
            response.errorCode = -100;
            response.errorMessage = "No Connect Internet";
        } catch (URISyntaxException e) {
            response.errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            return response;
        }
    }

    @SuppressWarnings("finally")
    protected SBNetworkResponse DELETE() {
        SBNetworkResponse response = new SBNetworkResponse();
        try {
            int timeoutConnection = SBConstant.TIME_OUT;
            int timeoutSocket = SBConstant.TIME_OUT;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters,
                    timeoutSocket);

            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpDelete httpDelete = new HttpDelete();
            URI uri = new URI(this._queryURL);
            httpDelete.setURI(uri);
//            httpDelete.addHeader("Authorization", "Bearer " + SBApp.getAccToken());
//            httpDelete.setHeader("User-Agent", SBApp.getUserAgent());

            String dateTime = SBDateTimeUtils.getCurrentUTCDateString();
            httpDelete.setHeader("User-Agent", SBApp.getUserAgent());
            httpDelete.addHeader("DateTime", dateTime);
            httpDelete.addHeader("Authorization", HeaderHelper.createAuthorizationValue("DELETE", dateTime,
                    SBApp.getAccToken(), SBApp.getClientAppID(), 0));

            response = client.execute(httpDelete, new SBNetworkResponseHandler());
        } catch (ClientProtocolException e) {
            response.errorMessage = e.getMessage();
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            response.errorCode = -101;
            response.errorMessage = "Connect Timeout";
        } catch (SocketTimeoutException e) {
            response.errorCode = -101;
            response.errorMessage = "Socket Timeout";
        } catch (IOException e) {
            response.errorCode = -100;
            response.errorMessage = "No Connect Internet";
        } catch (URISyntaxException e) {
            response.errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            return response;
        }
    }

    @SuppressWarnings("finally")
    protected SBNetworkResponse POSTMULTI() {
        SBNetworkResponse response = new SBNetworkResponse();
        String result = "";
        int statusCode = -1;
        String reasonPostMulti = "";

        String boundary = "Snapbuck"
                + Long.toString(System.currentTimeMillis()) + "Snapbuck";
        try {
            byte[] postData;
            if (_listIDs == null) {
                postData = getPostMultiPartData(_jsonData, _jsonFile, boundary);
            } else {
                postData = getPostMultiPartData(_jsonData, _jsonFile, _listIDs, boundary);
            }

            HttpURLConnection con = (HttpURLConnection) (new URL(_queryURL)).openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);

            String dateTime = SBDateTimeUtils.getCurrentUTCDateString();
            String usrAgent = SBApp.getUserAgent();
            String auth =  HeaderHelper.createAuthorizationValue("POST", dateTime,
                    SBApp.getAccToken(), SBApp.getClientAppID(), postData.length);
            String usrId= SBApp.getUserId();
            con.setRequestProperty("User-Agent", usrAgent);
            con.addRequestProperty("DateTime", dateTime);
            con.addRequestProperty("Authorization", auth);

            con.setConnectTimeout(SBConstant.TIME_OUT);
            con.setReadTimeout(SBConstant.TIME_OUT);
            con.connect();
            OutputStream os = con.getOutputStream();
            os.write(postData);

            InputStream is = con.getInputStream();
            result = CommonUtils.convertInputStreamToString(is);
            statusCode = con.getResponseCode();
            reasonPostMulti = con.getResponseMessage();
            con.disconnect();


        } catch (UnsupportedEncodingException e) {
            statusCode = 400;
            reasonPostMulti = "Unsupported Encoding Exception";
        } catch (ClientProtocolException e) {
            statusCode = 400;
            reasonPostMulti = "Client Protocol Exception";
        } catch (ConnectTimeoutException e) {
            statusCode = -101;
            reasonPostMulti = "Connect Timeout";
        } catch (SocketTimeoutException e) {
            statusCode = -101;
            reasonPostMulti = "Socket Timeout";
        } catch (IOException e) {
            statusCode = 404;
            reasonPostMulti = "IOException";
        } catch (Exception e) {
            statusCode = 400;
            reasonPostMulti = "Exception";
        } finally {
            response = handleMultiPartResponse(result, statusCode, reasonPostMulti);
            return response;
        }
    }


    private byte[] getPostMultiPartData(JSONObject postData, JSONObject fileData, String boundary) {
        return getPostMultiPartData(postData, fileData, null, boundary);
    }

    private byte[] getPostMultiPartData(JSONObject postData, JSONObject fileData, Pair<String, ArrayList<Integer>> listIds, String boundary) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        try {
            // Put param
            if (postData != null) {
                JSONObject json = postData;
                Iterator<String> keys = json.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = json.getString(key);
                    if (value != null && !value.isEmpty()
                            && !value.equals("null"))
                        result.write(getDataPart(key, value, boundary));
                }
            }

            if (listIds != null) {
                if (listIds.second.size() > 0) {
                    for (int item : listIds.second) {
                        result.write(getDataPart(listIds.first, String.valueOf(item), boundary));
                    }
                }
            }

            if (fileData != null) {
                // Put files
                JSONObject jsonFile = fileData;
                Iterator<String> keysFile = jsonFile.keys();
                while (keysFile.hasNext()) {
                    String key = keysFile.next();
                    String filePath = jsonFile.getString(key);
                    if (filePath != null && !filePath.isEmpty()
                            && !filePath.equals("null") && !filePath.equals("")) {
                        if (!FileUtils.getFileExtension(filePath).toLowerCase().contains("pdf")) {
                            ByteArrayOutputStream bmOutput = new ByteArrayOutputStream();
                            Bitmap bimapConvert = BitmapFactory.decodeFile(filePath);
                            int x = bimapConvert.getWidth();
                            int y = bimapConvert.getHeight();
                            SBLog.Log(SBLog.LogType.Camera, filePath);
                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                                    .format(new Date());
                            ExifInterface exif = new ExifInterface(filePath);

                            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                            Matrix matrix = new Matrix();
                            if (orientation == 6) {
                                matrix.postRotate(90);
                            } else if (orientation == 3) {
                                matrix.postRotate(180);
                            } else if (orientation == 8) {
                                matrix.postRotate(270);
                            }
                            bimapConvert = Bitmap.createBitmap(bimapConvert, 0, 0, bimapConvert.getWidth(), bimapConvert.getHeight(), matrix, true);

                            bimapConvert.compress(Bitmap.CompressFormat.JPEG, 30,
                                    bmOutput);
                            String fileNameExt = filePath.substring(
                                    filePath.lastIndexOf("/") + 1,
                                    filePath.length());
                            String fileName = fileNameExt.replace(".jpg", "");
                            fileName = fileNameExt.replace(".JPG", "");
                            result.write(getFilePart(key, fileName,
                                    bmOutput.toByteArray(), boundary, SBEnums.FileType.IMAGE));
                        }else{
                            byte[] data=FileUtils.convertFileToByteArray(filePath);
                            ByteArrayOutputStream bmOutput = new ByteArrayOutputStream(data.length);
                            bmOutput.write(data, 0, data.length);
                            String fileNameExt = filePath.substring(
                                    filePath.lastIndexOf("/") + 1,
                                    filePath.length());
                            String fileName = fileNameExt.replace(".pdf", "");
                            fileName = fileNameExt.replace(".PDF", "");
                            result.write(getFilePart(key, fileName,
                                    bmOutput.toByteArray(), boundary, SBEnums.FileType.PDF));

                        }
                    }
                }
            }

            result.write((delimiter + boundary + delimiter + "\r\n").getBytes());

            return result.toByteArray();
        } catch (Exception ex) {
            return null;
        }
    }

    private byte[] getDataPart(String paramName, String value, String boundary) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write((delimiter + boundary + "\r\n").getBytes());
        stream.write(("Content-Type: text/plain\r\n").getBytes());
        stream.write(("Content-Disposition: form-data; name=\"" + paramName + "\"\r\n")
                .getBytes());
        stream.write(("\r\n" + value + "\r\n").getBytes());
        return stream.toByteArray();
    }

    public byte[] getFilePart(String paramName, String fileName, byte[] data, String boundary, SBEnums.FileType type)
            throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write((delimiter + boundary + "\r\n").getBytes());
        os.write(("Content-Disposition: form-data; name=\"" + paramName
                + "\"; filename=\"" + fileName + "\"\r\n").getBytes());
        switch (type){
            case IMAGE:
                os.write(("Content-Type: image/jpeg\r\n").getBytes());
                break;
            case PDF:
                os.write(("Content-Type: application/pdf\r\n").getBytes());
                break;
        }

        os.write(("Content-Transfer-Encoding: binary\r\n").getBytes());
        os.write("\r\n".getBytes());

        os.write(data);

        os.write("\r\n".getBytes());
        return os.toByteArray();
    }



    private SBNetworkResponse handleMultiPartResponse(String resultBody, int statusCode, String respondMessage) {
        SBNetworkResponse result = new SBNetworkResponse();
        if (statusCode == 200) {
            try {
                JSONObject jsonResult = new JSONObject(resultBody);
                JSONObject meta = jsonResult.getJSONObject("meta");
                int code = meta.getInt("code");
                String message = meta.getString("message");
                if (code == 200) {
                    result.success = true;
                    result.result = jsonResult.getJSONObject("body");
                } else {
                    result.errorCode = code;
                    result.errorMessage = message;
                    result.success = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            result.success = false;
            result.result = null;
            result.errorCode = statusCode;
            result.errorMessage = respondMessage;
        }

        return result;
    }


    @SuppressWarnings("finally")
    protected SBNetworkResponse downloadFile(String fileUrl) {
        SBNetworkResponse response = new SBNetworkResponse();
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1, fileUrl.indexOf('?'));
        File pictureFile = FileUtils.getOutputMediaFile(fileName);
        File pictureFile_thumbnail = FileUtils.getThumbnailMediaFile(fileName);
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.setConnectTimeout(90 * 1000);
            connection.connect();
            InputStream input = connection.getInputStream();

            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            FileOutputStream stream = new FileOutputStream(
                    pictureFile.getAbsolutePath());
            FileOutputStream stream_thumbnail = new FileOutputStream(
                    pictureFile_thumbnail.getAbsolutePath());
            ByteArrayOutputStream outstream = new ByteArrayOutputStream();
            ByteArrayOutputStream outstream_thumbnail = new ByteArrayOutputStream();
//            myBitmap = BitmapUtils.scaleBitmap(myBitmap, 800);
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 30,
                    outstream);
            myBitmap = BitmapUtils.scaleBitmap(myBitmap, 200);
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 70,
                    outstream_thumbnail);

            byte[] byteArray = outstream.toByteArray();
            byte[] byteArraythumbnail = outstream_thumbnail
                    .toByteArray();
            stream.write(byteArray);
            stream_thumbnail.write(byteArraythumbnail);
            stream.close();
            stream_thumbnail.close();

            JSONObject result = new JSONObject();
            result.put("fullImagePath", pictureFile.getAbsolutePath());
            result.put("thumbnailImagePath", pictureFile_thumbnail.getAbsolutePath());

            response.errorCode = connection.getResponseCode();
            if (response.errorCode == 200)
                response.success = true;
            response.errorMessage = connection.getResponseMessage();
            response.result = result;

            SBLog.Log(SBLog.LogType.Network, response.errorCode + "_" + response.errorMessage + "_" + response.result.toString());
        } catch (ClientProtocolException e) {
            response.errorMessage = e.getMessage();
            response.success = false;
            e.printStackTrace();
        } catch (IOException e) {
            response.errorMessage = e.getMessage();
            response.success = false;
            e.printStackTrace();
        } finally {
            return response;
        }
    }

    @SuppressWarnings("finally")
    protected SBNetworkResponse DOWNLOAD(String url) {
        SBNetworkResponse response = new SBNetworkResponse();
        String fileName = url.substring(url.lastIndexOf('/') + 1, url.indexOf('?'));
        File filePath = FileUtils.getOutputMediaFile(fileName);
        if(filePath.exists()){
            JSONObject result = new JSONObject();
            try {
                result.put("fullPath", filePath);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            response.errorCode = 200;
            response.success = true;
            response.result = result;
            return response;
        }else {
            try {
                URL u = new URL(url);
                URLConnection conn = u.openConnection();
                int contentLength = conn.getContentLength();

                DataInputStream stream = new DataInputStream(u.openStream());

                byte[] buffer = new byte[contentLength];
                stream.readFully(buffer);
                stream.close();

                DataOutputStream fos = new DataOutputStream(new FileOutputStream(filePath));
                fos.write(buffer);
                fos.flush();
                fos.close();

                JSONObject result = new JSONObject();
                result.put("fullPath", filePath);

                response.errorCode = 200;
                response.success = true;
                response.result = result;

                SBLog.Log(SBLog.LogType.Network, response.errorCode + "_" + response.errorMessage + "_" + response.result.toString());
            } catch (ClientProtocolException e) {
                response.errorMessage = e.getMessage();
                response.success = false;
                e.printStackTrace();
            } catch (IOException e) {
                response.errorMessage = e.getMessage();
                response.success = false;
                e.printStackTrace();
            } finally {
                return response;
            }
        }
    }

    protected void prepareData(String url) {
        prepareData(url, null, null);
    }

    protected void prepareData(String url, JSONObject postData) {
        prepareData(url, postData, null);
    }

    protected void prepareData(String url, JSONObject postData, JSONObject fileData) {
        _queryURL = url;
        _jsonData = postData;
        _jsonFile = fileData;
    }

    protected void prepareData(String url, JSONObject postData, JSONObject fileData, Pair<String, ArrayList<Integer>> listIDs) {
        _queryURL = url;
        _jsonData = postData;
        _jsonFile = fileData;
        _listIDs = listIDs;
    }

    private StringEntity getEntityFromString(JSONObject input) {
        try {
            return new StringEntity(input.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class SBNetworkResponseHandler implements ResponseHandler<SBNetworkResponse> {
        public SBNetworkResponse handleResponse(HttpResponse response) throws IOException {

            SBNetworkResponse result = new SBNetworkResponse();
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode < 400 && statusCode > 0) {
                try {
                    JSONObject jsonResult = new JSONObject(stringBuilder.toString());
                    JSONObject meta = jsonResult.getJSONObject("meta");
                    int code = meta.getInt("code");
                    String message = meta.getString("message");
                    if (code == 200) {
                        result.success = true;
                        result.result = jsonResult.getJSONObject("body");
                    } else {
                        result.errorCode = code;
                        result.errorMessage = message;
                        result.success = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                result.success = false;
                result.result = null;
                result.errorCode = statusCode;
                result.errorMessage = response.getStatusLine().getReasonPhrase();
            }

            return result;
        }
    }

    public static class SBGoogleResponseHandler implements ResponseHandler<SBNetworkResponse> {
        public SBNetworkResponse handleResponse(HttpResponse response) throws IOException {

            SBNetworkResponse result = new SBNetworkResponse();
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);

            }

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode < 400 && statusCode > 0) {
                try {
                    JSONObject jsonResult = new JSONObject(stringBuilder.toString());

                    if (statusCode == 200) {
                        result.success = true;
                        result.result = jsonResult;
                    } else {
                        result.errorCode = statusCode;
                        result.errorMessage = "get address fail";
                        result.success = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                result.success = false;
                result.result = null;
                result.errorCode = statusCode;
                result.errorMessage = response.getStatusLine().getReasonPhrase();
            }

            return result;
        }
    }
}
