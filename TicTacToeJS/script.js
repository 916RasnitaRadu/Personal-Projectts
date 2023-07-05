// CONSTANTS

const cells = document.querySelectorAll('.cell');
const statusText = document.querySelector('#statusText');
const restartBtn = document.querySelector('#restartBtn');
// we define an array of win conditions
const winConditions = [
    [0,1,2], [3,4,5], [6,7,8], // rows
    [0,3,6], [1,4,7], [2,5,8], // columns
    [0,4,8], [2,4,6] // diagonals
]

let options = ["","","","","","","","",""]; // array of placeholders for each cell
let currentPlayer = "X"; // to keep track of the current player
let running = false; // keep track if our game is running

// needed functions

function initializeGame() { // take care of the setup of the game before we start

    cells.forEach(cell => {
        cell.addEventListener('click', cellClicked) // call cellClicked function
    }); // add event listener for each cell when clicked

    restartBtn.addEventListener('click', restartGame); // call restartGame() when clicked
    statusText.textContent = `${currentPlayer}'s turn`; 
    running = true; // start the game
}

function cellClicked() {
    const cellIndex = this.getAttribute('cellIndex'); // we take the index of the clicked cell; 'this' refers to the clicked cell

    if (options[cellIndex] !== "" || !running) { // we check if the index of the cell is already in the options (i.e. that cell is not empty) or if the game is not running
        return // in that case we don't do anything
    } 

    updateCell(this, cellIndex); // if the cell is empty we update the cell
    checkWinner(); // and check for a winner
}

function updateCell(cell, index) {
    options[index] = currentPlayer; // we put 'X' or 'O' on that cell
    cell.textContent = currentPlayer; // and we update it on the board
}

function changePlayer() {
    /*
        ? = ternary operator
        changes the value of the variable if the specified conditions condition is true, else (':') it gives the second value
    */
    currentPlayer = (currentPlayer === "X") ? "O" : "X";
    statusText.textContent = `${currentPlayer}'s turn`;
}

function checkWinner() {
    let roundWon = false; // variable to check if someone won

    for (i = 0; i < winConditions.length; i++)
    {
        const condition = winConditions[i]; // we take each win condition
        const cellA = options[condition[0]]; // take each cell from each index in the win condition
        const cellB = options[condition[1]];
        const cellC = options[condition[2]];

        if (cellA === "" || cellB === "" || cellC === "") {
            continue; // if one of them is empty we skip this iteration
        }

        if (cellA === cellB && cellB === cellC) { // if the all 3 are equal we stop the search and set the roundWon to true
            roundWon = true;
            break; 
        }

    }
    
    if (roundWon) { // if the round is won 
        statusText.textContent = `${currentPlayer} won the round!!!`;
        
        running = false;
       
    }
    else if (!options.includes("")) { // if there is a draw
        statusText.textContent = "Draw!";
        running = false;
    }
    else { // if the round isn't won and it's not draw
        changePlayer();
    }

}

function restartGame() {
    currentPlayer = "X";
    options = ["","","","","","","","",""];
    statusText.textContent = `${currentPlayer}'s turn`;
    
    cells.forEach(cell => {
        cell.textContent = ""
    });

    running = true;
}

// start game

initializeGame();