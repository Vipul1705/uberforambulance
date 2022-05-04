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
    protected static string msg = "";
    protected static string id;
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["id"] == null)
        {
            Response.Redirect("login.aspx");
        }
        SqlConnection conn = new SqlConnection(cs);
        if (!IsPostBack)
        {
            if (Request.QueryString["msg"] != null)
            {

                msg = Request.QueryString["msg"].ToString();
                id = Request.QueryString["id"].ToString();
                if (msg == "edit")
                {
                    using (SqlCommand cmd = new SqlCommand("select * from hospital_master where hid=@id", conn))
                    {
                        cmd.Parameters.AddWithValue("@id", id);
                        DataTable dt = new DataTable();
                        using (SqlDataAdapter sda = new SqlDataAdapter(cmd))
                        {
                            sda.Fill(dt);
                        }
                        if (dt.Rows.Count > 0)
                        {
                            txtName.Text = dt.Rows[0]["hname"].ToString();
                            txtEmail.Text = dt.Rows[0]["email"].ToString();
                            txtAddress.Text = dt.Rows[0]["address"].ToString();
                            txtType.Text = dt.Rows[0]["type"].ToString();
                            txtLocation.Text = dt.Rows[0]["location"].ToString();
                            txtNumber.Text = dt.Rows[0]["hno"].ToString();
                            txtEmail.ReadOnly = true;
                        }
                    }
                }
                else
                {
                    txtEmail.ReadOnly = false;
                }
            }
        }

    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        SqlConnection conn = new SqlConnection(cs);
        if (msg == "edit")
        {
            using (SqlCommand cmd = new SqlCommand("updateHospital", conn))
            {
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@hid", id);
                cmd.Parameters.AddWithValue("@hname", txtName.Text);
                cmd.Parameters.AddWithValue("@location", txtLocation.Text);
                cmd.Parameters.AddWithValue("@hno", txtNumber.Text);
                cmd.Parameters.AddWithValue("@email", txtEmail.Text);
                cmd.Parameters.AddWithValue("@address", txtAddress.Text);
                cmd.Parameters.AddWithValue("@type", txtType.Text);
                cmd.Parameters.AddWithValue("@lat", txtLat.Text);
                cmd.Parameters.AddWithValue("@lon", txtLon.Text);
                cmd.Parameters.AddWithValue("@pass", txtPass.Text);
                conn.Open();
                cmd.ExecuteNonQuery();
                conn.Close();
                Response.Redirect("manageHospital.aspx");

            }
        }
        else
        {
            DataTable dt = new DataTable();
            using (SqlCommand cmd1 = new SqlCommand("select * from hospital_master where hno=@no", conn))
            {
                cmd1.Parameters.AddWithValue("@no", txtNumber.Text);
                SqlDataAdapter sda = new SqlDataAdapter(cmd1);
                sda.Fill(dt);
                if (dt.Rows.Count > 0)
                {
                    Response.Write("<script>alert('hospital already exist')</script>");
                }
                else
                {
                    using (SqlCommand cmd = new SqlCommand("insertHospital", conn))
                    {
                        cmd.CommandType = CommandType.StoredProcedure;
                        cmd.Parameters.AddWithValue("@hname", txtName.Text);
                        cmd.Parameters.AddWithValue("@location", txtLocation.Text);
                        cmd.Parameters.AddWithValue("@hno", txtNumber.Text);
                        cmd.Parameters.AddWithValue("@email", txtEmail.Text);
                        cmd.Parameters.AddWithValue("@address", txtAddress.Text);
                        cmd.Parameters.AddWithValue("@type", txtType.Text);
                        cmd.Parameters.AddWithValue("@lat", txtLat.Text);
                        cmd.Parameters.AddWithValue("@lon", txtLon.Text);
                        cmd.Parameters.AddWithValue("@pass", txtPass.Text);
                        conn.Open();
                        cmd.ExecuteNonQuery();
                        conn.Close();
                        Response.Redirect("manageHospital.aspx");
                    }
                }
            }

        }
    }
    protected void Button2_Click(object sender, EventArgs e)
    {
        txtName.Text = txtLocation.Text = txtNumber.Text = txtEmail.Text = txtAddress.Text = txtType.Text = "";
    }
}