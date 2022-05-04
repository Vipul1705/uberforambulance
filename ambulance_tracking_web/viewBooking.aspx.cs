using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;

public partial class _Default : System.Web.UI.Page
{
    private string cs = ConfigurationManager.ConnectionStrings["connect"].ConnectionString;
    protected static bool flag;
    protected static DataTable dt;
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["id"] == null)
        {
            Response.Redirect("login.aspx");
        }
        SqlConnection conn = new SqlConnection(cs);
        dt = new DataTable();
        using (SqlCommand cmd = new SqlCommand("select bm.bid, userid, ambuid, hospid, date, bm.status,oname,hm.hname,um.uname,status_value from booking_master as bm left join user_master as um on userid=uid left join ambulance_master as am on ambuid=am.aid left join hospital_master as hm on hospid=hm.hid left join status_master as sm on status_id=bm.status", conn))
        {
            using (SqlDataAdapter sda = new SqlDataAdapter(cmd))
            {
                sda.Fill(dt);
            }
            if (dt.Rows.Count > 0)
            {
                flag = true;
            }
        }
    }
}