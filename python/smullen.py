from point import Point
from operator import attrgetter

def concavity(point1, point2, point3):
    dy1 = point2.y - point1.y
    dy2 = point3.y - point2.y

    return dy2 - dy1

def smullen(points):
    points = sorted(points, key = attrgetter('x', 'y'))
    upper = lower = points

    for i in range(0, len(points)):
        if i + 2 < len(upper):
            if concavity(upper[i], upper[i + 1], upper[i + 2]) < 0:
                del upper[i + 1]

        if i + 2 < len(lower):
            if concavity(lower[i], lower[i + 1], lower[i + 2]) > 0:
                del lower[i + 1]

    return upper + lower
