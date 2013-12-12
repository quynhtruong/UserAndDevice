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
            else if (aToken.reqType == "calculate")
            {
                alert("calculated Number is: " + aToken.calNumber);

            }
            else if (aToken.reqType == "updateFromOther")
            {
                updateFromOther(aToken.msg)
            }
            console.log(aToken);
            console.log(aToken.reqType)
            console.log(aToken.status)
        }
    },
    //Method is called from the button "Author"
    //to send a request to the jwebsocketserver-> LauridsPlugIn
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

    calculateMyNumber: function (inputNumber, aOptions)
    {
        if (this.isConnected())
        {
            //create the request token
            var lToken = {
                ns: jws.LauridsClientPlugIn.NS,
                type: "calculate",
                myNumber: inputNumber//add the input Number to our token
            };
            console.log("sending calculation request for:" + inputNumber);
            this.sendToken(lToken, aOptions);//send it
        }
    },
    addOrUpdateAToDoList: function (inputNumber, aOptions)
    {
        if (this.isConnected())
        {
            var lToken = {
                ns: jws.LauridsClientPlugIn.NS,
                type: "addOrUpdateAToDoList",
                id: inputNumber,
                description:"abc"
            };
            this.sendToken(lToken, aOptions);//send it
        }
        else
        {
            alert("disconnected");
        }
    }

};
function updateFromOther(data)
{
    console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
    console.log(data)
    var updateList = JSON.parse(data)
    for(var i = 0; i < updateList.length; i ++)
    {
        var indexOfNote = findIndexOfNote(updateList[i].webId);
        updateNoteAt(indexOfNote, updateList[i].description);
        console.log(updateList[i].webId);
        console.log(updateList[i].description);
    }
    console.log(data);
    console.log("aaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
};

function findIndexOfNote(webId)
{
    for(var i = 0 ; i < toDoList.length; i++)
    {
        if (toDoList[i].webId == webId)
            return i;
    }
    return -1;
}
function updateNoteAt(index, description)
{
    if (index == -1)
    {
        addRow(toDoList.length, description)
    }
    else
    {
        var table = document.getElementById("tableContent");
        var row = table.rows[index+1];
        updateRow(index, row, description)
    }
}

jws.oop.addPlugIn(jws.jWebSocketTokenClient, jws.LauridsClientPlugIn);