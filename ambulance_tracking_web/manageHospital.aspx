﻿<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage2.master" AutoEventWireup="true" CodeFile="manageHospital.aspx.cs" Inherits="_Default" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <div id="content-outer">
        <!-- start content -->
        <div id="content">

            <!--  start page-heading -->
            <div id="page-heading">
                <h1>View Hospital</h1>
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

                                <%--				<!--  start message-yellow -->
				<div id="message-yellow">
				<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="yellow-left">You have a new message. <a href="">Go to Inbox.</a></td>
					<td class="yellow-right"><a class="close-yellow"><img src="images/table/icon_close_yellow.gif"   alt="" /></a></td>
				</tr>
				</table>
				</div>
				<!--  end message-yellow -->
				
				<!--  start message-red -->
				<div id="message-red">
				<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="red-left">Error. <a href="">Please try again.</a></td>
					<td class="red-right"><a class="close-red"><img src="images/table/icon_close_red.gif"   alt="" /></a></td>
				</tr>
				</table>
				</div>
				<!--  end message-red -->
				
				<!--  start message-blue -->
				<div id="message-blue">
				<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="blue-left">Welcome back. <a href="">View my account.</a> </td>
					<td class="blue-right"><a class="close-blue"><img src="images/table/icon_close_blue.gif"   alt="" /></a></td>
				</tr>
				</table>
				</div>
				<!--  end message-blue -->
			
				<!--  start message-green -->
				<div id="message-green">
				<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="green-left">Product added sucessfully. <a href="">Add new one.</a></td>
					<td class="green-right"><a class="close-green"><img src="images/table/icon_close_green.gif"   alt="" /></a></td>
				</tr>
				</table>
				</div>
				<!--  end message-green -->--%>


                                <!--  start product-table ..................................................................................... -->
                                <form id="mainform">
                                    <table border="0" width="100%" cellpadding="0" cellspacing="0" id="product-table">
                                        <tr>
                                            <%--<th class="table-header-check"><a id="toggle-all" ></a>ID</th>--%>
                                            <th class="table-header-repeat line-left minwidth-1"><a>ID</a></th>
                                            <th class="table-header-repeat line-left minwidth-1"><a>Name</a></th>
                                            <th class="table-header-repeat line-left minwidth-1"><a>Type</a></th>
                                            <th class="table-header-repeat line-left minwidth-1"><a>Number</a></th>
                                            <th class="table-header-repeat line-left"><a>Email</a></th>
                                            <th class="table-header-repeat line-left"><a>Address</a></th>
                                            <th class="table-header-repeat line-left"><a>Location</a></th>
                                            <th class="table-header-repeat line-left"><a>Latitude</a></th>
                                            <th class="table-header-repeat line-left"><a>Longitude</a></th>
                                            <%--<th class="table-header-repeat line-left"><a href="">Website</a></th>--%>
                                            <th class="table-header-options line-left"><a href="">Options</a></th>
                                        </tr>
                                        <% if (flag == true)
                                           {
                                               foreach (System.Data.DataRow row in dt.Rows)
                                               { %>
                                        <tr>
                                            <td><%=row["hid"]%></td>
                                            <td><%=row["hname"]%></td>
                                            <td><%=row["type"]%></td>
                                            <td><%=row["hno"]%></td>
                                            <td><%=row["email"]%></td>
                                            <td><%=row["address"]%></td>
                                            <td><%=row["location"]%></td>
                                            <td><%=row["lat"]%></td>
                                            <td><%=row["lon"]%></td>
                                            <td class="options-width">
                                                <a href="addHospital.aspx?id=<%=row["hid"]%>&msg=edit" title="Update" class="icon-1 info-tooltip"></a>
                                                <a href="deleteHospital.aspx?id=<%=row["hid"]%>" title="Delete" class="icon-2 info-tooltip"></a>
                                            </td>
                                        </tr>
                                        <%  }
         }%>
                                        <% else
                                           {%>

               Response.Write("<script>alert('no data found')</script>"); <%}%>
                                        <%--				<tr class="alternate-row">
					<td><input  type="checkbox"/></td>
					<td>Sabev</td>
					<td>George</td>
					<td><a href="">george@mainevent.co.za</a></td>
					<td>R250</td>
					<td><a href="">www.mainevent.co.za</a></td>
					<td class="options-width">
					<a href="" title="Edit" class="icon-1 info-tooltip"></a>
					<a href="" title="Edit" class="icon-2 info-tooltip"></a>
					<a href="" title="Edit" class="icon-3 info-tooltip"></a>
					<a href="" title="Edit" class="icon-4 info-tooltip"></a>
					<a href="" title="Edit" class="icon-5 info-tooltip"></a>
					</td>
				</tr>
				<tr>
					<td><input  type="checkbox"/></td>
					<td>Sabev</td>
					<td>George</td>
					<td><a href="">george@mainevent.co.za</a></td>
					<td>R250</td>
					<td><a href="">www.mainevent.co.za</a></td>
					<td class="options-width">
					<a href="" title="Edit" class="icon-1 info-tooltip"></a>
					<a href="" title="Edit" class="icon-2 info-tooltip"></a>
					<a href="" title="Edit" class="icon-3 info-tooltip"></a>
					<a href="" title="Edit" class="icon-4 info-tooltip"></a>
					<a href="" title="Edit" class="icon-5 info-tooltip"></a>
					</td>
				</tr>
				<tr class="alternate-row">
					<td><input  type="checkbox"/></td>
					<td>Sabev</td>
					<td>George</td>
					<td><a href="">george@mainevent.co.za</a></td>
					<td>R250</td>
					<td><a href="">www.mainevent.co.za</a></td>
					<td class="options-width">
					<a href="" title="Edit" class="icon-1 info-tooltip"></a>
					<a href="" title="Edit" class="icon-2 info-tooltip"></a>
					<a href="" title="Edit" class="icon-3 info-tooltip"></a>
					<a href="" title="Edit" class="icon-4 info-tooltip"></a>
					<a href="" title="Edit" class="icon-5 info-tooltip"></a>
					</td>
				</tr>
				<tr>
					<td><input  type="checkbox"/></td>
					<td>Sabev</td>
					<td>George</td>
					<td><a href="">george@mainevent.co.za</a></td>
					<td>R250</td>
					<td><a href="">www.mainevent.co.za</a></td>
					<td class="options-width">
					<a href="" title="Edit" class="icon-1 info-tooltip"></a>
					<a href="" title="Edit" class="icon-2 info-tooltip"></a>
					<a href="" title="Edit" class="icon-3 info-tooltip"></a>
					<a href="" title="Edit" class="icon-4 info-tooltip"></a>
					<a href="" title="Edit" class="icon-5 info-tooltip"></a>
					</td>
				</tr>
				<tr class="alternate-row">
					<td><input  type="checkbox"/></td>
					<td>Sabev</td>
					<td>George</td>
					<td><a href="">george@mainevent.co.za</a></td>
					<td>R250</td>
					<td><a href="">www.mainevent.co.za</a></td>
					<td class="options-width">
					<a href="" title="Edit" class="icon-1 info-tooltip"></a>
					<a href="" title="Edit" class="icon-2 info-tooltip"></a>
					<a href="" title="Edit" class="icon-3 info-tooltip"></a>
					<a href="" title="Edit" class="icon-4 info-tooltip"></a>
					<a href="" title="Edit" class="icon-5 info-tooltip"></a>
					</td>
				</tr>--%>
                                    </table>
                                    <!--  end product-table................................... -->
                                </form>
                            </div>
                            <!--  end content-table  -->

                            <!--  start actions-box ............................................... -->
                            <%--<div id="actions-box">
				<a href="" class="action-slider"></a>
				<div id="actions-box-slider">
					<a href="" class="action-edit">Edit</a>
					<a href="" class="action-delete">Delete</a>
				</div>
				<div class="clear"></div>
			</div>--%>
                            <!-- end actions-box........... -->

                            <!--  start paging..................................................... -->
                            <%--<table border="0" cellpadding="0" cellspacing="0" id="paging-table">
			<tr>
			<td>
				<a href="" class="page-far-left"></a>
				<a href="" class="page-left"></a>
				<div id="page-info">Page <strong>1</strong> / 15</div>
				<a href="" class="page-right"></a>
				<a href="" class="page-far-right"></a>
			</td>
			<td>
			<%--<select  class="styledselect_pages">
				<option value="">Number of rows</option>
				<option value="">1</option>
				<option value="">2</option>
				<option value="">3</option>
			</select>--%>
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


