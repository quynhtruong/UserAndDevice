function log(objectx)
{
    console.log(objectx)
}
function log1(message,objectx)
{
    console.log(message + objectx)
}

function loadToDoList()
{
    try
    {
        toDoList = JSON.parse(localStorage.getItem("toDoList"));
    }
    catch (e)
    {
        localStorage.setItem("toDoList", {});
        toDoList = {}
    }
    if (toDoList == null)
        toDoList = {}
}
function getNoteById(_id)
{
    return toDoList[_id]
}
function removeNoteById(_id)
{
    delete  toDoList[_id]
}
function toDoListLength()
{
    return Object.keys(toDoList).length
}
function addNoteToLocalStorage(note)
{
    var nextId = getNextId()
    increaseNextId();
    toDoList[nextId + ""] = note;
    saveToDoList();
}
function saveToDoList()
{
    localStorage.setItem("toDoList", JSON.stringify(toDoList));
}

function saveLatestUpdated(latestUpdated)
{
    localStorage.setItem("latestUpdated", latestUpdated);
}
function getLatestUpdated()
{
    return localStorage.getItem("latestUpdated");
}
function getNextId()
{
    var nextId = localStorage.getItem("nextId");
    if (isNull(nextId))
    {
        var nextId = 1;
        localStorage.setItem("nextId",1);
    }
    return nextId;
}

function increaseNextId()
{
    var nextId = localStorage.getItem("nextId");
    if (isNull(nextId))
    {
        nextId = 1;
    }
    nextId++;
    localStorage.setItem("nextId", nextId);
}

function isNull(obj)
{
    if (obj == null)
        return truel
    else
    if (isNaN(obj))
        return true;
    if (typeof obj == "undefined")
        return true;
    return false;
}
