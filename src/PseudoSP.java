import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

class PseudoSP
{
  private final double[][] distTo;
  private final SimplePoint[][] pointTo;
  private final Picture picture;
  
  PseudoSP(Picture picture, boolean verticalSeam)
  {
    distTo = new double[picture.height() + 1][picture.width()]; // additional row for special points
    pointTo = new SimplePoint[picture.height() + 1][picture.width()]; // additional row for special points
    this.picture = picture;
    
    for (int rowIndex = 1; rowIndex < distTo.length; ++rowIndex)
    {
      for (int colIndex = 0; colIndex < distTo.length; ++colIndex) // colIndex starts from 1
      {
        distTo[colIndex][rowIndex] = Double.POSITIVE_INFINITY;
      }
    }

    distTo[0][picture.height()] = 0; // top special point, it is the source!
    distTo[1][picture.height()] = Double.POSITIVE_INFINITY; // bottom special point

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

  private void relaxVerticalSeam(SimplePoint point)
  {
    int colIndex = point.colIndex;
    int rowIndex = point.rowIndex;
    final double pointDist = distTo[colIndex][rowIndex];

    // check if the special top point
    if (point.rowIndex == picture.height() && point.colIndex == 0)
    {
      for (int col = 0; col < picture.width(); ++col)
      {
        relaxVerticalSeam(new SimplePoint(col, 0));
      }
      return;
    }

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
      updatePoint(point, pointDist, 1, picture.height());
    }
  }

  private void relaxHorizontalSeam(SimplePoint point)
  {
    int colIndex = point.colIndex;
    int rowIndex = point.rowIndex;
    final double pointDist = distTo[colIndex][rowIndex];

    // check if the special left-most point
    if (point.rowIndex == picture.height() && point.colIndex == 0)
    {
      for (int row = 0; row < picture.height(); ++row)
      {
        relaxHorizontalSeam(new SimplePoint(0, row));
      }
      return;
    }

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
      updatePoint(point, pointDist, 1, picture.height());
    }
  }
  
  private void updatePoint(SimplePoint source, double sourceDist, int colIndex, int rowIndex)
  {
    double energy = Helper.energy(picture, colIndex, rowIndex);
    if (distTo[colIndex][rowIndex] > sourceDist + energy)
    {
      distTo[colIndex][rowIndex] = sourceDist + energy;
      pointTo[colIndex][rowIndex] = source;
    }
  }
  
  int[] seamPoints(boolean verticalSeam)
  {
    Stack<SimplePoint> points = new Stack<>();
    SimplePoint point = pointTo[1][picture.height()];
    
    while (pointTo[point.colIndex][point.rowIndex] != null)
    {
      points.push(point);
      point = pointTo[point.colIndex][point.rowIndex];
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
}
