from src.Repository.repo import Repository
from src.Controller.controller import Controller
from src.UI.ui import UI
from src.Validator.validator import Validator

if __name__ == '__main__':
    file_path = "sentences.txt"
    game_repo = Repository(file_path)

    game_controller = Controller(game_repo)
    validator = Validator()

    ui = UI(game_controller, validator)

    ui.ui_start()

