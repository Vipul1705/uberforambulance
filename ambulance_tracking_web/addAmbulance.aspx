<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage2.master" AutoEventWireup="true" CodeFile="addAmbulance.aspx.cs" Inherits="_Default" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">

    <div id="page-heading">
        <h1>Add Ambulance</h1>
    </div>


    <table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
        <tr>
            <th rowspan="3" class="sized">&nbsp;</th>
            <th class="topleft"></th>
            <td id="tbl-border-top">&nbsp;</td>
            <th class="topright"></th>
            <th rowspan="3" class="sized">
                <img src="images/shared/side_shadowright.jpg" width="20" height="300" alt="" /></th>
        </tr>
        <tr>
            <td id="tbl-border-left">
                <img src="images/shared/side_shadowleft.jpg" width="20" height="300" alt="" /></td>
            <td>
                <!--  start content-table-inner -->
                <div id="content-table-inner">

                    <table border="0" width="100%" cellpadding="0" cellspacing="0">
                        <tr valign="top">
                            <td>


                              
                                <table border="0" cellpadding="0" cellspacing="0" id="id-form">
        
                                    <tr>
                                        <th valign="top">Name:</th>
                                        <td>
                                            <asp:TextBox ID="txtName" runat="server" class="inp-form"></asp:TextBox></td>
                                        <td>
                                            <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server"
                                                ErrorMessage="*" ControlToValidate="txtName"></asp:RequiredFieldValidator>
                                        </td>
                                    </tr>
                                     <tr>
                                        <th valign="top">Driver Name:</th>
                                        <td>
                                            <asp:TextBox ID="txtDriverName" runat="server" class="inp-form"></asp:TextBox></td>
                                        <td>
                                            <asp:RequiredFieldValidator ID="RequiredFieldValidator7" runat="server"
                                                ErrorMessage="*" ControlToValidate="txtDriverName"></asp:RequiredFieldValidator>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th valign="top">Email:</th>
                                        <td>
                                            <asp:TextBox ID="txtEmail" runat="server" class="inp-form"></asp:TextBox>&nbsp;</td>
                                        <td>
                                            <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server"
                                                ErrorMessage="*" ControlToValidate="txtEmail"></asp:RequiredFieldValidator>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th valign="top">Contact Number:</th>
                                        <td>
                                            <asp:TextBox ID="txtNumber" runat="server" class="inp-form"></asp:TextBox>&nbsp;</td>
                                        <td>
                                            <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server"
                                                ErrorMessage="*" ControlToValidate="txtNumber"></asp:RequiredFieldValidator>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th valign="top">Password</th>
                                        <td>
                                            <asp:TextBox ID="txtPassword" runat="server" class="inp-form"></asp:TextBox></td>
                                        <td>
                                            <asp:RequiredFieldValidator ID="RequiredFieldValidator6" runat="server"
                                                ErrorMessage="*" ControlToValidate="txtPassword"></asp:RequiredFieldValidator>
                                        </td>
                                    </tr>

                                   
                                    <tr>
                                        <th>&nbsp;</th>
                                        <td valign="top">
                                            <asp:Button ID="Button1" runat="server" Text="Submit" class="form-submit" OnClick="Button1_Click"
                                                 />
                                            <asp:Button ID="Button2" runat="server" Text="Reset" class="form-reset"
                                               />
                                        </td>
                                        <td></td>
                                    </tr>
                                </table>
                                <!-- end id-form  -->

                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>
                                <img src="images/shared/blank.gif" width="695" height="1" alt="blank" /></td>
                            <td></td>
                        </tr>
                    </table>

                    <div class="clear"></div>


                </div>
                <!--  end content-table-inner  -->
            </td>
            <td id="tbl-border-right"></td>
        </tr>
        <tr>
            <th class="sized bottomleft"></th>
            <td id="tbl-border-bottom">&nbsp;</td>
            <th class="sized bottomright"></th>
        </tr>
    </table>

</asp:Content>

