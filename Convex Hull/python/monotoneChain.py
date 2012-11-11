from point import Point
from operator import attrgetter


# Thee Points: O, A, B
# Two Vectors: OA, OB
# If the 2D cross product is greater than zero, the vectors are oriented counterclockwise.
def ccw (o, a, b):
    return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x) > 0

def monotoneChain(points):
    points = Point.sort(points)
    hull = []

    for p in points + points[::-1]:
        while len(hull) > 1 and not ccw(hull[-2], hull[-1], p):
            hull.pop()
        hull.append(p)

    return hull
