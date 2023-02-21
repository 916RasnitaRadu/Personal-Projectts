from src.board.board import Board
from src.game.game import Game
from src.ui.ui import UI
from src.AI.AI import AI
from src.gui.gui import GUI
from src.constants.constants import *


def main():
    settings_props = dict()

    with open("settings.txt", 'r') as f:
        lines = f.readlines()
        for line in lines:
            line = line.strip()
            key, value = line.split("=")
            key = key.strip()
            value = value.strip()
            settings_props[key] = value

    board = Board(CONST_ROW_COUNT, CONST_COLUMN_COUNT)
    game_handler = Game(board)

    if settings_props["AI"].lower() == "true":
        ai = AI()
    else:
        ai = None

    if settings_props["UI"].lower() == "ui":
        ui = UI(board, game_handler, ai)
        ui.ui_start()
    elif settings_props["UI"].lower() == "gui":
        gui = GUI(board, game_handler, ai)
        gui.gui_start()


if __name__ == '__main__':
    main()
