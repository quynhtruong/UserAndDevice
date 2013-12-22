function addRow(index, note)
{
    var table = document.getElementById("tableContent");
    var length = table.rows.length;
    var row = table.insertRow(index+1);

    updateDataRow(index, row, note);
}

function updateDataRow(index, row, note)
{
    if (row.cells.length == 0)
    {
        var cell0 = row.insertCell(0);
        var cell1 = row.insertCell(1);
    }
    else
    {
        var cell0 = row.cells[0];
        var cell1 = row.cells[1];
    }
    cell0.innerHTML = index;
    cell1.innerHTML = "<input type='text' id='" + index
        + "'   value='" + note.description + "' />";
    toDoList[index] = note;
    localStorage.setItem('toDoList', JSON.stringify(toDoList));
}

function addRowTable()
{
    var table = document.getElementById("tableContent");
    var length = table.rows.length;
    var row = table.insertRow(length);

    var cell0 = row.insertCell(0);
    var cell1 = row.insertCell(1);
//    var cell2 = row.insertCell(2);
//    var cell3 = row.insertCell(3);
    cell0.innerHTML = length - 1;
    cell1.innerHTML = "<input type='text' id='" + (length - 1)
        + "'   value='' />";
//    cell2.innerHTML = "";
//    cell3.innerHTML = "";
}

function submitData()
{
    var dataToSend = getChangedData();
    if (dataToSend.length > 0)
    {
        console.log(JSON.stringify(dataToSend))
        saveToDoList();
        var addedData = getAddedData(dataToSend);
        var modifiedData = getModifiedData(dataToSend);
        if (addedData.length > 0)
        {
            websocketClient.addToDoList(JSON.stringify(addedData));
        }
        if (modifiedData.length > 0)
        {
            websocketClient.updateToDoList(JSON.stringify(dataToSend));
        }
    }
}

function getAddedData(dataToSend)
{
    var result = []
    var length = 0;
    for (var i = 0; i < dataToSend.length; i++)
    {
        if (dataToSend[i].webId == null)
        {
            result[length++] = dataToSend[i];
        }
    }
    return result;
}
function getModifiedData(dataToSend)
{
    var result = []
    var length = 0;
    for (var i = 0; i < dataToSend.length; i++)
    {
        if (dataToSend[i].webId != null)
        {
            result[length++] = dataToSend[i];
        }
    }
    return result;
}
function getChangedData()
{
    var table = document.getElementById("tableContent");
    var lengthTable = table.rows.length;
    var todoListLength = toDoList.length
    var dataToSend = [];
    var additionalNumber = 0;
    for (var i = 0; i < todoListLength; i++)
    {
        var input = document.getElementById(i + "");
        console.log(input.value)
        if (input.value != toDoList[i].description)
        {
            toDoList[i].description = input.value
            var note = {};
            note.webId = toDoList[i].webId;
            note.description = input.value;
            dataToSend[additionalNumber++] = note;
        }
    }
    for (var i = todoListLength; i < lengthTable - 1; i++)
    {
        var input = document.getElementById(i + "");
        var note = {};
        note.webId = null;
        note.description = input.value;
        toDoList[i] = note;
        dataToSend[additionalNumber++] = note;
    }
    return dataToSend;
}
