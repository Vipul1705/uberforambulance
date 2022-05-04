using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Hospital_Home : System.Web.UI.Page
{

    SqlConnection con;
    SqlCommand cmd;
    SqlDataAdapter da;
    DataTable dt;
    string constr = ConfigurationManager.ConnectionStrings["connect"].ToString();
    string hid;

    protected void Page_Load(object sender, EventArgs e)
    {
        hid = Session["id"].ToString();
       

        if (Session["id"] == null)
        {
            Response.Redirect("../login.aspx");
        }


       if (!IsPostBack)
            fillGrid();

       
    }

    protected void gvAmbulance_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        //string id = e.CommandArgument.ToString();
        //Response.Write(id);

        if (e.CommandName == "Map")
        {
            string id = e.CommandArgument.ToString();
            con = new SqlConnection(constr);
            cmd = new SqlCommand("SELECT curr_loc.Lat 'Latitude', curr_loc.Long 'Longitude' FROM ambulance_master where aid=@id", con);
            cmd.Parameters.AddWithValue("@id", Convert.ToInt32(id));
            da = new SqlDataAdapter(cmd);
            dt = new DataTable();
            da.Fill(dt);
            if (dt.Rows.Count > 0)
            {
                double lat = Convert.ToDouble(dt.Rows[0]["Latitude"]);
                double lon = Convert.ToDouble(dt.Rows[0]["Longitude"]);


                Page page = HttpContext.Current.CurrentHandler as Page;
                page.ClientScript.RegisterStartupScript(
                    typeof(Page),
                    "Test",
                    "<script type='text/javascript'>initialize(" + lat + ",'" + lon + "');</script>");

            }





           // Response.Write(id);
        }
        // Response.Write("<script>alert('invalid detaills')</script>");
    }


    void fillGrid()
    {
       

        try
        {
            con = new SqlConnection(constr);
            cmd = new SqlCommand("select a.aid,a.oname,a.dname,a.contact_no,b.description from ambulance_master a inner join booking_master b on a.aid = b.ambuid where b.status = 2 and b.hospid = @hid", con);
            cmd.Parameters.AddWithValue("@hid", Convert.ToInt32(hid));
            da = new SqlDataAdapter(cmd);
            dt = new DataTable();
            da.Fill(dt);
            if (dt.Rows.Count > 0)
            {
                gvAmbulance.DataSource = dt;
                gvAmbulance.DataBind();
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}