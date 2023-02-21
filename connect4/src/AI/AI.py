import random

from src.constants.constants import *
import math
from copy import deepcopy


class AI:
    """
    This class is the implementation for the AI of the game by using the minimax algorythm.
    """
    def __init__(self):
        pass

    @staticmethod
    def evaluate_window(window, piece):
        score = 0
        opponent_piece = CONST_PLAYER_PIECE
        if piece == CONST_PLAYER_PIECE:
            opponent_piece = CONST_AI_PIECE
        if window.count(piece) == 4:
            score += 100
        elif window.count(piece) == 3 and window.count(CONST_EMPTY) == 1:
            score += 5
        elif window.count(piece) == 2 and window.count(CONST_EMPTY) == 2:
            score += 2
        elif window.count(opponent_piece) == 3 and window.count(CONST_EMPTY) == 1:
            score -= 4
        return score

    def score_horizontal(self, board, piece):
        score = 0
        for r in range(CONST_ROW_COUNT):
            row_arr = [int(i) for i in list(board[r, :])]
            for c in range(CONST_COLUMN_COUNT - 3):
                window = row_arr[c:c+4]  # 4 = length of a window
                score += self.evaluate_window(window, piece)
        return score

    def score_vertical(self, board, piece):
        score = 0
        for c in range(CONST_COLUMN_COUNT):
            col_arr = [int(i) for i in list(board[:, c])]
            for r in range(CONST_ROW_COUNT):
                window = col_arr[r: r + 4]
                score += self.evaluate_window(window, piece)
        return score

    def score_positive_slopped_diagonals(self, board, piece):
        score = 0
        for r in range(CONST_ROW_COUNT - 3):
            for c in range(CONST_COLUMN_COUNT - 3):
                window = [board[r+i][c+i] for i in range(4)]
                score += self.evaluate_window(window, piece)
        return score

    def score_negative_slopped_diagonals(self, board, piece):
        score = 0
        for r in range(CONST_ROW_COUNT - 3):
            for c in range(CONST_COLUMN_COUNT - 3):
                window = [board[r + 3 - i][c + i] for i in range(4)]
                score += self.evaluate_window(window, piece)
        return score

    def score_position(self, board, piece):
        score = 0
        # Score center columns - scoring center columns will create more opportunities with the diagonals and the
        # horizontals
        center_arr = [int(i) for i in list(board[:, CONST_COLUMN_COUNT // 2])]  # we get the middle column
        center_count = center_arr.count(piece)  # we get the number of pieces from the middle column
        score += center_count * 6
        # Score horizontal
        score += self.score_horizontal(board, piece)
        # Score vertical
        score += self.score_vertical(board, piece)
        # Score positive slopped diagonals
        score += self.score_positive_slopped_diagonals(board, piece)
        # Score negative slopped diagonals
        score += self.score_negative_slopped_diagonals(board, piece)
        return score

    # The next functions are functions from the game module, adapted for the copied board.

    @staticmethod
    def check_column_for_winning(board, piece):
        for c in range(CONST_COLUMN_COUNT - 3):
            for r in range(CONST_ROW_COUNT):
                if board[r][c] == piece and board[r][c + 1] == piece and board[r][c + 2] == piece and board[r][c + 3] == piece:
                    return True
        return False

    @staticmethod
    def check_row_for_winning(board, piece):
        for r in range(CONST_ROW_COUNT - 3):
            for c in range(CONST_COLUMN_COUNT):
                if board[r][c] == piece and board[r + 1][c] == piece and board[r + 2][c] == piece and board[r + 3][c] == piece:
                    return True
        return False

    @staticmethod
    def check_by_directions(board, r, c, dr, dc, piece):
        for i in range(4):
            if board[r + i * dr][c + i * dc] != piece:
                return False
        return True

    def check_diag_for_winning(self, board, piece):
        for r in range(CONST_ROW_COUNT - 3):
            for c in range(CONST_COLUMN_COUNT - 3):
                if self.check_by_directions(board, r, c, 1, 1, piece):
                    return True

        for r in range(3, CONST_ROW_COUNT):
            for c in range(CONST_COLUMN_COUNT - 3):
                if self.check_by_directions(board, r, c, -1, 1, piece):
                    return True
        return False

    def is_winning(self, board, piece):
        if self.check_row_for_winning(board, piece):
            return True
        if self.check_column_for_winning(board, piece):
            return True
        if self.check_diag_for_winning(board, piece):
            return True
        return False

    @staticmethod
    def is_valid_location(board, col):
        return board[CONST_ROW_COUNT - 1][col] == 0

    def get_all_valid_locations(self, board):
        valid_locations = []
        for c in range(CONST_COLUMN_COUNT):
            if self.is_valid_location(board, c):
                valid_locations.append(c)
        return valid_locations

    def is_terminal_node(self, board):
        return self.is_winning(board, CONST_PLAYER_PIECE) or self.is_winning(board, CONST_AI_PIECE) or len(self.get_all_valid_locations(board)) == 0

    @staticmethod
    def get_next_open_row(board, col):
        for r in range(CONST_ROW_COUNT):
            if board[r][col] == 0:
                return r

    @staticmethod
    def drop_piece_simulation(board, row, col, piece):
        board[row][col] = piece

    def minimax(self, board, depth, alpha, beta, maximizing_player):
        """
        The minimax algorythm for the game Connect4. The function gets as the first parameter a copy of the current
        board of the game. With the helps of this is simulating the tree of choices.
        The depth parameter is an integer which represents how deep in the choice tree the algorythm is going
        Parameters alpha and beta are integers that for the optimization of this algorythm by pruning the leaves of the
        choice tree.
        Alpha is the best choice we have found so far at any point of the path of the maximizer.
        Beta is the best choice we have found so far at any point of the path of the minimizer.
        """
        valid_locations = self.get_all_valid_locations(board)
        # Terminal node is: player winning, AI winning or all the pieces in the game are used.
        is_terminal = self.is_terminal_node(board)

        if depth == 0 or is_terminal:
            if is_terminal:
                if self.is_winning(board, CONST_AI_PIECE):
                    return None, 1000000000
                elif self.is_winning(board, CONST_PLAYER_PIECE):
                    return None, -1000000000
                else:  # Game is over/ no more valid moves
                    return None, 0
            else:
                return None, self.score_position(board, CONST_AI_PIECE)
        if maximizing_player:
            value = -math.inf
            column = random.choice(valid_locations)
            for col in valid_locations:
                row = self.get_next_open_row(board, col)
                board_copy = deepcopy(board)
                self.drop_piece_simulation(board_copy, row, col, CONST_AI_PIECE)
                new_score = self.minimax(board_copy, depth-1, alpha, beta, False)[1]
                if new_score > value:
                    value = new_score
                    column = col
                alpha = max(alpha, value)
                if alpha >= beta:
                    break
            return column, value
        else:  # minimizing player
            value = math.inf
            column = random.choice(valid_locations)
            for col in valid_locations:
                row = self.get_next_open_row(board, col)
                board_copy = deepcopy(board)
                self.drop_piece_simulation(board_copy, row, col, CONST_PLAYER_PIECE)
                new_score = self.minimax(board_copy, depth-1, alpha, beta, True)[1]
                if new_score < value:
                    value = new_score
                    column = col
                beta = min(beta, value)
                if alpha >= beta:
                    break
            return column, value
