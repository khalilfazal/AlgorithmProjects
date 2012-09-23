from operator import attrgetter

class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __str__(self):
        return "(%d, %d)" % (self.x, self.y)

    def __repr__(self):
        return self.__str__()

    @staticmethod
    def sort(point):
        return sorted(point, key=attrgetter('x', 'y'))
