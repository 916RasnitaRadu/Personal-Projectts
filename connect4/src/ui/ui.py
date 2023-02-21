import math
import random
from src.constants.constants import *
from src.exceptions.exception import *


class UI:
    def __init__(self, board, game_handler, ai):
        self.board = board
        self._rows = self.board.rows
        self._columns = self.board.columns
        self._game_handler = game_handler
        if ai is not None:
            self._ai = ai
        else:
            self._ai = None

    @staticmethod
    def print_start_text():
        print("Welcome to CONNECT 4! It's going to be BLANA-BOMBA!")
        print("10 - exit code")

    def print_board(self):
        print(self.board.get_flipped_board())

    def ui_is_valid_location(self, col):
        return self._game_handler.is_valid_location(col)

    def ui_drop_piece(self, col, piece):
        self._game_handler.drop_piece(col, piece)

    def ui_is_winning(self, piece):
        return self._game_handler.is_winning(piece)

    def ui_start(self):
        self.print_start_text()

        game_over = False
        choice = [0, 1]
        turn = random.choice(choice)

        if self._ai is None:
            while not game_over:
                self.print_board()
                try:
                    if turn == CONST_PLAYER_TURN:
                        col = int(input("Player 1 select a column (0, " + str(self._columns-1) + ") : "))
                        if col == 10:
                            return
                        if col > self._columns - 1:
                            raise ValueError
                        if self.ui_is_valid_location(col):
                            piece = 1
                            self.ui_drop_piece(col, piece)

                            if self.ui_is_winning(piece):
                                print("PLAYER 1 WINS! CONGRATS!")
                                game_over = True
                            if self.ui_is_winning(piece) == 5:
                                print("REMIZA FRAIERILOR!")
                                game_over = True
                        else:
                            raise GameException("This row is full.")

                    else:
                        col = int(input("Player 2 select a column: "))
                        if col == 10:
                            return
                        if col > self._columns - 1:
                            raise ValueError
                        if self.ui_is_valid_location(col):
                            piece = 2
                            self.ui_drop_piece(col, piece)

                            if self.ui_is_winning(piece):
                                print("PLAYER 2 WINS! CONGRATS!")
                                game_over = True
                            if self.ui_is_winning(piece) == 5:
                                print("REMIZA FRAIERILOR!")
                                game_over = True
                        else:
                            raise GameException("This row is full.")

                    turn += 1
                    turn = turn % 2
                    self.print_board()
                except ValueError:
                    print("The option you entered is not available.")
                except GameException as ge:
                    print(ge.get_message())
        else:
            while not game_over:
                try:
                    if turn == CONST_PLAYER_TURN:
                        col = int(input("Player select a column (0, " + str(self._columns-1) + ") : "))
                        if col == 10:
                            return
                        if col > self._columns - 1:
                            raise ValueError
                        if self.ui_is_valid_location(col):
                            piece = CONST_PLAYER_PIECE
                            self.ui_drop_piece(col, piece)

                            if self.ui_is_winning(piece):
                                print("Y O U   W O N! C O N G R A T S!")
                                game_over = True
                            if self.ui_is_winning(piece) == 5:
                                print("REMIZA FRAIERILOR")
                                game_over = True
                        else:
                            raise GameException("This row is full.")

                    else:
                        print("The computer picked: ")
                        board = self.board.__copy__
                        col, minimax_score = self._ai.minimax(board, 5, -math.inf, math.inf, True)
                        if self.ui_is_valid_location(col):
                            piece = CONST_AI_PIECE
                            self.ui_drop_piece(col, piece)

                            if self.ui_is_winning(piece):
                                print("C O M P U T E R   W I N S! L O S E R!")
                                game_over = True
                            if self.ui_is_winning(piece) == 5:
                                print("REMIZA FRAIERILOR")
                                game_over = True
                        else:
                            raise GameException("This row is full.")

                    turn += 1
                    turn = turn % 2
                    self.print_board()
                except ValueError:
                    print("The option you entered is not available.")
                except GameException as ge:
                    print(ge.get_message())
