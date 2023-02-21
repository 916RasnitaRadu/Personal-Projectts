from Exceptions.exceptions import GameException


class UI:
    def __init__(self, controller, validator):
        self._controller = controller
        self._validator = validator

    @staticmethod
    def print_menu():
        print("\nWelcome to Hangman!")
        print("Select what you want to do next: ")
        print("\t1. Play game")
        print("\t2. Add a sentence to the game")
        print("\t3. Exit\n")


    def play_game(self):
        game_over = False
        n = 0
        unknown_sentence = self._controller.pick_random()
        unknown_sentence = unknown_sentence[:-1] # ca sa scoata spatiul de la final
        hangman = self._controller.make_hangman()
        sentence_built = self._controller.make_sentence(unknown_sentence)
        print(sentence_built + " - " + hangman)
        while game_over is False:
            valid = False
            while valid is False:
                letter = input("Please enter a letter: ")
                if len(letter) == 1 and letter not in "0123456789":
                    valid = True

            if letter in unknown_sentence:
                sentence_built = self._controller.update_sentence(letter, sentence_built, unknown_sentence)
            else:
                hangman = self._controller.update_hangman(n, hangman)
                n += 1

            print(sentence_built + " - " + hangman)
            if self._controller.is_over1(sentence_built, unknown_sentence):
                game_over = True
                print("Congratulations! You won!")
            if self._controller.is_over2(hangman):
                game_over = True
                print("L O S E R !!!")
        return

    def add_sentence(self):
        sentence = input("Please enter a valid sentence: ")
        sentence = sentence.lower()
        sentence = sentence.strip()
        if not self._validator.validate(sentence):
            raise GameException("The sentence is not valid.")
        self._controller.save_to_repo(sentence)

    def ui_start(self):
        while True:
            self.print_menu()

            try:
                n = int(input("Your option is: "))
                if n <= 0 or n > 3:
                    raise ValueError
                if n == 1:
                    self.play_game()
                if n == 2:
                    self.add_sentence()
                if n == 3:
                    return
            except ValueError:
                print("Please enter a valid option.")
            except GameException as ge:
                print(ge.get_message())
