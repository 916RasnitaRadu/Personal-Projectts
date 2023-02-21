import numpy
from copy import deepcopy


class Board:
    """
    A class that implements the board for the game.
    The board is a matrix using numpy of row_count x column_count.
    """
    def __init__(self, row_count, column_count):
        self._board = numpy.zeros((row_count, column_count))
        self._rows = row_count
        self._columns = column_count

    def get_row(self, r):
        return self._board[r]

    def get_column(self, c):
        return self._board[:, c]

    def get_elem(self, r, c):
        return self._board[r][c]

    def set_elem(self, r, c, entity):
            self._board[r][c] = entity

    def get_flipped_board(self):
        # A function that returns the board flipped (the matrix flipped)
        return numpy.flip(self._board, 0)

    @property
    def rows(self):
        return self._rows

    @property
    def columns(self):
        return self._columns

    @property
    def __copy__(self):
        # A function that creates a copy for the board
        return deepcopy(self._board)


