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
    console.log(localStorage['toDoList']);
    loadToDoList();
    log1("todolist ",toDoList)
    for (var key in toDoList)
    {
        addToLastTable(toDoList[key].description, toDoList[key]._id)
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
