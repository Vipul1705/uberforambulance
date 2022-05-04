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
        using (SqlCommand cmd = new SqlCommand("select * from user_master", conn))
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