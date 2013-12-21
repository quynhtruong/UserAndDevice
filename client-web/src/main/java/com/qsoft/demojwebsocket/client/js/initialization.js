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
        websocketClient = new jws.jWebSocketJSONClient();
        logon();
        loadFromLocalStorage();
        requestGetLatest();
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
    try
    {
        if (localStorage['toDoList'] != 'null')
        {
            toDoList = JSON.parse(localStorage['toDoList']);
        }
    }
    catch (e)
    {

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
    if (!websocketClient.isConnected())
    {
        websocketClient = new jws.jWebSocketJSONClient();
        logon();
    }
    websocketClient.requestGetLatest();
}
