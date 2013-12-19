function saveToDoList()
{
    localStorage.setItem("toDoList", JSON.stringify(toDoList));
}

function saveLatestUpdated(latestUpdated)
{
    localStorage.setItem("latestUpdated", latestUpdated);
}
