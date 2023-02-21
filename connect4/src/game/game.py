class Game:
    """
    A class that handles the gameplay, the changes done on the board and checking if the players win or not.
    """
    def __init__(self, board):
        self._board = board
        self._rows = self._board.rows
        self._columns = self._board.columns

    def get_next_open_row(self, col):
        # A function that returns the next available row for a given column(i.e. the element in the matrix is 0)
        for r in range(self._rows):
            if self._board.get_elem(r, col) == 0:
                return r

    def is_valid_location(self, col):
        # A function that checks if a location is valid(i.e. the element from the given column and the last row is zero)
        return self._board.get_elem(self._rows - 1, col) == 0

    def get_all_valid_locations(self):
        # A function that uses the function from above to return a list of all the valid locations.
        valid_locations = []
        for c in range(self._columns):
            if self.is_valid_location(c):
                valid_locations.append(c)
        return valid_locations

    def drop_piece(self, col, piece):
        # A function that finds the next open row on a column and drops a piece in that place on the board
        # i.e. sets the element from the (row, col) in the matrix to either 1 or 2
        row = self.get_next_open_row(col)
        self._board.set_elem(row, col, piece)

    def check_row_for_winning(self, piece):
        # A function that checks if there are 4 same pieces in a row on horizontal
        for c in range(self._columns-3):
            for r in range(self._rows):
                listuta = self._board.get_row(r)
                lista = listuta[c:c+4]
                if len(set(lista)) == 1 and lista[0] == piece:
                    return True
        return False

    def check_column_for_winning(self, piece):
        # A function that checks if there are 4 same pieces in a row on vertical
        for r in range(self._rows - 3):
            for c in range(self._columns):
                listuta = self._board.get_column(c)
                lista = listuta[r:r + 4]
                if len(set(lista)) == 1 and lista[0] == piece:
                    return True
        return False

    def check_by_directions(self, r, c, dr, dc, piece):
        # A function that checks if the all elements in a sequence after some given directions are equal.
        for i in range(4):
            if self._board.get_elem(r + i*dr, c + i*dc) != piece:
                return False
        return True

    def check_diag_for_winning(self, piece):
        # # A function that checks if there are 4 same pieces in a row on diagonals using the above function.
        for r in range(self._rows - 3):
            for c in range(self._columns - 3):
                if self.check_by_directions(r, c, 1, 1, piece):
                    return True

        for r in range(3, self._rows):
            for c in range(self._columns - 3):
                if self.check_by_directions(r, c, -1, 1, piece):
                    return True
        return False

    def is_winning(self, piece):
        """
        A function that returns True if the last move was a winning move or False in the other case by using the above
        defined functions
        """
        if self.get_all_valid_locations() == []:
            return 5
        if self.check_row_for_winning(piece):
            return True
        if self.check_column_for_winning(piece):
            return True
        if self.check_diag_for_winning(piece):
            return True
        return False
