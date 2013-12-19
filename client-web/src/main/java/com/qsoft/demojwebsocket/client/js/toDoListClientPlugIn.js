jws.ToDoListClientPlugIn = {
    NS: "com.qsoft.demojwebsocket.addNote",

    //Method is called when a token has to be progressed
    processToken: function (aToken)
    {
        if (aToken.ns == jws.ToDoListClientPlugIn.NS)
        {
            if (aToken.reqType == "getAuthorName")
            {
                alert("This Tutorial is done by: " + aToken.name);
            }
            else if (aToken.reqType == "updateFromOther")
            {
                updateFromOther(aToken.msg)
            }
            else if (aToken.reqType == "responseAddFromSever")
            {
                updateResponseAdd(aToken)
            }
            else if (aToken.reqType == "responseUpdateFromSever")
            {
                updateResponseUpdate(aToken)
            }
            else if (aToken.reqType == "responseGetLatest")
            {
                doResponseGetLatest(aToken)
            }
            console.log(aToken);
            console.log(aToken.reqType)
            console.log(aToken.status)
        }
    },
    requestAuthorName: function (aOptions)
    {
        if (this.isConnected())
        {
            //create the request token
            var lToken = {
                ns: jws.ToDoListClientPlugIn.NS,
                type: "getAuthorName"
            };
            console.log("asking for Author Name...");
            this.sendToken(lToken, aOptions);//send it
        }
    },
    addToDoList: function (message, aOptions)
    {
        if (this.isConnected())
        {
            var lToken = {
                ns: jws.ToDoListClientPlugIn.NS,
                type: "addToDoList",
                msg: message
            };
            this.sendToken(lToken, aOptions);//send it
        }
        else
        {
            alert("disconnected");
        }
    },
    updateToDoList: function (message, aOptions)
    {
        if (this.isConnected())
        {
            var lToken = {
                ns: jws.ToDoListClientPlugIn.NS,
                type: "updateToDoList",
                msg: message
            };
            this.sendToken(lToken, aOptions);//send it
        }
        else
        {
            alert("disconnected");
        }
    },
    requestGetLatest: function (aOptions)
    {
        console.log("syncing.....")
        if (this.isConnected())
        {
            var latestUpdated = localStorage.getItem("latestUpdated");
            console.log(latestUpdated)
            var lToken = {
                ns: jws.ToDoListClientPlugIn.NS,
                type: "requestGetLatest",
                latestUpdated: latestUpdated
            };
            this.sendToken(lToken, aOptions);//send it
        }
        else
        {
            console.log("disconnected");
        }
    }
};

function updateResponseUpdate(data)
{
    console.log("updated.........")
}

function updateResponseAdd(token)
{
    console.log("updateResponseAdd ...............")
    var updateList = JSON.parse(token.msg)

    for (var i = 0; i < updateList.length; i++)
    {
        var indexOfNote = findNoteWithContent(updateList[i].description)
        console.log("index of note "+indexOfNote)
        toDoList[indexOfNote].webId = updateList[i].webId
    }
    console.log("to do list")
    console.log(toDoList)
    saveToDoList();
}

function doResponseGetLatest(aToken)
{
    updateFromOther(aToken.msg)
    console.log("doResponseGetLatest ..")
    console.log(aToken.latestUpdated);
    saveLatestUpdated();
}

function findNoteWithContent(description)
{
    for (var i = 0; i < toDoList.length; i++)
    {
        if ((toDoList[i].webId == null) && (toDoList[i].description == description))
        {
            return i;
        }
    }
}

function updateFromOther(data)
{
    console.log("update from other")
    console.log(data)
    var updateList = JSON.parse(data)
    for (var i = 0; i < updateList.length; i++)
    {
        var indexOfNote = findIndexOfNote(updateList[i].webId);
        console.log(indexOfNote)
        updateNoteAt(indexOfNote, updateList[i]);
        console.log(updateList[i].webId);
        console.log(updateList[i].description);
    }
    console.log(data);
    console.log("aaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
};

function findIndexOfNote(webId)
{
    for (var i = 0; i < toDoList.length; i++)
    {
        if (toDoList[i].webId == webId)
        {
            return i;
        }
    }
    return -1;
}
function updateNoteAt(index, note)
{
    if (index == -1)
    {
        addRow(toDoList.length, note)
    }
    else
    {
        var table = document.getElementById("tableContent");
        var row = table.rows[index + 1];
        updateDataRow(index, row, note)
    }
}

jws.oop.addPlugIn(jws.jWebSocketTokenClient, jws.ToDoListClientPlugIn);
