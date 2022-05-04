package com.example.cp.ambulancetracking_app.connect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.cp.ambulancetracking_app.Data.Location_;
import com.example.cp.ambulancetracking_app.Data.fillLocation;
import com.example.cp.ambulancetracking_app.ambulance.AccpetedReq;
import com.example.cp.ambulancetracking_app.ambulance.LogAmbDriver;
import com.example.cp.ambulancetracking_app.ambulance.UserReqData;
import com.example.cp.ambulancetracking_app.user.AmbulanceData;
import com.example.cp.ambulancetracking_app.user.BookedAmbuDetails;
import com.example.cp.ambulancetracking_app.user.ChargeListData;
import com.example.cp.ambulancetracking_app.user.HospitalData;
import com.example.cp.ambulancetracking_app.user.LogUser;
import com.example.cp.ambulancetracking_app.user.UserREg;
import com.example.cp.ambulancetracking_app.user.UserSelection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class ConnectionManager {

    public final String serverUrl = "http://my-demo.in/Ambulance_Tracking_service/Service1.svc/";
 //   public final String serverUrl = "http://192.168.43.73/amb_service/Service1.svc/";

    public static boolean checkNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public void UpdateLocation() {
        String responseString;
        try {
            final String TAG_RESULT = "msg";

            String url = String.format(serverUrl + "uploadAmbulocation");
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("lat", fillLocation.getLatitude());
            jsonObject.accumulate("lon", fillLocation.getLongitude());
            jsonObject.accumulate("ambuId", LogAmbDriver.getAmbulance_id());
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            HttpResponse response = httpclient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();

                JSONObject jsonObj = new JSONObject(responseString);
                String msg = jsonObj.getString("msg");

                fillLocation.setResult(0);
            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ambulance
    public int authAmbulance(String uname, String pass) {
        try {

            StringBuilder result = new StringBuilder();
            String url = String.format(serverUrl + "ambulanceLogin/" + uname + "/" + pass);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String id = jobj.getString("ambuId");
                String name = jobj.getString("driverName");
                String msg = jobj.getString("msg");
                if (msg.equals("valid")) {
                    LogAmbDriver.setDriver_name(name);
                    LogAmbDriver.setAmbulance_id(id);
                    return 1;
                } else {
                    return 2;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean viewRequests() {
        try {
            StringBuilder result = new StringBuilder();

            HttpClient httpclient = new DefaultHttpClient();
            String url = String.format(serverUrl + "viewBookings/" + LogAmbDriver.getAmbulance_id());

            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                br.close();
                JSONArray jarrayobj = new JSONArray(result.toString());
                ArrayList<String> stringArray1, stringArray2, stringArray3, stringArray4;
                stringArray1 = new ArrayList<String>(jarrayobj.length());
                stringArray2 = new ArrayList<String>(jarrayobj.length());
                stringArray3 = new ArrayList<String>(jarrayobj.length());
                stringArray4 = new ArrayList<String>(jarrayobj.length());

                for (int i = 0; i < jarrayobj.length(); i++) {
                    JSONObject job = jarrayobj.getJSONObject(i);

                    String bid = job.optString("bookingid");
                    String uid = job.optString("userid");
                    String cn = job.optString("contact_no");
                    String dt = job.optString("datetime");
                    stringArray1.add(bid);
                    stringArray2.add(uid);
                    stringArray3.add(cn);
                    stringArray4.add(dt);
                }
                UserReqData.setBookid(stringArray1);
                UserReqData.setUserid(stringArray2);
                UserReqData.setContact(stringArray3);
                UserReqData.setDate(stringArray4);
                return true;
            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (Exception e) {
            return false;
        }
    }

    public int acceptRequest(String bookingid) {
        try {
            StringBuilder result = new StringBuilder();
            String url = String.format(serverUrl + "acceptBooking/" + LogAmbDriver.getAmbulance_id() + "/" + bookingid);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String uid = jobj.getString("userid");
                String uname = jobj.getString("username");
                String ulat = jobj.getString("userLat");
                String ulon = jobj.getString("userLon");
                String hospid = jobj.getString("hospid");
                String hospname = jobj.getString("hospname");
                String hosplat = jobj.getString("hospLat");
                String hosplon = jobj.getString("hospLon");

                String msg = jobj.getString("msg");

                if (msg.equals("accepted")) {
                    AccpetedReq.setUserid(uid);
                    AccpetedReq.setUname(uname);
                    AccpetedReq.setUlat(ulat);
                    AccpetedReq.setUlon(ulon);
                    AccpetedReq.setHospid(hospid);
                    AccpetedReq.setHospname(hospname);
                    AccpetedReq.setHosplat(hosplat);
                    AccpetedReq.setHosplon(hosplon);
                    return 1;
                } else {
                    return 2;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean closeRequest() {
        try {
            StringBuilder result = new StringBuilder();
            String url = String.format(serverUrl + "closeBooking/" + LogAmbDriver.getAmbulance_id() + "/" + AccpetedReq.getBookid());
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());

                String msg = jobj.getString("msg");

                if (msg.equals("closed")) {
                    return true;
                } else {
                    return false;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return false;
        }
    }

    //PayCharges
    public int PayCharges(String charges) {
        try {

            StringBuilder result = new StringBuilder();
            String url = String.format(serverUrl + "submitCharges/" + AccpetedReq.getBookid() + "/" + LogAmbDriver.getAmbulance_id() + "/" + charges);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String msg = jobj.getString("msg");
                if (msg.equals("inserted")) {

                    return 1;
                } else {
                    return 2;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return 0;
        }
    }

    //user
    public int authUser(String uname, String pass) {
        try {

            StringBuilder result = new StringBuilder();
            String url = String.format(serverUrl + "userLogin/" + uname + "/" + pass);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String id = jobj.getString("userId");
                String name = jobj.getString("uName");
                String bkSt = jobj.getString("bookSt");
                String msg = jobj.getString("msg");
                if (msg.equals("valid")) {
                    LogUser.setUsername(name);
                    LogUser.setUserId(id);
                    LogUser.setBkst(bkSt);
                    return 1;
                } else {
                    return 2;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean getNHospitals() {
        try {

            ArrayList<String> stringarray1, stringarray2, stringarray3, stringarray4, stringarray5, stringarray6, stringarray7;

            StringBuilder result = new StringBuilder();
            String lat = Double.toString(Location_.getLatitude());
            String lon = Double.toString(Location_.getLongitude());

            String url = String.format(serverUrl + "findHospitals");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("lat",lat); //fillLocation.getLatitude());
            jsonObject.accumulate("lon",lon);// fillLocation.getLongitude());
            jsonObject.accumulate("userId", LogUser.getUserId());
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            HttpResponse response = httpclient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                br.close();
                JSONArray jarrayobj = new JSONArray(result.toString());
                int length = 0;
                if (jarrayobj.length() > 0) {
                    length = jarrayobj.length();
                }
                stringarray1 = new ArrayList<String>(length);
                stringarray2 = new ArrayList<String>(length);
                stringarray3 = new ArrayList<String>(length);
                stringarray4 = new ArrayList<String>(length);
                stringarray5 = new ArrayList<String>(length);
                stringarray6 = new ArrayList<String>(length);
                stringarray7 = new ArrayList<String>(length);

                for (int i = 0; i < jarrayobj.length(); i++) {
                    JSONObject job = jarrayobj.getJSONObject(i);
                    String id = job.optString("hospitalId");
                    String name = job.optString("hospitalName");
                    String mail = job.optString("email");
                    String cn = job.optString("contact_no");
                    String lati = job.optString("lat");
                    String longi = job.optString("lon");
                    String add = job.optString("address");

                    stringarray1.add(id);
                    stringarray2.add(name);
                    stringarray3.add(mail);
                    stringarray4.add(cn);
                    stringarray5.add(lati);
                    stringarray6.add(longi);
                    stringarray7.add(add);
                }

                if (!stringarray1.isEmpty()) {
                    HospitalData.setHospid(stringarray1);
                    HospitalData.setHospname(stringarray2);
                    HospitalData.setEmail(stringarray3);
                    HospitalData.setCont(stringarray4);
                    HospitalData.setLat(stringarray5);
                    HospitalData.setLon(stringarray6);
                    HospitalData.setAddress(stringarray7);

                    return true;
                } else {
                    return true;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getNAmbulance() {
        try {

            ArrayList<String> stringarray1, stringarray2, stringarray3, stringarray4, stringarray5,stringarray6;

            StringBuilder result = new StringBuilder();
            String lat = Double.toString(Location_.getLatitude());
            String lon = Double.toString(Location_.getLongitude());

            String url = String.format(serverUrl + "findNearbyAmbulance");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("lat",lat);// fillLocation.getLatitude());
            jsonObject.accumulate("lon",lon);// fillLocation.getLongitude());
            jsonObject.accumulate("userId", LogUser.getUserId());
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            HttpResponse response = httpclient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                br.close();
                JSONArray jarrayobj = new JSONArray(result.toString());
                stringarray1 = new ArrayList<String>(jarrayobj.length());
                stringarray2 = new ArrayList<String>(jarrayobj.length());
                stringarray3 = new ArrayList<String>(jarrayobj.length());
                stringarray4 = new ArrayList<String>(jarrayobj.length());
                stringarray5 = new ArrayList<String>(jarrayobj.length());
                stringarray6 = new ArrayList<String>(jarrayobj.length());

                for (int i = 0; i < jarrayobj.length(); i++) {
                    JSONObject job = jarrayobj.getJSONObject(i);
                    String id = job.optString("ambuId");
                    String name = job.optString("ambuName");
                    String cn = job.optString("contact_no");
                    String lati = job.optString("lat");
                    String longi = job.optString("lon");
                    String vehicleNo = job.optString("VehicleNo");

                    stringarray1.add(id);
                    stringarray2.add(name);
                    stringarray3.add(cn);
                    stringarray4.add(lati);
                    stringarray5.add(longi);
                    stringarray6.add(vehicleNo);
                }

                if (!stringarray1.isEmpty()) {
                    AmbulanceData.setAmbuId(stringarray1);
                    AmbulanceData.setAmbuName(stringarray2);
                    AmbulanceData.setAmbuContact(stringarray3);
                    AmbulanceData.setAmbulat(stringarray4);
                    AmbulanceData.setAmbulon(stringarray5);
                    AmbulanceData.setVehicleNo(stringarray6);

                    return true;
                } else {
                    return false;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean bookAmbulance() {
        try {

            StringBuilder result = new StringBuilder();
            String lat = Double.toString(Location_.getLatitude());
            String lon = Double.toString(Location_.getLongitude());
            String url = String.format(serverUrl + "bookAmbulHospital/" + LogUser.getUserId() + "/" + UserSelection.getSelAmbulanceId() + "/" + UserSelection.getSelHospitalId()+"/"+lat+"/"+lon+"/"+UserSelection.getDesc());
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());

                String msg = jobj.getString("msg");
                if (msg.equals("booked_succ")) {

                    return true;
                } else {
                    return false;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return false;
        }
    }

    public int getAmbulanceDetails() {
        try {

            StringBuilder result = new StringBuilder();
            String url = String.format(serverUrl + "getAmbulanceLoc/" + LogUser.getUserId());
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String bid = jobj.getString("bookid");
                String aid = jobj.getString("ambId");
                String name = jobj.getString("name");
                String cn = jobj.getString("contactno");
                String lat = jobj.getString("lat");
                String lon = jobj.getString("lon");
                String msg = jobj.getString("msg");
                String VehicleNo = jobj.getString("VehicleNo");

                if (msg.equals("valid")) {
                    BookedAmbuDetails.setAmbiId(aid);
                    BookedAmbuDetails.setBookid(bid);
                    BookedAmbuDetails.setCn(cn);
                    BookedAmbuDetails.setLat(lat);
                    BookedAmbuDetails.setLon(lon);
                    BookedAmbuDetails.setName(name);
                    BookedAmbuDetails.setVehicleNo(VehicleNo);
                    return 1;
                } else {
                    return 2;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public int register() {
        try {
            final String TAG_id = "msg";
            StringBuilder result = new StringBuilder();

            HttpClient httpclient = new DefaultHttpClient();
            String url = String.format(serverUrl + "registerUser");
            HttpPost httpPost = new HttpPost(url);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("cname", UserREg.getName());
            jsonObject.accumulate("mobile", UserREg.getContact());
            jsonObject.accumulate("address", UserREg.getAddress());
            jsonObject.accumulate("email", UserREg.getMail());
            jsonObject.accumulate("cpass", UserREg.getPass());

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response = httpclient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jobj = new JSONObject(result.toString());
                String msg = jobj.getString(TAG_id);

                if (msg.equals("register")) {

                    return 1;

                } else if (msg.equals("present")) {
                    return 2;
                } else {
                    return 3;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (Exception e) {
            return 0;
        }
    }

    //getChargesData
    public boolean getChargesData() {
        try {

            ArrayList<String> stringarray1, stringarray2, stringarray3, stringarray4, stringarray5;

            StringBuilder result = new StringBuilder();
            String lat = Double.toString(Location_.getLatitude());
            String lon = Double.toString(Location_.getLongitude());

            String url = String.format(serverUrl + "viewCharges/"+LogUser.getUserId());
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

                InputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                br.close();
                JSONArray jarrayobj = new JSONArray(result.toString());
                int length = 0;
                if (jarrayobj.length() > 0) {
                    length = jarrayobj.length();
                }
                stringarray1 = new ArrayList<String>(length);
                stringarray2 = new ArrayList<String>(length);
                stringarray3 = new ArrayList<String>(length);
                stringarray4 = new ArrayList<String>(length);
                stringarray5 = new ArrayList<String>(length);

                for (int i = 0; i < jarrayobj.length(); i++) {
                    JSONObject job = jarrayobj.getJSONObject(i);
                    String chrgid = job.optString("chargeid");
                    String ambid = job.optString("ambid");
                    String ambname = job.optString("ambname");
                    String charges = job.optString("charges");
                    String date1 = job.optString("date1");

                    stringarray1.add(chrgid);
                    stringarray2.add(ambid);
                    stringarray3.add(ambname);
                    stringarray4.add(charges);
                    stringarray5.add(date1);
                }

                if (!stringarray1.isEmpty()) {
                    ChargeListData.setChrgeid(stringarray1);
                    ChargeListData.setAmbid(stringarray2);
                    ChargeListData.setAmbname(stringarray3);
                    ChargeListData.setCharges(stringarray4);
                    ChargeListData.setDate1(stringarray5);

                    return true;
                } else {
                    return true;
                }
            } else {

                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
