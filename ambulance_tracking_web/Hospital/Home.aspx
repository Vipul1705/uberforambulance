<%@ Page Title="" Language="C#" MasterPageFile="~/Hospital/MasterPage.master" AutoEventWireup="true" CodeFile="Home.aspx.cs" Inherits="Hospital_Home" EnableEventValidation="false"%>

<asp:Content ID="Content1" ContentPlaceHolderID="head" Runat="Server">
    <meta http-equiv="refresh" content="10">
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC6v5-2uaq_wusHDktM9ILcqIrlPtnZgEk&sensor=false">
</script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
    
    <div style="margin-top:5%;margin-left:38%">
        <asp:GridView ID="gvAmbulance" runat="server" OnRowCommand="gvAmbulance_RowCommand" DataKeyNames="aid" AutoGenerateColumns="false" Width="400px" AlternatingRowStyle-BackColor="LightGray">
            <Columns>
                <asp:BoundField DataField="oname" HeaderText="Ambulance" />
                <asp:BoundField DataField="dname" HeaderText="Driver" />
                <asp:BoundField DataField="contact_no" HeaderText="Contact" />
                <asp:BoundField DataField="description" HeaderText="Description" />

                <asp:TemplateField ShowHeader="false">
                    <ItemTemplate>
                        <asp:Button ID="btn" runat="server" Text="View on Map" CommandArgument='<%# Eval("aid") %>' CommandName="Map"  />
                    </ItemTemplate>
                </asp:TemplateField>
            </Columns>
        </asp:GridView>

    </div>

    <div id="map_canvas" style="width: 500px; height: 400px; margin-left:35%;margin-top:5%">

    </div>
    <script type="text/javascript">

        function initialize(lat,lon) {
      //  var lat = document.getElementById('txtlat').value;
       // var lon = document.getElementById('txtlon').value;
        var myLatlng = new google.maps.LatLng(lat, lon) // This is used to center the map to show our markers
        var mapOptions = {
        center: myLatlng,
        zoom: 6,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        marker: true
        };
        var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
        var marker = new google.maps.Marker({
        position: myLatlng
        });
        marker.setMap(map);
        }
</script>
</asp:Content>

