jws.ToDoListClientPlugIn = {
    NS: "jwebsocket",

    //Method is called when a token has to be progressed
    processToken: function (aToken)
    {
        if (aToken.ns == jws.ToDoListClientPlugIn.NS)
        {
            if (aToken.reqType == "getAuthorName")
            {
                alert("This Tutorial is done by: " + aToken.name);
            }
            else if (aToken.reqType == "response")
            {
                console.log("response from server");
                console.log(aToken.msg)
            }
            console.log(aToken);
            console.log(aToken.reqType)
            console.log(aToken.status)
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
            console.log("sent")
            this.sendToken(lToken, aOptions);//send it
        }
        else
        {
            alert("disconnected");
        }
    }
}

jws.oop.addPlugIn(jws.jWebSocketTokenClient, jws.ToDoListClientPlugIn);
