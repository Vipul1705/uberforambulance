using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class _Default : System.Web.UI.Page
{
    private string cs = ConfigurationManager.ConnectionStrings["connect"].ConnectionString;
    protected static string msg = "";
    protected static string id;
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        SqlConnection conn = new SqlConnection(cs);
        if (msg == "edit")
        {
            //using (SqlCommand cmd = new SqlCommand("", conn))
            //{
            //    cmd.CommandType = CommandType.StoredProcedure;
            //    cmd.Parameters.AddWithValue("@hid", id);
            //    cmd.Parameters.AddWithValue("@hname", txtName.Text);
            //    cmd.Parameters.AddWithValue("@location", txtLocation.Text);
            //    cmd.Parameters.AddWithValue("@hno", txtNumber.Text);
            //    cmd.Parameters.AddWithValue("@email", txtEmail.Text);
            //    cmd.Parameters.AddWithValue("@address", txtAddress.Text);
            //    cmd.Parameters.AddWithValue("@type", txtType.Text);
            //    cmd.Parameters.AddWithValue("@lat", txtLat.Text);
            //    cmd.Parameters.AddWithValue("@lon", txtLon.Text);
            //    conn.Open();
            //    cmd.ExecuteNonQuery();
            //    conn.Close();
            //    Response.Redirect("manageHospital.aspx");

            //}
        }
        else
        {
            DataTable dt = new DataTable();
            using (SqlCommand cmd1 = new SqlCommand("select * from ambulance_master where contact_no=@no", conn))
            {
                cmd1.Parameters.AddWithValue("@no", txtNumber.Text);
                SqlDataAdapter sda = new SqlDataAdapter(cmd1);
                sda.Fill(dt);
                if (dt.Rows.Count > 0)
                {
                    Response.Write("<script>alert('Ambulance already exist')</script>");
                }
                else
                {
                    using (SqlCommand cmd = new SqlCommand("insert into ambulance_master(oname, dname, contact_no, email,  password, status,age) values(@aname,@dname,@cont,@email,@pass,0,0)", conn))
                    {
                        cmd.Parameters.AddWithValue("@aname", txtName.Text);
                        cmd.Parameters.AddWithValue("@dname", txtDriverName.Text);
                        cmd.Parameters.AddWithValue("@cont", txtNumber.Text);
                        cmd.Parameters.AddWithValue("@email", txtEmail.Text);
                        cmd.Parameters.AddWithValue("@pass", txtPassword.Text);
                        conn.Open();
                        cmd.ExecuteNonQuery();
                        conn.Close();
                    }
                    Response.Redirect("viewAmbulance.aspx");
                }
            }

        }
    }
}