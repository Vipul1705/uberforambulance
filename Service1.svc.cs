using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.IO;
using System.Linq;
using System.Net;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Web.Configuration;

namespace ambulance_tracking_service
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Service1.svc or Service1.svc.cs at the Solution Explorer and start debugging.
    public class Service1 : IService1
    {
        SqlConnection conn = new SqlConnection(WebConfigurationManager.ConnectionStrings["constr"].ConnectionString.ToString());
        SqlDataAdapter da = null;

        //ambulance

        public respAmbuLogin ambulanceLogin(string username, string password)
        {
            try
            {
                da = new SqlDataAdapter();
                da.SelectCommand = new SqlCommand("select aid,dname from ambulance_master where email=@mail and password=@pass", conn);
                da.SelectCommand.Parameters.AddWithValue("@mail", username);
                da.SelectCommand.Parameters.AddWithValue("@pass", password);
                DataTable dt = new DataTable();
                da.Fill(dt);
                da.Dispose();
                if (dt.Rows.Count > 0)
                {
                    return new respAmbuLogin
                    {
                        ambuId = dt.Rows[0]["aid"].ToString(),
                        driverName = dt.Rows[0]["dname"].ToString(),
                        msg = "valid"
                    };
                }
                else
                {
                    return new respAmbuLogin { msg = "invalid" };
                }
            }
            catch (Exception e)
            {
                return new respAmbuLogin { msg = e.ToString() };
            }
        }

        public respUpdateLoc uploadAmbulocation(Loc data)
        {
            try
            {
                conn.Open();
                SqlDataAdapter da = new SqlDataAdapter();
                da.SelectCommand = new SqlCommand("uploadAmbulocation");
                da.SelectCommand.Connection = conn;
                da.SelectCommand.CommandType = CommandType.StoredProcedure;
                da.SelectCommand.Parameters.AddWithValue("@amb_id", data.ambuId);
                da.SelectCommand.Parameters.AddWithValue("@lat", data.lat);
                da.SelectCommand.Parameters.AddWithValue("@lon", data.lon);
                DataTable de = new DataTable();
                da.SelectCommand.ExecuteNonQuery();
                conn.Close();
                da.Dispose();
                return new respUpdateLoc
                {
                    msg = "Inserted"
                };
            }
            catch (Exception e)
            {
                return new respUpdateLoc
                {
                    msg = e.ToString()
                };
            }
        }

        public List<respUserReq> viewBookings(string ambulanceId)
        {
            List<respUserReq> lst = new List<respUserReq>();
            try
            {
                da = new SqlDataAdapter();
                da.SelectCommand = new SqlCommand("select bid,userid,uno,date from booking_master left join user_master as u on uid=userid where ambuid=@ambId and status=1", conn);
                da.SelectCommand.Parameters.AddWithValue("@ambId", ambulanceId);
                DataTable dtData = new DataTable();
                da.Fill(dtData);
                da.Dispose();
                if (dtData.Rows.Count > 0)
                {
                    for (int i = 0; i < dtData.Rows.Count; i++)
                    {
                        respUserReq obj = new respUserReq
                        {
                            bookingid = dtData.Rows[i]["bid"].ToString(),
                            userid = dtData.Rows[i]["userid"].ToString(),
                            contact_no = dtData.Rows[i]["uno"].ToString(),
                            datetime = dtData.Rows[i]["date"].ToString()
                        };
                        lst.Add(obj);
                    }
                    return lst;
                }
                else
                {
                    return lst;
                }
            }
            catch (Exception e)
            {
                return lst;
            }
        }

        public respBookUserLoc acceptBooking(string ambulanceId, string bookingid)
        {
            try
            {
                da = new SqlDataAdapter();
                da.SelectCommand = new SqlCommand("spAcceptBooking", conn);
                da.SelectCommand.CommandType = CommandType.StoredProcedure;
                da.SelectCommand.Parameters.AddWithValue("@ambid", ambulanceId);
                da.SelectCommand.Parameters.AddWithValue("@bookingid", bookingid);
                DataTable dtData = new DataTable();
                conn.Open();
                //da.SelectCommand.ExecuteNonQuery();
                da.Fill(dtData);
                conn.Close();
                da.Dispose();
                if (dtData.Rows.Count > 0)
                {
                    return new respBookUserLoc
                    {
                        userid = dtData.Rows[0]["userid"].ToString(),
                        username = dtData.Rows[0]["uname"].ToString(),
                        userLat = dtData.Rows[0]["userlat"].ToString(),
                        userLon = dtData.Rows[0]["userlon"].ToString(),
                        hospid = dtData.Rows[0]["hospid"].ToString(),
                        hospname = dtData.Rows[0]["hname"].ToString(),
                        hospLat = dtData.Rows[0]["hosplat"].ToString(),
                        hospLon = dtData.Rows[0]["hosplon"].ToString(),
                        msg = "accepted"
                    };
                }
                else
                {
                    return new respBookUserLoc
                    {
                        msg = "invalid"
                    };
                }

            }
            catch (Exception e)
            {
                return new respBookUserLoc
                {
                    msg = e.ToString()
                };
            }
        }

        public resp closeBooking(string ambulanceId, string bookingid)
        {
            try
            {
                da = new SqlDataAdapter();
                da.SelectCommand = new SqlCommand("spCloseBooking", conn);
                da.SelectCommand.CommandType = CommandType.StoredProcedure;
                da.SelectCommand.Parameters.AddWithValue("@bookingid", bookingid);
                da.SelectCommand.Parameters.AddWithValue("@ambId", ambulanceId);
                conn.Open();
                da.SelectCommand.ExecuteNonQuery();
                conn.Close();
                return new resp { msg = "closed" };
            }
            catch (Exception e)
            {
                return new resp
                {
                    msg = e.ToString()
                };
            }
        }

        public resp submitCharges(string bookingid, string ambuId, string charges)
        {
            da = new SqlDataAdapter();
            SqlDataAdapter adp = new SqlDataAdapter();
            DataTable dt = new DataTable();
            DataTable dtData = new DataTable();
            try
            {
                da.SelectCommand = new SqlCommand("spInsertCharges", conn);
                da.SelectCommand.CommandType = CommandType.StoredProcedure;
                da.SelectCommand.Parameters.AddWithValue("@bookId", bookingid);
                da.SelectCommand.Parameters.AddWithValue("@ambId", ambuId);
                da.SelectCommand.Parameters.AddWithValue("@charge", charges);
                da.Fill(dt);

                adp.SelectCommand = new SqlCommand("select * from dbo.booking_master left join user_master on userid=uid where bid=@bookId", conn);
                adp.SelectCommand.Parameters.AddWithValue("@bookId", bookingid);
                adp.Fill(dtData);
                if (dtData.Rows.Count > 0)
                {
                    string name = dtData.Rows[0]["uname"].ToString();
                    string phno = dtData.Rows[0]["uno"].ToString();
                    sendsms(name, phno, charges);
                }

                return new resp
                {
                    msg = "inserted"
                };
            }
            catch (Exception e)
            {
                return new resp
                {
                    msg = e.ToString()
                };
            }
            finally
            {
                da.Dispose();
                dt.Dispose();
                adp.Dispose();
                dtData.Dispose();
            }
        }

        public void sendsms(string name, string ph, string charges)
        {
            try
            {
                WebRequest MyRssRequest = WebRequest.Create("https://www.smsgatewayhub.com/api/mt/SendSMS?APIKey=c5a0a9b5-24a5-49b7-9a45-77a9ab765f99&senderid=TESTIN&channel=1&DCS=0&flashsms=0&number=" + ph + "&text= " + name + " you have been charge " + charges + " for ambulance service.&route=13");
                WebResponse MyRssResponse = MyRssRequest.GetResponse();
                Stream MyRssStream = MyRssResponse.GetResponseStream();
            }
            catch (Exception)
            {

            }
        }


        //user
        public respUserLogin userLogin(string username, string password)
        {
            da = new SqlDataAdapter();
            DataSet dt = new DataSet();
            try
            {
                da.SelectCommand = new SqlCommand("select uid,uname from user_master where email=@mail and password=@pass;select * from booking_master where userid=(select uid from user_master where email=@mail and password=@pass) and convert(varchar(10),date,103)=convert(varchar(10),getdate(),103) and status in (1,2)", conn);
                da.SelectCommand.Parameters.AddWithValue("@mail", username);
                da.SelectCommand.Parameters.AddWithValue("@pass", password);

                da.Fill(dt);
                da.Dispose();
                if (dt.Tables[0].Rows.Count > 0)
                {
                    if (dt.Tables[1].Rows.Count > 0)
                    {
                        return new respUserLogin
                            {
                                uName = dt.Tables[0].Rows[0]["uname"].ToString(),
                                userId = dt.Tables[0].Rows[0]["uid"].ToString(),
                                msg = "valid",
                                bookSt = "book"
                            };
                    }
                    else
                    {
                        return new respUserLogin
                        {
                            uName = dt.Tables[0].Rows[0]["uname"].ToString(),
                            userId = dt.Tables[0].Rows[0]["uid"].ToString(),
                            msg = "valid",
                            bookSt = "notbook"
                        };
                    }

                }
                else
                {
                    return new respUserLogin { msg = "invalid" };
                }
            }
            catch (Exception e)
            {
                return new respUserLogin { msg = e.ToString() };
            }
            finally
            {
                da.Dispose(); dt.Dispose();
            }
        }

        public List<respAmbulanceList> findNearbyAmbulance(userLoc data)
        {
            List<respAmbulanceList> lst = new List<respAmbulanceList>();
            try
            {

                DataTable ds = new DataTable();
                SqlDataAdapter adp = new SqlDataAdapter();
                adp.SelectCommand = new SqlCommand("nearest_ambulance");
                adp.SelectCommand.Connection = conn;
                adp.SelectCommand.CommandType = CommandType.StoredProcedure;
                adp.SelectCommand.Parameters.AddWithValue("@lat", data.lat);
                adp.SelectCommand.Parameters.AddWithValue("@lon", data.lon);
                conn.Open();
                adp.Fill(ds);
                conn.Close();
                if (ds.Rows.Count > 0)
                {
                    for (int i = 0; i < ds.Rows.Count; i++)
                    {
                        respAmbulanceList obj = new respAmbulanceList
                        {
                            ambuId = ds.Rows[i]["aid"].ToString(),
                            lat = ds.Rows[i]["lat"].ToString(),
                            lon = ds.Rows[i]["lon"].ToString(),
                            contact_no = ds.Rows[i]["contact_no"].ToString(),
                            ambuName = ds.Rows[i]["oname"].ToString(),
                            VehicleNo = ds.Rows[i]["VehicleNo"].ToString(),
                        };

                        lst.Add(obj);
                    }
                    return lst;
                }
                else
                {
                    return lst;
                }
            }
            catch (Exception e)
            {
                return lst;
            }
        }

        public List<respHospitalList> findHospitals(userLoc data)
        {
            List<respHospitalList> lst = new List<respHospitalList>();
            try
            {

                DataTable ds = new DataTable();
                SqlDataAdapter adp = new SqlDataAdapter();
                adp.SelectCommand = new SqlCommand("nearest_hospitals");
                adp.SelectCommand.Connection = conn;
                adp.SelectCommand.CommandType = CommandType.StoredProcedure;
                adp.SelectCommand.Parameters.AddWithValue("@lat", data.lat);
                adp.SelectCommand.Parameters.AddWithValue("@lon", data.lon);
                conn.Open();
                adp.Fill(ds);
                conn.Close();
                if (ds.Rows.Count > 0)
                {
                    for (int i = 0; i < ds.Rows.Count; i++)
                    {
                        respHospitalList obj = new respHospitalList
                        {
                            hospitalId = ds.Rows[i]["hid"].ToString(),
                            hospitalName = ds.Rows[i]["hname"].ToString(),
                            email = ds.Rows[i]["email"].ToString(),
                            contact_no = ds.Rows[i]["hno"].ToString(),
                            address = ds.Rows[i]["address"].ToString(),
                            lat = ds.Rows[i]["lat"].ToString(),
                            lon = ds.Rows[i]["lon"].ToString()
                        };
                        lst.Add(obj);
                    }
                    return lst;
                }
                else
                {
                    return lst;
                }
            }
            catch (Exception e)
            {
                return lst;
            }
        }

        public respBook bookAmbulHospital(string userid, string ambid, string hospid, string lat, string lon,string desc)
        {
            try
            {
                da = new SqlDataAdapter();
                da.SelectCommand = new SqlCommand("bookAmbulance", conn);
                da.SelectCommand.CommandType = CommandType.StoredProcedure;
                da.SelectCommand.Parameters.AddWithValue("@userid", userid);
                da.SelectCommand.Parameters.AddWithValue("@ambid", ambid);
                da.SelectCommand.Parameters.AddWithValue("@hospid", hospid);
                da.SelectCommand.Parameters.AddWithValue("@ulat", lat);
                da.SelectCommand.Parameters.AddWithValue("@ulon", lon);
                da.SelectCommand.Parameters.AddWithValue("@desc", desc);

                conn.Open();
                da.SelectCommand.ExecuteNonQuery();
                conn.Close();
                da.Dispose();
                return new respBook
                {
                    msg = "booked_succ"
                };
            }
            catch (Exception e)
            {
                return new respBook { msg = e.ToString() };
            }
        }

        public respAmbuLoc getAmbulanceLoc(string userid)
        {
            try
            {
                da = new SqlDataAdapter();
                da.SelectCommand = new SqlCommand("select bid,userid,ambuid,lat,lon,oname,contact_no,VehicleNo from  booking_master as b left join ambulance_master on ambuid=aid where b.status=2 and userid=@userid", conn);
                da.SelectCommand.Parameters.AddWithValue("@userid", userid);
                DataTable dtAmb = new DataTable();
                da.Fill(dtAmb);
                da.Dispose();
                if (dtAmb.Rows.Count > 0)
                {
                    return new respAmbuLoc
                    {
                        bookid = dtAmb.Rows[0]["bid"].ToString(),
                        ambId = dtAmb.Rows[0]["ambuid"].ToString(),
                        contactno = dtAmb.Rows[0]["contact_no"].ToString(),
                        lat = dtAmb.Rows[0]["lat"].ToString(),
                        lon = dtAmb.Rows[0]["lon"].ToString(),
                        name = dtAmb.Rows[0]["oname"].ToString(),
                        msg = "valid",
                        VehicleNo = dtAmb.Rows[0]["VehicleNo"].ToString()
                    };

                }
                else
                {
                    return new respAmbuLoc { msg = "invalid" };
                }

            }
            catch (Exception e)
            {
                return new respAmbuLoc { msg = e.ToString() };
            }
        }

        public resp registerUser(UserData data)
        {
            da = new SqlDataAdapter();
            DataTable dt = new DataTable();
            try
            {
                da = new SqlDataAdapter("select * from user_master where uno=@ph or email=@mail", conn);
                da.SelectCommand.Parameters.AddWithValue("@ph", data.mobile);
                da.SelectCommand.Parameters.AddWithValue("@mail", data.email);
                da.Fill(dt);
                if (dt.Rows.Count > 0)
                {
                    return new resp { msg = "present" };
                }
                else
                {
                    da.SelectCommand = new SqlCommand("insert into user_master( uname, uno, email, password, address) values(@cust_name,@cont_no,@email,@cust_password,@addr)", conn);

                    da.SelectCommand.Parameters.AddWithValue("@cust_name", data.cname);
                    da.SelectCommand.Parameters.AddWithValue("@cust_password", data.cpass);
                    da.SelectCommand.Parameters.AddWithValue("@cont_no", data.mobile);
                    da.SelectCommand.Parameters.AddWithValue("@email", data.email);
                    da.SelectCommand.Parameters.AddWithValue("@addr", data.address);
                    conn.Open();
                    da.SelectCommand.ExecuteNonQuery();
                    conn.Close();
                    return new resp
                    {
                        msg = "register"
                    };

                }
            }
            catch (Exception e)
            {
                return new resp { msg = e.ToString() };
            }
            finally { da.Dispose(); dt.Dispose(); }

        }

        public List<respCharges> viewCharges(string userid)
        {
            da = new SqlDataAdapter();
            DataTable dt = new DataTable();
            List<respCharges> lst = new List<respCharges>();
            try
            {
                da.SelectCommand = new SqlCommand("select chargeid, bookingid, ambuid, charges, convert(varchar(10),dtme,103) date1,oname from Charges_master left join ambulance_master on aid=ambuid where bookingid in( select bid from booking_master where userid=@userid and status=3)  order by chargeid desc", conn);
                da.SelectCommand.Parameters.AddWithValue("@userid", userid);
                da.Fill(dt);
                if (dt.Rows.Count > 0)
                {
                    for (int i = 0; i < dt.Rows.Count; i++)
                    {
                        respCharges obj = new respCharges
                        {
                            chargeid = dt.Rows[i]["chargeid"].ToString(),
                            ambid = dt.Rows[i]["ambuid"].ToString(),
                            ambname = dt.Rows[i]["oname"].ToString(),
                            charges = dt.Rows[i]["charges"].ToString(),
                            date1 = dt.Rows[i]["date1"].ToString()
                        };
                        lst.Add(obj);
                    }
                    return lst;
                }
                else
                {
                    return lst;
                }
            }
            catch (Exception ex)
            {
                return lst;
            }
            finally
            {
                da.Dispose();
                dt.Dispose();
            }
        }

    }
}
