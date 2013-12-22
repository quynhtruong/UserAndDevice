function addNote()
{
    var inputText = document.getElementById("inputText");
    var note = saveNoteToLocalStorage(inputText.value);
    addToLastTable(inputText.value, note._id)
    submitAddNote(note);
    inputText.value = ""
}

function saveNoteToLocalStorage(description)
{
    var note = {}
    note.webId = null;
    note.description = description;
    addNoteToLocalStorage(note)
    return note;
}
function submitAddNote(note)
{
    websocketClient.addToDoList(JSON.stringify([note]));
}


function submitData()
{
    var dataToSend = getChangedData();
    if (dataToSend.length > 0)
    {
        websocketClient.updateToDoList(JSON.stringify(dataToSend));
    }
    saveToDoList();
}

function getChangedData()
{
    var table = document.getElementById("tableContent");
    var todoListLength = toDoList.length
    var dataToSend = [];
    var additionalNumber = 0;
    for (var key in toDoList)
    {
        var input = document.getElementById(key + "");
        if (input.value != toDoList[key].description)
        {
            toDoList[key].description = input.value

            var note = {};
            note.webId = toDoList[key].webId;
            note.description = input.value;

            dataToSend[additionalNumber++] = note;
        }
    }

    return dataToSend;
}
