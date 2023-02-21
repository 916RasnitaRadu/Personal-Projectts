import unittest

from src.board.board import Board


class BoardTest(unittest.TestCase):
    def setUp(self) -> None:
        self._board = Board(6, 7)
        self._board.set_elem(0, 0, 0)
        self._board.set_elem(0, 1, 1)
        self._board.set_elem(0, 2, 2)
        self._board.set_elem(0, 3, 3)
        self._board.set_elem(0, 4, 4)
        self._board.set_elem(0, 5, 5)
        self._board.set_elem(0, 6, 6)

    def tearDown(self) -> None:
        pass

    def test_get_elem(self):
        elem = self._board.get_elem(0, 1)
        self.assertEqual(elem, 1)

    def test_set_elem(self):
        self._board.set_elem(4,5, 69)
        elem = self._board.get_elem(4, 5)
        self.assertEqual(elem, 69)

    def test_get_row(self):
        row = list(self._board.get_row(0))
        self.assertEqual(row, [0, 1, 2, 3, 4, 5, 6])

    def test_get_column(self):
        col = list(self._board.get_column(1))
        self.assertEqual(col, [1, 0, 0, 0, 0, 0])

    def test_rows(self):
        rows = self._board.rows
        self.assertEqual(rows, 6)

    def test_columns(self):
        columns = self._board.columns
        self.assertEqual(columns, 7)

