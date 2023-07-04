// create a calculator class where we store the current operand and the previous operand

class Calculator {
    constructor(previousOperandTextElement, currentOperandTextElement) {
        this.previousOperandTextElement = previousOperandTextElement; // we are not gonna work with these, because they will be only the display values
        this.currentOperandTextElement = currentOperandTextElement;
        this.clear();
    }

    clear() { // clearing all output
        this.currentOperand= '' // we work with these
        this.previousOperand = ''
        this.operation = undefined
    }

    delete() { // remove a number
        this.currentOperand = this.currentOperand.toString().slice(0, -1) // slice down the last character of the string
    }
    
    appendNumber(number /* pass the number that the user selected*/) {
        if (number === '.' && this.currentOperand.includes('.')) return

        this.currentOperand = this.currentOperand.toString() + number.toString()
    }

    chooseOperation(operation /* pass the operation that the user selected*/) {
        if (this.currentOperand === '') return // if the current operand is empty we can't add a new operation
        if (this.previousOperand !== '') { // if there is already a new operand and we add a new operation the present operation will be computed
            this.compute()
        }


        this.operation = operation  // set the operation
        this.previousOperand = this.currentOperand // give to the previous operand field the current operand
        this.currentOperand = '' // clear the current operand
    }

    compute() { // do the calculation
        let computation // result

        const prev = parseFloat(this.previousOperand) // convert previous & the current operand to a number
        const current = parseFloat(this.currentOperand)

        if (isNaN(prev) || isNaN(current)) return // if one of the is not a number we end the function
        
        switch (this.operation) { // we do a switch statement for choosing the right operation to compute the result
            case '+':
                computation = prev + current
                break
            case '-':
                computation = prev - current
                break
            case '*':
                computation = prev * current
                break
            case '÷':
                computation = prev / current
                break
            default:
                return
        }

        this.currentOperand = computation // the current operand takes the result
        this.operation = undefined // reset the operation
        this.previousOperand = '' // also reset the previous operand
    }

    getDisplayNumber(number) {
        const stringNumber = number.toString()
        const integerDigits = parseFloat(stringNumber.split('.')[0]) // we take the digits until the decimal point
        const decimalDigits = stringNumber.split('.')[1] // we take the decimal digits; we don't parse it to a float because we don't need it to be a number

        let integerDisplay

        if (isNaN(integerDigits)) { // if someone did not enter anything or entered the decimal point .
            integerDisplay = ''
        }
        else { // if there are integer digits (digits before decimal point)
            integerDisplay = integerDigits.toLocaleString('en', {maximumFractionDigits: 0}) // convert them to locale string
            /*
                .toLocaleString(
                    'en', - specify the language (this will add the commas)
                    {maximumFractionDigits: 0} - it means that there can never be decimal values after this when it gets converted to a string
                )
            */
        }
        
        if (decimalDigits != null) {
            return `${integerDisplay}.${decimalDigits}`
        }
        else {
            return integerDisplay
        }
    }

    updateDisplay() {
        this.currentOperandTextElement.innerText = this.getDisplayNumber(this.currentOperand) 

        if (this.operation != null) { // if there is an operation selected it will also display the symbol of that operation
            this.previousOperandTextElement.innerText = `${ this.getDisplayNumber(this.previousOperand)} ${this.operation}`
        }
        else { // if there is no operation selected we clear the previous operand field
            this.previousOperandTextElement.innerText = ''
        }
    }
}

// CONSTANTS

const numberButtons = document.querySelectorAll('[data-number]');
const operationButtons = document.querySelectorAll('[data-operation]');
const equalsButton = document.querySelector('[data-equals]');
const deleteButton = document.querySelector('[data-delete]');
const allClearButton = document.querySelector('[data-all-clear]')
const previousOperandTextElement = document.querySelector('[data-previous-operand]');
const currentOperandTextElement = document.querySelector('[data-current-operand]');

// create a new object of class Calculator

const calculator = new Calculator(previousOperandTextElement, currentOperandTextElement);

// next we add event listeners

numberButtons.forEach(button => {
    button.addEventListener('click', () => {
        calculator.appendNumber(button.innerText)
        calculator.updateDisplay()
    });
});

operationButtons.forEach(button => {
    button.addEventListener('click', () => {
        calculator.chooseOperation(button.innerText)
        calculator.updateDisplay()
    });
});

equalsButton.addEventListener('click', button => {
    calculator.compute()
    calculator.updateDisplay()
});

allClearButton.addEventListener('click', button => {
    calculator.clear()
    calculator.updateDisplay()
});

deleteButton.addEventListener('click', button => {
    calculator.delete()
    calculator.updateDisplay()
});