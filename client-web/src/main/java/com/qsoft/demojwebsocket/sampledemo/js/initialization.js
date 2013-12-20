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
    }
    else
    {
        console.log("Websockets are NOT supported");
    }
}
function submitData()
{
    console.log("save clicked")
    websocketClient.addToDoList("1234567890");
}