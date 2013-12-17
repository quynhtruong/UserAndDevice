jws.LauridsClientPlugIn = {
    NS: "com.qsoft.demojwebsocket.addNote",

    //Method is called when a token has to be progressed
    processToken: function (aToken)
    {
        if (aToken.ns == jws.LauridsClientPlugIn.NS)
        {
            // if it's an answer for the request "getAuthorName"
            if (aToken.reqType == "getAuthorName")
            {
                alert("This Tutorial is done by: " + aToken.name);
            }
            else if (aToken.reqType == "updateFromOther")
            {
                updateFromOther(aToken.msg)
            }
            else if (aToken.reqType == "responseFromServer")
            {
                updateResponseFromServer(aToken.msg)
            }
            else if (aToken.reqType == "responseGetLatest")
            {
                updateResponseFromServer(aToken)
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
                ns: jws.LauridsClientPlugIn.NS,
                type: "getAuthorName"
            };
            console.log("asking for Author Name...");
            this.sendToken(lToken, aOptions);//send it
        }
    },

    addOrUpdateAToDoList: function (message, aOptions)
    {
        if (this.isConnected())
        {
            var lToken = {
                ns: jws.LauridsClientPlugIn.NS,
                type: "addOrUpdateAToDoList",
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
            var lToken = {
                ns: jws.LauridsClientPlugIn.NS,
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
function updateResponseFromServer(token)
{
    console.log("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
    console.log(token.latestUpdated)
    localStorage.setItem("latestUpdated", token.latestUpdated);
    var updateList = JSON.parse(token.msg)

    for (var i = 0; i < updateList.length; i++)
    {
        var indexOfNote = findNoteWithContent(updateList[i].description)
        toDoList[indexOfNote].webId = updateList[i].webid
    }
    localStorage.setItem("toDoList", toDoList);
}
function updateFromOther(data)
{
    console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
    console.log(data)
    var updateList = JSON.parse(data)
    for (var i = 0; i < updateList.length; i++)
    {
        var indexOfNote = findIndexOfNote(updateList[i].webId);
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

jws.oop.addPlugIn(jws.jWebSocketTokenClient, jws.LauridsClientPlugIn);