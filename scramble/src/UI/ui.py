from copy import deepcopy


class UI:
    def __init__(self, controller, validator):
        self._controller = controller
        self._validator = validator
        self._undo = []

    @staticmethod
    def print_intro():
        print("\nWelcome to scramble!")
        print("If you want to exit game just write <<exit>>.\n")

    @staticmethod
    def wrong_undo():
        print("There aren't any operations to undo.")

    @staticmethod
    def wrong_input():
        print("Wrong input.")

    def ui_swap_letters(self, params_list, unknwn_sentence):
        if not self._validator.validate(params_list, unknwn_sentence):
            raise KeyError
        word1 = int(params_list[0])
        letter1 = int(params_list[1])
        word2 = int(params_list[3])
        letter2 = int(params_list[4])

        updt_sentence = self._controller.swap_letters(word1, letter1, word2, letter2, unknwn_sentence)
        return updt_sentence

    def ui_start(self):
        unknwn_sentence, unknwn_sentence_scrambled = self._controller.get_scrambled_sentence()
        self._undo.append(deepcopy(unknwn_sentence_scrambled))

        score = self._controller.compute_score(unknwn_sentence)
        self.print_intro()
        print(unknwn_sentence_scrambled + "\t[score is " + str(score) + "]")

        game_over = False
        while game_over is False:
            cmd_line = input("Please enter a valid command: ")
            cmd_word, cmd_params = self._controller.split_command(cmd_line)
            params_list = self._controller.split_params(cmd_params)

            try:
                if cmd_word == "exit":
                    return
                elif cmd_word == "swap":
                    unknwn_sentence_scrambled = self.ui_swap_letters(params_list, unknwn_sentence_scrambled)
                    self._undo.append(deepcopy(unknwn_sentence_scrambled))
                    score -= 1
                elif cmd_word == "undo":
                    if len(self._undo) == 1:
                        self.wrong_undo()
                    else:
                        self._undo.pop()
                        unknwn_sentence_scrambled = self._undo[-1]
                else:
                    self.wrong_input()

            except ValueError:
                self.wrong_input()
            except KeyError:
                self.wrong_input()

            print(unknwn_sentence_scrambled + "\t[score is " + str(score) + "]")
            if unknwn_sentence_scrambled == unknwn_sentence:
                game_over = True
                print("You won madafaka!!!")
            if score == 0:
                game_over = True
                print("You lost bro...")
