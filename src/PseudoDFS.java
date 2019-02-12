import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

class PseudoDFS
{
  private final boolean[][] visited;
  private final Stack<SimplePoint> reversePostOrder;

  PseudoDFS(Picture picture, boolean verticalSeam)
  {
    //TODO: Is the dimension correct?
    visited = new boolean[picture.height()][picture.width()];
    reversePostOrder = new Stack<>();

    reversePostOrder.push(new SimplePoint(1, picture.height()));
    for (int colIndex = 0; colIndex < picture.width(); ++colIndex)
    {
      for (int rowIndex = 0; rowIndex < picture.height(); ++rowIndex)
      {
        if (!visited[rowIndex][colIndex])
        {
          if (verticalSeam)
          {
            verticalDfs(picture, colIndex, rowIndex);
          }
          else
          {
            horizontalDfs(picture, colIndex, rowIndex);
          }
        }
      }
    }
    reversePostOrder.push(new SimplePoint(0, picture.height()));
  }

  private void verticalDfs(Picture picture, int colIndex, int rowIndex)
  {
    visited[rowIndex][colIndex] = true;

    // below
    if ((rowIndex < (picture.height() - 1)) && !visited[rowIndex + 1][colIndex])
    {
      verticalDfs(picture, colIndex, rowIndex + 1);
    }

    // left-below
    if ((colIndex > 0) && (rowIndex < (picture.height() - 1)) && !visited[rowIndex + 1][colIndex - 1])
    {
      verticalDfs(picture, colIndex - 1, rowIndex + 1);
    }
    
    // right-below
    if ((colIndex < (picture.width() - 1)) && (rowIndex < (picture.height() - 1)) && !visited[rowIndex+1][colIndex + 1])
    {
      verticalDfs(picture, colIndex + 1, rowIndex + 1);
    }

    reversePostOrder.push(new SimplePoint(colIndex, rowIndex));
  }

  private void horizontalDfs(Picture picture, int colIndex, int rowIndex)
  {
    visited[rowIndex][colIndex] = true;

    // right
    if ((colIndex < (picture.width() - 1)) && !visited[rowIndex][colIndex + 1])
    {
      horizontalDfs(picture, colIndex + 1, rowIndex);
    }

    // right-below
    if ((colIndex < (picture.width() - 1)) && (rowIndex < (picture.height() - 1)) && !visited[rowIndex + 1][colIndex + 1])
    {
      horizontalDfs(picture, colIndex + 1, rowIndex + 1);
    }

    // right-up
    if ((colIndex < (picture.width() - 1)) && (rowIndex > 0) && !visited[rowIndex - 1][colIndex + 1])
    {
      horizontalDfs(picture, colIndex + 1, rowIndex - 1);
    }

    reversePostOrder.push(new SimplePoint(colIndex, rowIndex));
  }

  Iterable<SimplePoint> order()
  {
    return reversePostOrder;
  }
}
