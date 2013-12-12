var websocketID = "meinTest",
    websocketClient = null,
    wsUsername = null,
    wsPassword = null;
var toDoList = null;
//Method is called when the page is loaded
function initPage() {
    if( jws.browserSupportsWebSockets() ) {
        console.log("Websockets are supported");
        // instaniate new TokenClient, either JSON, CSV or XML
        websocketClient = new jws.jWebSocketJSONClient();
        logon();
//        var note1 = {}
//        note1.description = "abcdef";
//
//        var note2 = {};
//        note2.description = "abc1";
//
//        localStorage.setItem('toDoList',JSON.stringify([note1, note2]));

        loadFromLocalStorage();
    } else {
        console.log("Websockets are NOT supported");
    }
}

function loadFromLocalStorage()
{
    toDoList = JSON.parse(localStorage['toDoList']);
    var table = document.getElementById("tableContent");
    for(var i = 0; i < toDoList.length;i ++)
    {
        addRow(i, toDoList[i].description)
    }
}

function addRow(index, description)
{
    var table = document.getElementById("tableContent");
    var length = table.rows.length;
    var row = table.insertRow(length);

    updateDataRow(index, row, description);
}

function updateDataRow(index, row, description)
{
    var cell0 = row.insertCell(0);
    var cell1 = row.insertCell(1);
    cell0.innerHTML = index;
    cell1.innerHTML = "<input type='text' id='" + index
        + "'   value='" + description+ "' />";
}

function addRowTable()
{
    var table = document.getElementById("tableContent");
    var length = table.rows.length;
    var row = table.insertRow(length);

    var cell0 = row.insertCell(0);
    var cell1 = row.insertCell(1);
//    var cell2 = row.insertCell(2);
//    var cell3 = row.insertCell(3);
    cell0.innerHTML = length -1;
    cell1.innerHTML = "<input type='text' id='" + (length-1)
        + "'   value='' />";
//    cell2.innerHTML = "";
//    cell3.innerHTML = "";
}

function submitData()
{
    var table = document.getElementById("tableContent");
    var lengthTable = table.rows.length;
    websocketClient.addOrUpdateAToDoList("abcdef");
}

//Method is called when the user clicks the "calculate" button
function requestResult(){
    myNumber=parseInt(document.getElementById("inputNumber").value);//get the number
    websocketClient.calculateMyNumber(myNumber);//call the calculate method from LauridsClientPlugIn.js
}

function logon() {
//    var wsURL = "ws://172.16.10.37:9090/camel-tweet";// get the default server url

    var wsURL = jws.getDefaultServerURL();// get the default server url
    console.log("Connecting to " + websocketID + " at " + wsURL + "..." );

    //get the guest username and password
    wsUsername=jws.GUEST_USER_LOGINNAME;
    wsPassword=jws.GUEST_USER_PASSWORD;

    // try to establish the connection to jWebSocket server
    websocketClient.logon( wsURL, wsUsername, wsPassword, {
        OnOpen: function( aEvent ) {
            console.log("Connection to " + websocketID + " established." );
        },

        // OnMessage callback
        OnMessage: function( aEvent, aToken ) {
            console.log(( aToken ? aToken.type : "-" ) + ": " +	aEvent.data);

            if( websocketClient.isLoggedIn() ) {
                console.log("User is authenticated");
            } else {
                console.log("User is connected");
            }
            console.log(websocketClient.getId() + "&nbsp;"	+ ( jws.browserSupportsNativeWebSockets ? "(native)" : "(flashbridge)" ));
            if( aToken ) {
                // is it a response from a previous request of this client?
                if( aToken.type == "response" ) {
                    if( aToken.reqType == "login" ) {// figure out of which request
                        if( aToken.code == 0 ) {
                            console.log("Welcome '" + aToken.username + "'" );
                        }else {
                            console.log("Error logging in '" + eUsername.value + "': " + aToken.msg );
                        }
                    }
                    // is it an event w/o a previous request ?
                }else if( aToken.type == "goodBye" ) {
                    console.log("good bye (reason: " + aToken.reason + ")!" );
                }
            }
        },
        // OnClose callback
        OnClose: function( aEvent ) {
            console.log("Disconnected from " + websocketID + ".");
        }

    });
}