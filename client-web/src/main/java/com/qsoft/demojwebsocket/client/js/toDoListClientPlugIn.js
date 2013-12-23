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
                log("bbbbbbbbb")
                doResponseGetLatest(aToken)
            }
            console.log(aToken);
            console.log(aToken.reqType)
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
        addNewRowWithClientId(addedList[i]._id, addedList[i])
    }
}
function addNewRowWithClientId(clientId, noteFromServer)
{
    log("xxx")
    if (isNull(toDoList[clientId + ""]))
    {
        log("yyy")
        var note = addNoteToLocalStorage(noteFromServer)
        clientId = note._id;
        log("yyy1")
    }
    addToLastTable(noteFromServer.description, clientId);
    log("ttt")
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

function updateWithWebId(webId, description)
{
    var clientId = findClientIdByWebId(webId)
    updateNoteWithClientId(clientId, description);
    return clientId;
}
function updateFromOther(data)
{
    console.log("update from other")
    console.log(data)
    var updatedList = JSON.parse(data)
    for (var i = 0; i < updatedList.length; i++)
    {
        updateWithWebId(updatedList[i].webId, updatedList[i].description)
    }
    saveToDoList();
};

function findClientIdByWebId(webId)
{
    for (var key in toDoList)
    {
        log(toDoList[key].webId + "  " + webId)
        if (toDoList[key].webId == webId)
        {
            return key;
        }
    }
    return -1;
}
function updateNoteWithClientId(clientId, description)
{
    var input = document.getElementById(clientId);
    toDoList[clientId].description = description;
    input.value = toDoList[clientId].description
}

function updateResponseUpdate(data)
{
    console.log("updated.........")
}

function deleteFromOther(data)
{
    console.log("delete from other")
    console.log(data)
    var updatedList = JSON.parse(data)
    for (var i = 0; i < updatedList.length; i++)
    {
        deleteWithWebId(updatedList[i].webId);
    }
    saveToDoList();
}
function deleteWithWebId(webId)
{
    log("before deleteWithWebId " + webId)
    var clientId = findClientIdByWebId(webId)
    log("deleteWithWebId " + clientId)
    deleteNoteWithClientId(clientId);
    log("deleted..")
    return clientId;
}

function deleteNoteWithClientId(clientId)
{
    log("delete" + clientId)
    var input = document.getElementById(clientId);
    if (input != null)
    {
        var table = document.getElementById("tableContent");
        table.deleteRow(input.parentNode.parentNode.rowIndex)
        delete toDoList[clientId]
    }
}

function doResponseGetLatest(aToken)
{
    log("ccccccccccccc")
    var updatedList = JSON.parse(aToken.msg)
    log("ttttttttt")
    log(updatedList)
    for (var i = 0; i < updatedList.length; i++)
    {
        log(updatedList[i].isDeleted)
        if (updatedList[i].isDeleted)
        {
            log("sync delete--------------" + updatedList[i].webId)
            deleteWithWebId(updatedList[i].webId)
        }
        else if (findClientIdByWebId(updatedList[i].webId) != -1)
        {
            log("sync update---------")
            updateWithWebId(updatedList[i].webId, updatedList[i].description)
        }
        else
        {
            log("sync add")
            addNewRowWithClientId(updatedList[i]._id, updatedList[i])
        }
    }
    saveLatestUpdated(aToken.latestUpdated)
    log("updated...")
}

jws.oop.addPlugIn(jws.jWebSocketTokenClient, jws.ToDoListClientPlugIn);
