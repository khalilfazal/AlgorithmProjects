from point import Point

# Thee Points: O, A, B
# Two Vectors: OA, OB
# If the 2D cross product is less than or equal to zero, the vectors are oriented clockwise.
def cw (o, a, b):
    return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x) <= 0

def traverse(points):
    output = []

    for p in points:
        while len(output) > 1 and cw(output[-2], output[-1], p):
            output.pop()
        output.append(p)

    return output

def monotoneChain(points):
    points = Point.sort(points)

    l = traverse(points)
    u = traverse(reversed(points))

    return l[:-1] + u[:-1]
