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
            else if (aToken.reqType == "responseAddFromOther")
            {
                addFromOther(aToken.msg)
            }
            else if (aToken.reqType == "responseUpdateFromOther")
            {
                updateFromOther(aToken.msg)
            }
            else if (aToken.reqType == "responseDeleteFromOther")
            {
                deleteFromOther(aToken.msg)
            }
            else if (aToken.reqType == "responseAddFromSever")
            {
                updateResponseAdd(aToken)
            }
            else if (aToken.reqType == "responseUpdateFromSever")
            {
                updateResponseUpdate(aToken)
            }
            else if (aToken.reqType == "responseDeleteFromSever")
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
    commonStep: function (type, message, aOptions)
    {
        if (this.isConnected())
        {
            var lToken = {
                ns: jws.ToDoListClientPlugIn.NS,
                type: type,
                msg: message
            };
            this.sendToken(lToken, aOptions);//send it
        }
        else
        {
            alert("disconnected");
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
        this.commonStep("addToDoList", message, aOptions);
    },
    updateToDoList: function (message, aOptions)
    {
        this.commonStep("updateToDoList", message, aOptions);
    },
    deleteToDoList: function (message, aOptions)
    {
        this.commonStep("deleteToDoList", message, aOptions);
    },
    requestGetLatest: function (aOptions)
    {
        console.log("syncing.....")
        if (this.isConnected())
        {
            var latestUpdated = getLatestUpdated();
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

function addFromOther(data)
{
    log("adding from other..........")
    var addedList = JSON.parse(data)
    for (var i = 0; i < addedList.length; i++)
    {
        var clientId = addedList[i]._id;
        if (isNull(toDoList[addedList[i]._id + ""]))
        {
            var note = addNoteToLocalStorage(addedList[i])
            clientId = note._id;
        }
        addToLastTable(addedList[i].description, clientId);
    }
}

function updateResponseAdd(token)
{
    console.log("updateResponseAdd ...............")
    var updateList = JSON.parse(token.msg)

    for (var i = 0; i < updateList.length; i++)
    {
        toDoList[updateList[i]._id].webId = updateList[i].webId
    }
    saveToDoList();
}

function updateFromOther(data)
{
    console.log("update from other")
    console.log(data)
    var updatedList = JSON.parse(data)
    for (var i = 0; i < updatedList.length; i++)
    {
        var clientId = findClientIdByWebId(updatedList[i].webId)
        updateNoteWithClientId(clientId, updatedList[i].description);
    }
    saveToDoList();
};

function findClientIdByWebId(webId)
{
    for (var key in toDoList)
    {
        if (toDoList[key].webId == webId)
        {
            return key;
        }
    }
}
function updateNoteWithClientId(clientId, description)
{
    var input = document.getElementById(clientId);
    toDoList[clientId].description = description;
    input.value = toDoList[clientId].description
}

function doResponseGetLatest(aToken)
{
    updateFromOther(aToken.msg)
    console.log("doResponseGetLatest ..")
    console.log(aToken.latestUpdated);
    saveLatestUpdated(aToken.latestUpdated);
}

function updateResponseUpdate(data)
{
    console.log("updated.........")
}

function deleteFromOther(data)
{
    console.log("updated.........")
}

jws.oop.addPlugIn(jws.jWebSocketTokenClient, jws.ToDoListClientPlugIn);
