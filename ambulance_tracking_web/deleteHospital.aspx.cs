using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using System.Data.SqlClient;
using System.Data;

public partial class _Default : System.Web.UI.Page
{
    private string cs = ConfigurationManager.ConnectionStrings["connect"].ConnectionString;
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["id"] == null)
        {
            Response.Redirect("login.aspx");
        }
        SqlConnection conn = new SqlConnection(cs);
        string id = Request.QueryString["id"].ToString();
        using (SqlCommand cmd = new SqlCommand("delete from hospital_master where hid=@id", conn))
        {
            cmd.Parameters.AddWithValue("@id", id);
            conn.Open();
            cmd.ExecuteNonQuery();
            conn.Close();
            Response.Redirect("manageHospital.aspx");
        }
    }
}