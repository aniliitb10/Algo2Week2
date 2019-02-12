import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

class PseudoSP
{
  private static final SimplePoint topPoint = Helper.topPoint;

  private final double[][] distTo;
  private final SimplePoint[][] pointTo;
  private final int[][] rgb;
  private final Picture picture;

  private double distToLastPoint = Double.POSITIVE_INFINITY;
  private SimplePoint pointToLastPoint = null;

  PseudoSP(Picture picture, boolean verticalSeam)
  {
    distTo = new double[picture.height()][picture.width()];
    pointTo = new SimplePoint[picture.height()][picture.width()];
    rgb = new int[picture.height()][picture.width()];
    this.picture = picture;

    for (int rowIndex = 0; rowIndex < picture.height(); ++rowIndex)
    {
      for (int colIndex = 0; colIndex < picture.width(); ++colIndex) // colIndex starts from 1
      {
        distTo[rowIndex][colIndex] = Double.POSITIVE_INFINITY;
      }
    }

    if (verticalSeam)
    {
      for (int colIndex = 0; colIndex < picture.width(); ++colIndex)
      {
        distTo[0][colIndex] = 0;
        pointTo[0][colIndex] = topPoint;
      }
    }
    else
    {
      for (int rowIndex = 0; rowIndex < picture.height(); ++rowIndex)
      {
        distTo[rowIndex][0] = 0;
        pointTo[rowIndex][0] = topPoint;
      }
    }

    storeRGBValues();

    PseudoDFS dfs = new PseudoDFS(picture, verticalSeam);
    for (SimplePoint point : dfs.order())
    {
      if (verticalSeam)
      {
        relaxVerticalSeam(point);
      }
      else
      {
        relaxHorizontalSeam(point);
      }
    }
  }

  private void relaxVerticalSeam(final SimplePoint point)
  {
    // check if the special top point
    if (point.equals(topPoint))
    {
      for (int col = 0; col < picture.width(); ++col)
      {
        relaxVerticalSeam(new SimplePoint(col, 0));
      }
      return;
    }

    final int colIndex = point.colIndex;
    final int rowIndex = point.rowIndex;
    final double pointDist = distTo[rowIndex][colIndex];

    // below
    if ((rowIndex < (picture.height() - 1)))
    {
      updatePoint(point, pointDist, colIndex, rowIndex + 1);
    }

    // left-below
    if ((colIndex > 0) && (rowIndex < (picture.height() - 1)))
    {
      updatePoint(point, pointDist, colIndex - 1, rowIndex + 1);
    }

    // right-below
    if ((colIndex < (picture.width() - 1)) && (rowIndex < (picture.height() - 1)))
    {
      updatePoint(point, pointDist, colIndex + 1, rowIndex + 1);
    }

    // for bottom row to reach out to special bottom point
    if (rowIndex == picture.height() - 1)
    {
      if (distToLastPoint > pointDist)
      {
        distToLastPoint = pointDist;
        pointToLastPoint = point;
      }
    }
  }

  private void relaxHorizontalSeam(SimplePoint point)
  {
    // check if the special left-most point
    if (point.equals(topPoint))
    {
      for (int row = 0; row < picture.height(); ++row)
      {
        relaxHorizontalSeam(new SimplePoint(0, row));
      }
      return;
    }

    int colIndex = point.colIndex;
    int rowIndex = point.rowIndex;
    final double pointDist = distTo[rowIndex][colIndex];

    // right
    if (colIndex < (picture.width() - 1))
    {
      updatePoint(point, pointDist, colIndex + 1, rowIndex);
    }

    // right-up
    if ((colIndex < (picture.width() - 1)) && (rowIndex > 0))
    {
      updatePoint(point, pointDist, colIndex + 1, rowIndex - 1);
    }
    
    // right-below
    if ((colIndex < (picture.width() - 1)) && (rowIndex < (picture.height() - 1)))
    {
      updatePoint(point, pointDist, colIndex + 1, rowIndex + 1);
    }

    // for right-most row to reach out to special bottom point
    if (colIndex == picture.width() - 1)
    {
      if (distToLastPoint > pointDist)
      {
        distToLastPoint = pointDist;
        pointToLastPoint = point;
      }
    }
  }
  
  private void updatePoint(SimplePoint source, double sourceDist, int colIndex, int rowIndex)
  {
    double energy = Helper.energy(picture, rgb, colIndex, rowIndex);
    if (distTo[rowIndex][colIndex] > sourceDist + energy)
    {
      distTo[rowIndex][colIndex] = sourceDist + energy;
      pointTo[rowIndex][colIndex] = source;
    }
  }
  
  int[] seamPoints(boolean verticalSeam)
  {
    Stack<SimplePoint> points = new Stack<>();
    SimplePoint point = pointToLastPoint;
    
    while (!point.equals(topPoint))
    {
      points.push(point);
      point = pointTo[point.rowIndex][point.colIndex];
    }

    int[] seam = new int[points.size()];
    int indexCounter = 0;

    if (verticalSeam)
    {
      for (SimplePoint eachPoint : points)
      {
        seam[indexCounter++] = eachPoint.colIndex;
      }
    }
    else // horizontal seam
    {
      for (SimplePoint eachPoint : points)
      {
        seam[indexCounter++] = eachPoint.rowIndex;
      }
    }

    return seam;
  }

  private void storeRGBValues()
  {
    for (int rowIndex = 0; rowIndex < picture.height(); ++rowIndex)
    {
      for (int colIndex = 0; colIndex < picture.width(); ++colIndex)
      {
        rgb[rowIndex][colIndex] = picture.getRGB(colIndex, rowIndex);
      }
    }
  }
}
