var websocketID = "meinTest",
    websocketClient = null,
    wsUsername = null,
    wsPassword = null;
var toDoList = [];
//Method is called when the page is loaded
function initPage()
{
    if (jws.browserSupportsWebSockets())
    {
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
        setInterval(requestGetLatest, 5000)
    }
    else
    {
        console.log("Websockets are NOT supported");
    }
}

function loadFromLocalStorage()
{
    console.log(toDoList)
    console.log(toDoList.length)
    console.log(localStorage['toDoList']);

    try {
        if (localStorage['toDoList'] != 'null')
        {
            toDoList = JSON.parse(localStorage['toDoList']);
        }
    } catch (e) {

    }
    console.log(toDoList)
    var table = document.getElementById("tableContent");
    for (var i = 0; i < toDoList.length; i++)
    {
        addRow(i, toDoList[i])
    }
}
function requestGetLatest()
{
  websocketClient.requestGetLatest();
}
function addRow(index, note)
{
    var table = document.getElementById("tableContent");
    var length = table.rows.length;
    var row = table.insertRow(length);

    updateDataRow(index, row, note);
}

function updateDataRow(index, row, note)
{
    if (row.cells.length == 0)
    {
        var cell0 = row.insertCell(0);
        var cell1 = row.insertCell(1);
    }
    else
    {
        var cell0 = row.cells[0];
        var cell1 = row.cells[1];
    }
    cell0.innerHTML = index;
    cell1.innerHTML = "<input type='text' id='" + index
        + "'   value='" + note.description + "' />";
    toDoList[index] = note;
    localStorage.setItem('toDoList', JSON.stringify(toDoList));
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
    cell0.innerHTML = length - 1;
    cell1.innerHTML = "<input type='text' id='" + (length - 1)
        + "'   value='' />";
//    cell2.innerHTML = "";
//    cell3.innerHTML = "";
}

function submitData()
{
    var table = document.getElementById("tableContent");
    var lengthTable = table.rows.length;

    var todoListLength = toDoList.length
    var dataToSend = [];
    var additionalNumber = 0;

    for (var i = 0; i < todoListLength; i++)
    {
        var input = document.getElementById(i + "");
        console.log(input.value)
        if (input.value != toDoList[i].description)
        {
            toDoList[i].description = input.value

            var note = {};
            note.webId = toDoList[i].webId;
            note.description = input.value;

            dataToSend[additionalNumber++] = note;
        }
    }

    for (var i = todoListLength; i < lengthTable - 1; i++)
    {
        var input = document.getElementById(i + "");
        var note = {};
        note.webId = null;
        note.description = input.value;

        toDoList[i] = note;
        dataToSend[additionalNumber++] = note;
    }
    if (dataToSend.length > 0)
    {
        console.log(JSON.stringify(dataToSend))
        localStorage.setItem("toDoList", JSON.stringify(toDoList));
        websocketClient.addOrUpdateAToDoList(JSON.stringify(dataToSend));
    }
}

function logon()
{
//    var wsURL = "ws://172.16.10.37:9090/camel-tweet";// get the default server url

    var wsURL = jws.getDefaultServerURL();// get the default server url
    console.log("Connecting to " + websocketID + " at " + wsURL + "...");

    //get the guest username and password
    wsUsername = jws.GUEST_USER_LOGINNAME;
    wsPassword = jws.GUEST_USER_PASSWORD;

    // try to establish the connection to jWebSocket server
    websocketClient.logon(wsURL, wsUsername, wsPassword, {
        OnOpen: function (aEvent)
        {
            console.log("Connection to " + websocketID + " established.");
        },

        // OnMessage callback
        OnMessage: function (aEvent, aToken)
        {
            console.log(( aToken ? aToken.type : "-" ) + ": " + aEvent.data);

            if (websocketClient.isLoggedIn())
            {
                console.log("User is authenticated");
            }
            else
            {
                console.log("User is connected");
            }
            console.log(websocketClient.getId() + "&nbsp;" + ( jws.browserSupportsNativeWebSockets ? "(native)" : "(flashbridge)" ));
            if (aToken)
            {
                // is it a response from a previous request of this client?
                if (aToken.type == "response")
                {
                    if (aToken.reqType == "login")
                    {// figure out of which request
                        if (aToken.code == 0)
                        {
                            console.log("Welcome '" + aToken.username + "'");
                        }
                        else
                        {
                            console.log("Error logging in '" + eUsername.value + "': " + aToken.msg);
                        }
                    }
                    // is it an event w/o a previous request ?
                }
                else if (aToken.type == "goodBye")
                {
                    console.log("good bye (reason: " + aToken.reason + ")!");
                }
            }
        },
        // OnClose callback
        OnClose: function (aEvent)
        {
            console.log("Disconnected from " + websocketID + ".");
        }

    });
}