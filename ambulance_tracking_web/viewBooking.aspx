<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage2.master" AutoEventWireup="true" CodeFile="viewBooking.aspx.cs" Inherits="_Default" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <div id="content-outer">
        <!-- start content -->
        <div id="content">

            <!--  start page-heading -->
            <div id="page-heading">
                <h1>View Booking</h1>
            </div>
            <!-- end page-heading -->

            <table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
                <tr>
                    <th rowspan="3" class="sized">
                        <img src="images/shared/side_shadowleft.jpg" width="20" height="300" alt="" /></th>
                    <th class="topleft"></th>
                    <td id="tbl-border-top">&nbsp;</td>
                    <th class="topright"></th>
                    <th rowspan="3" class="sized">
                        <img src="images/shared/side_shadowright.jpg" width="20" height="300" alt="" /></th>
                </tr>
                <tr>
                    <td id="tbl-border-left"></td>
                    <td>
                        <!--  start content-table-inner ...................................................................... START -->
                        <div id="content-table-inner">

                            <!--  start table-content  -->
                            <div id="table-content">
                                <form id="mainform">
                                    <table border="0" width="100%" cellpadding="0" cellspacing="0" id="product-table">
                                        <tr>
                                            <%--<th class="table-header-check"><a id="toggle-all" ></a>ID</th>--%>
                                            <th class="table-header-repeat line-left minwidth-1"><a>ID</a></th>
                                            <th class="table-header-repeat line-left minwidth-1"><a>User Name</a></th>
                                            <th class="table-header-repeat line-left minwidth-1"><a>Ambulance Name</a></th>
                                            <th class="table-header-repeat line-left"><a>Hospital Name</a></th>
                                            <th class="table-header-repeat line-left"><a>Date</a></th>
                                            <th class="table-header-repeat line-left"><a>Status</a></th>
                                            <%--<th class="table-header-repeat line-left"><a href="">Website</a></th>--%>
                                            <%--<th class="table-header-options line-left"><a href="">Options</a></th>--%>
                                        </tr>
                                        <% if (flag == true)
                                           {
                                               foreach (System.Data.DataRow row in dt.Rows)
                                               { %>
                                        <tr>
                                            <td><%=row["bid"]%></td>
                                            <td><%=row["uname"]%></td>
                                            <td><%=row["oname"]%></td>
                                            <td><%=row["hname"]%></td>
                                            <td><%=row["date"]%></td>
                                            <td><%=row["status_value"]%></td>
                                        </tr>
                                        <%  }
                                           }%>
                                        <% else
                                           {%>

               Response.Write("<script>                                   alert('no data found')</script>"); <%}%>
                                    </table>
                                    <!--  end product-table................................... -->
                                </form>
                            </div>
                            <!--  end content-table  -->
                    </td>
                </tr>
            </table>
            <!--  end paging................ -->

            <div class="clear"></div>

        </div>
        <!--  end content-table-inner ............................................END  -->
        </td>
		<td id="tbl-border-right"></td>
        </tr>
	<tr>
        <th class="sized bottomleft"></th>
        <td id="tbl-border-bottom">&nbsp;</td>
        <th class="sized bottomright"></th>
    </tr>
        </table>
	<div class="clear">&nbsp;</div>

    </div>
    <!--  end content -->
    <div class="clear">&nbsp;</div>
</asp:Content>

