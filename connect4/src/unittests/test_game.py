import unittest
from src.game.game import Game
from src.board.board import Board


class GameTest(unittest.TestCase):
    def setUp(self) -> None:
        board = Board(6, 7)
        self._game_handler = Game(board)

    def tearDown(self) -> None:
        pass

    def test_is_valid_location(self):
        self.assertTrue(self._game_handler.is_valid_location(5))

    def test_get_all_valid_locations(self):
        valid_locations = self._game_handler.get_all_valid_locations()
        self.assertEqual(valid_locations, [0, 1, 2, 3, 4, 5, 6])

    def test_get_next_open_row(self):
        row = self._game_handler.get_next_open_row(4)
        self.assertEqual(row, 0)

    def test_drop_piece(self):
        self._game_handler.drop_piece(1, 1)
        row = self._game_handler.get_next_open_row(1)
        self.assertEqual(row, 1)

    def test_check_row_for_winning(self):
        self._game_handler.drop_piece(0, 1)
        self._game_handler.drop_piece(1, 1)
        self._game_handler.drop_piece(2, 1)
        self._game_handler.drop_piece(3, 1)
        self.assertTrue(self._game_handler.check_row_for_winning(1))

    def test_check_column_for_winning(self):
        self._game_handler.drop_piece(0, 1)
        self._game_handler.drop_piece(0, 1)
        self._game_handler.drop_piece(0, 1)
        self._game_handler.drop_piece(0, 1)
        self.assertTrue(self._game_handler.check_column_for_winning(1))

    def test_check_diagonals_for_winning(self):
        self._game_handler.drop_piece(0, 1)
        self._game_handler.drop_piece(1, 2)
        self._game_handler.drop_piece(1, 1)
        self._game_handler.drop_piece(2, 2)
        self._game_handler.drop_piece(2, 2)
        self._game_handler.drop_piece(2, 1)
        self._game_handler.drop_piece(3, 2)
        self._game_handler.drop_piece(3, 2)
        self._game_handler.drop_piece(3, 2)
        self._game_handler.drop_piece(3, 1)
        self.assertTrue(self._game_handler.check_diag_for_winning(1))

    def test_check_by_directions(self):
        self._game_handler.drop_piece(0, 1)
        self._game_handler.drop_piece(1, 2)
        self._game_handler.drop_piece(1, 1)
        self._game_handler.drop_piece(2, 2)
        self._game_handler.drop_piece(2, 2)
        self._game_handler.drop_piece(2, 1)
        self._game_handler.drop_piece(3, 2)
        self._game_handler.drop_piece(3, 2)
        self._game_handler.drop_piece(3, 2)
        self._game_handler.drop_piece(3, 1)
        self.assertTrue(self._game_handler.check_by_directions(0, 0, 1, 1, 1))

    def test_is_winning(self):
        self._game_handler.drop_piece(0, 1)
        self._game_handler.drop_piece(0, 1)
        self._game_handler.drop_piece(0, 1)
        self._game_handler.drop_piece(0, 1)
        self.assertTrue(self._game_handler.is_winning(1))
