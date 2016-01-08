var numbersToChange;
var multiplier;

function pageLoad()
{
	var inputBox = Number(document.getElementById("servingInputBox").value);
	multiplier = new Array();
	numbersToChange = document.getElementsByClassName("numberToChange");
	
	for (i = 0; i < numbersToChange.length; i++)
	{
		multiplier[i] = Number(numbersToChange[i].innerHTML) / inputBox;
	}
}

function changeNumbers()
{
	var inputboxValue = Number(document.getElementById("servingInputBox").value);
	
	for (i = 0; i < numbersToChange.length; i++)
	{
		numbersToChange[i].innerHTML = (Math.floor((inputboxValue * multiplier[i]) * 10)) / 10;
	}
}

function increase()
{
	var inputBox = Number(document.getElementById("servingInputBox").value);
	
	document.getElementById("servingInputBox").value = (Math.floor(inputBox * 10) + 10) / 10;
	
	changeNumbers();
}

function decrease()
{
	var inputBox = Number(document.getElementById("servingInputBox").value);
	var newNumber = (Math.floor(inputBox * 10) - 10) / 10;
	
	if (newNumber >= 0)
	{
		document.getElementById("servingInputBox").value = newNumber;
		changeNumbers();
	}
}

function enter(e)
{
	if (e.keyCode == 13)
	{
		var inputBox = Number(document.getElementById("servingInputBox").value);
		
		document.getElementById("servingInputBox").value = Math.floor(inputBox * 10) / 10;
		
		changeNumbers();
	}
}