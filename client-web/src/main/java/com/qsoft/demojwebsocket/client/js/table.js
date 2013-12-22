/**
 * Created with IntelliJ IDEA.
 * User: luult
 * Date: 12/22/13
 * Time: 8:18 PM
 * To change this template use File | Settings | File Templates.
 */

function addToLastTable(description, inputId)
{
    var table = document.getElementById("tableContent");
    var length = table.rows.length;
    addRow(length -1 , description, inputId)
}

function addRow(index, description, inputId)
{
    var table = document.getElementById("tableContent");
    var row = table.insertRow(index + 1);
    var cell0 = row.insertCell(0);
    var cell1 = row.insertCell(1);
    cell0.innerHTML = "<input type='text' id='" + inputId
        + "'   value='" + description + "' />";
    cell1.innerHTML = "12"
    cell1.onclick = function()
    {
        table.deleteRow(row.rowIndex);
        var clientId = cell0.firstChild.id
        websocketClient.deleteToDoList(JSON.stringify([toDoList[clientId]]));
        delete toDoList[inputId]
        saveToDoList()
    }
}
