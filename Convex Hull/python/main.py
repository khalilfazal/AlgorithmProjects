from pylab              import plot, ginput, scatter, show
from matplotlib.pyplot  import xlim, ylim
from sys                import maxint
from point              import Point
from monotoneChain      import monotoneChain

plot()

# Left Click:   Choose Point
# Middle Click: Submit
# Right Click:  Delete Last Point

points = ginput(
    n = maxint,
    timeout = 0,
    show_clicks = True
)

# Show chosen points
scatter(
    [p[0] for p in points],
    [p[1] for p in points],
    marker = '+',
    c = 'r'
)

hull = monotoneChain(map(lambda (x, y): Point(x, y), points))

# Connect the last point on the hull with the first point
if len(hull) > 0:
    hull = hull + [hull[0]]

# Draw the hull
plot(
    [h.x for h in hull],
    [h.y for h in hull]
)

# Reset the scale
xlim(0,1)
ylim(0,1)

show()
