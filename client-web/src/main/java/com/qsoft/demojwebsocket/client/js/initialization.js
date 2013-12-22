var websocketID = "meinTest",
    websocketClient = null,
    wsUsername = null,
    wsPassword = null;
var toDoList = {};
//Method is called when the page is loaded
function initPage()
{
    if (jws.browserSupportsWebSockets())
    {
        console.log("Websockets are supported");
        websocketClient = new jws.jWebSocketJSONClient();
        logon();
        loadFromLocalStorage();
//        requestGetLatest();
//        setInterval(requestGetLatest, 5000)
    }
    else
    {
        console.log("Websockets are NOT supported");
    }
}

function loadFromLocalStorage()
{
    log("xxxxxxxxxx")
    log(getNextId())
    increaseNextId();
    log(getNextId())
    log("yyyyyyyyy")
    console.log(toDoList)
    console.log(toDoList.length)
    console.log(localStorage['toDoList']);
    loadToDoList();
    log1("todolist ",toDoList)
    var i = 0;
    for (var key in toDoList)
    {
        addRow(i++, toDoList[key])
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
