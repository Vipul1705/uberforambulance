using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using System.Data.SqlClient;
using System.Data;

public partial class login : System.Web.UI.Page
{
    private string cs = ConfigurationManager.ConnectionStrings["connect"].ConnectionString;
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            Session.Abandon();
            Session.Clear();
        }
    }
    protected void btnSubmit_Click1(object sender, EventArgs e)
    {
        string type = ddType.SelectedValue;
        // Response.Write(type);

        SqlConnection conn = new SqlConnection(cs);
        DataTable dt = new DataTable();

        if (type == "Admin")
        {
            using (SqlCommand cmd = new SqlCommand("select * from admin_master where username=@username and password=@password ", conn))
            {
                cmd.Parameters.AddWithValue("@username", txtUser.Value.ToString());
                cmd.Parameters.AddWithValue("@password", txtPass.Value.ToString());
                using (SqlDataAdapter sda = new SqlDataAdapter(cmd))
                {
                    sda.Fill(dt);
                }
                if (dt.Rows.Count > 0)
                {
                    Session["id"] = dt.Rows[0].ItemArray[0].ToString();
                    Response.Redirect("home.aspx");
                }
                else
                    Response.Write("<script>alert('invalid detaills')</script>");
            }
        }
        else if (type == "Hospital")
        {
            using (SqlCommand cmd = new SqlCommand("select * from hospital_master where email=@email and password=@password ", conn))
            {
                cmd.Parameters.AddWithValue("@email", txtUser.Value.ToString());
                cmd.Parameters.AddWithValue("@password", txtPass.Value.ToString());
                using (SqlDataAdapter sda = new SqlDataAdapter(cmd))
                {
                    sda.Fill(dt);
                }
                if (dt.Rows.Count > 0)
                {
                    Session["id"] = dt.Rows[0]["hid"].ToString();
                    Response.Redirect("Hospital/Home.aspx");
                }
                else
                    Response.Write("<script>alert('invalid detaills')</script>");
            }
        }

    }
}