from Repository.repo import Repository
from Controller.controller import Controller
from UI.ui import UI
from Validator.validator import Validator

if __name__ == '__main__':
    file_path = "sentences.txt"
    game_repo = Repository(file_path)

    game_controller = Controller(game_repo)

    validator = Validator(game_repo)
    ui = UI(game_controller, validator)

    ui.ui_start()
