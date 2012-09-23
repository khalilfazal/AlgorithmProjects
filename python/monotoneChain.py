from point import Point

def cw (o, a, b):
    return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x) <= 0

def monotoneChain(point):
    point = Point.sort(point)

    l = []
    u = []

    for p in point:
        while len(l) > 1 and cw(l[-2], l[-1], p):
            l.pop()
        l.append(p)

    for p in reversed(point):
        while len(u) > 1 and cw(u[-2], u[-1], p):
            u.pop()
        u.append(p)

    return l[:-1] + u[:-1]

print monotoneChain([Point(i / 10, i % 10) for i in range(100)])
