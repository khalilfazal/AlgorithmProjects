from point import Point
from operator import attrgetter


# Thee Points: O, A, B
# Two Vectors: OA, OB
# If the 2D cross product is greater than zero, the vectors are oriented counterclockwise.
def ccw (o, a, b):
    return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x) > 0

def traverse(points):
    output = []

    for p in points:
        while len(output) > 1 and not ccw(output[-2], output[-1], p):
            output.pop()
        output.append(p)

    return output

def monotoneChain(points):
    points = sorted(points, key = attrgetter('x', 'y'))
    lower = upper = []

    for p in points:
        while len(lower) > 1 and not ccw(lower[-2], lower[-1], p):
            lower.pop()
        lower.append(p)

    for p in reversed(points):
        while len(upper) > 1 and not ccw(upper[-2], upper[-1], p):
            upper.pop()
        upper.append(p)

    return lower[:-1] + upper[:-1]
