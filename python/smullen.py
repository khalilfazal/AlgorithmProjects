from point import Point
from operator import attrgetter

def isConcaveUp(p1, p2, p3):
    dy2 = p3.y - p2.y
    dy1 = p2.y - p1.y

    return dy2 - dy1 >= 0

def smullen(points):
    points = Point.sort(points)
    upper = lower = []

    for i in points:
        upper.append(i)
        if len(upper) > 2 and not isConcaveUp (upper[-3], upper[-2], upper[-1]):
            del(upper[-2])

    for i in reversed(points):
        lower.append(i)
        if len(lower) > 2 and isConcaveUp (lower[-3], lower[-2], lower[-1]):
            del(lower[-2])

    return upper + lower
